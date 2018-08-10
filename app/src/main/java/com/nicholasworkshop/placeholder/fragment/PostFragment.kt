package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.TypedEpoxyController
import com.nicholasworkshop.placeholder.MainApplication
import com.nicholasworkshop.placeholder.R
import com.nicholasworkshop.placeholder.api.PostService
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.Post
import com.nicholasworkshop.placeholder.model.PostDao
import com.nicholasworkshop.placeholder.model.adapter.parse
import com.nicholasworkshop.placeholder.utility.DaoViewModel
import com.nicholasworkshop.placeholder.utility.SimpleDividerItemDecoration
import com.nicholasworkshop.placeholder.viewPostItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_post.*
import javax.inject.Inject


class PostFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(userId: Long): PostFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, userId)
            val fragment = PostFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var postService: PostService
    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: DaoViewModel<PostDao>
    private val postController = PostEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getLong(ARG_ID)!!
        epoxyRecyclerView.setController(postController)
        epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
        epoxyRecyclerView.addItemDecoration(SimpleDividerItemDecoration(context!!))
        Observable.fromCallable { mainDatabase.userDao().findByIdSync(userId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (activity as AppCompatActivity).supportActionBar!!.title = "${it?.name}'s posts" }
        viewModel = DaoViewModel.newInstance(this, PostDao::class.java, mainDatabase.postDao())
        viewModel.dao.findByUserId(userId).observe(this, Observer {
            postController.setData(it)
        })
        postService.getPostList()
                .subscribeOn(Schedulers.io())
                .subscribe { mainDatabase.postDao().insertAll(parse(it)) }
    }

    inner class PostEpoxyController : TypedEpoxyController<List<Post>>() {

        override fun buildModels(posts: List<Post>) {
            for (post in posts) {
                viewPostItem {
                    id(post.id)
                    post(post)
                    clickListener { v ->
                        fragmentManager!!.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.contentView, CommentFragment.newInstance(post.id))
                                .commit()
                    }
                }
            }
        }
    }
}
