package com.wywrot.ewa.curriculumvitae.adapter

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wywrot.ewa.curriculumvitae.R
import com.wywrot.ewa.curriculumvitae.adapter.ArticlesAdapter.ViewType.Companion.ARTICLE
import com.wywrot.ewa.curriculumvitae.adapter.ArticlesAdapter.ViewType.Companion.HEADER
import com.wywrot.ewa.curriculumvitae.rest.Article
import kotlinx.android.synthetic.main.fragment_article_item_view.view.*

open class ArticlesAdapter : RecyclerView.Adapter<AbstractViewHolder>() {
    private val items: ArrayList<AdapterItem> = ArrayList()
    private lateinit var cachedArticles: List<Article>

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) = holder.bind()
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int): Int = items[position].adapterItemType

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(HEADER, ARTICLE)
    private annotation class ViewType {
        companion object {
            const val HEADER = 0
            const val ARTICLE = 1
        }
    }

    fun setArticlesList(articles: List<Article>) {
        this.cachedArticles = articles
        regenerateAdapterItems()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return when (viewType) {
            HEADER -> HeaderHolder(parent, R.layout.fragment_item_header)
            else -> ArticleHolder(parent, R.layout.fragment_article_item_view)
        }
    }

    private fun regenerateAdapterItems() {
        items.clear()
        items.add(AdapterItem(HEADER))

        if (::cachedArticles.isInitialized) {
            for (article in cachedArticles) {
                items.add(AdapterItem(ARTICLE, article))
            }
        }

        notifyDataSetChanged()
    }

    private inner class HeaderHolder(parent: ViewGroup, res: Int) :
        AbstractViewHolder(parent, res) {
        override fun bind() {
            (itemView as TextView).setText(R.string.my_articles)
        }
    }

    private inner class ArticleHolder(parent: ViewGroup, res: Int) :
        AbstractViewHolder(parent, res) {
        override fun bind() {
            val item: AdapterItem = items[adapterPosition]

            with(item.article!!) {
                itemView.articleName.text = title
                itemView.articleUrl.text = link

                itemView.setOnClickListener {
                    try {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(link)
                        startActivity(getContext(), i, null)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private class AdapterItem(
        @ViewType var adapterItemType: Int,
        var article: Article? = null
    ) {
        override fun toString(): String {
            return "type: $adapterItemType"
        }
    }
}