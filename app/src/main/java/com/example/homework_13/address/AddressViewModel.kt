package com.example.homework_13.address

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressViewModel : ViewModel() {
    private val _addresses = MutableLiveData<List<Address>>(emptyList())

    val addresses : LiveData<List<Address>> get() = _addresses

    private val _selectedAddress = MutableLiveData<Address?>(null)
    val selectedAddress : LiveData<Address?> get() = _selectedAddress

    fun addAddress(address : Address) {
        val list = _addresses.value?.toMutableList() ?: mutableListOf()
        list.add(0,address)
        _addresses.value = list

        Log.d("DEBUG_TAG", "Address added: $address | List now: ${_addresses.value}")
    }
    fun deleteAddress(address : Address) {
        val list = _addresses.value?.toMutableList() ?: mutableListOf()
        list.removeAll {it.id == address.id}
        _addresses.value = list
    }
    fun selectAddress(address : Address) {
        _selectedAddress.value = address
    }

    fun updateAddress(updated : Address) {
        val list = _addresses.value?.toMutableList() ?: mutableListOf()
        val index = list.indexOfFirst{it.id == updated.id}

        if(index != -1) {
            list[index] = updated
            _addresses.value = list
        }
    }
    fun clear() {
        _selectedAddress.value = null
    }
}