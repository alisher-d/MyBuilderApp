package uz.texnopos.mybuilderapp.ui.main.builder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.core.Constants.RESUME
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.models.TradeModel
import uz.texnopos.mybuilderapp.databinding.FragmentSingleBuilderBinding
import uz.texnopos.mybuilderapp.ui.login.ViewPagerAdapter
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.FeedbackPager
import uz.texnopos.mybuilderapp.ui.main.builder.profile.ProfilePager

class SingleBuilderFragment : Fragment(R.layout.fragment_single_builder) {

    lateinit var bind: FragmentSingleBuilderBinding
    private lateinit var navController: NavController
    private val viewModel by viewModel<BuilderViewModel>()
    private val fragments = listOf(ProfilePager(this), FeedbackPager(this))
    lateinit var trade: TradeModel
    val resume=MutableLiveData<ResumeModel>()
    val feedbackCount=MutableLiveData<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trade = arguments?.getParcelable(RESUME)!!
        val userId = trade.userId
        val resumeId = trade.resumeId
        viewModel.getSingleResume(userId, resumeId)
        setUpObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind = FragmentSingleBuilderBinding.bind(view).apply {
            resume.observe(requireActivity(), {
                val rating = it.rating
                bind.createdTime.text = it.createdTime!!.toDate()

                if (rating != null) {
                    resumeRating.rating = rating
                    tvRatingValue.text = "%.1f".format(rating)
                }

                val adapter =
                    ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, fragments)
                viewPager.adapter = adapter

                feedbackCount.observe(requireActivity(),{count->
                    TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                        tab.text = arrayOf("Profile", "$count Feedbacks")[pos]
                    }.attach()
                })
                feedbackCount.postValue(it.feedbackCount!!)
                toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
                tvFullName.text = trade.fullname
                tvAddress.text = requireContext()
                    .getString(
                        R.string.tv_address,
                        trade.address.countryName,
                        trade.address.stateName,
                        trade.address.regionName
                    )
            })
        }
    }

    private fun setUpObserver() {
        viewModel.resume.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> showProgress()
                LoadingState.SUCCESS -> {
                    resume.postValue(it.data!!)
                    hideProgress()
                }
                LoadingState.ERROR -> {
                    hideProgress()
                    toast(it.message!!)
                }
            }
        })
    }
}