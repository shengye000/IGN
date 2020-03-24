package com.example.ign

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ign.api.IGNComments
import com.example.ign.api.IGNVideos
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class VideosAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<VideosAdapter.VH>() {

    private var posts = listOf<IGNVideos>()
    private var comments = listOf<IGNComments>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdapter.VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_videos, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VideosAdapter.VH, position: Int) {
        holder.bind(posts[holder.adapterPosition], comments[holder.adapterPosition])
    }

    override fun getItemCount() = posts.size

    inner class VH(itemView: View)
        : RecyclerView.ViewHolder(itemView){

        private var date : TextView = itemView.findViewById(R.id.videos_date)
        private var picture : ImageView = itemView.findViewById(R.id.videos_picture)
        private var button : ImageButton = itemView.findViewById(R.id.videos_button)
        private var title : TextView = itemView.findViewById(R.id.videos_title)
        private var game : TextView = itemView.findViewById(R.id.videos_game)
        private var comment: TextView = itemView.findViewById(R.id.videos_comment_count)

        fun bind(item: IGNVideos?, commentCount: IGNComments?) {
            if(item == null)
                return

            val publishTimeConvert = item.metadata.publishDate.substring(0, 22) + ":" + item.metadata.publishDate.substring(22, item.metadata.publishDate.length)
            val publishTime = OffsetDateTime.parse(publishTimeConvert, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            val currentTime = OffsetDateTime.now()
            val seconds = publishTime.until(currentTime, ChronoUnit.SECONDS)
            if(seconds < 60){
                date.text = seconds.toString() + " secs ago"
            }
            else if (seconds >= 60 && seconds < 3600){
                date.text = publishTime.until(currentTime, ChronoUnit.MINUTES).toString() + " mins ago"
            }
            else if (seconds >= 3600 && seconds <= 86400){
                date.text = publishTime.until(currentTime, ChronoUnit.HOURS).toString() + " hours ago"
            }
            else{
                date.text = publishTime.until(currentTime, ChronoUnit.DAYS).toString() + " days ago"
            }


            Glide.glideFetch(item.thumbnails[2].url, item.thumbnails[0].url, picture)
            picture.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/videos/" + item.metadata.slug)
                context.startActivity(intent)
            }

            button.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/videos/" + item.metadata.slug)
                context.startActivity(intent)
            }

            title.text = item.metadata.title
            title.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/videos/" + item.metadata.slug)
                context.startActivity(intent)
            }

            game.text = item.metadata.videoSeries.toUpperCase()
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

            comment.text = commentCount!!.count.toString()
        }

    }

    fun submitList(items: List<IGNVideos>) {
        posts = items
        notifyDataSetChanged()
    }

    fun submitComments(items: List<IGNComments>){
        comments = items
        notifyDataSetChanged()
    }
}