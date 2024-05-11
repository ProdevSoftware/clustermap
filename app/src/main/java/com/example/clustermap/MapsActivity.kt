package com.example.clustermap

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.clustermap.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager

class MapsActivity : AppCompatActivity(),OnMapReadyCallback, ClusterManager.OnClusterClickListener<User>{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var userList :ArrayList<User> = arrayListOf()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var mLocationRequest :LocationRequest? = null
    private var userIsInteractingWithMap = false
    var mCurrLocationMarker: Marker? = null
    private var latitude: Double = 0.toDouble()
    private var longitude: Double =0.toDouble()
    private var currentMapLocationClicked: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnCurrentLocation.setOnClickListener {
            updateCurrentLocation()
            currentMapLocationClicked = !currentMapLocationClicked
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitude, longitude),
                    if (currentMapLocationClicked) 10f else 10f
                )
            )
            userIsInteractingWithMap = false

        }

        mapFragment.getMapAsync(this)
        mapFragment.getMapAsync {
            setUpClusterManager(mMap)
        }
    }

    private fun setUpClusterManager(mMap: GoogleMap) {
        val clusterManager = ClusterManager<User>(this, mMap)
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        clusterManager.setOnClusterClickListener(this) // Set cluster click listener
        userList = getAllItem()
        clusterManager.addItems(userList)
        clusterManager.cluster()
    }

    override fun onClusterClick(cluster: Cluster<User>): Boolean {
        // Return true to consume the click event and prevent camera movement
        return false
    }

    private fun getAllItem(): java.util.ArrayList<User> {
        var arrayList: ArrayList<User> = ArrayList()
        val latLng = LatLng(23.0225, 72.5714)
        val latLng1 = LatLng(21.1702, 72.8311)
        val latLng2 = LatLng(22.3072, 73.1812)
        val latLng3 = LatLng(22.3039, 70.8022)
        val latLng4 = LatLng(21.7645, 72.1519)
        val latLng5 = LatLng(23.2156, 72.6369)
        val latLng6 = LatLng(22.4707, 70.0577)
        val latLng7 = LatLng(21.5222, 70.4579)
        val latLng8 = LatLng(22.5645, 72.9289)
        val latLng9 = LatLng(22.6915, 72.8612)
        val latLng10 = LatLng(22.8177, 70.8364)
        val latLng11 = LatLng(23.0767, 70.1337)
        val latLng12 = LatLng(22.7253, 71.6379)
        val latLng13 = LatLng(20.3858, 72.9115)
        val latLng14 = LatLng(20.5991, 72.9342)
        val latLng15 = LatLng(21.6437, 73.0104)
        val latLng16 = LatLng(20.9524, 72.9322)
        val latLng17 = LatLng(24.1714, 72.4386)

        val user1 = User("Person1",latLng)
        val user2 = User("Person2",latLng1)
        val user3 = User("Person3",latLng2)
        val user4 = User("Person4",latLng3)
        val user5 = User("Person5",latLng4)
        val user6 = User("Person6",latLng5)
        val user7 = User("Person7",latLng6)
        val user8 = User("Person8",latLng7)
        val user9 = User("Person9",latLng8)
        val user10 = User("Person10",latLng9)
        val user11 = User("Person11",latLng10)
        val user12 = User("Person12",latLng11)
        val user13 = User("Person13",latLng12)
        val user14 = User("Person14",latLng13)
        val user15 = User("Person15",latLng14)
        val user16 = User("Person16",latLng15)
        val user17 = User("Person17",latLng16)
        val user18 = User("Person18",latLng17)

        arrayList.add(user1)
        arrayList.add(user2)
        arrayList.add(user3)
        arrayList.add(user4)
        arrayList.add(user5)
        arrayList.add(user6)
        arrayList.add(user7)
        arrayList.add(user8)
        arrayList.add(user9)
        arrayList.add(user10)
        arrayList.add(user11)
        arrayList.add(user12)
        arrayList.add(user13)
        arrayList.add(user14)
        arrayList.add(user15)
        arrayList.add(user16)
        arrayList.add(user17)
        arrayList.add(user18)

        return arrayList
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            p0.isMyLocationEnabled = false
            p0.uiSettings.isMyLocationButtonEnabled = false
            p0.uiSettings.isMapToolbarEnabled = false
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Add marker at current location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(currentLatLng)
                        .title("Current Location")
                )
                // Move camera to current location
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 5f))
            }
        }
        // Add an OnCameraIdleListener to set userIsInteractingWithMap to true when the camera becomes idle
        mMap.setOnCameraIdleListener {
            userIsInteractingWithMap = true
        }
    }

    private fun updateCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = false
            mLocationRequest =
                LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000).setFastestInterval(5000)

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        // Update the marker's position
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        if (!userIsInteractingWithMap) { // Only update if user is not interacting with the map
                            if (mCurrLocationMarker == null) {
                                // Marker not yet initialized, create a new marker
                                mCurrLocationMarker = mMap.addMarker(
                                    MarkerOptions()
                                        .position(currentLatLng)
                                        .title("Current Location")
                                )
                            } else {
                                // Marker already exists, update its position
                                mCurrLocationMarker?.position = currentLatLng
                            }

                            latitude = location.latitude
                            longitude = location.longitude

                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng,
                                    if (currentMapLocationClicked) 10f else 10.5f
                                )
                            )
                        }
                    }
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest!!, locationCallback, Looper.getMainLooper()
            )
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
    }
}