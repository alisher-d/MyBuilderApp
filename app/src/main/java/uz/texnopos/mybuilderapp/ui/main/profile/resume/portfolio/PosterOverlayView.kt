package uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.sendShareIntent
import uz.texnopos.mybuilderapp.data.models.ImageP
import uz.texnopos.mybuilderapp.databinding.ViewPosterOverlayBinding

class PosterOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var bind: ViewPosterOverlayBinding
    var onDelete: (ImageP) -> Unit = {}

    init {
        val view = View.inflate(context, R.layout.view_poster_overlay, this)
        bind = ViewPosterOverlayBinding.bind(view)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun update(poster: ImageP) {
        bind.apply {
            posterOverlayDescriptionText.text = poster.description
            posterOverlayShareButton.onClick { context.sendShareIntent(poster.imageUrl!!) }
            posterOverlayDeleteButton.onClick { onDelete(poster) }
        }
    }
}