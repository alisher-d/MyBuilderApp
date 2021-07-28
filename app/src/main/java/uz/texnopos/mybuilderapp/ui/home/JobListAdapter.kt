package uz.texnopos.mybuilderapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.texnopos.mybuilderapp.databinding.ItemBuilderCardBinding
import uz.texnopos.mybuilderapp.data.models.JobModel

class JobListAdapter : RecyclerView.Adapter<JobListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val item: ItemBuilderCardBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun populateModel(job: JobModel) {
            item.name.text = job.name
            Glide.with(item.root)
                .load(job.image)
                .into(item.image)
        }
    }

    var models = mutableListOf<JobModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind =
            ItemBuilderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size
}