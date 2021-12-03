package uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.showLog
import uz.texnopos.mybuilderapp.data.models.ImageP
import uz.texnopos.mybuilderapp.databinding.ItemPortfolioImageBinding

class ImageViewerAdapter(val imageViewer: ImageViewer) :
    RecyclerView.Adapter<ImageViewerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val bind: ItemPortfolioImageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(poster: ImageP, position: Int) {
            bind.apply {
                imageViewer.loadPosterImage(image, poster)
                imageViews.add(image)
                image.onClick {
                    imageViewer.openViewer(models.indexOf(poster), image)
                }
                imageViewer.onDelete { image, onSuccess ->
                    onDelete(image, onSuccess)
                }
            }
        }
    }

    private var onDelete: (ImageP, onDelete: (String) -> Unit) -> Unit = { s, onSuccess ->
    }

    fun onDelete(onDelete: (ImageP, onDelete: (String) -> Unit) -> Unit) {
        this.onDelete = onDelete
    }

    fun add(uri: ImageP) {
        models.add(0, uri)
        notifyItemInserted(0)
    }

    fun modify(url: ImageP) {
        val index = models.indexOf(url)
        models[index] = url
        notifyItemChanged(index, url)
    }

    fun remove(imageP: ImageP) {
        val index = models.indexOf(imageP)
        showLog("$imageP -- $index")
//        models.removeAt(index)
//        notifyItemRemoved(index)
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