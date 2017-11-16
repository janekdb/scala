import scala.tools.nsc.doc.model._
import scala.tools.nsc.doc.base.comment._
import scala.tools.partest.ScaladocModelTest

// Test with:
// partest --verbose --srcpath scaladoc test/scaladoc/run/tables-warnings.scala

object Test extends ScaladocModelTest {

  import access._

  override def resourceFile = "tables-warnings.scala"

  def scaladocSettings = ""

  def testModel(rootPackage: Package): Unit = {

    val base = rootPackage._package("scala")._package("test")._package("scaladoc")._package("tables")._package("warnings")

    def withComment(commentNames: String*)(test: Comment => Unit) = {
      commentNames foreach {
        commentName =>
          val comment = getComment(commentName, base)
          test(comment)
      }
    }

    /* Compact table creation */

    def pt(content: String): Paragraph = Paragraph(Text(content))

    def c(contents: String*): Cell = Cell(contents.toList.map(pt))

    def r(contents: String*): Row = Row(contents.toList.map(content => c(content)))

    withComment("TableEndMissing") { comment =>

      val caption = Caption(pt("Unclosed Table\n") :: Nil)
      assertTableEquals(Table(None, Some(caption), Nil), comment.body)
    }

    withComment("CaptionsAtMostOne") { comment =>

      val caption = Caption(pt("A") :: Nil)
      assertTableEquals(Table(None, Some(caption), Nil), comment.body)
    }

    withComment("CaptionsIsBeforeHeader") { comment =>

      val caption = Caption(pt("Then Caption") :: Nil)
      val header = r("Header")

      assertTableEquals(Table(Some(header), Some(caption), Nil), comment.body)
    }

    withComment("CaptionsIsBeforeCells") { comment =>

      val caption = Caption(pt("Then Caption") :: Nil)

      val row = r("Cell")
      val rows = row :: Nil
      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("HeadersAtMostOne") { comment =>

      val header = r("One header good")

      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersIsBeforeCells") { comment =>

      val header = r("Then Header")

      val row = r("Cell")
      val rows = row :: Nil
      assertTableEquals(Table(Some(header), None, rows), comment.body)
    }

    withComment("RowCountsCellsEqualsHeader") { comment =>

      val header = r("h1", "h2")

      val row = r("c")
      val rows = row :: Nil
      assertTableEquals(Table(Some(header), None, rows), comment.body)
    }

    withComment("RowCountsAllRowsSameSize") { comment =>

      val row1 = r("a")
      val row2 = Row(c("b1") :: c("b2a", "b2b") :: c("b3") :: Nil)
      val row3 = r("c1", "c2")
      val rows = row1 :: row2 :: row3 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("PrematureEndOfText") { comment =>

      val row = r("cell")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("MissingTableMarkAfterCellClose") { comment =>

      val row = r("Orange")
      val rows = row :: Nil
      // The body is table, paragraph but don't want to test the paragraph parsing
      // that happens after the table parsing bails out following the unexpected
      // content.
      val tableBlock = comment.body.blocks(0)
      assertTableEquals(Table(None, None, rows), tableBlock)
    }

    withComment("CaptionPrematureEndOfText") { comment =>
      val caption = Caption(pt("Cappy Caption") :: Nil)
      assertTableEquals(Table(None, Some(caption), Nil), comment.body)
    }
  }

  private def getComment(traitName: String, containingPackage: Package): Comment = {
    containingPackage._trait(traitName).comment.get
  }

  private def assertTableEquals(expectedTable: Table, actualBody: Body): Unit = {
    val expectedBody = Body(List(expectedTable))
    assert(expectedBody == actualBody, s"Expected: $expectedBody, Actual: $actualBody")
  }

  private def assertTableEquals(expectedTable: Table, actualBlock: Block): Unit = {
    assert(expectedTable == actualBlock, s"Expected: $expectedTable, Actual: $actualBlock")
  }
}