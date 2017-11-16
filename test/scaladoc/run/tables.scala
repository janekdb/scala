import scala.tools.nsc.doc.model._
import scala.tools.nsc.doc.base.comment._
import scala.tools.partest.ScaladocModelTest

// Test with:
// partest --verbose --srcpath scaladoc test/scaladoc/run/tables.scala

object Test extends ScaladocModelTest {

  import access._

  override def resourceFile = "tables.scala"

  def scaladocSettings = ""

  def testModel(rootPackage: Package): Unit = {

    val base = rootPackage._package("scala")._package("test")._package("scaladoc")._package("tables")

    val allTests = true
    val whitelist = Set[String]()
    val blacklist = Set[String]()
    val whitelistPrefix: Option[String] = None
    val printCommentName = false

    def includeTest(commentName: String) = {
      val whitelisted = whitelist(commentName) || whitelistPrefix.map(commentName startsWith _).getOrElse(false)
      (allTests && !blacklist(commentName)) || whitelisted
    }

    def withComment(commentNames: String*)(test: Comment => Unit) = {
      commentNames foreach {
        commentName =>
          if (includeTest(commentName)) {
            if (printCommentName) {
              println(commentName)
            }
            val comment = getComment(commentName, base)
            test(comment)
          }
      }
    }

    /* Compact table creation */

    def pt(content: String): Paragraph = Paragraph(Text(content))

    def c(contents: String*): Cell = Cell(contents.toList.map(pt))

    def ci(content: Inline): Cell = Cell(Paragraph(content) :: Nil)

    /* None transforms to an empty block list */
    def r(contents: Any*): Row = {
      val cells = contents.toList.map {
        case x: String => c(x)
        case None => Cell(Nil)
      }
      Row(cells)
    }

    withComment("Empty") { comment =>
      assertTableEquals(Table(None, None, Nil), comment.body)
    }

    withComment("CellsA") { comment =>

      val row = r("Orange")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsB") { comment =>

      val row = r("Orange", "Apple")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsC") { comment =>

      val cell1 = ci(Chain(List(Text("Oranges "), Underline(Text("and")), Text(" Aubergines"))))

      val cell2 = ci(Chain(List(Text("Peaches "), Monospace(Text("or")), Text(" Pears"))))

      val row = Row(cell1 :: cell2 :: Nil)
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsD") { comment =>

      val row = r("Orange", "Apple")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsE") { comment =>

      val cell1 = ci(Chain(List(Text("Oranges "), Underline(Text("and")), Text(" Aubergines"))))

      val cell2 = ci(Chain(List(Text("Peaches "), Monospace(Text("or")), Text(" Pears"))))

      val row = Row(cell1 :: cell2 :: Nil)
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    {
      val newRowConditionOneCellExpected = {
        val row1 = r("row-1")
        val row2 = r("row-2")
        val rows = row1 :: row2 :: Nil
        Table(None, None, rows)
      }

      withComment("CellsMinimalCellMarks", "CellsAlternativeCellMarksA") { comment =>
        assertTableEquals(newRowConditionOneCellExpected, comment.body)
      }

      withComment("CellsAlternativeCellMarksB", "CellsAlternativeCellMarksC") { comment =>
        assertTableEquals(newRowConditionOneCellExpected, comment.body)
      }
    }

    withComment("CellsInferredNewRowTwoCellsA", "CellsInferredNewRowTwoCellsB") { comment =>
      val row1 = r("Orange", "Apple")
      val row2 = r("Bread", "Pie")
      val row3 = r("Butter", "Ice cream")
      val rows = row1 :: row2 :: row3 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsMarkerRecalculatedAfterInferredNewRow") { comment =>
      val row1 = r("A", "B", "C")
      val row2 = r(" D|D ", " E|E ", " F|F ")
      val rows = row1 :: row2 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("TrailingCellsEmpty") { comment =>
      val row1 = r("Plum", None)
      val row2 = r("Fig", None)
      val row3 = r("Cherry", None)
      val rows = row1 :: row2 :: row3 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("OpenTrailingCellAndCellTerminatorIsRedefined") { comment =>
      val row = r("Plum", "Fig", "|Cherry", "|")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsMultiLineA") { comment =>

      val row = r("Oranges and\nTangerines")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsMultiLineB") { comment =>

      val cell3 = ci(
        Chain(
          Text("Falcon,\nEagle ") :: Underline(Text("and")) :: Text("\n") :: Monospace(Text("Goose")) :: Nil
        )
      )

      val row = Row(
        c("Oranges and\nTangerines") ::
          c("Transistors") ::
          cell3 ::
          Nil
      )
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsParagraphsA") { comment =>

      val cell = Cell(
        pt("Paragraph 1") :: pt("Paragraph 2") :: Nil
      )

      val row = Row(cell :: Nil)

      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsParagraphsB") { comment =>

      val cell1 = Cell(
        pt("Paragraph 1") :: pt("and paragraph 2") :: Nil
      )

      val cell2 = Cell(
        pt("p1") :: pt("p2") :: pt("p3") :: Nil
      )

      val cell3 = {
        val a = Paragraph(Chain(Text("t1,\nt2 ") :: Underline(Text("and")) :: Nil))
        val b = Paragraph(Monospace(Text("t3")))
        Cell(a :: b :: Nil)
      }

      val cell4 = {
        val a = Paragraph(Text("Line 1\nwith line 2 part of the same paragraph"))
        val b = pt("But line 3 is a new paragraph.")
        Cell(a :: b :: Nil)
      }

      val row = Row(cell1 :: cell2 :: cell3 :: cell4 :: Nil)

      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsSameLineA") { comment =>

      val row = r("Orange", "Apple", "more")

      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsSameLineAExplicitRowEnd") { comment =>

      val row = r("Orange", "Apple", "more")

      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsSameLineB") { comment =>

      val row = r("Bitwise", " | ", "Or")

      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsSameLineBExplicitRowEnd") { comment =>

      val row = r("Bitwise", " | ", "Or")

      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsDeeperMarkA", "CellsDeeperMarkA_ExplicitRowEnd") { comment =>
      val row = r("A", "B")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsDeeperMarkB", "CellsDeeperMarkB_ExplicitRowEnd") { comment =>
      val row = r("C")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsDeeperMarkC", "CellsDeeperMarkC_ExplicitRowEnd") { comment =>
      val row = r("A", "B", "C")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsDeeperMarkD", "CellsDeeperMarkD_ExplicitRowEnd") { comment =>
      val row1 = r("Bitwise", " | ", "Or")
      val row2 = r("Logical", " || ", "Or")
      val rows = row1 :: row2 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsDeeperMarkMultiLineA", "CellsDeeperMarkMultiLineA_ExplicitRowEnd") { comment =>
      val row1 = r("Bitwise OR\nis |", "Logical OR\nis ||.")
      val row2 = r("Logical OR\nis || and\nlogical AND is &&", "Logical NOT\nis !")
      val rows = row1 :: row2 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CellsDeeperMarkMultiLineA_Simplified", "CellsDeeperMarkMultiLineA_Simplified_ExplicitRowEnd") { comment =>
      val row = r("P\nis\nQ", "R\nx")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("HeadersA", "HeadersA_ExplicitRowEnd") { comment =>
      val header = r("Amount")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersB", "HeadersB_ExplicitRowEnd") { comment =>
      val header = r("Amount", "Cost")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersMultiLineA", "HeadersMultiLineA_ExplicitRowEnd") { comment =>
      val header = r("Fruits,\nBeverages and\nVegetables")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersMultiLineB", "HeadersMultiLineB_ExplicitRowEnd") { comment =>

      val cell3 = ci(Chain(List(Italic(Text("Raptors")), Text(",\nand Poultry"))))

      val header = Row(
        c("Fruits,\nBeverages and\nVegetables") ::
          c("Semiconductors") ::
          cell3 ::
          Nil)

      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersSameLineA", "HeadersSameLineA_ExplicitRowEnd") { comment =>
      val header = r("Fruits", "Semiconductors", "Raptors")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersSameLineB", "HeadersSameLineB_ExplicitRowEnd") { comment =>
      val header = r("Wow! ", " ! ", "n!/")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersDeeperMarkA", "HeadersDeeperMarkA_ExplicitRowEnd") { comment =>
      val header = r("A", "B")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersDeeperMarkB", "HeadersDeeperMarkB_ExplicitRowEnd") { comment =>
      val header = r("C")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersDeeperMarkC", "HeadersDeeperMarkC_ExplicitRowEnd") { comment =>
      val header = r("A", "B", "C")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("HeadersDeeperMarkMultiLineA", "HeadersDeeperMarkMultiLineA__ExplicitRowEnd") { comment =>
      val header = r("A\na", "B\nb\nb", "C\nc\nc")
      assertTableEquals(Table(Some(header), None, Nil), comment.body)
    }

    withComment("CellsUsingHeaderMarkdown") { comment =>
      val row = r("A!", "B!!", "!", "!!")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("HeadersUsingCellMarkdown") { comment =>
      val row = r("A|", "B||", "|", "||")
      assertTableEquals(Table(Some(row), None, Nil), comment.body)
    }

    withComment("HeadersParagraphs") { comment =>
      val para2 =
        Paragraph(
          Chain(
            Text("Para ") :: Superscript(Text("2")) :: Text(" has ") :: Subscript(Text("styled")) :: Text(" text.") :: Nil
          )
        )
      val cell1 = Cell(pt("Para 1 has\nthree\nlines.") :: para2 :: Nil)
      val cell2 = c("a b", "c d", "e f", "z")
      val row = Row(cell1 :: cell2 :: Nil)
      assertTableEquals(Table(Some(row), None, Nil), comment.body)
    }

    withComment("CaptionsA") { comment =>

      val caption = Caption(pt("Food Complements") :: Nil)
      val row = r("Lamb", "Mint")
      val rows = row :: Nil
      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("CaptionsB") { comment =>

      val para1 = Paragraph(Chain(List(Text("See "), Monospace(Text("CaptionsA")), Text(" for more ideas"))))
      val caption = Caption(para1 :: Nil)
      val row = r("Bacon", "Jam")
      val rows = row :: Nil
      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("CaptionsC") { comment =>

      val caption = Caption(pt("Just a caption") :: Nil)
      assertTableEquals(Table(None, Some(caption), Nil), comment.body)
    }

    withComment("CaptionsOtherMarkNotActiveInCaptionLineA") { comment =>

      val caption = Caption(pt("Caption: Hi!") :: Nil)
      val header = r("Header 1", "Header 2")
      assertTableEquals(Table(Some(header), Some(caption), Nil), comment.body)
    }

    withComment("CaptionsOtherMarkNotActiveInCaptionLineB") { comment =>

      val caption = Caption(pt("Caption: {|,|+") :: Nil)
      val header = r("Header 1", "Header 2")
      assertTableEquals(Table(Some(header), Some(caption), Nil), comment.body)
    }

    withComment("CaptionsMultilineA") { comment =>

      val caption = Caption(pt("Food\nComplements") :: Nil)
      val row = r("Lamb")
      val rows = row :: Nil
      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("CaptionsMultilineB") { comment =>

      val para1 = Paragraph(Chain(List(Text("Food\nfor"), Text("\n"), Underline(Text("Foodies")))))
      val caption = Caption(para1 :: Nil)
      val row = r("Toasted Cheese Sandwich")
      val rows = row :: Nil
      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("CaptionsWithParagraphs") { comment =>

      val para1 = pt("Foo for")
      val para2 = pt("Fooies")
      val caption = Caption(para1 :: para2 :: Nil)
      val row = r("Cheesy Toast Wrap")
      val rows = row :: Nil
      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("NonCellMarkNotActiveInCellsLine") { comment =>

      val row = r(" |+Caption ", " |-Row ", " {|Start ", " |}End ", " !Header")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("NonHeaderMarkNotActiveInHeadersLine") { comment =>

      val row = r(" |+Caption ", " |-Row ", " {|Start ", " |}End ", " |Cell")
      assertTableEquals(Table(Some(row), None, Nil), comment.body)
    }

    withComment("CellMarksStartCellsExceptWhenTrailing") { comment =>

      val row = r("Penultimate Cell", "Ultimate Cell")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("HeaderMarksStartHeadersExceptWhenTrailing") { comment =>

      val row = r("Antepenultimate Header", "Penultimate Header", "Ultimate Header")
      assertTableEquals(Table(Some(row), None, Nil), comment.body)
    }

    withComment("EmptyFinalCellFollowedByNonCellMarkA") { comment =>

      val row = r("Make Scaladoc Great Again!", None, None)
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("EmptyFinalCellFollowedByNonCellMarkB") { comment =>

      val row1 = r("A", None, None)
      val row2 = r("Two is the delimiter of this content", "\"*\"", None)
      val rows = row1 :: row2 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("CombinedA") { comment =>

      val caption = Caption(pt("Liberation Day") :: Nil)

      val header = r("Item", "Price")

      val row1 = r("Rookworst", "15")
      val row2 = r("Apple Sauce", "5")
      val rows = row1 :: row2 :: Nil
      assertTableEquals(Table(Some(header), Some(caption), rows), comment.body)
    }

    withComment("CombinedB", "CombinedB_ExplicitRowEnd") { comment =>

      val caption = Caption(pt("Captain Caption") :: Nil)

      val header = Row(
        c("A ") ::
          c("B ") ::
          ci(Chain(List(Text("C is a"), Text("\n"), Bold(Text("multiline")), Text(" "), Italic(Text("header"))))) ::
          Nil
      )

      val row1 = Row(
        c("Hi!") ::
          c("Welcome to row 1") ::
          ci(Chain(List(Text("Cell"), Text("\n"), Monospace(Text("3")), Text(" is cool...")))) ::
          Nil
      )

      val row2 = r(" Row Two begins! ", " and continues", "for a bit")

      val row3 = r("It's going to be so beautiful!", "So very so!", None)

      val row4 = Row(
        c("I, for one, welcome our new _|tabular|_ overlords") ::
          c("ten") ::
          ci(HtmlTag("<a href=\"tttt\">link</a>")) ::
          Nil
      )

      val rows = row1 :: row2 :: row3 :: row4 :: Nil
      assertTableEquals(Table(Some(header), Some(caption), rows), comment.body)
    }

    withComment("CombinedB_ExplicitRowEnd_Simplified") { comment =>
      val row1 = r("A", "BB", "CCC")
      val row2 = r("D4", "E5", None)
      val row3 = r("overlords", "ten", "link")
      val rows = row1 :: row2 :: row3 :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("MultipleTables") { comment =>

      val table1 = Table(None, None, r("Ant") :: Nil)
      val table2 = Table(None, None, r("Bee") :: Nil)
      val table3 = Table(None, None, r("Cricket") :: Nil)

      assertTablesEquals(table1 :: table2 :: table3 :: Nil, comment.body)
    }

    withComment("EmptyContentLines") { comment =>

      val cell1 = c("1\na", "2", "3")
      val cell2 = c("4")
      val cell3 = c("5", "and more")
      val row = Row(cell1 :: cell2 :: cell3 :: Nil)
      val rows = row :: Nil

      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("EmptyContentLinesTrailing") { comment =>

      val row = r("1", "2")
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    withComment("ParaEnded") { comment =>

      val summary = Paragraph(Chain(List(Summary(Text("Summary")))))
      val paragraph = pt("Paragraph text should end here.")
      val table = Table(None, None, r("nuttiest") :: Nil)
      val expected = Body(List(summary, paragraph, table))

      assertBodiesEquals(expected, comment.body)
    }

    withComment("CellsEmptySingleCellRow") { comment =>

      val caption = Caption(pt("If you want that have it, there'll be no judging.") :: Nil)

      val row = r("")
      val rows = row :: Nil

      assertTableEquals(Table(None, Some(caption), rows), comment.body)
    }

    withComment("CodeMarkdownNotYetParsed") { comment =>
      val cell = c("", "{{{\n  val a = b\n}}}")
      var row = Row(cell :: Nil)
      val rows = row :: Nil
      assertTableEquals(Table(None, None, rows), comment.body)
    }

    /* Deferred Enhancements.
     *
     * When these improvements are made corresponding test updates to any new or
     * changed error messages and parsed content and would be included.
     */

    // TODO: As a later enhancement skip whitespace before table marks to reduce rate of silently incorrect table markdown.
    withComment("LeadingWhitespaceNotYetSkipped") { comment =>

      val caption = Caption(pt("Leading\n  whitespace before marks\n   !!Not Yet Skipped!!Maybe TO DO\n   |-\n   |A\n      |B") :: Nil)
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

  private def assertTablesEquals(expectedTables: Seq[Table], actualBody: Body): Unit = {
    val expectedBody = Body(expectedTables)
    assert(expectedBody == actualBody, s"Expected: $expectedBody, Actual: $actualBody")
  }

  private def assertBodiesEquals(expectedBody: Body, actualBody: Body): Unit = {
    assert(expectedBody == actualBody, s"Expected: $expectedBody, Actual: $actualBody")
  }
}