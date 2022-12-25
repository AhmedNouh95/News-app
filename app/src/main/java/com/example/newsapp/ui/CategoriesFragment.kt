package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.CategoriesAdapter
import com.example.newsapp.R
import com.example.newsapp.model.Category


class CategoriesFragment : Fragment() {
    lateinit var categoriesRecyclerView: RecyclerView
    lateinit var categoriesAdapter: CategoriesAdapter

    var categories = listOf<Category>(
        Category(id = "sports", imageId = R.drawable.sports),
        Category(id = "general", imageId = R.drawable.politics),
        Category(id = "health", imageId = R.drawable.health),
        Category(id = "business", imageId = R.drawable.business),
        Category(id = "entertainment", imageId = R.drawable.enviroment),
        Category(id = "science", imageId = R.drawable.science),
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)

    }

    fun initViews() {

        categoriesRecyclerView = requireView().findViewById(R.id.categories_recycler_view)
        categoriesAdapter = CategoriesAdapter(categories)
        categoriesRecyclerView.adapter = categoriesAdapter
        categoriesAdapter.onCategoryClick = object : CategoriesAdapter.OnItemClick {
            override fun onCategoryClick(position: Int, item: Category) {
                ///todo: make your call to home activity
                onCategoryClicked?.onCategoryClicked(item)
            }

        }
    }
    var onCategoryClicked : OnCategoryClicked? = null
    interface OnCategoryClicked {
        fun onCategoryClicked (category: Category)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }
}