package uz.texnopos.mybuilderapp.ui.resume.homeMain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULL_NAME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.textToString
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.models.UserModel2
import uz.texnopos.mybuilderapp.databinding.FragmentHomeMainBinding
import uz.texnopos.mybuilderapp.ui.resume.personalInfo.PersonalInfoDialog
import uz.texnopos.mybuilderapp.ui.resume.professions.SelectProfessionDialog
import java.util.*


class HomeMainFragment : BaseFragment(R.layout.fragment_home_main) {
     lateinit var bind: FragmentHomeMainBinding
    private lateinit var navController: NavController
     val tradeAdapter = TradeAdapter()
    private val professionDialog = SelectProfessionDialog(this)
    private val personalInfoDialog = PersonalInfoDialog()
    private val viewModel by viewModel<ResumeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentHomeMainBinding.bind(view)
        navController = Navigation.findNavController(view)
        bind.rvTrade.adapter = tradeAdapter
        setUpObserves()

        val resume2=requireActivity().intent.getParcelableExtra<ResumeModel>("resume")
        if (resume2!=null) manageViews(resume2)

        val userInfo=requireActivity().intent.getParcelableExtra<UserModel2>("user_info")!!
        bind.tvFullName.text=userInfo.fullname
        bind.tvEmail.text=userInfo.email
        bind.tvPhone.text=userInfo.phone
        Log.d("user_info","$userInfo")
        bind.apply {

            val arguments = Bundle()
            arguments.putString(USER_FULL_NAME, tvFullName.textToString())
            arguments.putString(USER_EMAIL, tvEmail.textToString())
            arguments.putString(USER_PHONE_NUMBER, tvPhone.textToString())
            personalInfoDialog.arguments = arguments

            editPersonalInfo.onClick {
                showDialog1()
            }

            editProfession.onClick {
                showDialog2()
            }

            addProfession.onClick {
                showDialog2()
            }

            editAddress.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_addressFragment)
            }
            addAddress.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_addressFragment)
            }
            editDescription.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_selfFragment)
            }
            addDescription.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_selfFragment)
            }
        }
        bind.btnSave.onClick {
            val resume = ResumeModel()
            resume.isCreated = true
            resume.resumeID = resume2?.resumeID ?: UUID.randomUUID().toString()
            resume.profession = bind.professionName.textToString()
            resume.trades = tradeAdapter.models
            viewModel.setResume(resume)
        }
    }

    fun setUpObserves() {
        viewModel.resumeSaved.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> {
                    showProgress()
                }
                LoadingState.SUCCESS -> {
                    hideProgress()
                    toast(it.data!!)
                    requireActivity().onBackPressed()

                }
                LoadingState.ERROR -> {
                    hideProgress()
                    toast(it.data!!)
                }
            }
        })
    }

    fun showDialog1() {
        personalInfoDialog.show(requireActivity().supportFragmentManager, "tag")
        toast("click")
        personalInfoDialog.onClickSaveButton { fullname, email, phone ->
            this@HomeMainFragment.bind.tvFullName.text = fullname
            this@HomeMainFragment.bind.tvEmail.text = email
            this@HomeMainFragment.bind.tvPhone.text = phone
        }

    }
fun manageViews(resume:ResumeModel){
    if (resume.profession.isNotEmpty()){
        bind.professionName.text=resume.profession
        tradeAdapter.models=resume.trades
        bind.ln2.visibility=View.VISIBLE
        bind.addProfession.visibility=View.GONE
    }
    if (resume.address!=null){
        bind.tvAddress.text="${resume.address?.countryName}\n${resume.address?.cityName}"
        bind.ln3.visibility=View.VISIBLE
        bind.addAddress.visibility=View.GONE
    }
    if (resume.description.isNotEmpty()){
        bind.tvDescription.text=resume.description
        bind.ln4.visibility=View.VISIBLE
        bind.addDescription.visibility=View.GONE
    }
}
    fun showDialog2() {
        professionDialog.show(requireActivity().supportFragmentManager, "tag")
        professionDialog.onClickSaveButton { profession, trades ->
            bind.professionName.text = profession
            tradeAdapter.models = trades
            if (bind.professionName.text.isNotEmpty()) {
                bind.ln2.visibility = View.VISIBLE
                bind.addProfession.visibility = View.GONE
            } else {
                bind.ln2.visibility = View.GONE
                bind.addProfession.visibility = View.VISIBLE
            }
        }
    }
}