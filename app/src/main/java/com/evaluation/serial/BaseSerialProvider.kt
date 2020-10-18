package com.evaluation.serial

import android.os.Build
import javax.inject.Inject

class BaseSerialProvider @Inject constructor() : SerialProvider() {

    override val serial: String
        get() = serialNumber()

    @android.annotation.SuppressLint("HardwareIds")
    private fun serialNumber(): String {
        val deviceId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    Build.getSerial()
                } catch (e: SecurityException) {
                    Build.SERIAL
                }
            } else Build.SERIAL
        return deviceId.toString()
    }

}