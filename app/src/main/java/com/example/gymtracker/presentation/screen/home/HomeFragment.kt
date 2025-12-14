package com.example.gymtracker.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentHomeBinding
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: TodayViewModel by viewModels()
    private val weekAdapter by lazy {
        WeekAdapter { day ->
            viewModel.processIntent(TodayIntent.DateSelected(day.date))
        }
    }

    override fun bind() {
        setUpWeekRecycler()
    }

    override fun listeners() {
        binding.btnAddExercises.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_userFragment)
        }
    }

    private fun setUpWeekRecycler() = with(binding) {
        rvWeek.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvWeek.adapter = weekAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { st ->
                weekAdapter.submitList(st.week)
            }
        }
    }

}