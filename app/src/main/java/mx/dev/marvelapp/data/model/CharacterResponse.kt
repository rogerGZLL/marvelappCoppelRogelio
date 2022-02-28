package mx.dev.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
        @SerializedName("code") var code: Int,
        @SerializedName("status") var status: String,
        @SerializedName("copyright") var copyright: String,
        @SerializedName("data") var data: Data,
    ) {

}