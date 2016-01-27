/**
 * Created by michele on 30/12/15.
 */
package maze

import org.junit.*
import kotlin.test.*
import utils.*

class TestMaze {

    @Test fun testBuild() {
        var maze = Maze()
        assertEquals(fullMaze(maze), maze.dump())
        assertEquals(5, maze.rows)
        assertEquals(5, maze.cols)
        assertEquals(Pair(5, 5), maze.size)
        maze = Maze(6, 2)
        assertEquals("**\n" * 6, maze.dump())
        assertEquals(6, maze.rows)
        assertEquals(2, maze.cols)
    }

    @Test fun testNoTrivialMaze() {
        var maze = Maze(empties = listOf(
                Coordinate(0, 0),
                Coordinate(0, 1),
                Coordinate(0, 2),
                Coordinate(1, 2),
                Coordinate(2, 2),
                Coordinate(2, 3),
                Coordinate(3, 3),
                Coordinate(4, 3)
        ))
        assertEquals("""
   **
** **
**  *
*** *
*** *
""".substring(1), maze.dump())
    }

    @Test fun testInvalidMazeCoordinate() {
        assertFailsWith(ArrayIndexOutOfBoundsException::class.java,
                { Maze(empties = listOf(Coordinate(6, 0))) })
        assertFailsWith(ArrayIndexOutOfBoundsException::class.java,
                { Maze(empties = listOf(Coordinate(-1, 0))) })
        assertFailsWith(ArrayIndexOutOfBoundsException::class.java,
                { Maze(empties = listOf(Coordinate(0, 6))) })
        assertFailsWith(ArrayIndexOutOfBoundsException::class.java,
                { Maze(empties = listOf(Coordinate(0, -1))) })
    }

    @Test fun testCell() {
        assertEquals(Maze().getCell(3, 4).state, CellState.Block)
        assertEquals(Maze().getCell(0, 2).state, CellState.Block)
    }

    @Test fun testMazeCoordinate() {
        val coordinate = Coordinate(3, 5)
        assertEquals(3, coordinate.row)
        assertEquals(5, coordinate.col)
        val (row, col) = coordinate
        assertEquals(3, row)
        assertEquals(5, col)
    }

    @Test fun testSolutionTrivial0() {
        var from = Coordinate(1, 1)
        var to = Coordinate(2, 1)
        var maze = Maze (empties = listOf(
                from,
                to
        ))
        var solution = maze.solution(from, to)
        assertSolution(maze, from, to, solution)

    }

    fun assertSolution(maze: Maze, from: Coordinate, to: Coordinate, solution: List<Coordinate>) {
        assertTrue(solution.size > 0)
        assertEquals(from, solution.first())
        assertEquals(to, solution.last())
        solution.map { assertTrue(maze.empty(it)) }
    }

    fun fullMaze(rows: Int, cols: Int): String {
        return ("*" * cols + "\n") * rows
    }

    fun fullMaze(maze: Maze): String {
        return fullMaze(maze.rows, maze.cols)
    }
}

