package uz.texnopos.mybuilderapp.ui.main.builder.feedback.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.Feedback
import uz.texnopos.mybuilderapp.databinding.DialogPostFeedbackBinding
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.FeedbackPager
import java.util.*

class PostFeedbackDialog(private val parentFragment: FeedbackPager) :
    DialogFragment(R.layout.dialog_post_feedback) {
    private val viewModel by viewModel<PostViewModel>()
    private val auth by inject<FirebaseAuth>()
    private lateinit var bind: DialogPostFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
    }

    init {
        show(parentFragment.requireActivity().supportFragmentManager, "tag")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bind = DialogPostFeedbackBinding.inflate(layoutInflater).apply {
            rating.rating = parentFragment.bind.rating.rating
            btnPost.onClick {
                if (auth.currentUser!=null) {
                    val feedback = Feedback(
                        id = UUID.randomUUID().toString(),
                        receiverId = parentFragment.parentFragment.trade.userId,
                        resumeId = parentFragment.parentFragment.resume.value?.resumeID,
                        text = etFeedback.textToString(),
                        createdTime = System.currentTimeMillis(),
                        rating = rating.rating.toInt(),
                    )
                    viewModel.postFeedback(feedback)
                }
            }
        }
        setUpObserves()
        return bind.root
    }

    private var onClickSave: () -> Unit = {}

    fun onClickSaveButton(onClickSave: () -> Unit) {
        this.onClickSave = onClickSave
    }

    private fun setUpObserves() {
        viewModel.feedback.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING-> showProgress()
                LoadingState.SUCCESS->{
                    hideProgress()
                    toast(it.data!!)
                    dismiss()
                }
                LoadingState.ERROR->{
                    hideProgress()
                    toast(it.message!!)
                }
            }
        })
    }
}