package uz.texnopos.mybuilderapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.isLoggedIn
import uz.texnopos.mybuilderapp.core.setStatusBarColor
import uz.texnopos.mybuilderapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var bind: FragmentMainBinding
    lateinit var navView: BottomNavigationView
    private lateinit var childNavController: NavController
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor(R.color.white)
        bind = FragmentMainBinding.bind(view)
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_activity_main)
        childNavController =
            Navigation.findNavController(requireActivity(), R.id.nav_fragment_main)
        navView = bind.navView
        NavigationUI.setupWithNavController(navView, childNavController)
        navView.menu.getItem(2).setOnMenuItemClickListener {
            if (!isLoggedIn()) {
                navController.navigate(R.id.action_mainFragment_to_loginFragment)
                true
            } else false

        }
    }
}