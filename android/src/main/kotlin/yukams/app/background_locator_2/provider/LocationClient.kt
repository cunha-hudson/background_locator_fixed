package yukams.app.background_locator_2.provider

enum class LocationClient(val value: Int) {
    Google(0), Android(1);

    companion object {
        fun fromInt(value: Int) = LocationClient.entries.firstOrNull { it.value == value }
    }
}