package juniar.nicolas.mypokemonapp.inject

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import juniar.nicolas.mypokemonapp.BuildConfig
import juniar.nicolas.mypokemonapp.util.Constant.Companion.NAMED_LOCAL_OKHTTP
import juniar.nicolas.mypokemonapp.util.Constant.Companion.NAMED_LOCAL_RETROFIT
import juniar.nicolas.mypokemonapp.util.Constant.Companion.NAMED_POKEAPI_OKHTTP
import juniar.nicolas.mypokemonapp.util.Constant.Companion.NAMED_POKEAPI_RETROFIT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    single(named(NAMED_POKEAPI_OKHTTP)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        OkHttpClient.Builder().apply {
            addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }.build()
    }

    single(named(NAMED_LOCAL_OKHTTP)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        OkHttpClient.Builder().apply {
            addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }.build()
    }

    single(named(NAMED_POKEAPI_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_POKEAPI)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>(named(NAMED_POKEAPI_OKHTTP)))
            .build()
    }

    single(named(NAMED_LOCAL_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_LOCAL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>(named(NAMED_LOCAL_OKHTTP)))
            .build()
    }
}