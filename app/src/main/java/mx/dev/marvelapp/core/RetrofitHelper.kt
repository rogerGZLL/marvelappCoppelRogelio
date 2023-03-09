package mx.dev.marvelapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(ConstantsApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}