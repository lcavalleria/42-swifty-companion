package com.lcavalle.switfy_companion

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SwiftyCompanion : Application() {
    companion object {
        const val TAG = "StudentInfoFragment"
    }
}
