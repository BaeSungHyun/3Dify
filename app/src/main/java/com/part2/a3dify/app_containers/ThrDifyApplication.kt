package com.part2.a3dify.app_containers

import android.app.Application

// Invoked by (application as ThrDifyApplication) in activity and fragment
class ThrDifyApplication : Application() {
    val mainContainer = MainContainer()
}