package com.example.gymtracker.presentation.screen.exercises

import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymtracker.databinding.FragmentExercisesBinding
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExercisesFragment :
    BaseFragment<FragmentExercisesBinding>(FragmentExercisesBinding::inflate) {

    private val viewModel: ExercisesViewModel by viewModels()
    private val adapter by lazy {
        ExerciseGroupsAdapter { group ->
            viewModel.processIntent(
                ExercisesIntent.GroupClicked(group.id, group.title)
            )
        }
    }

    override fun bind() {
        addNewExercise()
        closeDialog()
    }

    override fun listeners() {
        setUpListeners()
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