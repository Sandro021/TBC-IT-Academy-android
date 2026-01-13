package com.example.homework_32.presentation.screen.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework_32.databinding.BottomsheetImagePickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImagePickerBottomSheet(
    private val onGallery: () -> Unit,
    private val onCamera: () -> Unit,
    private val onUpload: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetImagePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        btnGallery.setOnClickListener {
            dismiss()
            onGallery()
        }
        btnCamera.setOnClickListener {
            dismiss()
            onCamera()
        }
        btnUpload.setOnClickListener {
            dismiss()
            onUpload()
        }
    }
}