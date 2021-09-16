package uz.texnopos.mybuilderapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.databinding.ItemBuilderCardBinding

class ProfessionAdapter : RecyclerView.Adapter<ProfessionAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemBuilderCardBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(job: JobModel) {
            bind.name.text = job.name
            Glide.with(bind.root.context)
                .load(job.image)
                .placeholder(R.drawable.landscaping)
                .into(bind.image)
        }
    }

    var models = mutableListOf<JobModel>()
    fun add(job: JobModel) {
        models.add(0, job)
        notifyItemInserted(0)
    }

    fun modify(job: JobModel) {
        val index = models.map { it.jobId }.indexOf(job.jobId)
        models[index] = job
        notifyItemChanged(index, job)
    }

    fun remove(id: String) {
        val index = models.map { it.jobId }.indexOf(id)
        models.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind =
            ItemBuilderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}