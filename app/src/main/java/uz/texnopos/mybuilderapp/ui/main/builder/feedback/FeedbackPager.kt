package uz.texnopos.mybuilderapp.ui.main.builder.feedback

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.databinding.PagerFeedbackBinding
import uz.texnopos.mybuilderapp.ui.main.builder.SingleBuilderFragment
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.post.PostFeedbackDialog


class FeedbackPager(val parentFragment: SingleBuilderFragment) : Fragment(R.layout.pager_feedback) {
    val viewModel by viewModel<FeedbackViewModel>()
    lateinit var bind: PagerFeedbackBinding
    private val feedbackAdapter = FeedbackAdapter()
    lateinit var navController: NavController
    private val auth by inject<FirebaseAuth>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)

        bind = PagerFeedbackBinding.bind(view).apply {
            rvFeedbacks.adapter = feedbackAdapter

            rating.setOnRatingBarChangeListener { ratingBar, fl, b ->
                if (auth.currentUser != null)
                    PostFeedbackDialog(this@FeedbackPager)
                else
                    navController.navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragment.resume.value?.resumeID?.let { resumeId ->
            viewModel.getAllFeedbacks(parentFragment.trade.userId, resumeId,
                {
                    feedbackAdapter.add(it)
                },
                {
                    feedbackAdapter.modify(it)
                },
                {
                    feedbackAdapter.remove(it)
                },
                {
                    toast(it!!)
                }
            )
        }
    }
}