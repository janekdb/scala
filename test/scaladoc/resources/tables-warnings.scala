package scala.test.scaladoc.tables.warnings {

  /**
    * {|
    * |+Unclosed Table
    */
  trait TableEndMissing

  /**
    * {|
    * |+A
    * |+B
    * |}
    */
  trait CaptionsAtMostOne

  /**
    * {|
    * !Header
    * |+Then Caption
    * |}
    */
  trait CaptionsIsBeforeHeader

  /**
    * {|
    * |Cell
    * |+Then Caption
    * |}
    */
  trait CaptionsIsBeforeCells

  /**
    * {|
    * !One header good!
    * !Two headers are worse than one
    * |}
    */
  trait HeadersAtMostOne

  /**
    * {|
    * |Cell
    * !Then Header
    * |}
    */
  trait HeadersIsBeforeCells

  /**
    * {|
    * !h1!h2
    * |c
    * |}
    */
  trait RowCountsCellsEqualsHeader

  /**
    * {|
    * |a|
    * |b1|b2a
    *
    * b2b|b3|
    * |c1|c2
    * |}
    */
  trait RowCountsAllRowsSameSize

  /**
    * {|
    * |cell*/
  trait PrematureEndOfText

  /**
    * {|
    * |Orange|
    * Apple|
    * |}
    */
  trait MissingTableMarkAfterCellClose

  /**
    * {|
    * |+Cappy Caption*/
  trait CaptionPrematureEndOfText

}