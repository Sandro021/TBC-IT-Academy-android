package com.example.exam_3.screen


import androidx.navigation.fragment.findNavController
import com.example.exam_3.R
import com.example.exam_3.common.BaseFragment
import com.example.exam_3.databinding.FragmentMenuBinding


class MenuFragment : BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {



    override fun bind() {
        setUp()
    }

    override fun listeners() {

    }

    private fun setUp()  = with(binding){
        btStartGame.setOnClickListener {
            val selectedId = rgRadioGroup.checkedRadioButtonId
            val boardSize = when (selectedId) {
                R.id.btn3x3 -> 3
                R.id.btn4x4 -> 4
                R.id.btn5x5 -> 5
                else -> 3
            }

            val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(boardSize)
            findNavController().navigate(action)

        }
    }
}