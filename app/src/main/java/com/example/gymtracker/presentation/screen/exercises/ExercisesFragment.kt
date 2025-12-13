package com.example.gymtracker.presentation.screen.exercises

import android.view.View
import androidx.core.widget.addTextChangedListener
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

    }

    override fun listeners() {
        setUpListeners()
    }

    private fun setUpListeners() = with(binding) {
        rvGroups.layoutManager = LinearLayoutManager(requireContext())
        rvGroups.adapter = adapter
        btnNewExercise.setOnClickListener {
            viewModel.processIntent(ExercisesIntent.NewExerciseClicked)
        }
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

}