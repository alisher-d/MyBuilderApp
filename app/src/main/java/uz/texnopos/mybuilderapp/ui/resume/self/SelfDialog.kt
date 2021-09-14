package uz.texnopos.mybuilderapp.ui.resume.self

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilderapp.*
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.data.models.Address
import uz.texnopos.mybuilderapp.databinding.FragmentSelfBinding


class SelfDialog : DialogFragment(R.layout.fragment_self) {
    private lateinit var bind: FragmentSelfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind= FragmentSelfBinding.inflate(inflater).apply {

        }
        return bind.root
    }
    private var onClickSave: (String) -> Unit =
        { description -> }

    fun onClickSaveButton(onClickSave: (String) -> Unit) {
        this.onClickSave = onClickSave
    }
}