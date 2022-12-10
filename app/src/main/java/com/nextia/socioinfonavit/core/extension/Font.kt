package com.nextia.socioinfonavit.core.extension

import android.content.res.AssetManager
import android.graphics.Typeface

fun AssetManager.getMontserratRegular(): Typeface {
    return Typeface.createFromAsset(this, "fonts/Montserrat-Regular.ttf")
}

fun AssetManager.getMontserratBold(): Typeface {
    return Typeface.createFromAsset(this, "fonts/Montserrat-Bold.ttf")
}

fun AssetManager.getMontserratItalic(): Typeface {
    return Typeface.createFromAsset(this, "fonts/Montserrat-Italic.ttf")
}