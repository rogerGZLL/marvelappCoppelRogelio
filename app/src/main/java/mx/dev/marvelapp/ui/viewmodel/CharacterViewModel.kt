package mx.dev.marvelapp.ui.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.dev.marvelapp.core.ConstantsApi
import mx.dev.marvelapp.data.model.Character
import mx.dev.marvelapp.data.model.CharacterResponse
import mx.dev.marvelapp.data.network.ApiClient
import mx.dev.marvelapp.core.RetrofitHelper

class CharacterViewModel : ViewModel() {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val characterList = mutableListOf<Character>()
    val characterListMutable = MutableLiveData<List<Character>>()
    private var offset: Int = 10
    private var limit: Int = 10
    var total: Int = 0
    val isLoading = MutableLiveData <Boolean>()
    val isLoadingNew = MutableLiveData <Boolean>()
    val isDataLoaded = MutableLiveData <Boolean>()
    val isFinalList = MutableLiveData <Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            callAPI(true)
        }
    }

    fun showErrorNetwork(){
        isDataLoaded.postValue(false)
    }

    fun callAPI(firstTime: Boolean){
        isFinalList.postValue(false)
        if (firstTime){
            isLoading.postValue(true)
            isLoadingNew.postValue(false)
        } else {
            isLoading.postValue(false)
            isLoadingNew.postValue(true)
        }
        CoroutineScope(Dispatchers.IO).launch {
            var urlApi = "${ConstantsApi.characters}?ts=${ConstantsApi.ts}&apikey=${ConstantsApi.apiKey}&hash=${ConstantsApi.hash}&limit=${limit}&offset=${offset}"
            val call = retrofit.create(ApiClient::class.java).getCharacters(urlApi)
                if (call.isSuccessful){
                    val characterResponse: CharacterResponse? = call.body()
                    val character = characterResponse?.data?.results ?: emptyList()
                    characterList.addAll(character)
                    characterListMutable.postValue(characterList)
                    isLoading.postValue(false)
                    isLoadingNew.postValue(false)
                    if (firstTime) isDataLoaded.postValue(true)
                    offset += 10
                    total = characterResponse?.data?.total ?: 10
                } else {
                    isLoading.postValue(false)
                    isLoadingNew.postValue(false)
                    if (firstTime) isDataLoaded.postValue(false)
                }
            }
        }
    }
