package com.example.expensely.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.expensely.R
import com.example.expensely.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }
   override fun onSupportNavigateUp(): Boolean {
       return NavigationUI.navigateUp(navController,null)
    }

    fun showBottomNavigation(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(0f).duration=300
        binding.navView.visibility= View.VISIBLE
    }
    fun hideBottomNavigation(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(binding.navView.height.toFloat()).duration=300
        binding.navView.visibility= View.GONE


    }

}