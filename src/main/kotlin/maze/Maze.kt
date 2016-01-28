/**
 * Created by michele on 30/12/15.
 */

package maze

private val NoEmpty: List<Coordinate> = emptyList()

data class Maze(val rows: Int = 5, val cols: Int = 5, val empties: List<Coordinate> = NoEmpty) {
    private val cells: Array<Array<Cell>> = Array(rows,
            {row -> Array(cols,
                    {col -> Cell(this, row, col, CellState.Block)})
            }
    )
    // Fino alla prossima release non funziona
//    private val cells: Array<Array<Cell>> = array2d<Cell>(rows, cols,
//        {row, col -> Cell(this, row, col, CellState.Block)})

    init {
        for ((row, col) in empties){
            cells[row][col] = Cell(this, row, col, CellState.Empty)
        }
    }

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

    fun getCell(row: Int, col: Int): Cell {
        return cells[row][col]
    }

    fun getCell(coordinate: Coordinate): Cell {
        return getCell(coordinate.row, coordinate.col)
    }

    fun empty(row: Int, col: Int): Boolean {
        return getCell(row, col).state == CellState.Empty
    }

    fun empty(coordinate: Coordinate): Boolean {
        return empty(coordinate.row, coordinate.col)
    }

    fun solution(from: Coordinate, to: Coordinate): List<Coordinate> {
        var solution: MutableList<Coordinate> = linkedListOf()
        var pos: Cell = getCell(from)
        solution.add(from)
        for (cel in pos.neighbor()){
            if (to == cel.coordinate){
                solution.add(cel.coordinate)
            }
        }

        return solution
    }




    val size: Pair<Int, Int> = Pair(rows, cols)
}

data class Coordinate(val row: Int, val col: Int)

data class Cell(val maze: Maze, val row: Int, val col: Int, val state: CellState) {

    val coordinate = Coordinate(row, col)

    fun blockString(): String {
        return when (state) {
            CellState.Empty -> " "
            CellState.Block -> "*"
        }
    }

    fun empty(): Boolean{
        return  state == CellState.Empty
    }

    fun neighbor(): List<Cell> {
        var neighbor = linkedListOf<Cell>();
        for (i in -1..1) {
            for (j in -1..1) {
                val coord = Coordinate(row + i, col + j)
                val cel = maze.getCell(coord)
                if (cel != this && cel.empty()){
                    neighbor.add(cel)
                }
            }
        }
        return neighbor
    }
}

enum class CellState {
    Empty,
    Block
}

