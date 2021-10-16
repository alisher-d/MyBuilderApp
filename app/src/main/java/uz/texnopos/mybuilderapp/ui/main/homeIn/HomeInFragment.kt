package uz.texnopos.mybuilderapp.ui.main.homeIn

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.Constants
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.databinding.FragmentHomeInBinding

class HomeInFragment:Fragment(R.layout.fragment_home_in) {
    private lateinit var bind: FragmentHomeInBinding
    private val professionAdapter = ProfessionAdapter()
    private val viewModel by viewModel<HomeInViewModel>()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refresh()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        bind = FragmentHomeInBinding.bind(view).apply {

            rvProfession.adapter = professionAdapter
            etSearch.onClick {
                toast("Functionality not available", Toast.LENGTH_SHORT)
            }
            professionAdapter.onItemClickListener {job->
                val bundle= Bundle()
                bundle.putString(Constants.JOB_NAME,job)
                navController.navigate(R.id.action_homeFragment_to_tradeFragment2,bundle)
            }
        }
    }
    private fun refresh(){
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
                Log.d(Constants.TAG, "onCreate: $it")
                toast(it!!)
            }
        )
    }
}