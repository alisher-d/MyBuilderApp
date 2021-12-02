package uz.texnopos.mybuilderapp.ui.imageViewer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.data.models.Demo
import uz.texnopos.mybuilderapp.data.models.Poster
import uz.texnopos.mybuilderapp.databinding.ItemPortfolioImageBinding
import uz.texnopos.mybuilderapp.ui.main.builder.profile.ProfilePager

class ImageViewerAdapter(val fragment: ProfilePager) :
    RecyclerView.Adapter<ImageViewerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val bind: ItemPortfolioImageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun populateModel(poster: Poster, position: Int) {

            bind.apply {
                imageViews.add(image)
                fragment.loadPosterImage(image, poster)
                image.onClick {
                    fragment.openViewer(position, image)
                }
            }
        }
    }

    var imageViews = mutableListOf<ImageView>()
    var models = Demo.posters

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