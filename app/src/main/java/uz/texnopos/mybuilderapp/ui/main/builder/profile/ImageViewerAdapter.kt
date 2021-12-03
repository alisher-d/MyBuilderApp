package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.Demo
import uz.texnopos.mybuilderapp.data.models.ImageP
import uz.texnopos.mybuilderapp.data.models.Poster
import uz.texnopos.mybuilderapp.databinding.ItemPortfolioImageBinding

class ImageViewerAdapter(val imageViewer: ImageViewer) :
    RecyclerView.Adapter<ImageViewerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val bind: ItemPortfolioImageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(poster: ImageP, position: Int) {
            bind.apply {
                imageViews.add(image)
                imageViewer.loadPosterImage(image, poster)
                image.onClick {
                    imageViewer.openViewer(position, image)
                }
            }
        }
    }
    fun add(uri: ImageP) {
        models.add(0, uri)
        notifyItemInserted(1)
    }

    fun modify(url: ImageP) {
        val index = models.indexOf(url)
        models[index] = url
        notifyItemChanged(index, url)
    }

    fun remove(imageP: ImageP) {
        val index = models.indexOf(imageP)
        models.removeAt(index)
        notifyItemRemoved(index)
    }
    var imageViews = mutableListOf<ImageView>()
    var models = mutableListOf<ImageP>()

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