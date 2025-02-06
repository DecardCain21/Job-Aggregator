package ru.practicum.android.diploma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.databinding.CountryItemBinding
import ru.practicum.android.diploma.databinding.RegionItemBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region

class FilterAdapter : RecyclerView.Adapter<ViewHolder>() {

    var saveFilterListener: SaveFilterListener? = null
    private var items: List<ItemFilter> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemFilter.TYPE_AREA -> CountryViewHolder(
                binding = CountryItemBinding.inflate(layoutInflater, parent, false)
            )

            ItemFilter.TYPE_REGION -> RegionViewHolder(
                binding = RegionItemBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is CountryViewHolder -> {
                holder.bind(item.data as Country)
                holder.itemView.setOnClickListener { saveFilterListener?.onItemClicked(item) }
            }

            is RegionViewHolder -> {
                holder.bind(item.data as Region)
                holder.itemView.setOnClickListener { saveFilterListener?.onItemClicked(item) }
            }

            else -> throw IllegalArgumentException("Unknown ViewHolder for position $position")
        }
    }

    private fun updateItems(items: List<ItemFilter>) {
        this.items = emptyList()
        this.items = items
    }

    fun convertToItemFilter(data: List<Any>, typeItemFilter: Int): List<ItemFilter> {
        return data.map { ItemFilter(data = it, type = typeItemFilter) }
    }

    fun setCourses(newItems: List<ItemFilter>) {
        val diffCallback = ItemFilterDiffUtils(items, newItems)
        val diffItems = DiffUtil.calculateDiff(diffCallback)
        updateItems(newItems)
        diffItems.dispatchUpdatesTo(this)
    }

    interface SaveFilterListener {
        fun onItemClicked(item: ItemFilter)
    }
}
