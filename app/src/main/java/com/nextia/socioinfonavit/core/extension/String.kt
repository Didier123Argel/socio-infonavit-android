package com.nextia.socioinfonavit.core.extension

import android.util.Patterns

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()