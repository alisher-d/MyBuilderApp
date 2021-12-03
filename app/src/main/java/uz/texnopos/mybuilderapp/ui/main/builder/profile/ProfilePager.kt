package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.curUserUid
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.databinding.PagerProfileBinding
import uz.texnopos.mybuilderapp.ui.main.builder.SingleBuilderFragment


class ProfilePager(private val parentFragment: SingleBuilderFragment) :
    Fragment(R.layout.pager_profile) {
    private lateinit var bind: PagerProfileBinding
    private val tradeAdapter = ProfileAdapter()
    private lateinit var portfolioAdapter: ImageViewerAdapter

    private val viewModel by viewModel<PortfolioViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        portfolioAdapter = ImageViewer(requireContext()).adapter
        bind = PagerProfileBinding.bind(view).apply {
            rvTrades.adapter = tradeAdapter
            rvPortfolio.adapter = portfolioAdapter
            tvDescription.text = parentFragment.trade.description
            parentFragment.resume.observe(requireActivity(), {
                tradeAdapter.setData(it.trades!!)
            })
        }
    }
    private fun loadData() {
        parentFragment.resume.value?.resumeId?.let { it ->
            viewModel.getPortfolios(
                userId = curUserUid,
                resumeId = it,
                onImageAdded = {
                    portfolioAdapter.add(it)
                },
                onImageModified = {
                    portfolioAdapter.modify(it)
                },
                onImageRemoved = {
                    portfolioAdapter.remove(it)
                },
                onFailure = {
                    toast(it!!)
                }
            )
        }
    }
}