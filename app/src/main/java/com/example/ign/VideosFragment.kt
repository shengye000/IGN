package com.example.ign


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_videos.*

/**
 * A simple [Fragment] subclass.
 */
class VideosFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var subAdapter : VideosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_videos, container, false)
        viewModel = activity?.run{
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid activity")

        val rv = root.findViewById<RecyclerView>(R.id.videos_recycler_view)
        subAdapter = VideosAdapter(viewModel)
        rv.adapter = subAdapter
        rv.layoutManager = LinearLayoutManager(context)

        viewModel.netFetchVideosPost()
        viewModel.observeVideosPost().observe(this, Observer{
            subAdapter.submitList(it)
        })

        return root
    }


}
