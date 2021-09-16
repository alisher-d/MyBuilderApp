package uz.texnopos.mybuilderapp.ui.resume.professions


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.databinding.SelectableJobItemCheckboxBinding

class SelectJobsAdapter : RecyclerView.Adapter<SelectJobsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(var bind: SelectableJobItemCheckboxBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(job: String) {
            val item = bind.jobName
            item.text = job
            item.setOnCheckedChangeListener { _, isChecked ->
                onClick.invoke(job,isChecked)
            }
        }
    }

    var onClick: (model: String,isChecked:Boolean) -> Unit =
        { _, _ -> }

    fun onItemClickListener(onClick: (model: String,isChecked:Boolean) -> Unit) {
        this.onClick = onClick
    }
    var models = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectableJobItemCheckboxBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size

}