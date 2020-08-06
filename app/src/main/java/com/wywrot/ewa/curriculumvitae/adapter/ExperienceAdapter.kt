package com.wywrot.ewa.curriculumvitae.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wywrot.ewa.curriculumvitae.R
import com.wywrot.ewa.curriculumvitae.adapter.ExperienceAdapter.ViewType.Companion.EXPERIENCE
import com.wywrot.ewa.curriculumvitae.adapter.ExperienceAdapter.ViewType.Companion.HEADER
import com.wywrot.ewa.curriculumvitae.data.DateUtils.dateFormat
import com.wywrot.ewa.curriculumvitae.rest.Experience
import kotlinx.android.synthetic.main.fragment_experience_item_view.view.*


open class ExperienceAdapter : RecyclerView.Adapter<AbstractViewHolder>() {
    private val items: ArrayList<AdapterItem> = ArrayList()
    private lateinit var cachedExperience: List<Experience>

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) = holder.bind()
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int): Int = items[position].adapterItemType

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(HEADER, EXPERIENCE)
    private annotation class ViewType {
        companion object {
            const val HEADER = 0
            const val EXPERIENCE = 1
        }
    }

    fun setExperienceList(experiences: List<Experience>) {
        this.cachedExperience = experiences
        regenerateAdapterItems()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return when (viewType) {
            HEADER -> HeaderHolder(parent, R.layout.fragment_item_header)
            else -> ExperienceHolder(parent, R.layout.fragment_experience_item_view)
        }
    }

    private fun regenerateAdapterItems() {
        items.clear()
        items.add(AdapterItem(HEADER))

        if (::cachedExperience.isInitialized) {
            for (experience in cachedExperience) {
                items.add(AdapterItem(EXPERIENCE, experience))
            }
        }

        notifyDataSetChanged()
    }

    private inner class HeaderHolder(parent: ViewGroup, res: Int) :
        AbstractViewHolder(parent, res) {
        override fun bind() {
            (itemView as TextView).setText(R.string.experience)
        }
    }

    private inner class ExperienceHolder(parent: ViewGroup, res: Int) :
        AbstractViewHolder(parent, res) {
        override fun bind() {
            val item: AdapterItem = items[adapterPosition]

            with(item.experience!!) {
                itemView.companyName.text = companyName
                itemView.jobTitle.text = jobTitle
                itemView.startTime.setText(R.string.start_date, dateFormat.format(startTimestamp))
                itemView.endTime.setText(
                    R.string.end_date, getFormattedEndDate(getContext(), endTimestamp)
                )
                itemView.developedApps.text = getFormattedList(developedApps)
                Glide
                    .with(itemView)
                    .load(companyIcon)
                    .placeholder(R.drawable.ic_baseline_work_24)
                    .into(itemView.companyIcon)
            }
        }
    }

    private fun getFormattedEndDate(context: Context, endTimestamp: Long?): String =
        if (endTimestamp != null && endTimestamp > 0) dateFormat.format(endTimestamp)
        else context.resources.getString(R.string.present)

    private fun getFormattedList(list: List<String>?): String {
        var formattedList = ""
        list?.forEach {
            formattedList += it + "\n"
        }

        return formattedList
    }

    private class AdapterItem(
        @ViewType var adapterItemType: Int,
        var experience: Experience? = null
    ) {
        override fun toString(): String {
            return "type: $adapterItemType"
        }
    }
}