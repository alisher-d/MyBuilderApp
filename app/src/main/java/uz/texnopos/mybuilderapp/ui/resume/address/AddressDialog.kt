package uz.texnopos.mybuilderapp.ui.resume.address


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.textToString
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.Address
import uz.texnopos.mybuilderapp.data.models.Country
import uz.texnopos.mybuilderapp.databinding.DialogAddressBinding


class AddressDialog(fragmentManager: FragmentManager) : DialogFragment(R.layout.dialog_address) {
    private val viewModel by viewModel<AddressViewModel>()
    private val countries = MutableLiveData<List<Country>>()
    private lateinit var bind: DialogAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
        setUpObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPlaces()
    }

    init {
        show(fragmentManager, "tag")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DialogAddressBinding.inflate(layoutInflater).apply {
            val address = Address()
            btClose.onClick {
                dismiss()
            }

            btSave.onClick {
                if (editCity.textToString() != getString(R.string.default_region_text)) {
                    onClickSave.invoke(address)
                    dismiss()
                } else toast(getString(R.string.default_region_text))
            }
            countries.observe(requireActivity(), {
                Log.d(TAG, "getPlaces: $it")
                editCountry.setAdapter((it.map { n ->
                    n.countryName
                }).toAdapter())
                editCountry.setOnItemClickListener { _, _, pos1, _ ->
                    val states = it[pos1].stateList
                    address.countryName = it[pos1].countryName
                    inputState.isVisible = true
                    editState.setText(R.string.default_state_text)
                    editCity.setText(R.string.default_region_text)
                    inputCity.isVisible = false
                    editState.setAdapter((states.map { n ->
                        n.stateName
                    }).toAdapter())
                    editState.setOnItemClickListener { _, _, pos2, _ ->
                        val regions = states[pos2].regionList
                        address.stateName = states[pos2].stateName
                        inputCity.isVisible = true
                        editCity.setText(R.string.default_region_text)
                        editCity.setAdapter((regions.map { n ->
                            n.region
                        }).toAdapter())
                        editCity.setOnItemClickListener { _, _, pos3, _ ->
                            address.regionName = regions[pos3].region
                        }
                    }
                }
            })

        }
        return bind.root
    }

    private fun setUpObserver() {
        viewModel.places.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> {
                    (requireActivity() as AppBaseActivity).showProgress(true)
                }
                LoadingState.SUCCESS -> {
                    countries.postValue(it.data!!)
                    (requireActivity() as AppBaseActivity).showProgress(false)
                }
                LoadingState.ERROR -> {
                    (requireActivity() as AppBaseActivity).showProgress(false)
                    toast(it.message!!)
                }
            }


        })
    }


    private var onClickSave: (Address) -> Unit =
        { address -> }

    fun onClickSaveButton(onClickSave: (Address) -> Unit) {
        this.onClickSave = onClickSave
    }

    private fun List<String>.toAdapter(): ArrayAdapter<String> {
        return ArrayAdapter(requireContext(), R.layout.item_spinner, this)
    }
}