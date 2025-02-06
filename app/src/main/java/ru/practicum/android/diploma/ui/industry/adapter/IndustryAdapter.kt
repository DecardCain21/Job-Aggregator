package ru.practicum.android.diploma.ui.industry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(
    private val clickListener: IndustryClickListener
) : RecyclerView.Adapter<IndustryViewHolder>() {

    private var industryList: ArrayList<IndustryItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IndustryItemBinding.inflate(layoutInflater, parent, false)
        return IndustryViewHolder(binding)
    }

    override fun getItemCount(): Int = industryList.size

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = industryList[position]
        holder.bind(industry, industry.isChecked)
        holder.itemView.setOnClickListener { clickListener.onIndustryClick(industry.industry) }
    }

    fun updateIndustries(industries: List<IndustryItem>) {
        val diffCallback = IndustryDiffUtils(industryList, industries)
        val diffIndustry = DiffUtil.calculateDiff(diffCallback)
        industryList.clear()
        industryList.addAll(industries)
        diffIndustry.dispatchUpdatesTo(this)
    }

    fun interface IndustryClickListener {
        fun onIndustryClick(industry: Industry?)
    }
}
