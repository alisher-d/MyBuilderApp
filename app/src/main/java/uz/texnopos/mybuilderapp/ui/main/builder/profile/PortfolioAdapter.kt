package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.databinding.ItemPortfolioImageBinding

class PortfolioAdapter : RecyclerView.Adapter<PortfolioAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemPortfolioImageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(model: String) {

        }
    }

    var models = mutableListOf<String>(
        "Trade1",
        "Trade1",
        "Trade1",
        "Trade1",
        "Trade1",
        "Trade1",
        "Trade1",
        "Trade1",
    )

    fun setData(models: List<String>) {
        for (i in models) {
            this.models.add(i)
            notifyItemInserted(models.size)
        }
    }

    private var onClick: (String) -> Unit = {}
    fun onItemClickListener(onClick: (String) -> Unit) {
        this.onClick = onClick
    }

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind = ItemPortfolioImageBinding.inflate(
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