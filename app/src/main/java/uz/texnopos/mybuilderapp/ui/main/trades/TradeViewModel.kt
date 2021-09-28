package uz.texnopos.mybuilderapp.ui.main.trades

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.callApi
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.TradeModel
import uz.texnopos.mybuilderapp.data.repository.Repository

class TradeViewModel(private val repo: Repository) : ViewModel() {
    private val _trades = MutableLiveData<Resource<List<TradeModel>>>()
    val trades get() = _trades

    fun getAllTrades(jobName: String) {
        _trades.value=Resource.loading()
        callApi(repo.getAllTrades(jobName),
            {
                _trades.value = Resource.success(it!!.data)
            },
            {
                _trades.value= Resource.error(it)
            })
    }
}