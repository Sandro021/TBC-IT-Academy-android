package com.example.homework_15.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework_15.R
import com.example.homework_15.databinding.FragmentReviewBottomBinding
import com.example.homework_15.orderInfo.Order
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ReviewBottomFragment(
    private val order: Order,
    private val onSubmit: (String, Int) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentReviewBottomBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        return inflater.inflate(R.layout.fragment_review_bottom, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        ivProduct.setImageResource(order.imageResId)
        tvName.text = order.title
        tvColor.text = order.color + " |"
        tvPrice.text = order.price
        tvStatus.text = order.status.toString()
        tvQuantity.text = "Qty = " + order.quantity.toString()
        indicator.setBackgroundResource(order.indicator)

        btSubmit.setOnClickListener {
            val reviewText = etReview.text.toString()
            val rating = ratingBar.rating.toInt()
            onSubmit(reviewText, rating)
            dismiss()
        }

        btCancel.setOnClickListener { dismiss() }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
