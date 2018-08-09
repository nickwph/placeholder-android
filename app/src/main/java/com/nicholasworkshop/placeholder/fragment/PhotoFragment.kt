package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.TypedEpoxyController
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.api.PhotoService
import com.nicholasworkshop.placeholder.model.Photo
import com.nicholasworkshop.placeholder.model.PhotoDao
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.adapter.parse
import com.nicholasworkshop.placeholder.viewPhotoItem
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_photo.*
import javax.inject.Inject
import android.support.v7.widget.GridLayoutManager




class PhotoFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(albumId: Long): PhotoFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, albumId)
            val fragment = PhotoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var photoService: PhotoService
    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: PhotoViewModel
    private val photoController = PhotoEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val albumId = arguments?.getLong(ARG_ID)!!
        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        photoController.spanCount = spanCount
        epoxyRecyclerView.setController(photoController)
        layoutManager.spanSizeLookup = photoController.spanSizeLookup
        epoxyRecyclerView.layoutManager = layoutManager
        viewModel = ViewModelProviders
                .of(this, PhotoViewModel.Factory(mainDatabase.photoDao()))
                .get(PhotoViewModel::class.java)
        viewModel.photoDao.findByAlbumId(albumId).observe(this, Observer {
            photoController.setData(it)
        })
        photoService.getPhotoList()
                .subscribeOn(Schedulers.io())
                .subscribe { mainDatabase.photoDao().insertAll(parse(it)) }
    }

    class PhotoViewModel(
            val photoDao: PhotoDao
    ) : ViewModel() {

        @Suppress("UNCHECKED_CAST")
        class Factory(
                private val photoDao: PhotoDao
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PhotoViewModel(photoDao) as T
            }
        }
    }

    inner class PhotoEpoxyController : TypedEpoxyController<List<Photo>>() {

        override fun buildModels(photos: List<Photo>) {
            for (photo in photos) {
                viewPhotoItem {
                    id(photo.id)
                    photo(photo)
                    clickListener { v ->
                        fragmentManager!!.beginTransaction()
//                                .addToBackStack(null)
//                                .replace(R.id.contentView, HomeTabFragment.newInstance(photo.id))
                                .commit()
                    }
                }
            }
        }
    }
}
