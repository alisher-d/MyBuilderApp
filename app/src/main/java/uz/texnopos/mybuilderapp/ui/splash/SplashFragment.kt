package uz.texnopos.mybuilderapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var bind: FragmentSplashBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentSplashBinding.bind(view)
        navController = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(R.id.action_splashFragment2_to_mainFragment)
        }, 500)
    }
}