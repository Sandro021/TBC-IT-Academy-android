package com.example.homework_29.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil3.load
import com.example.homework_29.R
import com.example.homework_29.databinding.BottomsheetLocationBinding
import com.example.homework_29.databinding.FragmentMapBinding
import com.example.homework_29.domain.model.LocationItem
import com.example.homework_29.presentation.map.LocationClusterItem
import com.example.homework_29.presentation.common.BaseFragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()

    private var googleMap: GoogleMap? = null
    private var clusterManager: ClusterManager<LocationClusterItem>? = null

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) viewModel.process(MapIntent.PermissionGranted)
            else viewModel.process(MapIntent.PermissionDenied)
        }

    override fun bind() {
        setUpFragments()

    }

    override fun listeners() {
        setUpZoomListeners()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = true

        setupClustering(map)


        if (hasFineLocation()) {
            viewModel.process(MapIntent.PermissionGranted)
        }
    }

    private fun setUpZoomListeners() = with(binding) {
        btnZoomIn.setOnClickListener {
            val map = googleMap
            if (map == null) {
                Toast.makeText(requireContext(), "Map not ready yet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        btnZoomOut.setOnClickListener {
            val map = googleMap
            if (map == null) {
                Toast.makeText(requireContext(), "Map not ready yet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }


        btnZoomIn.bringToFront()
        btnZoomOut.bringToFront()
    }


    private fun setUpFragments() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progress.isVisible = state.isLoading


                if (!state.hasPermission && state.errorMessage != null) {
                    Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG).show()
                }


                if (googleMap != null) renderClusters(state.locations)


                state.selected?.let { showLocationBottomSheet(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { eff ->
                when (eff) {
                    MapEffect.RequestPermission -> ensureLocationPermission()
                    MapEffect.MoveCameraToMyLocation -> moveCameraToMyLocation()
                    is MapEffect.ShowError ->
                        Toast.makeText(requireContext(), eff.message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        viewModel.process(MapIntent.ScreenOpened)
    }

    private fun hasFineLocation(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun moveCameraToMyLocation() {
        val map = googleMap ?: return
        if (!hasFineLocation()) return

        map.isMyLocationEnabled = true

        val client = LocationServices.getFusedLocationProviderClient(requireActivity())
        client.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                val latLng = LatLng(loc.latitude, loc.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
            } else {

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.7151, 44.8271), 11f))
            }
        }
    }

    private fun ensureLocationPermission() {
        if (hasFineLocation()) {
            viewModel.process(MapIntent.PermissionGranted)
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun setupClustering(map: GoogleMap) {
        val cm = ClusterManager<LocationClusterItem>(requireContext(), map)
        clusterManager = cm

        map.setOnCameraIdleListener(cm)
        map.setOnMarkerClickListener(cm)

        cm.setOnClusterItemClickListener { clusterItem ->
            viewModel.process(MapIntent.MarkerClicked(clusterItem.item))
            true
        }
    }

    private fun renderClusters(items: List<LocationItem>) {
        val cm = clusterManager ?: return
        cm.clearItems()
        cm.addItems(items.map { LocationClusterItem(it) })
        cm.cluster()
    }

    private fun showLocationBottomSheet(item: LocationItem) {
        val dialog = BottomSheetDialog(requireContext())

        val sheetBinding = BottomsheetLocationBinding.inflate(layoutInflater)

        sheetBinding.title.text = item.title
        sheetBinding.desc.text = item.description
        sheetBinding.img.load(item.imageUrl)

        dialog.setContentView(sheetBinding.root)

        dialog.setOnDismissListener {
            viewModel.process(MapIntent.BottomSheetDismissed)
        }

        dialog.show()
    }

}