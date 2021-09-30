package uz.texnopos.mybuilderapp.ui.main.builder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_single_builder.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.Constants.RESUME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULLNAME
import uz.texnopos.mybuilderapp.core.hideProgress
import uz.texnopos.mybuilderapp.core.showProgress
import uz.texnopos.mybuilderapp.core.toDate
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.models.TradeModel
import uz.texnopos.mybuilderapp.databinding.FragmentSingleBuilderBinding
import uz.texnopos.mybuilderapp.ui.login.ViewPagerAdapter
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.FeedbackPager
import uz.texnopos.mybuilderapp.ui.main.builder.profile.ProfilePager
import java.text.SimpleDateFormat
import java.util.*

class SingleBuilderFragment : Fragment(R.layout.fragment_single_builder) {

    lateinit var bind: FragmentSingleBuilderBinding
    private lateinit var navController: NavController
    private val viewModel by viewModel<BuilderViewModel>()
    private val fragments = listOf(ProfilePager(this), FeedbackPager(this))
    lateinit var trade: TradeModel
    val resume=MutableLiveData<ResumeModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trade = arguments?.getParcelable(RESUME)!!
        val userId = trade.userId
        val resumeId = trade.resumeID
        viewModel.getSingleResume(userId, resumeId)
        setUpObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val adapter =
            ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, fragments)
        bind = FragmentSingleBuilderBinding.bind(view).apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
            tvFullName.text=trade.fullname
                tvAddress.text = requireContext()
                .getString(
                    R.string.tv_address,
                    trade.address.countryName,
                    trade.address.stateName,
                    trade.address.regionName
                )
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = arrayOf("Profile", "487 Feedback")[pos]
            }.attach()
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
        resume.observe(requireActivity(),{
            bind.createdTime.text=it.createdTime!!.toDate()
        })
    }
}