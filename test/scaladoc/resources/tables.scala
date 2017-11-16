package scala.test.scaladoc.tables {

  /**
    * {|
    * |}
    */
  trait Empty

  /**
    * {|
    * |Orange
    * |}
    */
  trait CellsA

  /**
    * {|
    * |Orange
    * |Apple
    * |}
    */
  trait CellsB

  /**
    * {|
    * |Oranges __and__ Aubergines
    * |Peaches `or` Pears
    * |}
    */
  trait CellsC

  /**
    * {|
    * |Orange
    * |Apple|
    * |}
    */
  trait CellsD

  /**
    * {|
    * |Oranges __and__ Aubergines
    * |Peaches `or` Pears|
    * |}
    */
  trait CellsE

  // Minimal cell marks
  /**
    * {|
    * |row-1|
    * |row-2|
    * |}
    */
  trait CellsMinimalCellMarks

  // Alternative cell marks
  /**
    * {|
    * ||row-1||
    * |||row-2|||
    * |}
    */
  trait CellsAlternativeCellMarksA

  // Alternative cell marks
  /**
    * {|
    * |||row-1|||
    * ||row-2||
    * |}
    */
  trait CellsAlternativeCellMarksB

  // Alternative cell marks
  /**
    * {|
    * ||row-1||
    * |row-2|
    * |}
    */
  trait CellsAlternativeCellMarksC

  // Empty final cell as new row introduction
  /**
    * {|
    * |Orange
    * |Apple|
    * |Bread
    * |Pie|
    * |Butter
    * |Ice cream|
    * |}
    */
  trait CellsInferredNewRowTwoCellsA

  // Empty final cell as new row introduction
  /**
    * {|
    * |Orange|Apple|
    * |Bread|Pie|
    * |Butter|Ice cream|
    * |}
    */
  trait CellsInferredNewRowTwoCellsB

  /**
    * {|
    * |A
    * |B|C|
    * || D|D || E|E || F|F ||
    * |}
    */
  trait CellsMarkerRecalculatedAfterInferredNewRow

  /**
    * {|
    * |Plum||
    * ||Fig||||
    * |||Cherry||||||
    * |}
    */
  trait TrailingCellsEmpty

  /**
    * {|
    * |Plum
    * ||Fig|||Cherry|||
    * |}
    */
  trait OpenTrailingCellAndCellTerminatorIsRedefined

  /**
    * {|
    * |Oranges and
    * Tangerines
    * |}
    */
  trait CellsMultiLineA

  /**
    * {|
    * |Oranges and
    * Tangerines
    * |Transistors
    * |Falcon,
    * Eagle __and__
    * `Goose`
    * |}
    */
  trait CellsMultiLineB

  /**
    * {|
    * |Paragraph 1
    *
    * Paragraph 2
    * |}
    */
  trait CellsParagraphsA

  /**
    * {|
    * |Paragraph 1
    *
    * and paragraph 2
    * |p1
    *
    * p2
    *
    * p3
    *
    *
    * |t1,
    * t2 __and__
    *
    *
    * `t3`
    * |Line 1
    * with line 2 part of the same paragraph
    *
    * But line 3 is a new paragraph.
    * |}
    */
  trait CellsParagraphsB

  /**
    * {|
    * |Orange|Apple|more
    * |}
    */
  trait CellsSameLineA

  /**
    * {|
    * |Orange|Apple|more|
    * |}
    */
  trait CellsSameLineAExplicitRowEnd

  /**
    * {|
    * ||Bitwise|| | ||Or
    * |}
    */
  trait CellsSameLineB

  /**
    * {|
    * ||Bitwise|| | ||Or||
    * |}
    */
  trait CellsSameLineBExplicitRowEnd

  /**
    * {|
    * ||A
    * ||B
    * |}
    */
  trait CellsDeeperMarkA

  /**
    * {|
    * ||A
    * ||B||
    * |}
    */
  trait CellsDeeperMarkA_ExplicitRowEnd

  /**
    * {|
    * |||C
    * |}
    */
  trait CellsDeeperMarkB

  /**
    * {|
    * |||C|||
    * |}
    */
  trait CellsDeeperMarkB_ExplicitRowEnd

  /**
    * {|
    * |A
    * ||B
    * |||C
    * |}
    */
  trait CellsDeeperMarkC

  /**
    * {|
    * |A
    * ||B
    * |||C|||
    * |}
    */
  trait CellsDeeperMarkC_ExplicitRowEnd

  /**
    * {|
    * ||Bitwise|| | ||Or||
    * |||Logical||| || |||Or
    * |}
    */
  trait CellsDeeperMarkD

  /**
    * {|
    * ||Bitwise|| | ||Or||
    * |||Logical||| || |||Or|||
    * |}
    */
  trait CellsDeeperMarkD_ExplicitRowEnd

  /**
    * {|
    * ||Bitwise OR
    * is |
    * ||||Logical OR
    * is ||.||||
    * |||Logical OR
    * is || and
    * logical AND is &&
    * |Logical NOT
    * is !
    * |}
    */
  trait CellsDeeperMarkMultiLineA

  /**
    * {|
    * ||Bitwise OR
    * is |
    * ||||Logical OR
    * is ||.||||
    * |||Logical OR
    * is || and
    * logical AND is &&
    * |Logical NOT
    * is !|
    * |}
    */
  trait CellsDeeperMarkMultiLineA_ExplicitRowEnd

  /**
    * {|
    * |||P
    * is
    * Q
    * |R
    * x
    * |}
    */
  trait CellsDeeperMarkMultiLineA_Simplified

  /**
    * {|
    * |||P
    * is
    * Q
    * |R
    * x|
    * |}
    */
  trait CellsDeeperMarkMultiLineA_Simplified_ExplicitRowEnd

  // Headers

  /**
    * {|
    * !Amount
    * |}
    */
  trait HeadersA

  /**
    * {|
    * !Amount!
    * |}
    */
  trait HeadersA_ExplicitRowEnd

  /**
    * {|
    * !Amount
    * !Cost
    * |}
    */
  trait HeadersB

  /**
    * {|
    * !Amount
    * !Cost!
    * |}
    */
  trait HeadersB_ExplicitRowEnd

  /**
    * {|
    * !Fruits,
    * Beverages and
    * Vegetables
    * |}
    */
  trait HeadersMultiLineA

  /**
    * {|
    * !Fruits,
    * Beverages and
    * Vegetables!
    * |}
    */
  trait HeadersMultiLineA_ExplicitRowEnd

  /**
    * {|
    * !Fruits,
    * Beverages and
    * Vegetables
    * !Semiconductors
    * !''Raptors'',
    * and Poultry
    * |}
    */
  trait HeadersMultiLineB

  /**
    * {|
    * !Fruits,
    * Beverages and
    * Vegetables
    * !Semiconductors
    * !''Raptors'',
    * and Poultry!
    * |}
    */
  trait HeadersMultiLineB_ExplicitRowEnd

  /**
    * {|
    * !Fruits!Semiconductors!Raptors
    * |}
    */
  trait HeadersSameLineA

  /**
    * {|
    * !Fruits!Semiconductors!Raptors!
    * |}
    */
  trait HeadersSameLineA_ExplicitRowEnd

  /**
    * {|
    * !!Wow! !! ! !!n!/
    * |}
    */
  trait HeadersSameLineB

  /**
    * {|
    * !!Wow! !! ! !!n!/!!
    * |}
    */
  trait HeadersSameLineB_ExplicitRowEnd

  /**
    * {|
    * !!A
    * !!B
    * |}
    */
  trait HeadersDeeperMarkA

  /**
    * {|
    * !!A
    * !!B!!
    * |}
    */
  trait HeadersDeeperMarkA_ExplicitRowEnd

  /**
    * {|
    * !!!C
    * |}
    */
  trait HeadersDeeperMarkB

  /**
    * {|
    * !!!C!!!
    * |}
    */
  trait HeadersDeeperMarkB_ExplicitRowEnd

  /**
    * {|
    * !A
    * !!B
    * !!!C
    * |}
    */
  trait HeadersDeeperMarkC

  /**
    * {|
    * !A
    * !!B
    * !!!C!!!
    * |}
    */
  trait HeadersDeeperMarkC_ExplicitRowEnd

  /**
    * {|
    * !A
    * a
    * !!B
    * b
    * b
    * !!!C
    * c
    * c
    * |}
    */
  trait HeadersDeeperMarkMultiLineA

  /**
    * {|
    * !A
    * a
    * !!B
    * b
    * b
    * !!!C
    * c
    * c!!!
    * |}
    */
  trait HeadersDeeperMarkMultiLineA__ExplicitRowEnd

  /**
    * {|
    * |A!|B!!
    * ||!||!!
    * |}
    */
  trait CellsUsingHeaderMarkdown

  /**
    * {|
    * !A|!B||
    * !!|!!||
    * |}
    */
  trait HeadersUsingCellMarkdown

  /**
    * {|
    * !Para 1 has
    * three
    * lines.
    *
    * Para ^2^ has ,,styled,, text.
    * !a b
    *
    * c d
    *
    * e f
    *
    *
    *
    *
    * z
    *
    *
    * |}
    */
  trait HeadersParagraphs

  // Captions

  /**
    * {|
    * |+Food Complements
    * |Lamb
    * |Mint
    * |}
    */
  trait CaptionsA

  /**
    * {|
    * |+See `CaptionsA` for more ideas
    * |Bacon
    * |Jam
    * |}
    */
  trait CaptionsB

  /**
    * {|
    * |+Just a caption
    * |}
    */
  trait CaptionsC

  /**
    * {|
    * |+Caption: Hi!
    * !Header 1!Header 2
    * |}
    */
  trait CaptionsOtherMarkNotActiveInCaptionLineA

  /**
    * {|
    * |+Caption: {|,|+
    * !Header 1!Header 2
    * |}
    */
  trait CaptionsOtherMarkNotActiveInCaptionLineB

  /**
    * {|
    * |+Food
    * Complements
    * |Lamb
    * |}
    */
  trait CaptionsMultilineA

  /**
    * {|
    * |+Food
    * for
    * __Foodies__
    * |Toasted Cheese Sandwich
    * |}
    */
  trait CaptionsMultilineB

  /**
    * {|
    * |+Foo for
    *
    * Fooies
    * |Cheesy Toast Wrap
    * |}
    */
  trait CaptionsWithParagraphs

  /**
    * {|
    * || |+Caption || |-Row || {|Start || |}End || !Header
    * |}
    */
  trait NonCellMarkNotActiveInCellsLine

  /**
    * {|
    * ! |+Caption ! |-Row ! {|Start ! |}End ! |Cell
    * |}
    */
  trait NonHeaderMarkNotActiveInHeadersLine

  /**
    * {|
    * |Penultimate Cell|Ultimate Cell|
    * |}
    */
  trait CellMarksStartCellsExceptWhenTrailing

  /**
    * {|
    * !Antepenultimate Header!Penultimate Header!Ultimate Header!
    * |}
    */
  trait HeaderMarksStartHeadersExceptWhenTrailing

  /**
    * {|
    * |Make Scaladoc Great Again!|||
    * |}
    */
  trait EmptyFinalCellFollowedByNonCellMarkA

  /**
    * {|
    * |A|||
    * ||Two is the delimiter of this content||"*"||||
    * |}
    */
  trait EmptyFinalCellFollowedByNonCellMarkB

  /**
    * {|
    * |+Liberation Day
    * !Item
    * !Price
    * |Rookworst
    * |15|
    * |Apple Sauce
    * |5
    * |}
    */
  trait CombinedA

  /**
    * {|
    * |+Captain Caption
    * !A !B !C is a
    * '''multiline''' ''header''!
    * |Hi!|Welcome to row 1|Cell
    * `3` is cool...|
    * || Row Two begins! || and continues
    * |for a bit|
    * |It's going to be so beautiful!|So very so!||
    * ||I, for one, welcome our new _|tabular|_ overlords
    * ||||||||||ten||||||||||<a href="tttt">link</a>
    * |}
    */
  trait CombinedB

  /**
    * {|
    * |+Captain Caption
    * !A !B !C is a
    * '''multiline''' ''header''!
    * |Hi!|Welcome to row 1|Cell
    * `3` is cool...|
    * || Row Two begins! || and continues
    * |for a bit|
    * |It's going to be so beautiful!|So very so!||
    * ||I, for one, welcome our new _|tabular|_ overlords
    * ||||||||||ten||||||||||<a href="tttt">link</a>||||||||||
    * |}
    */
  trait CombinedB_ExplicitRowEnd

  /**
    * {|
    * ||A||BB
    * |CCC|
    * |D4|E5||
    * |overlords|ten|link|
    * |}
    */
  trait CombinedB_ExplicitRowEnd_Simplified

  /**
    * {|
    * |1
    * a
    *
    * 2
    *
    * 3|4|5
    *
    * and more
    * |}
    */
  trait EmptyContentLines

  /**
    * {|
    * |1
    *
    * |2
    *
    *
    * |}
    */
  trait EmptyContentLinesTrailing

  /**
    * {|
    * |Ant
    * |}
    * {|
    * |Bee
    * |}
    *
    * {|
    * |Cricket
    * |}
    *
    */
  trait MultipleTables

  /**
    * Summary
    *
    * Paragraph text should end here.
    * {|
    * |nuttiest
    * |}
    */
  trait ParaEnded

  /**
    * {|
    * |+If you want that have it, there'll be no judging.
    * |
    * |}
    */
  trait CellsEmptySingleCellRow

  // Known suboptimal behaviour. Candidates for improving later.

  // Should parse to table with a caption, no header and no rows.
  // If the parsing is later extended to allow leading whitespace this would
  // parse to a caption, two header cells and a two cell row.
  /**
    * {|
    *  |+Leading
    *   whitespace before marks
    *    !!Not Yet Skipped!!Maybe TO DO
    *    |-
    *    |A
    *       |B
    * |}
    */
  trait LeadingWhitespaceNotYetSkipped

  /**
    * {|
    * |
    * {{{
    *   val a = b
    * }}}
    * |}
    */
  trait CodeMarkdownNotYetParsed

}