package mx.dev.marvelapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class  Character(
    @SerializedName("id") var id:Int,
    @SerializedName("name") var name:String,
    @SerializedName("description") var description:String,
    @SerializedName("modified") var modified:String,
    @SerializedName("thumbnail") var thumbnail:Thumbnail,
    @SerializedName("resourceURI") var resourceURI:String,
    @SerializedName("comics") var comics:Comics,
    @SerializedName("series") var series:Series,
    @SerializedName("stories") var stories:Stories,
    @SerializedName("events") var events:Events,
    ) : Serializable {
}