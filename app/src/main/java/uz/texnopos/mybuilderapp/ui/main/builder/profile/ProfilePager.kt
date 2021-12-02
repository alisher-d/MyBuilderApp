package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.curUserUid
import uz.texnopos.mybuilderapp.core.getDrawableCompat
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.data.models.Demo
import uz.texnopos.mybuilderapp.data.models.Poster
import uz.texnopos.mybuilderapp.databinding.PagerProfileBinding
import uz.texnopos.mybuilderapp.ui.imageViewer.PosterOverlayView
import uz.texnopos.mybuilderapp.ui.imageViewer.ImageViewerAdapter
import uz.texnopos.mybuilderapp.ui.imageViewer.StylingOptions
import uz.texnopos.mybuilderapp.ui.main.builder.SingleBuilderFragment


class ProfilePager(private val parentFragment: SingleBuilderFragment) :
    Fragment(R.layout.pager_profile) {
    private lateinit var bind: PagerProfileBinding
    private val tradeAdapter = ProfileAdapter()
    private val portfolioAdapter = PortfolioAdapter()
    private val viewModel by viewModel<PortfolioViewModel>()

    private var options = StylingOptions()
    private var overlayView: PosterOverlayView? = null
    private var viewer: StfalconImageViewer<Poster>? = null
    private lateinit var adapter: ImageViewerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        adapter = ImageViewerAdapter(this)
        bind = PagerProfileBinding.bind(view).apply {
            rvTrades.adapter = tradeAdapter
            rvPortfolio.adapter = adapter
            tvDescription.text = parentFragment.trade.description
            parentFragment.resume.observe(requireActivity(), {
                tradeAdapter.setData(it.trades!!)
            })
        }
    }
    private fun loadData() {
        parentFragment.resume.value?.resumeId?.let {
            viewModel.getPortfolios(
                userId = curUserUid,
                resumeId = it,
                onImageAdded = {
                    portfolioAdapter.add(it)
                },
                onImageModified = {
                    portfolioAdapter.modify(it)
                },
                onImageRemoved = {
                    portfolioAdapter.remove(it)
                },
                onFailure = {
                    toast(it!!)
                }
            )
        }
    }

    fun openViewer(startPosition: Int, imageView: ImageView) {
        val posters = Demo.posters.toMutableList()

        val builder = StfalconImageViewer.Builder(requireContext(), posters, ::loadPosterImage)
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

    private fun setupOverlayView(posters: MutableList<Poster>, startPosition: Int) {
        overlayView = PosterOverlayView(requireContext()).apply {
            update(posters[startPosition])

            onDeleteClick = {
                val currentPosition = viewer?.currentPosition() ?: 0

                posters.removeAt(currentPosition)
                viewer?.updateImages(posters)

                posters.getOrNull(currentPosition)
                    ?.let { poster -> update(poster) }
            }
        }
    }

    fun loadPosterImage(imageView: ImageView, poster: Poster?) {
        imageView.apply {
            background = requireContext().getDrawableCompat(R.drawable.shape_placeholder)
            Picasso.with(requireContext()).load(poster?.url).into(this)
        }
    }
}