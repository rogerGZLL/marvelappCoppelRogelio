package mx.dev.marvelapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.marvelapp.data.model.Character
import mx.dev.marvelapp.viewholders.CharacterViewHolder
import mx.dev.marvelapp.R
import mx.dev.marvelapp.data.model.Item
import mx.dev.marvelapp.ui.view.DetailCharacterActivity
import mx.dev.marvelapp.viewholders.ItemViewHolder

class ItemAdapter(private val characters : List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(layoutInflater.inflate(R.layout.item_generic, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = characters[position]
        val name = item.name
        holder.bind(name = name)
    }

    override fun getItemCount(): Int = characters.size


}