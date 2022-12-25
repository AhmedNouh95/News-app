package com.example.newsapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.model.Category
import com.example.newsapp.ui.CategoriesFragment
import com.example.newsapp.ui.news_fragment.Newsfragment
import com.example.newsapp.ui.SettingsFragment

// import com.example.newsapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var imageDrawer: ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var categoriesLinearLayout: LinearLayout
    var categoriesFragment : CategoriesFragment = CategoriesFragment()
    var settingsFragment : SettingsFragment = SettingsFragment()
    lateinit var settingsLinearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
        showFragment(categoriesFragment)


    }

    private fun initViews() {
        drawerLayout = findViewById(R.id.drawer_layout)
        imageDrawer = findViewById(R.id.image_drawer_button)
        categoriesLinearLayout = findViewById(R.id.linear_layout_categories)
        settingsLinearLayout = findViewById(R.id.linear_layout_settings)
        //Call back implementation for the interface of categories fragment class
        categoriesFragment.onCategoryClicked = object : CategoriesFragment.OnCategoryClicked{
            override fun onCategoryClicked(category: Category) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container , Newsfragment.getInstance(category))
                    .addToBackStack(" ")
                    .commit()

            }

        }

        imageDrawer.setOnClickListener {
            drawerLayout.open()
        }
        categoriesLinearLayout.setOnClickListener {
            showFragment(categoriesFragment)
            drawerLayout.close()
        }
        settingsLinearLayout.setOnClickListener {
            showFragment(settingsFragment)
            drawerLayout.close()
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

}