package uz.texnopos.mybuilderapp.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.databinding.ItemResumeBinding

class ResumeAdapter : RecyclerView.Adapter<ResumeAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val item: ItemResumeBinding) : RecyclerView.ViewHolder(item.root) {
        fun populateModel(resume: ResumeModel) {
            item.tvProfession.text = resume.profession
            item.resumeCard.onClick {
            cardOnClick.invoke(resume)
            }
        }
    }

    private var cardOnClick: (resume: ResumeModel) -> Unit = {}
    fun resumeCardOnClickListener(cardOnClick: (resume: ResumeModel) -> Unit) {
        this.cardOnClick = cardOnClick
    }

    var models = mutableListOf<ResumeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind = ItemResumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size

    fun add(resume: ResumeModel) {
        models.add(0, resume)
        notifyItemInserted(0)
    }

    fun modify(resume: ResumeModel) {
        val index = models.map { it.resumeID }.indexOf(resume.resumeID)
        models[index] = resume
        notifyItemChanged(index, resume)
    }

    fun remove(id: String) {
        val index = models.map { it.resumeID }.indexOf(id)
        models.removeAt(index)
        notifyItemRemoved(index)
    }
}
