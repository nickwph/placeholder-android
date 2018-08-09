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
import com.nicholasworkshop.placeholder.api.ToDoService
import com.nicholasworkshop.placeholder.model.MainDatabase
import com.nicholasworkshop.placeholder.model.ToDo
import com.nicholasworkshop.placeholder.model.ToDoDao
import com.nicholasworkshop.placeholder.model.adapter.parse
import com.nicholasworkshop.placeholder.utility.DaoViewModel
import com.nicholasworkshop.placeholder.viewTodoItem
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_todo.*
import javax.inject.Inject


class ToDoFragment : Fragment() {

    companion object {

        private const val ARG_ID = "ARG_ID"

        fun newInstance(userId: Long): ToDoFragment {
            val bundle = Bundle()
            bundle.putLong(ARG_ID, userId)
            val fragment = ToDoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var toDoService: ToDoService
    @Inject lateinit var mainDatabase: MainDatabase

    private lateinit var viewModel: DaoViewModel<ToDoDao>
    private val toDoController = ToDoEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getLong(ARG_ID)!!
        epoxyRecyclerView.setController(toDoController)
        viewModel = DaoViewModel.newInstance(this, mainDatabase.toDoDao(), ToDoDao::class.java)
        viewModel.dao.findByUserId(userId).observe(this, Observer {
            toDoController.setData(it)
        })
        toDoService.getToDoList()
                .subscribeOn(Schedulers.io())
                .subscribe { mainDatabase.toDoDao().insertAll(parse(it)) }
    }

    inner class ToDoEpoxyController : TypedEpoxyController<List<ToDo>>() {

        override fun buildModels(toDos: List<ToDo>) {
            for (toDo in toDos) {
                viewTodoItem {
                    id(toDo.id)
                    toDo(toDo)
                    clickListener { v ->
                        fragmentManager!!.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.contentView, PhotoFragment.newInstance(toDo.id))
                                .commit()
                    }
                }
            }
        }
    }
}
