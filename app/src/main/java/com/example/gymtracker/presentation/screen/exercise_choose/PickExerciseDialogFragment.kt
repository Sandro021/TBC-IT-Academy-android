package com.example.gymtracker.presentation.screen.exercise_choose

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymtracker.R
import com.example.gymtracker.databinding.DialogPickExercisesBinding
import com.example.gymtracker.presentation.common.ResultKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PickExercisesDialogFragment : DialogFragment(R.layout.dialog_pick_exercises) {

    private val vm: PickExercisesViewModel by viewModels()

    private val adapter by lazy {
        PickExercisesAdapter { ui -> vm.toggle(ui.id) }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.92).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val b = DialogPickExercisesBinding.bind(view)

        val groupId = requireArguments().getString(ARG_GROUP_ID)!!
        val groupTitle = requireArguments().getString(ARG_GROUP_TITLE)!!

        b.tvTitle.text = groupTitle

        b.rv.layoutManager = LinearLayoutManager(requireContext())
        b.rv.adapter = adapter

        vm.start(groupId, groupTitle)

        b.btnAdd.setOnClickListener {
            vm.addExercise(b.etNew.text?.toString().orEmpty())
            b.etNew.setText("")
        }

        b.btnDone.setOnClickListener {
            val selected = vm.state.value.ui.filter { it.isSelected }

            parentFragmentManager.setFragmentResult(
                ResultKeys.REQ_PICK_EXERCISES,
                bundleOf(
                    ResultKeys.KEY_IDS to selected.map { it.id }.toTypedArray(),
                    ResultKeys.KEY_NAMES to selected.map { it.name }.toTypedArray()
                )
            )
            dismiss()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { st -> adapter.submitList(st.ui) }
        }
    }

    companion object {
        private const val ARG_GROUP_ID = "arg_group_id"
        private const val ARG_GROUP_TITLE = "arg_group_title"

        fun newInstance(groupId: String, groupTitle: String) =
            PickExercisesDialogFragment().apply {
                arguments = bundleOf(ARG_GROUP_ID to groupId, ARG_GROUP_TITLE to groupTitle)
            }
    }
}