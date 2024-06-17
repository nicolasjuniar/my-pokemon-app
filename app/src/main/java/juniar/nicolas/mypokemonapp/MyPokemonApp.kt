package juniar.nicolas.mypokemonapp

import android.app.Application
import juniar.nicolas.mypokemonapp.inject.AppModules
import juniar.nicolas.mypokemonapp.inject.networkModule
import juniar.nicolas.mypokemonapp.inject.repositoryModule
import juniar.nicolas.mypokemonapp.inject.viewModelModule
import juniar.nicolas.mypokemonapp.util.PokemonPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MyPokemonApp : Application() {

    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()
        koinApplication = startKoin {
            androidContext(this@MyPokemonApp.applicationContext)
            modules(
                listOf(
                    AppModules,
                    networkModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}