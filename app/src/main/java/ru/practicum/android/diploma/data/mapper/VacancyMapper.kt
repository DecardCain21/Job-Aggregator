package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.vacancy.self.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.SalaryFormatter

class VacancyMapper {

    fun map(response: Result<VacanciesSearchResponse>): Result<VacancySearchResult> {
        return response.map { vacanciesResponse ->
            VacancySearchResult(
                items = vacanciesResponse.items.map { vacancy -> map(vacancy) },
                pages = vacanciesResponse.pages,
                totalVacancyCount = vacanciesResponse.found
            )
        }
    }

    private fun map(dto: VacancyDto) = Vacancy(
        id = dto.id,
        imageUrl = dto.employer.logoUrls?.original,
        name = dto.name,
        city = dto.area?.name.orEmpty(),
        salaryFrom = dto.salary?.from ?: 0,
        salaryTo = dto.salary?.to ?: 0,
        currency = SalaryFormatter.fromCurrencyName(dto.salary?.currency),
        employerName = dto.employer.name.orEmpty(),
        experience = dto.experience?.name.orEmpty(),
        employmentName = dto.employment?.name.orEmpty(),
        schedule = dto.schedule?.name.orEmpty()
    )
}
