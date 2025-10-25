package yukams.app.background_locator_2.provider

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.location.LocationResult
import yukams.app.background_locator_2.Keys

class LocationParserUtil {
    companion object {
        @RequiresApi(Build.VERSION_CODES.S)
        fun getLocationMapFromLocation(location: Location): HashMap<Any, Any> {
            val speedAccuracy: Float = location.speedAccuracyMetersPerSecond
            val isMocked: Boolean = location.isMock

            return HashMap<Any, Any>().apply {
                this[Keys.ARG_IS_MOCKED] = isMocked
                this[Keys.ARG_LATITUDE] = location.latitude
                this[Keys.ARG_LONGITUDE] = location.longitude
                this[Keys.ARG_ACCURACY] = location.accuracy
                this[Keys.ARG_ALTITUDE] = location.altitude
                this[Keys.ARG_SPEED] = location.speed
                this[Keys.ARG_SPEED_ACCURACY] = speedAccuracy
                this[Keys.ARG_HEADING] = location.bearing
                this[Keys.ARG_TIME] = location.time.toDouble()
                this[Keys.ARG_PROVIDER] = location.provider ?: ""
            }
        }

        @RequiresApi(Build.VERSION_CODES.S)
        fun getLocationMapFromLocation(location: LocationResult?): HashMap<Any, Any>? {
            val firstLocation = location?.lastLocation ?: return null
            val speedAccuracy: Float = firstLocation.speedAccuracyMetersPerSecond
            val isMocked: Boolean = firstLocation.isMock

            return HashMap<Any, Any>().apply {
                this[Keys.ARG_IS_MOCKED] = isMocked
                this[Keys.ARG_LATITUDE] = firstLocation.latitude
                this[Keys.ARG_LONGITUDE] = firstLocation.longitude
                this[Keys.ARG_ACCURACY] = firstLocation.accuracy
                this[Keys.ARG_ALTITUDE] = firstLocation.altitude
                this[Keys.ARG_SPEED] = firstLocation.speed
                this[Keys.ARG_SPEED_ACCURACY] = speedAccuracy
                this[Keys.ARG_HEADING] = firstLocation.bearing
                this[Keys.ARG_TIME] = firstLocation.time.toDouble()
            }
        }
    }
}