package com.ceiba.domain.parking.exception

import java.lang.Exception
import java.lang.RuntimeException

class GlobalException(message: String, exception: Exception) :
    RuntimeException(message, exception) {
}
