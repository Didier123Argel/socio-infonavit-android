package com.nextia.socioinfonavit.core.utils

import com.nextia.socioinfonavit.core.exception.Failure

interface OnFailure {
    fun handleFailure(failure: Failure?)
}