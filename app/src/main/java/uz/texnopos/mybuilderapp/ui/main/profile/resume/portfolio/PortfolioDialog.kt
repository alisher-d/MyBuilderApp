package uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.IntentUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.core.imagehelper.pickGalleryImage
import uz.texnopos.mybuilderapp.data.models.Address
import uz.texnopos.mybuilderapp.data.models.ImageP
import uz.texnopos.mybuilderapp.databinding.DialogPortfolioBinding
import uz.texnopos.mybuilderapp.ui.main.profile.resume.resumeMain.ResumeFragment
import java.util.*
import uz.texnopos.mybuilderapp.ui.MainActivity

import android.provider.MediaStore

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class PortfolioDialog(private val mFragment: ResumeFragment) :
    DialogFragment(R.layout.dialog_portfolio) {
    private val viewModel by viewModel<PortfolioViewModel>()
    private val adapter = PortfolioAdapter()
    private lateinit var bind: DialogPortfolioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        super.onCreate(savedInstanceState)
        setUpObserves()
    }

    init {
        show(mFragment.requireActivity().supportFragmentManager, "tag")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = DialogPortfolioBinding.inflate(layoutInflater).apply {
            rvPortfolio.adapter = adapter

            adapter.onItemClickListener { view, image, pos ->
                if (image?.id == null) {
                    pickGalleryImage(PASSPORT_GALLERY_REQ_CODE)
                } else {
                    try {
                        startActivity(IntentUtils.getUriViewIntent(
                            requireContext(),
                            image.imageUrl!!.toUri())
                        )
                    } catch (e: Exception) {

                    }
                    Log.d(TAG, "onCreateView: $image")
                }
            }
            loadData()
            btClose.onClick {
                dismiss()
            }

            btSave.onClick {

            }

        }
        return bind.root
    }

    private var onClickSave: (Address) -> Unit = {}

    fun onClickSaveButton(onClickSave: (Address) -> Unit) {
        this.onClickSave = onClickSave
    }

    private fun List<String>.toAdapter(): ArrayAdapter<String> {
        return ArrayAdapter(requireContext(), R.layout.item_spinner, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                when (requestCode) {
                    PASSPORT_GALLERY_REQ_CODE, PASSPORT_CAMERA_REQ_CODE -> {
                        viewModel.uploadImage(
                            uri = uri,
                            imageP = ImageP(
                                id = UUID.randomUUID().toString(),
                                userId = curUserUid,
                                resumeId = mFragment.resume?.resumeId,
                                createdTime = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }
            ImagePicker.RESULT_ERROR -> {
                toast(ImagePicker.getError(data))
            }
        }
    }

    private fun setUpObserves() {
        viewModel.uploadTask.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> showProgress()
                LoadingState.SUCCESS -> {
                    hideProgress()
                    Log.d("firebase", it.data.toString())
                    toast(it.data.toString())
                }
                LoadingState.ERROR -> {
                    hideProgress()
                    toast(it.message!!)
                }
            }
        })
    }

    private fun loadData() {
        viewModel.getPortfolios(
            userId = curUserUid,
            resumeId = mFragment.resume?.resumeId!!,
            onImageAdded = {
                adapter.add(it)
            },
            onImageModified = {
                adapter.modify(it)
            },
            onImageRemoved = {
                adapter.remove(it)
            },
            onFailure = {
                toast(it!!)
            }
        )
    }

    companion object {
        private const val PASSPORT_GALLERY_REQ_CODE = 102
        private const val PASSPORT_CAMERA_REQ_CODE = 103
    }
}