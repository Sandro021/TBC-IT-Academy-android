package com.example.homework_26.presentation.screen

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_26.R
import com.example.homework_26.databinding.FragmentPasscodeBinding
import com.example.homework_26.presentation.intent.PasscodeIntent
import com.example.homework_26.presentation.state.PasscodeState
import com.example.homework_26.presentation.viewmodel.PasscodeViewModel
import com.example.homework_26.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasscodeFragment : BaseFragment<FragmentPasscodeBinding>(FragmentPasscodeBinding::inflate) {

    private val viewModel: PasscodeViewModel by viewModels()


    private lateinit var dots: List<View>
    override fun bind() {
        setUpVisual()
    }

    override fun listeners() {

    }

    private fun setUpVisual() = with(binding) {


        dots = listOf(
            dot1, dot2, dot3, dot4
        )

        setUpDigits()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { render(it) }
            }
        }
    }

    private fun setUpDigits() = with(binding) {
        btn1.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(1))
        }
        btn2.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(2))
        }
        btn3.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(3))
        }
        btn4.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(4))
        }
        btn5.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(5))
        }
        btn6.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(6))
        }
        btn7.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(7))
        }
        btn8.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(8))
        }
        btn9.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(9))
        }
        btn0.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.NumberClicked(0))
        }
        btnDelete.setOnClickListener {
            viewModel.processIntent(PasscodeIntent.DeleteClicked)
        }
    }

    private fun render(state: PasscodeState) = with(binding) {
        dots.forEachIndexed { index, dot ->
            val active = index < state.digitsCount
            dot.setBackgroundResource(
                if (active) R.drawable.dot_active else R.drawable.dot_inactive
            )
        }

        when (state.isSuccess) {
            true -> {
                tvMessage.text = "Success"
                tvMessage.setTextColor(requireContext().getColor(android.R.color.holo_green_dark))
            }

            false -> {
                tvMessage.text = state.errorMessage ?: "Incorrect passcode"
                tvMessage.setTextColor(requireContext().getColor(android.R.color.holo_red_dark))
            }

            null -> tvMessage.text = ""
        }
    }
}