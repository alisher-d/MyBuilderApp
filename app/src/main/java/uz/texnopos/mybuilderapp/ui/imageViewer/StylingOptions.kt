package uz.texnopos.mybuilderapp.ui.imageViewer

import uz.texnopos.mybuilderapp.ui.imageViewer.StylingOptions.Property.*

class StylingOptions {

    private val options = sortedMapOf(
        HIDE_STATUS_BAR to true,
        IMAGES_MARGIN to true,
        CONTAINER_PADDING to false,
        SHOW_TRANSITION to true,
        SWIPE_TO_DISMISS to true,
        ZOOMING to true,
        SHOW_OVERLAY to true,
        RANDOM_BACKGROUND to false)

    fun isPropertyEnabled(property: Property): Boolean {
        return options[property] == true
    }

    enum class Property {
        HIDE_STATUS_BAR,
        IMAGES_MARGIN,
        CONTAINER_PADDING,
        SHOW_TRANSITION,
        SWIPE_TO_DISMISS,
        ZOOMING,
        SHOW_OVERLAY,
        RANDOM_BACKGROUND
    }
}