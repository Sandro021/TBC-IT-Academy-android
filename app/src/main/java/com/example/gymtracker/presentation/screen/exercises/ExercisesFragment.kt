package com.example.gymtracker.presentation.screen.exercises

import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymtracker.databinding.FragmentExercisesBinding
import com.example.gymtracker.presentation.common.BaseFragment
import com.example.gymtracker.presentation.screen.exercise_choose.PickExercisesDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExercisesFragment :
    BaseFragment<FragmentExercisesBinding>(FragmentExercisesBinding::inflate) {

    private val viewModel: ExercisesViewModel by viewModels()
    private val adapter by lazy {
        ExerciseGroupsAdapter { group ->
            PickExercisesDialogFragment
                .newInstance(group.id, group.title)
                .show(childFragmentManager, "PickExercisesDialog")
        }
    }
    private val pickedMap = linkedMapOf<String, String>()

    override fun bind() {
        addNewExercise()
        closeDialog()
        setupPickResult()
    }

    override fun listeners() {
        setUpListeners()
        goToCalendarPage()
    }

    private fun setupPickResult() = with(binding) {
        childFragmentManager.setFragmentResultListener(
            com.example.gymtracker.presentation.common.ResultKeys.REQ_PICK_EXERCISES,
            viewLifecycleOwner
        ) { _, bundle ->

            val ids = bundle
                .getStringArray(com.example.gymtracker.presentation.common.ResultKeys.KEY_IDS)
                ?.toList().orEmpty()

            val names = bundle
                .getStringArray(com.example.gymtracker.presentation.common.ResultKeys.KEY_NAMES)
                ?.toList().orEmpty()


            ids.zip(names).forEach { (id, name) ->
                pickedMap[id] = name
            }

            btnDone.visibility = if (pickedMap.isNotEmpty()) View.VISIBLE else View.GONE
            btnDone.text = "Done (${pickedMap.size})"
        }
    }

    private fun setUpListeners() = with(binding) {
        rvGroups.layoutManager = LinearLayoutManager(requireContext())
        rvGroups.adapter = adapter
        etSearch.addTextChangedListener { text ->
            viewModel.processIntent(ExercisesIntent.SearchChanged(text?.toString().orEmpty()))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progress.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                adapter.submitList(state.filteredGroups)
            }
        }
    }

    private fun goToCalendarPage() {
        binding.btnDone.setOnClickListener {

            val pickedIds = pickedMap.keys.toTypedArray()
            val pickedNames = pickedMap.values.toTypedArray()

            parentFragmentManager.setFragmentResult(
                com.example.gymtracker.presentation.common.ResultKeys.REQ_PICK_EXERCISES,
                androidx.core.os.bundleOf(
                    com.example.gymtracker.presentation.common.ResultKeys.KEY_IDS to pickedIds,
                    com.example.gymtracker.presentation.common.ResultKeys.KEY_NAMES to pickedNames
                )
            )
            pickedMap.clear()
            binding.btnDone.visibility = View.GONE
            findNavController().popBackStack()
        }
    }

    private fun addNewExercise() = with(binding) {
        btnNewExercise.setOnClickListener {
            val groups = viewModel.state.value.groups
            val ids = ArrayList(groups.map { it.id })
            val titles = ArrayList(groups.map { it.title })

            NewExerciseDialogFragment
                .newInstance(ids, titles)
                .show(childFragmentManager, "NewExerciseDialog")
        }

    }

    private fun closeDialog() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { eff ->
                when (eff) {
                    ExercisesEffect.CloseNewExerciseDialog ->
                        childFragmentManager.findFragmentByTag("NewExerciseDialog")
                            ?.let { (it as DialogFragment).dismiss() }

                    is ExercisesEffect.ShowMessage -> {

                        Toast.makeText(
                            requireContext(),
                            eff.text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }
}