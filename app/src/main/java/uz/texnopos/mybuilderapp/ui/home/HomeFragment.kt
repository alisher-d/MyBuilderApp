package uz.texnopos.mybuilderapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var bind: FragmentHomeBinding
    private val professionAdapter = ProfessionAdapter()
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getJobs(
            {
                professionAdapter.add(it)
            },
            {
                professionAdapter.modify(it)
            },
            {
                professionAdapter.remove(it)
            },
            {
                Log.d(TAG, "onCreate: $it")
                toast(it!!)
            }
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentHomeBinding.bind(view).apply {
            rvProfession.adapter = professionAdapter
            etSearch.onClick {
                toast("Functionality not available", Toast.LENGTH_SHORT)
            }
        }
    }
}