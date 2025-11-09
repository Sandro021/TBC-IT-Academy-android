package com.example.homework_17.screen

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.homework_17.R
import com.example.homework_17.common.BaseFragment
import com.example.homework_17.databinding.FragmentAddCardBinding
import com.example.homework_17.model.CardInfo
import com.example.homework_17.viewModel.CardViewModel
import com.google.android.material.snackbar.Snackbar

class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {
    private val viewModel: CardViewModel by activityViewModels()
    override fun bind() {}

    override fun listeners() {
        goBack()
        addCard()
    }


    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addCardFragment_to_paymentFragment)
        }
    }

    private fun addCard() = with(binding) {
        btAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val number = etCardNumber.text.toString().trim()
            val date = etDate.text.toString().trim()
            val selectedRadioId = rgCardChoice.checkedRadioButtonId

            if (name.isEmpty()) {
                Snackbar.make(
                    binding.root, getString(R.string.please_fill_name_field), Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (number.length != 16) {
                Snackbar.make(
                    binding.root, getString(R.string.please_put_16_digits), Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (date.length != 5 || !date.contains('/')) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.please_enter_date_like_in_the_hint), Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (selectedRadioId == -1) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.please_choose_card_type), Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val cardType = when (selectedRadioId) {
                R.id.rbVisa -> "VISA"
                R.id.rbMasterCard -> "MASTERCARD"
                else -> ""
            }
            val newCard = CardInfo(
                cardHolderName = name,
                cardNumber = number,
                validThru = date,
                cardType = cardType,
            )
            viewModel.addCard(newCard)
            findNavController().navigate(R.id.action_addCardFragment_to_paymentFragment)
        }
    }
}
