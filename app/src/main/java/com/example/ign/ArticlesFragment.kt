package com.example.ign


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_articles.*

/**
 * A simple [Fragment] subclass.
 */
class ArticlesFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var subAdapter : ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_articles, container, false)
        viewModel = activity?.run{
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid activity")

        val rv = root.findViewById<RecyclerView>(R.id.articles_recycler_view)
        subAdapter = ArticlesAdapter(viewModel)
        rv.adapter = subAdapter
        rv.layoutManager = LinearLayoutManager(context)

        viewModel.netFetchArticlesPost()
        viewModel.observeArticlesPost().observe(this, Observer{
            subAdapter.submitList(it)
        })

        return root
    }

}
