import streams.{Solver, StringParserTerrain, _}

trait Level1 extends GameDef with Solver with StringParserTerrain {
  /* terrain for level 1*/

  val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

}

val level = new Level1 {

}

level.goal
level.pathsFromStart.take(20).toList
level.pathsToGoal.head