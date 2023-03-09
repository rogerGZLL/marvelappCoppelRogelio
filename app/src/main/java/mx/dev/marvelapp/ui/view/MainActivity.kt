package mx.dev.marvelapp.ui.view
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.dev.marvelapp.R
import mx.dev.marvelapp.ui.adapters.CharacterAdapter
import mx.dev.marvelapp.databinding.ActivityMainBinding
import mx.dev.marvelapp.core.CheckNetwork
import mx.dev.marvelapp.ui.viewmodel.CharacterViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: CharacterAdapter
    private val checkNetwork = CheckNetwork()
    //View model
    private val characterViewModel : CharacterViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
        initObservers()
        detectEndRecycler()

        binding.btnRecharge.setOnClickListener {
            initData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initData(){
        characterViewModel.isDataLoaded.postValue(true)
        if (checkNetwork.isOnline(this)){
            characterViewModel.onCreate()
        } else {
            characterViewModel.showErrorNetwork()
        }
    }

    private fun initRecyclerView(){
        binding.rvCharacters.layoutManager = LinearLayoutManager(this)
    }

    private fun detectEndRecycler(){
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    if (characterViewModel.isLoadingNew?.value == false){
                        if (characterViewModel.characterListMutable.value?.size!! < characterViewModel.total)
                            if (checkNetwork.isOnline(this@MainActivity)){
                                characterViewModel.callAPI(firstTime = false)
                            } else {
                                Toast.makeText(this@MainActivity, getString(R.string.error_red_descripción), Toast.LENGTH_LONG).show()
                            }
                        else
                            characterViewModel.isFinalList.postValue(true)
                    }
                }
            }
        })
    }

    private fun initObservers(){
        characterViewModel.isLoading.observe(this, Observer {
            binding.pbLoading.isVisible = it
        })
        characterViewModel.isLoadingNew.observe(this, Observer {
            binding.pbLoadingNew.isVisible = it
        })
        characterViewModel.isFinalList.observe(this, Observer {
            binding.tvFinalList.isVisible = it
        })
        characterViewModel.isDataLoaded.observe(this, Observer {
            if (it){
                binding.rvCharacters.isVisible = true
                binding.llErrorNetwork.isVisible = false
            } else {
                binding.rvCharacters.isVisible = false
                binding.llErrorNetwork.isVisible = true
            }
        });
        characterViewModel.characterListMutable.observe(this, Observer {
            adapter = CharacterAdapter(it)
            if (binding.rvCharacters.adapter == null){
                binding.rvCharacters.adapter = adapter
            }
            adapter.notifyDataSetChanged()
        });
    }
}

//CODIGO SIN LA UTILIZACIÓN DE NINGÚN MODELO

/*package mx.dev.marvelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.dev.marvelapp.R
import mx.dev.marvelapp.ui.adapters.CharacterAdapter
import mx.dev.marvelapp.data.model.CharacterResponse
import mx.dev.marvelapp.data.model.Character
import mx.dev.marvelapp.data.network.ApiService
import mx.dev.marvelapp.databinding.ActivityMainBinding
import mx.dev.marvelapp.helpers.RetrofitHelper
import mx.dev.marvelapp.ui.viewmodel.CharacterViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: CharacterAdapter
    private val retrofit = RetrofitHelper.getRetrofit()
    private var characterList = mutableListOf<Character>()
    private var offset: Int = 10
    private var limit: Int = 10
    private var total: Int = 0
    private var loading: Boolean = false
    private var loadingNew: Boolean = false
    private var loadData: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        callAPI(firstTime = true)
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    if (!loadingNew){
                        if (characterList.size < 50)
                            callAPI(firstTime = false)
                        else
                            Toast.makeText(this@MainActivity, getString(R.string.sin_resultados), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun initRecyclerView(){
        adapter = CharacterAdapter(characterList)
        binding.rvCharacters.layoutManager = LinearLayoutManager(this)
        binding.rvCharacters.adapter = adapter
    }

    private fun callAPI(firstTime: Boolean){
        if (firstTime){
            loading = true
            loadingNew = false
            showLoading(firstTime, loading)
        } else {
            loading = false
            loadingNew = true
            showLoading(firstTime, loadingNew)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(ApiService::class.java).getCharacters("v1/public/characters?ts=1&apikey=defe8f7fc759c1f45c7eeac3f56a69d4&hash=e54429ec9e25b2b40043016e9f523b7c&limit=${limit}&offset=${offset}")
            val characterResponse: CharacterResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    val character = characterResponse?.data?.results ?: emptyList()
                    characterList.addAll(character)
                    adapter.notifyDataSetChanged()
                        loading = false
                        loadingNew = false
                        loadData = true
                    offset += 10
                    total = characterResponse?.data?.total ?: 10
                    showLoading(firstTime, loading)

                } else {
                        loading = false
                        loadingNew = false
                        loadData = false
                    showLoading(firstTime, loading)
                }
            }

        }
    }

    private fun showLoading(firsTime: Boolean, loading: Boolean){
        if (firsTime){
            binding.pbLoading.isVisible = loading
        } else {
            binding.pbLoadingNew.isVisible = loading
        }
    }

}*/