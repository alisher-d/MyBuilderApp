package uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.ImageP
import uz.texnopos.mybuilderapp.databinding.ItemPortfolioImageBinding


class PortfolioAdapter : RecyclerView.Adapter<PortfolioAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val bind: ItemPortfolioImageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(imageP: ImageP, position: Int) {
            if (imageP.id == null) {
                Picasso.with(bind.image.context)
                    .load("https://cdn4.iconfinder.com/data/icons/vectory-bonus-3/40/button_add-512.png")
                    .into(bind.image)
            } else {
                Picasso.with(bind.image.context)
                    .load(imageP.imageUrl)
                    .into(bind.image)
            }

            bind.root.onClick {
                onClick.invoke(bind.image, imageP, position)
            }
        }
    }

    private var onClick: (ImageView, ImageP?, Int) -> Unit = { view, url, position ->

    }
    var models = mutableListOf(ImageP())

    fun add(uri: ImageP) {
        models.add(1, uri)
        notifyItemInserted(1)
    }

    fun modify(url: ImageP) {
        val index = models.indexOf(url)
        models[index] = url
        notifyItemChanged(index, url)
    }

    fun remove(id: String) {
        val index = models.map { it.id }.indexOf(id)
        models.removeAt(index)
        notifyItemRemoved(index)
    }

    fun onItemClickListener(onClick: (ImageView, ImageP?, Int) -> Unit) {
        this.onClick = onClick
    }

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bind =
            ItemPortfolioImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }
}