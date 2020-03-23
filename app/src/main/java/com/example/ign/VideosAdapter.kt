package com.example.ign

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ign.api.IGNVideos

class VideosAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<VideosAdapter.VH>() {

    private var posts = listOf<IGNVideos>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdapter.VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_videos, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VideosAdapter.VH, position: Int) {
        holder.bind(posts[holder.adapterPosition])
    }

    override fun getItemCount() = posts.size

    inner class VH(itemView: View)
        : RecyclerView.ViewHolder(itemView){

//        private var date : TextView = itemView.findViewById(R.id.articles_date)
        private var date : TextView = itemView.findViewById(R.id.videos_date)
        private var picture : ImageView = itemView.findViewById(R.id.videos_picture)
        private var button : ImageButton = itemView.findViewById(R.id.videos_button)
        private var title : TextView = itemView.findViewById(R.id.videos_title)
        private var game : TextView = itemView.findViewById(R.id.videos_game)
        private var comment: TextView = itemView.findViewById(R.id.videos_comment_count)

        fun bind(item: IGNVideos?) {
            if(item == null)
                return

            date.text = item.metadata.publishDate

            Glide.glideFetch(item.thumbnails[2].url, item.thumbnails[0].url, picture)
            picture.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", item.assets[item.assets.size - 1].url)
                context.startActivity(intent)
            }

            button.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", item.assets[item.assets.size - 1].url)
                context.startActivity(intent)
            }

            title.text = item.metadata.title
            title.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", item.assets[item.assets.size - 1].url)
                context.startActivity(intent)
            }

            game.text = item.metadata.videoSeries
            if(item.metadata.videoSeries != "none"){
                game.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                game.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, WebsiteView::class.java)
                    intent.putExtra("URL", "https://www.ign.com/search?q=" + item.metadata.videoSeries + "&page=0&count=10&filter=all&")
                    context.startActivity(intent)
                }
            }
            else{
                game.text = ""
            }
            comment.text = "???"
        }

    }

    fun submitList(items: List<IGNVideos>) {
        posts = items
        notifyDataSetChanged()
    }
}