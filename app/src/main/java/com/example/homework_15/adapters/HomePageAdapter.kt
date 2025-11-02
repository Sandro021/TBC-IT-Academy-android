package com.example.homework_15.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homework_15.orderInfo.OrderStatus
import com.example.homework_15.screen.OrdersListFragment

class HomePageAdapter(
    fragment: Fragment,
    private val statuses: List<OrderStatus>
) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        Log.d("HomePageAdapter", "Creating OrdersListFragment for ${statuses[position]}")
        return OrdersListFragment(statuses[position])
    }

    override fun getItemCount() = statuses.size
}