package mx.dev.marvelapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stories(
    @SerializedName("available") var available: String,
    @SerializedName("collectionURI") var collectionURI: String,
    @SerializedName("items") var items: List<Item>
) : Serializable {
}