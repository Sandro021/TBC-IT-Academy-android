package com.example.homework_12.screen

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_12.Product
import com.example.homework_12.R
import com.example.homework_12.adapters.CategoryAdapter
import com.example.homework_12.adapters.ProductAdapter
import com.example.homework_12.common.BaseFragment
import com.example.homework_12.databinding.StoreFragmentBinding

class StoreFragment : BaseFragment<StoreFragmentBinding>(StoreFragmentBinding::inflate) {

    override fun bind() {
        setUpCategoryAdapter()
        setUpProductAdapter()
    }

    val categories = listOf(
        "All",
        "Party",
        "Camping", "Footballer",
        "Category2", "Category3"
    )

    val allProducts = listOf(
        Product(
            R.drawable.product1,
            "Party blazer",
            "120",
            "Party"
        ),
        Product(
            R.drawable.product2,
            "Party blazer",
            "120",
            "Party"
        ),
        Product(
            R.drawable.product3,
            "Formal suit", "80", "Party"
        ),
        Product(
            R.drawable.product4,
            "Sleeping bag", "70", "Party"
        ),
        Product(
            R.drawable.ronaldo, "Ronaldo",
            "Infinity", "Footballer"
        ),
        Product(
            R.drawable.messi, "Messi",
            "100M", "Footballer"
        ),
        Product(
            R.drawable.ibra, "Ibrahimovic",
            "80", "Footballer"
        ),
        Product(
            R.drawable.magician,
            "Magician", "150M", "Footballer"
        ),
    )


    private fun setUpCategoryAdapter() {
        val categoryAdapter = CategoryAdapter(categories) { selected -> filterProducts(selected) }
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter

    }

    val productAdapter = ProductAdapter(allProducts)
    fun setUpProductAdapter() {
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = productAdapter
    }


    private fun filterProducts(category: String) {
        val filteredList = if (category == "All") {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
        productAdapter.updateList(filteredList)
    }
}