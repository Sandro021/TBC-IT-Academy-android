package com.example.homework_17.screen

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homework_17.R
import com.example.homework_17.adapter.CardPagerAdapter
import com.example.homework_17.common.BaseFragment
import com.example.homework_17.databinding.FragmentPaymentBinding
import com.example.homework_17.viewModel.CardViewModel
import kotlinx.coroutines.launch


class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private val viewModel: CardViewModel by activityViewModels()

    private val adapter by lazy { CardPagerAdapter() }

    override fun bind() {
        setUpViewPager()
        observeCardData()
    }

    override fun listeners() {
        addCard()
    }

    private fun setUpViewPager() {
        val viewPager = binding.vpCards
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter

    }

    private fun observeCardData() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cards.collect { cardList ->
                    adapter.submitList(cardList)
                }
            }
        }
    }
    private fun addCard() {
        binding.btAddNew.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_addCardFragment)
        }
    }
}