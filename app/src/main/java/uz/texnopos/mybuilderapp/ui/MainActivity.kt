package uz.texnopos.mybuilderapp.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.databinding.ActivityMainBinding

class MainActivity : AppBaseActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.apply {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_activity_main) as NavHostFragment
            navController = navHostFragment.findNavController()
        }
    }
}