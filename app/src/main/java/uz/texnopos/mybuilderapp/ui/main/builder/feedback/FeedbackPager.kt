package uz.texnopos.mybuilderapp.ui.main.builder.feedback

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.databinding.PagerFeedbackBinding
import uz.texnopos.mybuilderapp.ui.main.builder.SingleBuilderFragment
import uz.texnopos.mybuilderapp.ui.main.profile.resume.address.AddressDialog


class FeedbackPager(private val parentFragment: SingleBuilderFragment) : Fragment(R.layout.pager_feedback) {
    private lateinit var bind:PagerFeedbackBinding
    private val feedbackAdapter=FeedbackAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind= PagerFeedbackBinding.bind(view).apply {
            rvFeedbacks.adapter=feedbackAdapter
            feedbackAdapter.setData(arrayListOf(1,1,11,1,1,1,1,1,1,1,1,1,11,1,1,1,11,1,1,11,11,1,11))
            rating.setOnRatingBarChangeListener { ratingBar, fl, b ->
                showPostFeedbackDialog()
            }
        }
    }
    private fun showPostFeedbackDialog(){
        val dialog=PostFeedbackDialog(requireActivity().supportFragmentManager)
        dialog.onClickSaveButton {

        }
    }
}