package com.nicholasworkshop.placeholder.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.Typed2EpoxyController
import com.nicholasworkshop.placeholder.*
import com.nicholasworkshop.placeholder.api.CommentService
import com.nicholasworkshop.placeholder.api.PostService
import com.nicholasworkshop.placeholder.model.*
import com.nicholasworkshop.placeholder.model.adapter.parse
import com.nicholasworkshop.placeholder.utility.Dao2ViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_post.*
import javax.inject.Inject


class CommentFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(postId: Long): CommentFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, postId)
            val fragment = CommentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var postService: PostService
    @Inject lateinit var commentService: CommentService
    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: Dao2ViewModel<PostDao, CommentDao>
    private val commentController = CommentEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val postId = arguments?.getLong(ARG_ID)!!
        epoxyRecyclerView.setController(commentController)
        epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel = Dao2ViewModel.newInstance(this, PostDao::class.java, mainDatabase.postDao(), CommentDao::class.java, mainDatabase.commentDao())
        viewModel.dao1.findById(postId).observe(this, Observer {
            commentController.setData(it)
        })
        viewModel.dao2.findByPostId(postId).observe(this, Observer {
            commentController.setData(it)
        })
        postService.getPostList()
                .subscribeOn(Schedulers.io())
                .subscribe { mainDatabase.postDao().insertAll(parse(it)) }
        commentService.getCommentList()
                .subscribeOn(Schedulers.io())
                .subscribe { mainDatabase.commentDao().insertAll(parse(it)) }
    }

    inner class CommentEpoxyController : Typed2EpoxyController<Post, List<Comment>>() {

        var post: Post? = null
        var comments: List<Comment>? = null

        fun setData(data1: Post?) {
            post = data1
            super.setData(data1, comments)
        }

        fun setData(data2: List<Comment>?) {
            comments = data2
            super.setData(post, data2)
        }

        override fun setData(data1: Post?, data2: List<Comment>?) {
            post = data1
            comments = data2
            super.setData(data1, data2)
        }

        override fun buildModels(post: Post?, comments: List<Comment>?) {
            if (post != null) {
                viewPostItem {
                    id("post-${post.id}")
                    post(post)
                }
            }
            viewCommentItemHeader {
                id("header")
            }
            if (comments != null) {
                for (comment in comments) {
                    viewCommentItem {
                        id("comment-${comment.id}")
                        comment(comment)
                    }
                }
            }
        }
    }
}
