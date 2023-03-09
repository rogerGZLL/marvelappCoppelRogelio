package mx.dev.marvelapp.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mx.dev.marvelapp.databinding.ItemGenericBinding

class ItemViewHolder(view:View) : RecyclerView.ViewHolder(view){
    private val binding = ItemGenericBinding.bind(view)
    fun bind(name: String){
        binding.tvItemGeneric.text = name
    }
}