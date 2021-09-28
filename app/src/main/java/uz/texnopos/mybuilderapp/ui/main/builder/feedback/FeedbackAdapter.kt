package uz.texnopos.mybuilderapp.ui.main.builder.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.databinding.ItemFeedbackBinding

class FeedbackAdapter : RecyclerView.Adapter<FeedbackAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(bind:ItemFeedbackBinding) : RecyclerView.ViewHolder(bind.root) {
        fun populateModel(model:Any?) {

        }
    }
    private var models= mutableListOf<Any?>()

    fun setData(models: List<Int>) {
        this.models.clear()
        for (i in models) {
            this.models.add(i)
            notifyItemInserted(this.models.lastIndex)
        }
    }
    override fun getItemCount()=models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind= ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(bind)
    }
     override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}