package uz.texnopos.mybuilderapp.ui.main.trades

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.Constants.JOB_NAME
import uz.texnopos.mybuilderapp.core.Constants.RESUME
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.core.hideProgress
import uz.texnopos.mybuilderapp.core.showProgress
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.core.LoadingState
import uz.texnopos.mybuilderapp.databinding.FragmentTradeBinding


class TradeFragment : Fragment(R.layout.fragment_trade) {
    private lateinit var navController: NavController
    private lateinit var bind: FragmentTradeBinding
    private val adapter = TradeAdapter()
    private lateinit var jobName: String
    private val viewModel by viewModel<TradeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobName = arguments?.getString(JOB_NAME)!!
        showProgress()
        refresh()
        setUpObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind = FragmentTradeBinding.bind(view).apply {
            container.setOnRefreshListener {
                refresh()
            }
            collapsingToolbar.title=jobName
            rvTrades.adapter = adapter
            toolbar.setNavigationOnClickListener{
                requireActivity().onBackPressed()
            }
            adapter.onItemClickListener {
                Log.d(TAG, "onViewCreated: $it")
                val bundle = Bundle()
                bundle.putParcelable(RESUME, it)
                navController.navigate(R.id.action_tradeFragment2_to_singleBuilderFragment2, bundle)
            }
        }
    }
    private fun refresh(){
        viewModel.getAllTrades(jobName)
    }
    private fun setUpObserver() {
        viewModel.trades.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> {}
                LoadingState.SUCCESS -> {
                    hideProgress()
                    adapter.setData(it.data!!)
                    bind.container.isRefreshing=false
                }
                LoadingState.ERROR -> {
                    hideProgress()
                    toast(it.message!!)
                    bind.container.isRefreshing=false
                }
            }
        })
    }

}