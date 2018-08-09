package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.TypedEpoxyController
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.api.AlbumService
import com.nicholasworkshop.placeholder.model.Album
import com.nicholasworkshop.placeholder.model.AlbumDao
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.adapter.parse
import com.nicholasworkshop.placeholder.utility.DaoViewModel
import com.nicholasworkshop.placeholder.viewAlbumItem
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_album.*
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
    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: DaoViewModel<AlbumDao>
    private val albumController = AlbumEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getLong(ARG_ID)!!
        epoxyRecyclerView.setController(albumController)
        viewModel = DaoViewModel.newInstance(this, AlbumDao::class.java, mainDatabase.albumDao())
        viewModel.dao.findByUserId(userId).observe(this, Observer {
            albumController.setData(it)
        })
        albumService.getAlbumList()
                .subscribeOn(Schedulers.io())
                .subscribe { mainDatabase.albumDao().insertAll(parse(it)) }
    }

    inner class AlbumEpoxyController : TypedEpoxyController<List<Album>>() {

        override fun buildModels(albums: List<Album>) {
            for (album in albums) {
                viewAlbumItem {
                    id(album.id)
                    album(album)
                    clickListener { v ->
                        fragmentManager!!.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.contentView, PhotoFragment.newInstance(album.id))
                                .commit()
                    }
                }
            }
        }
    }
}
