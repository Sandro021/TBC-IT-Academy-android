package com.example.exam_4_recrete.screen


import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam_4_recrete.R
import com.example.exam_4_recrete.adapters.GroupAdapter
import com.example.exam_4_recrete.common.BaseFragment
import com.example.exam_4_recrete.databinding.FragmentFormBinding
import com.example.exam_4_recrete.viewmodel.FormViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue


class FormFragment : BaseFragment<FragmentFormBinding>(FragmentFormBinding::inflate) {

    private val viewModel: FormViewModel by viewModels()

    override fun bind() {
        setUp()
    }

    override fun listeners() {}

    private fun setUp() = with(binding) {
        rvFields.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.formGroups.collectLatest { groups ->
                if (groups.isNotEmpty()) {
                    rvFields.adapter = GroupAdapter(groups) { id, value ->
                        viewModel.updateFieldValue(id, value)
                    }
                }
            }
        }
        viewModel.loadData()
        btRegister.setOnClickListener {
            handleRegister()
        }
    }

    private fun handleRegister() {
        val formData = viewModel.formData

        if (formData.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_fill_in_the_fields), Toast.LENGTH_SHORT
            ).show()
        } else {
            val message = buildString {
                formData.forEach { (id, value) ->
                    append("Field ID: $id → $value\n")
                }
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }
}