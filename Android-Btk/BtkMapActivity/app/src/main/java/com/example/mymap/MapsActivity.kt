package com.example.mymap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mymap.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(listener)

        // Add a marker in Sydney and move the camera

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                //println(location.latitude)
                //println(location.longitude)
                val currentLocation = LatLng(location.latitude, location.longitude)
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
                //LatLng to address
                 val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
                try {


                    val adress = geocoder.getFromLocation(location.latitude,location.longitude,1)

                    println(adress)



                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
        if (ContextCompat.checkSelfPermission(
                this@MapsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //no access
            ActivityCompat.requestPermissions(
                this@MapsActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        } else {
            //have access
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000,
                1f,
                locationListener
            )
            //last location
            val lastKnowLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastKnowLocation != null) {
                val lastLocationLatLng =
                    LatLng(lastKnowLocation.latitude, lastKnowLocation.longitude)
                mMap.addMarker(
                    MarkerOptions().position(lastLocationLatLng).title("Last Know Location")
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocationLatLng, 10f))

            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0) {
                if (ContextCompat.checkSelfPermission(
                        this@MapsActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    val listener = object : GoogleMap.OnMapClickListener{
        override fun onMapClick(p0: LatLng) {
            mMap.clear()

            val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())

            if(p0 != null){
                try {
                    val address = geocoder.getFromLocation(p0.latitude,p0.longitude,1)
                    if(address != null){
                        if(address.size > 0){
                            println(address.get(0))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p0,10f))
                            mMap.addMarker(MarkerOptions().position(p0).title(address.get(0).locality.toString()))
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

    }
}