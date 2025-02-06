package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesAdapter(
    private val clickListener: VacancyClickListener
) : RecyclerView.Adapter<VacanciesHolder>() {

    private var vacancyList: ArrayList<Vacancy> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacanciesHolder(
            binding = VacancyItemBinding.inflate(layoutInspector, parent, false),
            clickListener = clickListener
        )
    }

    override fun onBindViewHolder(holder: VacanciesHolder, position: Int) {
        val vacancy = vacancyList[position]
        holder.bind(vacancy)
    }

    override fun getItemCount(): Int = vacancyList.size

    fun updateVacancies(newVacancies: List<Vacancy>) {
        val diffCallback = VacanciesDiffUtils(vacancyList, vacancyList + newVacancies)
        val diffVacancy = DiffUtil.calculateDiff(diffCallback)
        vacancyList.addAll(newVacancies)
        diffVacancy.dispatchUpdatesTo(this)
    }

    fun clear() {
        vacancyList.clear()
        notifyDataSetChanged()
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancyId: String)
    }
}
