package mx.dev.marvelapp.data.network

import mx.dev.marvelapp.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getCharacters(@Url url: String) : Response<CharacterResponse>
}