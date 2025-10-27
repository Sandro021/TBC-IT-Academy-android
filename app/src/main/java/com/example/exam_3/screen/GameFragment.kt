package com.example.exam_3.screen


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.example.exam_3.R
import com.example.exam_3.common.BaseFragment
import com.example.exam_3.databinding.FragmentGameBinding


class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {

    private var boardSize = 3

    private lateinit var board: Array<Array<String>>

    private var currentPlayer = "X"
    override fun bind() {
        setUpBoard()
    }

    override fun listeners() {

    }

    @SuppressLint("SetTextI18n")
    private fun setUpBoard() = with(binding) {
        val args = GameFragmentArgs.fromBundle(requireArguments())
        boardSize = args.boardSize

        tvTurn.text = "Player X turn"
        gridBoard.columnCount = boardSize
        gridBoard.rowCount = boardSize

        board = Array(boardSize) { Array(boardSize) { "" } }

        buildGrid(gridBoard, tvTurn)
    }

    private fun buildGrid(grid: GridLayout, tvTurn: TextView) {
        val inflater = LayoutInflater.from(requireContext())

        val cellSize : Int
        val textSize : Float
        when (boardSize) {
            3 -> {
                cellSize = 180
                textSize = 40f
            }
            4 -> {
                cellSize = 140
                textSize = 32f
            }
            else -> {
                cellSize = 100
                textSize = 24f
            }
        }

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val cell = inflater.inflate(R.layout.cell_layout, grid, false)
                val btnX = cell.findViewById<Button>(R.id.btnX)
                val btnO = cell.findViewById<Button>(R.id.btnO)

                val params = GridLayout.LayoutParams().apply {
                    width = cellSize
                    height = cellSize
                    setMargins(6, 6, 6, 6)
                }
                btnO.textSize = textSize
                btnX.textSize = textSize
                cell.layoutParams = params

                btnX.setOnClickListener {
                    handleMove(i, j, "X", btnX, btnO, tvTurn)
                }
                btnO.setOnClickListener {
                    handleMove(i, j, "O", btnO, btnX, tvTurn)
                }

                grid.addView(cell)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleMove(
        row: Int,
        col: Int,
        choice: String,
        clickedBtn: Button,
        otherBtn: Button,
        tvTurn: TextView
    ) {
        if (board[row][col].isNotEmpty()) return

        if (choice != currentPlayer) {
            binding.tvTurn.text = "Its $currentPlayer turn"
            return
        }

        board[row][col] = choice
        clickedBtn.isEnabled = false
        otherBtn.isEnabled = false

        otherBtn.visibility = View.GONE
        when {
            checkWinner() != null -> {
                tvTurn.text = "Player ${checkWinner()} wins!"
                disableBoard()
            }

            isDraw() -> tvTurn.text = "Draw!"
            else -> {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                tvTurn.text = "Player $currentPlayer's turn"
            }
        }
    }

    private fun disableBoard() {
        val grid = view?.findViewById<GridLayout>(R.id.gridBoard) ?: return
        for (i in 0 until grid.childCount) {
            val cell = grid.getChildAt(i)
            cell.findViewById<Button>(R.id.btnX).isEnabled = false
            cell.findViewById<Button>(R.id.btnO).isEnabled = false
        }
    }

    private fun checkWinner(): String? {
        for (i in 0 until boardSize) {
            if (board[i].all { it == "X" }) return "X"
            if (board[i].all { it == "O" }) return "O"
        }
        for (j in 0 until boardSize) {
            if ((0 until boardSize).all { board[it][j] == "X" }) return "X"
            if ((0 until boardSize).all { board[it][j] == "O" }) return "O"
        }
        if ((0 until boardSize).all { board[it][it] == "X" }) return "X"
        if ((0 until boardSize).all { board[it][it] == "O" }) return "O"
        if ((0 until boardSize).all { board[it][boardSize - 1 - it] == "X" }) return "X"
        if ((0 until boardSize).all { board[it][boardSize - 1 - it] == "O" }) return "O"
        return null
    }

    private fun isDraw(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }
}





