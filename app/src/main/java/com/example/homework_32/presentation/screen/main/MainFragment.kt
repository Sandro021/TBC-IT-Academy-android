package com.example.homework_32.presentation.screen.main

import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.homework_32.databinding.FragmentMainBinding
import com.example.homework_32.presentation.common.BaseFragment
import com.example.homework_32.presentation.common.showSnackBar
import com.example.homework_32.presentation.helper.ImageCompressor
import com.example.homework_32.presentation.screen.bottomsheet.ImagePickerBottomSheet
import com.example.homework_32.presentation.screen.main.contract.MainEffect
import com.example.homework_32.presentation.screen.main.contract.MainEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {


    override fun bind() {
        observeState()
        observeEffects()
    }

    override fun listeners() = with(binding) {
        btnPick.setOnClickListener { showPickerSheet() }
        btnUpload.setOnClickListener { viewModel.onEvent(MainEvent.UploadClicked) }
    }

    private val viewModel: MainViewModel by viewModels()

    private var cameraTempUri: Uri? = null


    private val pickFromGallery =
        registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.GetContent()) { uri ->

            uri?.let {
                val compressed = ImageCompressor.compress(requireContext(), it, quality = 80)
                viewModel.onEvent(MainEvent.ImageSelected(compressed))
            }
        }

    private val takePicture =
        registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.TakePicture()) { success ->
            if (success) {

                cameraTempUri?.let {
                    val compressed = ImageCompressor.compress(requireContext(), it, quality = 80)
                    viewModel.onEvent(MainEvent.ImageSelected(compressed))
                }
            }
        }

    private val requestCameraPermission =
        registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openCamera()
            else binding.root.showSnackBar("Camera permission denied")
        }

    private fun showPickerSheet() {
        ImagePickerBottomSheet(
            onGallery = { pickFromGallery.launch("image/*") },
            onCamera = { checkCameraPermissionThenOpen() },
            onUpload = { viewModel.onEvent(MainEvent.UploadClicked) }
        ).show(parentFragmentManager, "ImagePickerBottomSheet")
    }

    private fun checkCameraPermissionThenOpen() {
        val granted = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) openCamera()
        else requestCameraPermission.launch(android.Manifest.permission.CAMERA)
    }

    private fun openCamera() {
        val file = File.createTempFile("camera_", ".jpg", requireContext().cacheDir)
        cameraTempUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.file provider",
            file
        )
        takePicture.launch(cameraTempUri)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progress.visibility =
                    if (state.isUploading) android.view.View.VISIBLE else android.view.View.GONE

                state.selectedUri?.let { uri ->
                    binding.ivPreview.load(uri)
                }

                binding.tvUrl.text = state.uploadedUrl ?: ""
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is MainEffect.ShowMessage -> binding.root.showSnackBar(effect.text)
                }
            }
        }
    }
}