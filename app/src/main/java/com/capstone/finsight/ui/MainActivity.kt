package com.capstone.finsight.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.finsight.R
import com.capstone.finsight.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        val navView: BottomNavigationView = binding.bottomNav
        val navController = findNavController(R.id.navHost)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.itemHome, R.id.itemPorto, R.id.itemFeed, R.id.itemInsight, R.id.itemProfile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.hide()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.itemHome || destination.id == R.id.itemPorto ||
                destination.id == R.id.itemFeed || destination.id == R.id.itemInsight ||
                destination.id == R.id.itemProfile) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }
}