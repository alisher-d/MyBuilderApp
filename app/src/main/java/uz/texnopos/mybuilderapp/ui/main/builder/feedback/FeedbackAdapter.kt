package uz.texnopos.mybuilderapp.ui.main.builder.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.core.toDate
import uz.texnopos.mybuilderapp.data.models.Feedback
import uz.texnopos.mybuilderapp.databinding.ItemFeedbackBinding

class FeedbackAdapter : RecyclerView.Adapter<FeedbackAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val bind:ItemFeedbackBinding) : RecyclerView.ViewHolder(bind.root) {
        fun populateModel(feedback:Feedback) {
            bind.apply {

                feedbackBody.text=feedback.text
                rating.rating=feedback.rating!!.toFloat()
                createdTime.text=feedback.createdTime!!.toDate()
                tvFullName.text=feedback.authorFullname
            }
        }
    }
    private var models= mutableListOf<Feedback>()

    override fun getItemCount()=models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind= ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(bind)
    }
     override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    fun add(feedback: Feedback) {
        models.add(0, feedback)
        notifyItemInserted(0)
    }

    fun modify(feedback: Feedback) {
        val index = models.map { it.id }.indexOf(feedback.id)
        models[index] = feedback
        notifyItemChanged(index, feedback)
    }

    fun remove(id: String) {
        val index = models.map { it.id }.indexOf(id)
        models.removeAt(index)
        notifyItemRemoved(index)
    }
}