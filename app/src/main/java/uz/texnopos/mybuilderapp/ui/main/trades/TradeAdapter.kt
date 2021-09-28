package uz.texnopos.mybuilderapp.ui.main.trades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.TradeModel
import uz.texnopos.mybuilderapp.databinding.ItemShortinfoResumeCardBinding

class TradeAdapter : RecyclerView.Adapter<TradeAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemShortinfoResumeCardBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(model: TradeModel) {
            bind.apply {
                val desc = model.description
                tvFullName.text = model.fullname
                tvAddress.text = bind.root.context
                    .getString(
                        R.string.tv_address,
                        model.address.countryName,
                        model.address.stateName,
                        model.address.regionName
                    )
                tvDescription.text = if (desc.length >= 99) desc.substring(0, 100) + "..." else desc
                bind.root.onClick {
                    onClick.invoke(model)
                }
            }
        }
    }

    private var models = mutableListOf<TradeModel>()

    fun setData(models: List<TradeModel>) {
        this.models.clear()
        for (i in models) {
            this.models.add(i)
            notifyItemInserted(models.size)
        }
    }

    private var onClick: (TradeModel) -> Unit = {}
    fun onItemClickListener(onClick: (TradeModel) -> Unit) {
        this.onClick = onClick
    }

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind = ItemShortinfoResumeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}