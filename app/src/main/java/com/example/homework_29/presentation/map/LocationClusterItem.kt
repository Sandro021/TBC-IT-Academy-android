package com.example.homework_29.presentation.map

import com.example.homework_29.domain.model.LocationItem
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class LocationClusterItem(val item: LocationItem) : ClusterItem {
    override fun getPosition() = LatLng(item.latitude, item.longitude)

    override fun getTitle() = item.title

    override fun getSnippet() = item.description

    override fun getZIndex() = 0f
}