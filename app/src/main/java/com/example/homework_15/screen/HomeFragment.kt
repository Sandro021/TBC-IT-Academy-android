package com.example.homework_15.screen

import androidx.fragment.app.activityViewModels
import com.example.homework_15.adapters.HomePageAdapter
import com.example.homework_15.common.BaseFragment
import com.example.homework_15.databinding.FragmentHomeBinding
import com.example.homework_15.model.OrdersViewModel
import com.example.homework_15.orderInfo.OrderStatus
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: OrdersViewModel by activityViewModels()
    override fun bind() {
        setUp()
    }

    override fun listeners() {}
    private fun setUp() {
        val statuses = listOf(OrderStatus.ACTIVE, OrderStatus.COMPLETED)
        val pagerAdapter = HomePageAdapter(this, statuses)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = if (statuses[pos] == OrderStatus.ACTIVE) "Active" else "Completed"
        }.attach()
    }
}