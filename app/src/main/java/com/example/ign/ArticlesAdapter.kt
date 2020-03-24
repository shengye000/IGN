package com.example.ign

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.ign.api.IGNArticles
import com.example.ign.api.IGNComments
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.xml.datatype.DatatypeConstants.DAYS



class ArticlesAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<ArticlesAdapter.VH>() {

    private var posts = listOf<IGNArticles>()
    private var comments = listOf<IGNComments>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesAdapter.VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_articles, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: ArticlesAdapter.VH, position: Int) {
        holder.bind(posts[holder.adapterPosition], comments[holder.adapterPosition])
    }

    override fun getItemCount() = posts.size

    inner class VH(itemView: View)
        : RecyclerView.ViewHolder(itemView){

        private var date : TextView = itemView.findViewById(R.id.articles_date)
        private var title: TextView = itemView.findViewById(R.id.articles_title)
        private var picture : ImageView = itemView.findViewById(R.id.articles_picture)
        private var description : TextView = itemView.findViewById(R.id.articles_description)
        private var authorImage : ImageView = itemView.findViewById(R.id.articles_author_picture)
        private var authorName : TextView = itemView.findViewById(R.id.articles_author_name)
        private var game : TextView = itemView.findViewById(R.id.articles_game)
        private var comment : TextView = itemView.findViewById(R.id.articles_comment_count)

        fun bind(item: IGNArticles?, commentCount: IGNComments?) {
            if(item == null)
                return

            Log.d("id", item.contentID)

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

            title.text = item.metadata.headline
            title.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/articles/" + item.metadata.slug)
                context.startActivity(intent)
            }

            Glide.glideFetch(item.thumbnails[2].url, item.thumbnails[0].url, picture)
            picture.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/articles/" + item.metadata.slug)
                context.startActivity(intent)
            }

            description.text = item.metadata.description
            description.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/articles/" + item.metadata.slug)
                context.startActivity(intent)
            }

            Glide.glideFetch(item.authors[0].authorThumbNail, item.authors[0].authorThumbNail, authorImage)
            authorImage.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/search?q=" + item.authors[0].authorName + "&page=0&count=10&filter=all&")
                context.startActivity(intent)
            }

            authorName.text = "By " + item.authors[0].authorName
            authorName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            authorName.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, WebsiteView::class.java)
                intent.putExtra("URL", "https://www.ign.com/search?q=" + item.authors[0].authorName + "&page=0&count=10&filter=all&")
                context.startActivity(intent)
            }

            game.text = item.metadata.objectName.toUpperCase()
            if(item.metadata.objectName != ""){
                game.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                game.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, WebsiteView::class.java)
                    var name = item.metadata.objectName
                    intent.putExtra("URL", "https://www.ign.com/search?q=" + name + "&page=0&count=10&filter=all&")
                    context.startActivity(intent)
                }
            }

            comment.text = commentCount!!.count.toString()
            Log.d("number", commentCount.count.toString() + " " + commentCount.id)
        }

    }

    fun submitList(items: List<IGNArticles>) {
        posts = items
        notifyDataSetChanged()
    }

    fun submitComments(items: List<IGNComments>){
        comments = items
        notifyDataSetChanged()
    }
}


