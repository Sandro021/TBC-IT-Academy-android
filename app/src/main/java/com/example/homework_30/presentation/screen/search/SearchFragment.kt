package com.example.homework_30.presentation.screen.search


import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_30.databinding.FragmentSearchBinding
import com.example.homework_30.presentation.common.BaseFragment
import com.example.homework_30.presentation.common.showSnackBar
import com.example.homework_30.presentation.screen.search.adapter.SearchAdapter
import com.example.homework_30.presentation.screen.search.contract.SearchEffect
import com.example.homework_30.presentation.screen.search.contract.SearchIntent
import com.example.homework_30.presentation.screen.search.contract.SearchState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy {
        SearchAdapter { clickedItem ->
            viewModel.onEvent(SearchIntent.OnItemClicked(clickedItem))
        }
    }

    override fun bind() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        handleState(state)
                    }
                }
                launch {
                    viewModel.effect.collect { effect ->
                        handleEffect(effect)
                    }
                }
            }
        }
    }

    override fun listeners() {
        binding.etSearch.doAfterTextChanged { text ->
            viewModel.onEvent(SearchIntent.OnSearchQueryChanged(text.toString()))
        }
    }

    private fun handleState(state: SearchState) {
        binding.progressBar.isVisible = state.isLoading
        searchAdapter.submitList(state.categories)
    }

    private fun handleEffect(effect: SearchEffect) {
        when (effect) {
            is SearchEffect.ShowError -> {
                binding.root.showSnackBar(effect.message)
            }
        }
    }
}