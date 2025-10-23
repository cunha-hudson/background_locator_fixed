package yukams.app.background_locator_2.provider

import android.location.Location
import android.os.Build
import com.google.android.gms.location.LocationResult
import yukams.app.background_locator_2.Keys

class LocationParserUtil {
    companion object {
        fun getLocationMapFromLocation(location: Location): HashMap<Any, Any> {
            var speedAccuracy = 0f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                speedAccuracy = location.speedAccuracyMetersPerSecond
            }
            var isMocked = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                isMocked = location.isFromMockProvider
            }

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

        fun getLocationMapFromLocation(location: LocationResult?): HashMap<Any, Any>? {
            val firstLocation = location?.lastLocation ?: return null

            var speedAccuracy = 0f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                speedAccuracy = firstLocation.speedAccuracyMetersPerSecond
            }
            var isMocked = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                isMocked = firstLocation.isFromMockProvider
            }

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