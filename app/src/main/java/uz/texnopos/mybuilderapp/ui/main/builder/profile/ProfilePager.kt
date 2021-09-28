package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.databinding.PagerProfileBinding
import uz.texnopos.mybuilderapp.ui.main.builder.SingleBuilderFragment


class ProfilePager(private val parentFragment: SingleBuilderFragment) :
    Fragment(R.layout.pager_profile) {
    private lateinit var bind: PagerProfileBinding
    private val tradeAdapter = ProfileAdapter()
    private val portfolioAdapter = PortfolioAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = PagerProfileBinding.bind(view).apply {
            rvTrades.adapter = tradeAdapter
            rvPortfolio.adapter = portfolioAdapter
            tvDescription.text = parentFragment.trade.description
            parentFragment.resume.observe(requireActivity(), {
                tradeAdapter.setData(it.trades!!)
            })
        }
    }

}