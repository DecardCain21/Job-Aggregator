package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.DecimalFormat

class SalaryFormatter(
    private val context: Context,
) {

    private val formatter = DecimalFormat("#,###")

    fun salaryFormat(salaryTo: Int, salaryFrom: Int, currency: Currency): String {
        val formattedSalaryTo = formatter.format(salaryTo).replace(",", " ")
        val formattedSalaryFrom = formatter.format(salaryFrom).replace(",", " ")
        val currencySymbol = currency.symbol

        return when {
            salaryTo != 0 && salaryFrom == 0 -> context.getString(
                R.string.salary_to_format,
                formattedSalaryTo,
                currencySymbol
            )

            salaryTo == 0 && salaryFrom != 0 -> context.getString(
                R.string.salary_from_format,
                formattedSalaryFrom,
                currencySymbol
            )

            salaryTo != 0 && salaryFrom != 0 -> context.getString(
                R.string.salary_range_format,
                formattedSalaryFrom,
                formattedSalaryTo,
                currencySymbol
            )

            else -> context.getString(R.string.salary_is_not_specified)
        }
    }

    companion object {
        fun fromCurrencyName(name: String?): Currency {
            return Currency.entries.find { it.currencyName == name } ?: Currency.CURRENCY_UNSPECIFIED
        }
    }
}
