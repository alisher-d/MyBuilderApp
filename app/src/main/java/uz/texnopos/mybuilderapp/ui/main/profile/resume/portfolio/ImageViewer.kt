package uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.getDrawableCompat
import uz.texnopos.mybuilderapp.core.showLog
import uz.texnopos.mybuilderapp.data.models.ImageP
import uz.texnopos.mybuilderapp.ui.imageViewer.StylingOptions

class ImageViewer(val context: Context) {
    private var options = StylingOptions()
    private var overlayView: PosterOverlayView? = null
    private var viewer: StfalconImageViewer<ImageP>? = null
    var adapter = ImageViewerAdapter(this)

    fun openViewer(startPosition: Int, imageView: ImageView) {
        val posters = adapter.models

        val builder = StfalconImageViewer.Builder(context, posters, ::loadPosterImage)
            .withStartPosition(startPosition)
            .withImageChangeListener { position ->
                if (options.isPropertyEnabled(StylingOptions.Property.SHOW_TRANSITION)) {
                    viewer?.updateTransitionImage(adapter.imageViews[position])
                }

                overlayView?.update(posters[position])
            }
        if (options.isPropertyEnabled(StylingOptions.Property.SHOW_TRANSITION)) {
            builder.withTransitionFrom(imageView)
        }
        if (options.isPropertyEnabled(StylingOptions.Property.SHOW_OVERLAY)) {
            setupOverlayView(posters, startPosition)
            builder.withOverlayView(overlayView)
        }
        viewer = builder.show()
    }

    fun loadPosterImage(imageView: ImageView, poster: ImageP?) {
        imageView.apply {
            background = context.getDrawableCompat(R.drawable.shape_placeholder)
            Picasso.with(context).load(poster?.imageUrl).into(this)
        }
    }

    private fun setupOverlayView(posters: MutableList<ImageP>, startPosition: Int) {
        overlayView = PosterOverlayView(context).apply {
            update(posters[startPosition])
            onDelete = { imageP ->
                this@ImageViewer.onDelete(imageP) {
                    showLog(it)
                    val currentPosition = viewer?.currentPosition()
                    if (currentPosition != null) {
                        posters.removeAt(currentPosition)
                        adapter.notifyItemRemoved(currentPosition)
                        posters.getOrNull(currentPosition)
                            ?.let { poster -> update(poster) }
                    }
                    viewer?.dismiss()
                }

            }
        }
    }

    private var onDelete: (ImageP, onSuccess: (String) -> Unit) -> Unit = { id, _ ->

    }

    fun onDelete(onDelete: (ImageP, onSuccess: (String) -> Unit) -> Unit) {
        this.onDelete = onDelete
    }
}