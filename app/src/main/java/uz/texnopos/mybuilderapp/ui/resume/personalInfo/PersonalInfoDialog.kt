package uz.texnopos.mybuilderapp.ui.resume.personalInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULL_NAME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.textToString
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.UserModel2
import uz.texnopos.mybuilderapp.databinding.DialogPersonalInfoBinding

class PersonalInfoDialog : DialogFragment() {
    lateinit var bind: DialogPersonalInfoBinding
    private val viewModel by viewModel<PersonalInfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
    }

    private var onClickSave: (fullname: String, email: String, phone: String) -> Unit =
        { fullname, email, phone -> }

    fun onClickSaveButton(onClickSave: (fullname: String, email: String, phone: String) -> Unit) {
        this.onClickSave = onClickSave
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogPersonalInfoBinding.inflate(inflater)
        bind.etFullName.setText(arguments?.getString(USER_FULL_NAME))
        bind.etEmail.setText(arguments?.getString(USER_EMAIL))
        bind.etPhone.setText(arguments?.getString(USER_PHONE_NUMBER))
        bind.btClose.onClick {
            dismiss()
        }

        return bind.root
    }

    override fun onStart() {
        super.onStart()

        bind.btSave.onClick {
            viewModel.updateUserInfo(
                UserModel2(
                    fullname = bind.etFullName.textToString(),
                    email = bind.etEmail.textToString(),
                    phone = bind.etPhone.textToString()
                )
            )
            setUpObserver()
        }
    }
    private fun setUpObserver(){
        viewModel.updating.observe(requireActivity(),{
            when(it.status){
                LoadingState.LOADING->{
                    (requireActivity() as AppBaseActivity).showProgress(true)
                }
                LoadingState.SUCCESS->{
                    (requireActivity() as AppBaseActivity).showProgress(false)
                    onClickSave.invoke(
                        bind.etFullName.textToString(),
                        bind.etEmail.textToString(),
                        bind.etPhone.textToString()
                    )
                    dismiss()
                }
                LoadingState.ERROR->{
                    (requireActivity() as AppBaseActivity).showProgress(false)
                    toast(it.message!!)
                }
            }
        })
    }

}


