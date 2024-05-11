package com.example.clustermap

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class User(val userName :String ,val latLng: LatLng) : ClusterItem {
    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String {
        return userName
    }

    override fun getSnippet(): String {
        return ""
    }
}