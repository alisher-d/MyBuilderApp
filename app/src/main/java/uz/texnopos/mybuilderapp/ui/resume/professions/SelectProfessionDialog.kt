package uz.texnopos.mybuilderapp.ui.resume.professions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.textToString
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.databinding.DialogSelectProfessionBinding
import uz.texnopos.mybuilderapp.ui.resume.homeMain.HomeMainFragment

class SelectProfessionDialog(private val mFragment: HomeMainFragment) :
    DialogFragment(R.layout.dialog_select_profession) {
    private val viewModel by viewModel<JobsViewModel>()
    private val tradeAdapter = SelectJobsAdapter()
    var remoteList = mutableListOf<JobModel>()
    private val sendList = mutableListOf<String>()
    var profession = ""
    private lateinit var bind: DialogSelectProfessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getJobs()
        setUpObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogSelectProfessionBinding.inflate(layoutInflater)

        bind.rvJobsList.adapter = tradeAdapter
        tradeAdapter.remoteModels = mFragment.tradeAdapter.models
        if (mFragment.bind.professionName.textToString().isNotEmpty())
            bind.autoComplete.setText(mFragment.bind.professionName.textToString())

        bind.btClose.onClick {
            dismiss()
        }
        bind.btSave.onClick {
            onClickSave.invoke(profession, sendList)
            dismissAllowingStateLoss()
        }

        bind.autoComplete.setOnItemClickListener { parent, view, position, id ->
            tradeAdapter.models = remoteList[position].trades
            profession = remoteList[position].name
            sendList.clear()
        }

        tradeAdapter.onItemClickListener { model, isChecked ->
            if (isChecked) sendList.add(model)
            else sendList.remove(model)
        }

        return bind.root
    }

    private fun setUpObserver() {
        viewModel.jobs.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> {
                    (requireActivity() as AppBaseActivity).showProgress(true)
                }
                LoadingState.SUCCESS -> {
                    (requireActivity() as AppBaseActivity).showProgress(false)
                    remoteList = it.data!!
                    val arrayAdapter = ArrayAdapter(
                        requireContext(),
                        R.layout.item_spinner_profession,
                        it.data.map { it.name })
                    bind.autoComplete.setAdapter(arrayAdapter)
                }
                LoadingState.ERROR -> {
                    (requireActivity() as AppBaseActivity).showProgress(false)
                    toast(it.message!!)
                }
            }


        })
    }


    private var onClickSave: (profession: String, trades: MutableList<String>) -> Unit =
        { profession, trades -> }

    fun onClickSaveButton(onClickSave: (profession: String, trades: MutableList<String>) -> Unit) {
        this.onClickSave = onClickSave
    }
}