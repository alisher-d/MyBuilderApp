package uz.texnopos.mybuilderapp.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.databinding.ItemResumeBinding

class ResumeAdapter : RecyclerView.Adapter<ResumeAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemResumeBinding) : RecyclerView.ViewHolder(bind.root) {
        fun populateModel(resume: ResumeModel) {
            bind.apply {
                resume.apply {
                    tvCountFeedback.text=bind.root.context.getString(R.string.tv_feedback_count,if (feedbackCount!=null) feedbackCount else 0)
                    tvProfession.text = profession
                    resumeCard.onClick {
                        cardOnClick.invoke(resume)
                    }
                }
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
        val index = models.map { it.resumeId }.indexOf(resume.resumeId)
        models[index] = resume
        notifyItemChanged(index, resume)
    }

    fun remove(id: String) {
        val index = models.map { it.resumeId }.indexOf(id)
        models.removeAt(index)
        notifyItemRemoved(index)
    }
}
