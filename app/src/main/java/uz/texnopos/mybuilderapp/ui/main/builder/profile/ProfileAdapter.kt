package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.databinding.ItemProfileTradeBinding
import uz.texnopos.mybuilderapp.databinding.ItemTradeBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemTradeBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(model: String) {
            bind.apply {
                tvTrade.text = model
            }
        }
    }

    var models = mutableListOf<String>()

    fun setData(models: List<String>) {
        for (i in models) {
            this.models.add(i)
            notifyItemInserted(this.models.lastIndex)
        }
    }

    private var onClick: (String) -> Unit = {}
    fun onItemClickListener(onClick: (String) -> Unit) {
        this.onClick = onClick
    }

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind = ItemTradeBinding.inflate(
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