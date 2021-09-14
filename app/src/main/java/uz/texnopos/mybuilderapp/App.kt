package uz.texnopos.mybuilderapp

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.texnopos.mybuilderapp.core.SharedPrefUtils
import uz.texnopos.mybuilderapp.data.di.*

class App : MultiDexApplication() {
    private val modules = listOf(
        repositoryModule, viewModelModule, retrofitModule, apiModule,
        firebaseModule
    )

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            koin.loadModules(modules)
        }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private lateinit var appInstance: App
        var sharedPrefUtils: SharedPrefUtils? = null

        fun getAppInstance(): App {
            return appInstance
        }
    }

}