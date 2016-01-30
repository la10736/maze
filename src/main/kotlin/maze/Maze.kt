/**
 * Labirinto `Maze`. Ha una dimensione e una lista di coordinate per gli elementi vuoti.
 *
 * Fatto un labirinto è possibile cecare una soluzione da un punto di partenza a
 * un'altro con `Maze.solution`.
 *
 * Created by michele on 30/12/15.
 */

package maze
import utils.*

private val Empty: List<Coordinate> = emptyList()

/**
 * Il labirinto
 *
 * @param rows numero di righe
 * @param cols numero di colonne
 * @param empties lista di coordinate dove la cella è vuota
 */
data class Maze(val rows: Int = 5, val cols: Int = 5, val empties: List<Coordinate> = Empty) {
    private val cells: Array<Array<Cell>> = Array(rows,
            { row ->
                Array(cols,
                        { col -> Cell(this, row, col, CellState.Block) })
            }
    )

    // Fino alla prossima release non funziona
//    private val cells = array2d(rows, cols, {
//        row, col ->
//        Cell(this, row, col, CellState.Block)
//    })

    init {
        for ((row, col) in empties) {
            cells[row][col] = Cell(this, row, col, CellState.Empty)
        }
    }
    /**
     * Torna la lista di coordinate vuote
     */
    fun emptiesCoordinate(): List<Coordinate> {
        return empties().map { it.coordinate }
    }

    /**
     * Torna la delle celle vuote
     */
    fun empties(): List<Cell> {
        return cells.flatMap { it.asIterable() }.filter { it.empty()}
    }

    fun copy() : Maze {
        return Maze(rows, cols, emptiesCoordinate())
    }

    /**
     * Torna una stringa con il disegno del labirinto
     */
    fun dump(): String {
        val builder: StringBuilder = StringBuilder()
        for (row in 0..rows - 1) {
            for (col in 0..cols - 1) {
                builder.append(getCell(row, col).blockString())
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    /**
     * La cella
     */
    fun getCell(row: Int, col: Int): Cell {
        return cells[row][col]
    }

    /**
     * La cella
     */
    fun getCell(coordinate: Coordinate): Cell {
        return getCell(coordinate.row, coordinate.col)
    }

    /**
     * Indica se la posizione è vuota
     */
    fun empty(row: Int, col: Int): Boolean {
        return getCell(row, col).state == CellState.Empty
    }

    /**
     * Indica se la posizione è vuota
     */
    fun empty(coordinate: Coordinate): Boolean {
        return empty(coordinate.row, coordinate.col)
    }

    /**
     * Indica se la posizione è dentro ai confini del labirinto
     */
    fun validCoordinate(row: Int, col: Int): Boolean {
        return row >= 0 && col >= 0 && row < rows && col < cols
    }

    /**
     * Indica se la posizione è dentro ai confini del labirinto
     */
    fun validCoordinate(coordinate: Coordinate): Boolean {
        return validCoordinate(coordinate.row, coordinate.col)
    }

    private fun recursive_solution(from: Coordinate, to: Coordinate): List<Coordinate> {
        var actual = getCell(from)
        actual.state = CellState.Visited
        if (from == to) {
            return linkedListOf(from)
        }
        for (candidate in actual.emptyNeighbor()) {
            val solution = solution(candidate.coordinate, to)
            if (solution.size > 0) {
                return linkedListOf(from) + solution
            }
        }
        return Empty
    }

    /**
     * Torna un percorso da `from` a `to` se esiste, lista vuota altrimenti
     */
    fun solution(from: Coordinate, to: Coordinate): List<Coordinate> {
        val m = copy()
        return m.recursive_solution(from, to)
    }

    val size: Pair<Int, Int> = Pair(rows, cols)
}

/**
 * Coordinate
 */
data class Coordinate(val row: Int, val col: Int)

/**
 * Cella di un labirinto
 */
data class Cell(val maze: Maze, val row: Int, val col: Int, var state: CellState) {

    val coordinate = Coordinate(row, col)

    /**
     * Rappresentazione ascii della cella
     */
    fun blockString(): String {
        return when (state) {
            CellState.Empty -> " "
            CellState.Block -> "*"
            CellState.Visited -> "+"
        }
    }

    /**
     * Indica se la cella è vuota
     */
    fun empty(): Boolean {
        return state == CellState.Empty
    }

    /**
     * Rende la lista con gli intorni della cella
     */
    fun neighbor(): List<Cell> {
        return (row - 1..row + 2).flatMap {
            r ->
            (col - 1..col + 2).map {
                c ->
                Coordinate(r, c)
            }
        }.filter {
            maze.validCoordinate(it) && it != coordinate
        }.map {
            maze.getCell(it)
        }
    }

    /**
     * Rende la lista con gli intorni della cella vuoti
     */
    fun emptyNeighbor(): List<Cell> {
        return neighbor().filter { it.empty() }

    }
}

enum class CellState {
    Empty,
    Block,
    Visited
}

/**
 * Linea di più segmenti, ogni coordinata deve essere in linea con la precedente:
 * o la stessa riga o la stessa colonna
 *
 * @param coordinates spigoli della polilinea
 */
fun polyLine(vararg coordinates: Coordinate): List<Coordinate> {
    if (coordinates.isEmpty()) {
        return Empty
    }
    return linkedListOf(coordinates.first()) +
            coordinates.zip(coordinates.asList().subListSlice(1)).flatMap {
                p ->
                val (from, to) = p;
                gridLine(from, to).subListSlice(1)
            }
}

private fun gridLine(from: Coordinate, to: Coordinate): List<Coordinate> {
    if (from.row == to.row) {
        return horizontalLine(from.row, from.col, to.col)
    }
    if (from.col == to.col) {
        return verticalLine(from.col, from.row, to.row)
    }
    throw IllegalArgumentException(
            "Gli elementi consecutivi devono essere sulla stessa riga o colonna")
}

/**
 * Linea orizzontale
 *
 * @param row riga
 * @param from colonna di partenza (compresa)
 * @param to colonna di arrivo (compreso)
 */
fun horizontalLine(row: Int, from: Int, to: Int): List<Coordinate> {
    return (from..to).map { Coordinate(row, it) }
}

/**
 * Linea verticale
 *
 * @param col colonna
 * @param from riga di partenza (compresa)
 * @param to riga di arrivo (compreso)
 */
fun verticalLine(col: Int, from: Int, to: Int): List<Coordinate> {
    return (from..to).map { Coordinate(it, col) }
}
