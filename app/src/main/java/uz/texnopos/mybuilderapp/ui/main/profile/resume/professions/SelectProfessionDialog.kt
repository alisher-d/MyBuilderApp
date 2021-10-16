package uz.texnopos.mybuilderapp.ui.main.profile.resume.professions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.databinding.DialogSelectProfessionBinding

class SelectProfessionDialog(fragment: Fragment) :
    DialogFragment(R.layout.dialog_select_profession) {
    private val viewModel by viewModel<JobsViewModel>()
    private val tradeAdapter = SelectJobsAdapter()
    private var tradeList = MutableLiveData<List<JobModel>>()
    private lateinit var bind: DialogSelectProfessionBinding

    init {
        show(fragment.requireActivity().supportFragmentManager, "tag")
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
                    showProgress()
                }
                LoadingState.SUCCESS -> {
                    hideProgress()
                    tradeList.postValue(it.data!!)
                }
               LoadingState.ERROR -> {
                   hideProgress()
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