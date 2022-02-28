package mx.dev.marvelapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Thumbnail(
    @SerializedName("path") var path:String,
    @SerializedName("extension") var extension:String
) : Serializable {
}