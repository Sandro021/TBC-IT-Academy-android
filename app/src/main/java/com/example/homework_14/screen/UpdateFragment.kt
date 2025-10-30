package com.example.homework_14.screen

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_14.common.BaseFragment
import com.example.homework_14.databinding.FragmentUpdateBinding
import com.example.homework_14.model.OrdersViewModel
import com.example.homework_14.order.OrderStatus
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate) {

    private val viewModel: OrdersViewModel by activityViewModels()
    override fun bind() {
        updateStatus()
    }

    override fun listeners() {}


    @SuppressLint("SetTextI18n")
    private fun updateStatus() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedOrder.filterNotNull().collect { order ->
                tvOrderId.text = "Order #${order.id}"


                if (order.status == OrderStatus.PENDING) {
                    btnDelivered.visibility = View.VISIBLE
                    btnCancelled.visibility = View.VISIBLE
                } else {
                    btnDelivered.visibility = View.GONE
                    btnCancelled.visibility = View.GONE
                }
                btnCancelled.setOnClickListener {
                    viewModel.updateOrderStatus(order.id, OrderStatus.CANCELLED)
                    findNavController().popBackStack()
                }
                btnDelivered.setOnClickListener {
                    viewModel.updateOrderStatus(order.id, OrderStatus.DELIVERED)
                    findNavController().popBackStack()
                }
                btnCancel.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }
}