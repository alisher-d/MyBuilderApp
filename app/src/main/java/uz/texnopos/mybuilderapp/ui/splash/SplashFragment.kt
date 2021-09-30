package uz.texnopos.mybuilderapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.core.os.HandlerCompat.postDelayed
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private lateinit var bind: FragmentSplashBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentSplashBinding.bind(view)
        navController=Navigation.findNavController(view)

        Handler(Looper.getMainLooper()).postDelayed({
           navController.navigate(R.id.action_splashFragment2_to_mainFragment)
        }, 500)
    }
}