package ru.practicum.android.diploma.ui.industry.adapter

import androidx.recyclerview.widget.DiffUtil

class IndustryDiffUtils(
    private val oldList: List<IndustryItem>,
    private val newList: List<IndustryItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].industry.id == newList[newItemPosition].industry.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isChecked == newList[newItemPosition].isChecked
    }
}
