package com.evaluation.serial

import com.evaluation.utils.SERIAL

class TestSerialProvider : SerialProvider() {
    override val serial: String = SERIAL
}