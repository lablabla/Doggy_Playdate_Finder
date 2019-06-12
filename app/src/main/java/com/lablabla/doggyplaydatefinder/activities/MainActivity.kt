package com.lablabla.doggyplaydatefinder.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lablabla.doggyplaydatefinder.R
import com.lablabla.doggyplaydatefinder.models.User

class MainActivity : AppCompatActivity() {

    class MainViewModel : ViewModel()
    {
        var user : User? = null
    }

    private var user : User? = null
    private lateinit var navView: BottomNavigationView
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = intent.getParcelableExtra("user")

        val sharedViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        sharedViewModel.user = user


        navView = findViewById(R.id.nav_view)
        controller = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(controller)
        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_friends, R.id.navigation_user, R.id.navigation_map -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }
        // Setup bottom navigation as hidden
        navView.visibility = View.GONE
    }

    private fun hideBottomNavigation() {
        // bottom_navigation is BottomNavigationView
        with(navView) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate()
                    .alpha(0f)
                    .withEndAction { visibility = View.GONE }
                    .duration = 1
            }
        }
    }

    private fun showBottomNavigation() {
        // bottom_navigation is BottomNavigationView
        with(navView) {
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 5
        }
    }

}
