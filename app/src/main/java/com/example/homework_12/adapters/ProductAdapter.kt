package com.example.homework_12.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_12.Product
import com.example.homework_12.R


class ProductAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = products[position]
        holder.img.setImageResource(product.image)
        holder.name.text = product.name
        holder.price.text = product.price
    }

    override fun getItemCount() = products.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Product>) {
        products = newList
        notifyDataSetChanged()
    }
    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.ivProduct)
        val name: TextView = view.findViewById(R.id.tvProductName)
        val price: TextView = view.findViewById(R.id.tvPrice)
    }
}