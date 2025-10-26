package com.example.homework_13.screen


import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.homework_13.address.Address
import com.example.homework_13.address.AddressViewModel
import com.example.homework_13.R
import com.example.homework_13.common.BaseFragment
import com.example.homework_13.databinding.FragmentAddAddressesBinding

class AddAddressesFragment :
    BaseFragment<FragmentAddAddressesBinding>(FragmentAddAddressesBinding::inflate) {
    override fun bind() {
        setUp()
    }

    private val viewModel: AddressViewModel by navGraphViewModels(R.id.nav_graph)
    private var editingAddress: Address? = null
    override fun listeners() {}


    private fun setUp() {
        editAddress()
        addAddress()
        turnOffRadioButton()
    }

    private fun editAddress() = with(binding) {
        viewModel.selectedAddress.observe(viewLifecycleOwner) { selected ->
            if (selected != null) {
                editingAddress = selected
                when (selected.type) {
                    "Home" -> rbHome.isChecked = true
                    "Office" -> rbOffice.isChecked = true
                }
                etAddress.setText(selected.address)
                btAdd.text = getString(R.string.update)
            } else {
                btAdd.text = getString(R.string.add)
            }
        }
    }

    private fun addAddress() = with(binding) {
        btAdd.setOnClickListener {
            val type = if (rbHome.isChecked) "Home" else "Office"
            val addressText = etAddress.text.toString()
            if (addressText.isEmpty()) return@setOnClickListener

            if (editingAddress != null) {
                viewModel.updateAddress(
                    editingAddress!!.copy(type = type, address = addressText)
                )
            } else {
                viewModel.addAddress(Address(type = type, address = addressText))
            }
            findNavController().navigateUp()

        }
    }

    private fun turnOffRadioButton() = with(binding) {
        rbOffice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.rbHome.isChecked = false
        }

        rbHome.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.rbOffice.isChecked = false
        }

    }




}