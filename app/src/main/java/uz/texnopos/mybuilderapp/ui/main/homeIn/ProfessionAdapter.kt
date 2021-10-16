package uz.texnopos.mybuilderapp.ui.main.homeIn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_builder_card.view.*
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.databinding.ItemBuilderCardBinding

class ProfessionAdapter : RecyclerView.Adapter<ProfessionAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemBuilderCardBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(job: JobModel) {
            bind.name.text = job.name
            Picasso.with(bind.root.image.context)
                .load(job.image)
                .into(bind.image)
            bind.card.onClick {
                onClick.invoke(job.name)
            }
        }
    }

    private var onClick: (String) -> Unit = {}

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

    fun onItemClickListener(onClick: (String) -> Unit) {
        this.onClick = onClick
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