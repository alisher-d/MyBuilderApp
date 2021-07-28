package uz.texnopos.mybuilderapp.ui.information

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.databinding.FragmentInformationBinding

class InformationFragment : BaseFragment(R.layout.fragment_information) {
    lateinit var bind: FragmentInformationBinding
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind = FragmentInformationBinding.bind(view)
        navController = Navigation.findNavController(view)

    }
}