package uz.texnopos.mybuilderapp.ui.resume.homeMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.databinding.ItemTradeBinding

class TradeAdapter:RecyclerView.Adapter<TradeAdapter.ItemViewHolder> (){
    inner class ItemViewHolder(val bind:ItemTradeBinding):RecyclerView.ViewHolder(bind.root){
        fun populateModel(trade:String){
            bind.tvTrade.text=trade
        }
    }
var models= mutableListOf<String>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind=ItemTradeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount()=models.size
}