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
import androidx.recyclerview.widget.RecyclerView
import com.example.ign.api.IGNArticles

class ArticlesAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<ArticlesAdapter.VH>() {

    private var posts = listOf<IGNArticles>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesAdapter.VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_articles, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: ArticlesAdapter.VH, position: Int) {
        holder.bind(posts[holder.adapterPosition])
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

        fun bind(item: IGNArticles?) {
            if(item == null)
                return

            Log.d("id", item.contentID)
            date.text = item.metadata.publishDate

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
                intent.putExtra("URL", "https://www.ign.com/search?q=" + item.metadata.slug + "&page=0&count=10&filter=all&")
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

            game.text = item.metadata.objectName
            if(item.metadata.objectName != ""){
                game.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                game.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, WebsiteView::class.java)
                    var name = item.metadata.objectName
                    val regex = Regex("[^a-zA-Z0-9_-]")
                    name = name.replace(" ", "-")
                    name = regex.replace(name, "").toLowerCase()
                    Log.d("name", name)
                    intent.putExtra("URL", "https://www.ign.com/games/" + name)
                    context.startActivity(intent)
                }
            }
            comment.text = "???"
        }

    }

    fun submitList(items: List<IGNArticles>) {
        posts = items
        notifyDataSetChanged()
    }
}


