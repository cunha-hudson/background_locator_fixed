package yukams.app.background_locator_2.provider

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.location.*

class GoogleLocationProviderClient(context: Context, override var listener: LocationUpdateListener?) : BLLocationProvider {
    private val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = LocationListener(listener)

    override fun removeLocationUpdates() {
        client.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(request: LocationRequestOptions) {
        client.requestLocationUpdates(getLocationRequest(request), locationCallback, null)
    }

    private fun getLocationRequest(request: LocationRequestOptions): LocationRequest {
        val locationRequestBuilder = LocationRequest.Builder(request.interval).apply {
        setIntervalMillis(request.interval)
        setMinUpdateIntervalMillis(request.interval) // Use setMinUpdateIntervalMillis para o 'fastestInterval'
        setMaxUpdateDelayMillis(request.interval) // Use setMaxUpdateDelayMillis para o 'maxWaitTime'
        setPriority(request.accuracy)
        setMinUpdateDistanceMeters(request.distanceFilter)
    }

    return locationRequestBuilder.build()

    }
}

private class LocationListener(val listener: LocationUpdateListener?) : LocationCallback() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onLocationResult(location: LocationResult) {
        listener?.onLocationUpdated(LocationParserUtil.getLocationMapFromLocation(location))
    }
}