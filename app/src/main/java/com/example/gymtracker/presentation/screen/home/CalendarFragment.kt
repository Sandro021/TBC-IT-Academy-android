package com.example.gymtracker.presentation.screen.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentHomeBinding
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: CalendarViewModel by viewModels()

    private val calendarAdapter by lazy {
        CalendarAdapter { day ->
            viewModel.processIntent(CalendarIntent.DateSelected(day.date))
        }
    }
    private val workoutAdapter by lazy {
        WorkoutItemsAdapter(
            onAddSetClicked = { id -> viewModel.processIntent(CalendarIntent.AddSetClicked(id)) },
            onSetWeightChanged = { id, setNum, v ->
                viewModel.processIntent(CalendarIntent.SetWeightChanged(id, setNum, v))
            },
            onSetRepsChanged = { id, setNum, v ->
                viewModel.processIntent(CalendarIntent.SetRepsChanged(id, setNum, v))
            },
            onLongPressDelete = { item ->
                viewModel.processIntent(CalendarIntent.DeleteExercise(item.exerciseId))
            }
        )
    }

    private var didScrollToToday = false

    override fun bind() {
        setupCalendar()
        setupFragmentResult()
        setupLogout()
        observeState()
    }

    override fun listeners() {
        goToAddExercises()
        uploadExercises()

    }

    private fun goToAddExercises() {
        binding.btnAddExercises.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_userFragment)
        }
    }

    private fun uploadExercises() = with(binding) {
        btnDone.setOnClickListener {
            root.clearFocus()
            viewModel.processIntent(CalendarIntent.SaveClicked)
        }
    }


    private fun setupCalendar() = with(binding) {
        rvWeek.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvWeek.adapter = calendarAdapter

        rvExercises.layoutManager = LinearLayoutManager(requireContext())
        rvExercises.adapter = workoutAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { st ->
                tvToday.text = st.headerTitle
                calendarAdapter.submitList(st.calendarDays)

                val isEmpty = st.items.isEmpty()
                imgEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
                tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
                btnDone.isEnabled = st.items.isNotEmpty()
                workoutAdapter.submitList(st.items)

                if (!didScrollToToday && st.calendarDays.isNotEmpty()) {
                    didScrollToToday = true
                    rvWeek.scrollToPosition(st.todayIndex)
                }

            }
        }
    }

    private fun setupLogout() = with(binding) {
        btLogOut.setOnClickListener {
            viewModel.processIntent(CalendarIntent.ClickLogout)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.navigateToLogin) {
                        findNavController().navigate(
                            R.id.action_homeFragment2_to_loginFragment2
                        )
                        viewModel.processIntent(CalendarIntent.NavigationHandled)
                    }
                }
            }
        }
    }


    private fun setupFragmentResult() {
        parentFragmentManager.setFragmentResultListener(
            com.example.gymtracker.presentation.common.ResultKeys.REQ_PICK_EXERCISES,
            viewLifecycleOwner
        ) { _, bundle ->
            val ids =
                bundle.getStringArray(com.example.gymtracker.presentation.common.ResultKeys.KEY_IDS)
                    ?.toList().orEmpty()
            val names =
                bundle.getStringArray(com.example.gymtracker.presentation.common.ResultKeys.KEY_NAMES)
                    ?.toList().orEmpty()

            viewModel.processIntent(CalendarIntent.ExercisesPicked(ids.zip(names)))

        }
    }
}
