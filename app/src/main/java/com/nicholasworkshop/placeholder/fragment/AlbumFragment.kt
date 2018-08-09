package com.nicholasworkshop.placeholder.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.TypedEpoxyController
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.api.Album
import com.nicholasworkshop.placeholder.api.AlbumService
import com.nicholasworkshop.placeholder.viewAlbumItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user.*
import timber.log.Timber
import javax.inject.Inject


class AlbumFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(userId: Long): AlbumFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, userId)
            val fragment = AlbumFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var albumService: AlbumService

    private val albumController = AlbumController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getLong(ARG_ID)
        epoxyRecyclerView.setController(albumController)
        albumService.getAlbumList(userId = userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { albumController.setData(it) }
    }

    inner class AlbumController : TypedEpoxyController<List<Album>>() {

        override fun buildModels(albums: List<Album>) {
            for (album in albums) {
                Timber.e("data user $album")
                viewAlbumItem {
                    id(album.id)
                    album(album)
                    clickListener { v ->
                        Timber.e("hi user $album")
                        fragmentManager!!.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.containerView, HomeTabFragment())
                                .commit()
                    }
                }
            }
        }
    }
}
