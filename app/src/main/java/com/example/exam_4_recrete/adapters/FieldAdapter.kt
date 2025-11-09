package com.example.exam_4_recrete.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_4_recrete.databinding.ItemFieldBinding
import com.example.exam_4_recrete.model.FieldItem

class FieldAdapter(
    private val fields: List<FieldItem>,
    private val onValueChanged: (Int, String) -> Unit
) :
    RecyclerView.Adapter<FieldAdapter.FieldViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FieldViewHolder {
        val binding = ItemFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FieldViewHolder,
        position: Int
    ) {
        val item = fields[position]
        with(holder.binding) {
            fieldInput.hint = item.hint
            when (item.keyboard) {
                "number" -> fieldInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER
                "text" -> fieldInput.inputType = android.text.InputType.TYPE_CLASS_TEXT
            }
            fieldInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onValueChanged(item.field_id, s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    override fun getItemCount() = fields.size

    inner class FieldViewHolder(val binding: ItemFieldBinding) :
        RecyclerView.ViewHolder(binding.root)
}