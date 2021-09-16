package uz.texnopos.mybuilderapp.ui.resume.professions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.databinding.DialogSelectProfessionBinding
import android.net.wifi.p2p.WifiP2pManager.ERROR as Lo

class SelectProfessionDialog(fragmentManager: FragmentManager) :
    DialogFragment(R.layout.dialog_select_profession) {
    private val viewModel by viewModel<JobsViewModel>()
    private val tradeAdapter = SelectJobsAdapter()
    private var tradeList = MutableLiveData<List<JobModel>>()
    private lateinit var bind: DialogSelectProfessionBinding
    init {
        show(fragmentManager, "tag")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
        viewModel.getJobs()
        setUpObserver()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogSelectProfessionBinding.inflate(layoutInflater).apply {
            val sendList = mutableListOf<String>()
            var profession = ""
            rvJobsList.adapter = tradeAdapter

            btClose.onClick {
                dismiss()
            }
            btSave.onClick {
                if (sendList.size > 0) {
                    onClickSave.invoke(profession, sendList)
                    dismiss()
                } else toast("Minimum 1 trade!")
            }
            tradeList.observe(requireActivity(), {
                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.item_spinner, it.map { n -> n.name })
                autoComplete.setAdapter(arrayAdapter)
                autoComplete.setOnItemClickListener { _, _, position, _ ->
                    tradeAdapter.models = it[position].trades
                    profession = it[position].name
                    sendList.clear()
                }
            })
            tradeAdapter.onItemClickListener { model, isChecked ->
                if (isChecked) sendList.add(model)
                else sendList.remove(model)
            }
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
                    tradeList.postValue(it.data!!)
                }
               LoadingState.ERROR -> {
                    (requireActivity() as AppBaseActivity).showProgress(false)
                    toast(it.message!!)
                }
            }
        })
    }

    private var onClickSave: (profession: String, trades: MutableList<String>) -> Unit = { _, _ -> }

    fun onClickSaveButton(onClickSave: (profession: String, trades: MutableList<String>) -> Unit) {
        this.onClickSave = onClickSave
    }
}