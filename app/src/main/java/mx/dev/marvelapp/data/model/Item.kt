package mx.dev.marvelapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
    @SerializedName("resourceURI") var resourceURI: String,
    @SerializedName("name") var name: String,
) : Serializable {
}