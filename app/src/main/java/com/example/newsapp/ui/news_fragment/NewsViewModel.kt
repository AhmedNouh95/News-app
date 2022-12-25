package com.example.newsapp.ui.news_fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    var sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    var articlesLiveData = MutableLiveData<List<ArticlesItem?>?>()
    var progressBarLiveData = MutableLiveData<Boolean>()
    var errorMessageLiveData = MutableLiveData<String>()
     fun getSources(selectedCategory: Category) {
        ApiManager.getApis().getSources(Constants.API_KEY , category = selectedCategory.id )
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBarLiveData.value = false
                   // progressBar.isVisible = false
                    Log.e(" onResponse", response.body()!!.status ?: "unknown message")
                    response.body()!!.sources?.forEach { source ->
                        Log.e("onResponse ", "Source name ${source!!.name}")
                    }
                    sourcesLiveData.value = response.body()!!.sources!!

                   // showTabs()
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                   progressBarLiveData.value = false
                    // progressBar.isVisible = false
                    Log.e(" onFailure", "${t.localizedMessage}")
                    errorMessageLiveData.value =  "Failed to load Sources"
                   // Toast.makeText(context, "Failed to load Sources", Toast.LENGTH_LONG)
                       // .show()

                }
            })

    }
     fun loadArticles(sourceId: String) {
         progressBarLiveData.value = true
       // progressBar.isVisible = true
        ApiManager.getApis().getArticles(
            Constants.API_KEY,
            sourceId = sourceId
        ).enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                articlesLiveData.value = response.body()!!.articles
                progressBarLiveData.value = false
            //                progressBar.isVisible = false
//
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("On Failure ", t.localizedMessage)
                errorMessageLiveData.value =  "Failed to load Articles"
            //   progressBar.isVisible = false

              //
            }

        })
    }
}