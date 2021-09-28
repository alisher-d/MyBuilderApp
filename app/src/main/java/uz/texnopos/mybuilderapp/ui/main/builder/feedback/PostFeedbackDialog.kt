package uz.texnopos.mybuilderapp.ui.main.builder.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.databinding.DialogPostFeedbackBinding

class PostFeedbackDialog(fragmentManager: FragmentManager) : DialogFragment(R.layout.dialog_post_feedback) {

    private lateinit var bind: DialogPostFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
    }

    init {
        show(fragmentManager, "tag")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogPostFeedbackBinding.inflate(layoutInflater).apply {

        }
        return bind.root
    }

    private var onClickSave: () -> Unit = {}

    fun onClickSaveButton(onClickSave: () -> Unit) {
        this.onClickSave = onClickSave
    }
}