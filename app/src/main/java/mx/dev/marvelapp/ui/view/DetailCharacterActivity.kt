package mx.dev.marvelapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import mx.dev.marvelapp.R
import mx.dev.marvelapp.ui.adapters.ItemAdapter
import mx.dev.marvelapp.data.model.Character
import mx.dev.marvelapp.databinding.ActivityDetailCharacterBinding

class DetailCharacterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailCharacterBinding
    private lateinit var character : Character
    private lateinit var adapterComics: ItemAdapter
    private lateinit var adapterSeries: ItemAdapter
    private lateinit var adapterStories: ItemAdapter
    private lateinit var adapterEvents: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        character = intent.getSerializableExtra("CHARACTER") as Character
        initScreen()
        initRecyclerComic()
        initRecyclerSeries()
        initRecyclerStories()
        initRecyclerEvents()
    }

    private fun initScreen(){
        initToolbar()
        val urlImage = character.thumbnail.path.replace("http", "https")+'.'+character.thumbnail.extension
        Picasso.get().load(urlImage).into(binding.ivLogo)
        binding.collapsingToolbarMaterial.title = character.name
        binding.icId.tvTitle.text = getString(R.string.id)
        binding.icId.tvContent.text = "${ character.id}"
        binding.icNombre.tvTitle.text = getString(R.string.nombre)
        binding.icNombre.tvContent.text = character.name
        binding.icDescripcion.tvTitle.text = getString(R.string.descripcion)
        if (character.description.isNotEmpty())
            binding.icDescripcion.tvContent.text = character.description
        else
            binding.icDescripcion.tvContent.text = getString(R.string.sin_descripcion)
    }

    private fun initToolbar(){
        setSupportActionBar(binding.toolbarMaterial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initRecyclerComic(){
        adapterComics = ItemAdapter(character.comics.items)
        binding.rvComics.layoutManager = LinearLayoutManager(this)
        binding.rvComics.adapter = adapterComics
        if (character.comics.items.isEmpty()){
            binding.cvComics.isVisible = false
        }
    }

    private fun initRecyclerSeries(){
        adapterSeries = ItemAdapter(character.series.items)
        binding.rvSeries.layoutManager = LinearLayoutManager(this)
        binding.rvSeries.adapter = adapterSeries
        if (character.series.items.isEmpty()){
            binding.cvSeries.isVisible = false
        }
    }

    private fun initRecyclerStories(){
        adapterStories = ItemAdapter(character.stories.items)
        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.adapter = adapterStories
        if (character.stories.items.isEmpty()){
            binding.cvStories.isVisible = false
        }
    }

    private fun initRecyclerEvents(){
        adapterEvents = ItemAdapter(character.events.items)
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapterEvents
        if (character.events.items.isEmpty()){
            binding.cvEvents.isVisible = false
        }
    }


}