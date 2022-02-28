package mx.dev.marvelapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.marvelapp.data.model.Character
import mx.dev.marvelapp.viewholders.CharacterViewHolder
import mx.dev.marvelapp.R
import mx.dev.marvelapp.ui.view.DetailCharacterActivity

class CharacterAdapter(private val characters : List<Character>) : RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(layoutInflater.inflate(R.layout.item_character, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = characters[position]
        val name = item.name
        val urlImage = item.thumbnail.path.replace("http", "https")+'.'+item.thumbnail.extension
        holder.bind(name, urlImage)
        holder.binding.cvItem.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCharacterActivity::class.java)
            intent.putExtra("CHARACTER", item)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = characters.size


}