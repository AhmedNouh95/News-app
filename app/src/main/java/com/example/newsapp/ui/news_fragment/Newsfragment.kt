package com.example.newsapp.ui.news_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ArticlesAdapter
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager

import com.example.newsapp.databinding.FragmentNewsfragmentBinding
import com.example.newsapp.model.*
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Newsfragment : Fragment() {
    lateinit var viewModel: NewsViewModel
    lateinit var selectedCategory : Category
    lateinit var newsFragmentBinding : FragmentNewsfragmentBinding
    companion object{
        fun getInstance (category: Category) : Newsfragment {
            var newsfragment = Newsfragment ()
            newsfragment.selectedCategory = category
            return newsfragment
        }
    }
   // lateinit var tabLayout: TabLayout
    //var sourcesList: List<SourcesItem?>? = null
    var adapter: ArticlesAdapter = ArticlesAdapter()
    // lateinit var articleRecyclerView: RecyclerView
    var articleList: List<ArticlesItem?>? = null
    // lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // instance of object view model of the news VM
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsFragmentBinding = DataBindingUtil.inflate(inflater ,R.layout.fragment_newsfragment, container , false)

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.activity_main, container, false)
        return newsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        viewModel.getSources(selectedCategory)
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner
        ) {
            showTabs(it) }
        viewModel.articlesLiveData.observe(viewLifecycleOwner){
            adapter.items = it
            adapter.notifyDataSetChanged()

        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            newsFragmentBinding.progressBar.isVisible = it == true
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_LONG)
                  .show()
        }
    }


    private fun initViews() {

//        newsFragmentBinding.articlesRecyclerView = (R.id.articles_recycler_view)
//        newsFragmentBinding.tabLayout = requireView().findViewById(R.id.tab_layout)
//        newsFragmentBinding.progressBar = requireView().findViewById(R.id.progress_bar)
        newsFragmentBinding.articlesRecyclerView.adapter = adapter
        newsFragmentBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var source: SourcesItem = tab!!.tag as SourcesItem
                viewModel.loadArticles(source!!.id!!)
                newsFragmentBinding.articlesRecyclerView.layoutManager!!.scrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                var source: SourcesItem = tab!!.tag as SourcesItem
                viewModel.loadArticles(source!!.id!!)
                newsFragmentBinding.articlesRecyclerView.layoutManager!!.scrollToPosition(0)
            }


        })

    }



    private fun showTabs(sourcesList : List<SourcesItem?>?) {
        for (i in 0 until sourcesList!!.size) {
            var tab = newsFragmentBinding.tabLayout.newTab()
            tab.text = sourcesList!!.get(i)!!.name ?: "unknown name"
            tab.tag = sourcesList!!.get(i)
            newsFragmentBinding.tabLayout.addTab(tab)
        }


    }



}