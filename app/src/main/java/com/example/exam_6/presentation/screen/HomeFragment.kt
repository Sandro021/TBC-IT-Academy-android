package com.example.exam_6.presentation.screen

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.exam_6.databinding.FragmentHomeBinding
import com.example.exam_6.presentation.adapter.WorkspacePagingAdapter
import com.example.exam_6.presentation.common.BaseFragment
import com.example.exam_6.presentation.intent.HomeIntent
import com.example.exam_6.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: WorkspacePagingAdapter


    override fun bind() {
        setUp()
    }

    override fun listeners() {}

    private fun setUp() = with(binding) {
        adapter = WorkspacePagingAdapter()
        vpWorkspaces.adapter = adapter
        vpWorkspaces.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpWorkspaces.offscreenPageLimit = 1

        viewModel.processIntent(HomeIntent.LoadWorkSpaces)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.workspaces.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
        }

    }

}