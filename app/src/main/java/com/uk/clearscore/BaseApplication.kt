package com.uk.clearscore

import android.app.Application
import com.androidnetworking.AndroidNetworking
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Package com.uk.clearscore in

 * Project ClearScore

 * Created by Maxwell on 05/12/2021
 */
@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(configuration)
        AndroidNetworking.initialize(this)
    }
}