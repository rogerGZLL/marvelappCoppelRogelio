package mx.dev.marvelapp.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.dev.marvelapp.R
import mx.dev.marvelapp.databinding.ItemCharacterBinding

class CharacterViewHolder(view:View) : RecyclerView.ViewHolder(view){
    public val binding = ItemCharacterBinding.bind(view)
    fun bind(name: String, urlImage: String){
        binding.tvNombre.text = name
        print(urlImage)
        Picasso.get().load(urlImage).placeholder(R.drawable.marvel).into(binding.ivThumbnail)
    }
}