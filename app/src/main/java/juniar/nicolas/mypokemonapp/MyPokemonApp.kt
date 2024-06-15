package juniar.nicolas.mypokemonapp

import android.app.Application
import juniar.nicolas.mypokemonapp.inject.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MyPokemonApp : Application() {

    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()
        koinApplication = startKoin {
            androidContext(this@MyPokemonApp.applicationContext)
            modules(listOf(AppModules))
        }
    }
}