package ru.practicum.android.diploma.ui.adapters

import androidx.recyclerview.widget.DiffUtil

class ItemFilterDiffUtils(
    private val oldList: List<ItemFilter>,
    private val newList: List<ItemFilter>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getItemId() == newList[newItemPosition].getItemId()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getItemId() == newList[newItemPosition].getItemId()
    }
}
