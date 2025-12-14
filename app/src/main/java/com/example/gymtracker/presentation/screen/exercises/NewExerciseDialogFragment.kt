package com.example.gymtracker.presentation.screen.exercises


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.gymtracker.databinding.DialogNewExerciseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewExerciseDialogFragment : DialogFragment() {

    private val viewModel: ExercisesViewModel by viewModels({ requireParentFragment() })


    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogNewExerciseBinding.inflate(LayoutInflater.from(requireContext()))
        val groupIds = requireArguments().getStringArrayList(ARG_GROUP_IDS)!!
        val groupTitles = requireArguments().getStringArrayList(ARG_GROUP_TITLES)!!

        binding.spGroup.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            groupTitles
        )

        return AlertDialog.Builder(requireContext())
            .setTitle("New exercise")
            .setView(binding.root)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val index = binding.spGroup.selectedItemPosition
                viewModel.processIntent(
                    ExercisesIntent.SaveNewExercise(
                        name = binding.etName.text.toString(),
                        groupId = groupIds[index],
                        groupTitle = groupTitles[index]
                    )
                )
            }
            .create()
    }


    companion object {
        private const val ARG_GROUP_IDS = "group_ids"
        private const val ARG_GROUP_TITLES = "group_titles"

        fun newInstance(groupIds: ArrayList<String>, groupTitles: ArrayList<String>) =
            NewExerciseDialogFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_GROUP_IDS, groupIds)
                    putStringArrayList(ARG_GROUP_TITLES, groupTitles)
                }
            }
    }

}