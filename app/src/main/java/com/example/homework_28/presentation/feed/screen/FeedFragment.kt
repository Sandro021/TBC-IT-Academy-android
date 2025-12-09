package com.example.homework_28.presentation.feed.screen

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_28.databinding.FragmentFeedBinding
import com.example.homework_28.presentation.feed.intent.FeedIntent
import com.example.homework_28.presentation.feed.viewmodel.FeedViewModel
import com.example.homework_28.presentation.common.BaseFragment
import com.example.homework_28.presentation.feed.adapter.StoriesAdapter
import com.example.homework_28.presentation.feed.adapter.PostsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate) {

    private val viewModel: FeedViewModel by viewModels()

    private val storiesAdapter by lazy {
        StoriesAdapter()
    }
    private val postsAdapter by lazy {
        PostsAdapter()
    }

    override fun bind() = with(binding) {
        rvStories.apply {
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
        }
        rvPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }
        viewModel.processIntent(FeedIntent.Load)
    }

    override fun listeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progressBar.visibility =
                    if (state.isLoading == true) View.VISIBLE else
                        View.GONE

                storiesAdapter.submitList(state.stories)
                postsAdapter.submitList(state.posts)

                state.error?.let { errorMsg ->
                    Snackbar.make(
                        binding.root,
                        errorMsg,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}