package uz.texnopos.mybuilderapp.ui.resume.professions


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.databinding.SelectableJobItemCheckboxBinding

class SelectJobsAdapter : RecyclerView.Adapter<SelectJobsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(var binding: SelectableJobItemCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(job: String, position: Int) {
            val item = binding.jobName
            item.isChecked = remoteModels.contains(job)
            item.text = job
            item.setOnCheckedChangeListener { buttonView, isChecked ->
                onClick.invoke(job,isChecked)
            }

        }
    }

    var onClick: (model: String,isChecked:Boolean) -> Unit =
        { model,isChecked ->

        }

    fun onItemClickListener(onClick: (model: String,isChecked:Boolean) -> Unit) {
        this.onClick = onClick
    }

    var models = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var remoteModels = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        count = remoteModels.size
        val binding = SelectableJobItemCheckboxBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position], position)

    }

    override fun getItemCount() = models.size
}