package com.example.homework_15.screen

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_15.adapters.OrdersAdapter
import com.example.homework_15.common.BaseFragment
import com.example.homework_15.databinding.FragmentOrdersListBinding
import com.example.homework_15.model.OrdersViewModel
import com.example.homework_15.orderInfo.OrderStatus
import kotlinx.coroutines.launch

class OrdersListFragment(private val status: OrderStatus) :
    BaseFragment<FragmentOrdersListBinding>(FragmentOrdersListBinding::inflate) {
    private val viewModel: OrdersViewModel by activityViewModels()
    private lateinit var adapter: OrdersAdapter

    override fun bind() {
        setUpAdapter()
    }

    override fun listeners() {}

    private fun setUpAdapter() {
        adapter = OrdersAdapter { order ->

            if (!order.hasReview && order.status == OrderStatus.COMPLETED) {

                val reviewSheet = ReviewBottomFragment(order) { reviewText, rating ->
                    viewModel.markOrderAsReviewed(order.id, reviewText, rating)
                }
                reviewSheet.show(parentFragmentManager, "ReviewSheet")
            }
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect { allOrders ->
                val filtered = allOrders.filter { it.status == status }
                adapter.submitList(filtered)
            }
        }

    }
}