package uz.texnopos.mybuilderapp.ui.resume.homeMain

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.Address
import uz.texnopos.mybuilderapp.databinding.FragmentHomeMainBinding
import uz.texnopos.mybuilderapp.ui.resume.address.AddressDialog
import uz.texnopos.mybuilderapp.ui.resume.professions.SelectProfessionDialog
import java.util.*
import uz.texnopos.mybuilderapp.data.models.ResumeModel

import kotlin.jvm.internal.Intrinsics





class HomeMainFragment : BaseFragment(R.layout.fragment_home_main) {
    lateinit var bind: FragmentHomeMainBinding
    private lateinit var navController: NavController
    private val tradeAdapter = TradeAdapter()
    private var resume: ResumeModel? = null
    private val viewModel by viewModel<ResumeViewModel>()
    private val tvProfession = MutableLiveData<String?>()
    private val rvTrades = MutableLiveData<MutableList<String>?>()
    private val tvAddress = MutableLiveData<Address?>()
    private val tvDescription = MutableLiveData<String?>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentHomeMainBinding.bind(view)
        navController = Navigation.findNavController(view)
        bind.ln2.rvTrade.adapter = tradeAdapter
        setUpObserves()

        resume = requireActivity().intent.getParcelableExtra("resume")?: ResumeModel()
        manageViews(resume)

        bind.apply {
            personalData.tvFullName.text = getFullName()
            personalData.tvPhone.text = getPhoneNumber()
            personalData.tvEmail.text = getEmail()

            ln2.editProfession.onClick {
                showProfessionDialog()
            }

            addProfession.root.onClick {
                showProfessionDialog()
            }

            ln3.editAddress.onClick {
                showAddressDialog()
            }
            addAddress.root.onClick {
                showAddressDialog()
            }
            ln4.editDescription.onClick {

            }
            addDescription.root.onClick {

            }

            tvProfession.observe(requireActivity(), {
                val t = it != null
                ln2.root.isVisible = t
                addProfession.root.isVisible = !t
                if (t) {
                    ln2.professionName.text = it
                    resume!!.profession = it!!
                } else addProfession.title.setText(R.string.add_profession)
            })

            rvTrades.observe(requireActivity(), {
                if (it != null) tradeAdapter.models = it

            })
            tvAddress.observe(requireActivity(), {
                val t = it != null
                ln3.root.isVisible = t
                addAddress.root.isVisible = !t
                if (t) {
                    ln3.tvCountry.text = it!!.countryName
                    ln3.tvState.text = it.stateName
                    ln3.tvRegion.text = it.regionName
                } else addAddress.title.setText(R.string.add_address)
            })
            tvDescription.observe(requireActivity(), {
                val t = it != null
                ln4.root.isVisible = t
                addDescription.root.isVisible = !t
                if (t) {
                    ln4.tvDescription.text = it
                } else addDescription.title.setText(R.string.add_description)
            })

            btnSave.onClick {
                viewModel.setResume(
                    resume!!.apply {
                        createdTime = resume?.createdTime ?: System.currentTimeMillis()
                        updatedTime = System.currentTimeMillis()
                        resumeID = resume?.resumeID ?: UUID.randomUUID().toString()
                    })
            }
        }
    }

    private fun setUpObserves() {
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

    private fun manageViews(resume: ResumeModel?) {
        tvProfession.postValue(resume?.profession)
        rvTrades.postValue(resume?.trades)
        tvAddress.postValue(resume?.address)
        tvDescription.postValue(resume?.description)
    }

    private fun showProfessionDialog() {
        val dialog = SelectProfessionDialog()
        dialog.show(requireActivity().supportFragmentManager, "tag")
        dialog.onClickSaveButton { profession, trades ->
            tvProfession.postValue(profession)
            rvTrades.postValue(trades)
            resume!!.trades = trades
        }
    }

    private fun showAddressDialog() {
        val dialog = AddressDialog(requireActivity().supportFragmentManager)
        dialog.onClickSaveButton {
            resume?.address = it
            tvAddress.postValue(it)
        }
    }

}