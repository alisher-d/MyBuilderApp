package uz.texnopos.mybuilderapp.ui.imageViewer

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_poster_overlay.view.*
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.sendShareIntent
import uz.texnopos.mybuilderapp.data.models.Poster

class PosterOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onDeleteClick: (Poster) -> Unit = {}

    init {
        View.inflate(context, R.layout.view_poster_overlay, this)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun update(poster: Poster) {
        posterOverlayDescriptionText.text = poster.description
        posterOverlayShareButton.setOnClickListener { context.sendShareIntent(poster.url) }
        posterOverlayDeleteButton.setOnClickListener { onDeleteClick(poster) }
    }
}