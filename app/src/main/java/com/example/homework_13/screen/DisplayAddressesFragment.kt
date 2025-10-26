package com.example.homework_13.screen

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.homework_13.address.AddressViewModel
import com.example.homework_13.R
import com.example.homework_13.adapter.AddressAdapter
import com.example.homework_13.common.BaseFragment
import com.example.homework_13.databinding.FragmentDisplayAddressesBinding

class DisplayAddressesFragment :
    BaseFragment<FragmentDisplayAddressesBinding>(FragmentDisplayAddressesBinding::inflate) {
    override fun bind() {
        setUp()

    }

    private val viewModel: AddressViewModel by navGraphViewModels(R.id.nav_graph)

    private lateinit var adapter: AddressAdapter


    override fun listeners() {}

    private fun displayToAddNewAddress() {
        findNavController().navigate(R.id.action_displayAddressesFragment_to_addAddressesFragment)
    }

    private fun setUp() {
        setUpAdapter()
    }

    private fun setUpAdapter()  {
        adapter = AddressAdapter(onClick = { address ->
            viewModel.selectAddress(address)
            findNavController().navigate(R.id.action_displayAddressesFragment_to_addAddressesFragment)
        }, onLongClick = { address -> viewModel.deleteAddress(address) })


        binding.rvAddresses.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvAddresses.adapter = adapter
        viewModel.addresses.observe(viewLifecycleOwner) {
            android.util.Log.d("DEBUG_TAG", "Observed addresses: $it")
            adapter.submitList(it)
        }

        binding.btAddNew.setOnClickListener {
            viewModel.clear()
            displayToAddNewAddress()
        }
    }

}




