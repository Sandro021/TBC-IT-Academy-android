package com.example.homework_14.screen

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_14.R
import com.example.homework_14.adapters.CategoryAdapter
import com.example.homework_14.adapters.OrdersAdapter
import com.example.homework_14.common.BaseFragment
import com.example.homework_14.databinding.FragmentOrdersBinding
import com.example.homework_14.model.OrdersViewModel
import com.example.homework_14.order.OrderStatus
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {


    private val viewModel: OrdersViewModel by activityViewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var ordersAdapter: OrdersAdapter

    override fun bind() {
        setUp()
    }

    override fun listeners() {}

    private fun setUp() {
        setUpCategoryAdapter()
        setUpOrdersAdapter()
        combine()
    }

    private fun setUpCategoryAdapter() {
        categoryAdapter = CategoryAdapter { status ->
            viewModel.selectStatus(status)
        }
        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        categoryAdapter.submitList(OrderStatus.entries)
    }

    private fun setUpOrdersAdapter() {
        ordersAdapter = OrdersAdapter { order ->
            viewModel.selectOrder(order)
            findNavController().navigate(R.id.action_ordersFragment_to_updateFragment)
        }
        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }
    }

    private fun combine() {
        viewLifecycleOwner.lifecycleScope.launch {
            combine(viewModel.orders, viewModel.selectedStatus) { orders, status ->
                orders.filter { it.status == status }
            }.collect { filtered -> ordersAdapter.submitList(filtered) }
        }
    }
}