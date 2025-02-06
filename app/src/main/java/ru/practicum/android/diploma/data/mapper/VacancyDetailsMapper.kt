package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsResponse
import ru.practicum.android.diploma.data.formatter.AddressFormatter
import ru.practicum.android.diploma.data.formatter.SkillsFormatter
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.SalaryFormatter

class VacancyDetailsMapper {

    fun map(response: Result<VacancyDetailsResponse>): Result<VacancyDetails> =
        response.map { vacancyDetailsResponse ->
            VacancyDetails(
                id = vacancyDetailsResponse.id,
                imageUrl = vacancyDetailsResponse.employer?.logoUrls?.original,
                name = vacancyDetailsResponse.name.orEmpty(),
                area = vacancyDetailsResponse.area.name.orEmpty(),
                salaryFrom = vacancyDetailsResponse.salary?.from ?: 0,
                salaryTo = vacancyDetailsResponse.salary?.to ?: 0,
                currency = SalaryFormatter.fromCurrencyName(vacancyDetailsResponse.salary?.currency),
                employerName = vacancyDetailsResponse.employer?.name.orEmpty(),
                experience = vacancyDetailsResponse.experience?.name.orEmpty(),
                employmentName = vacancyDetailsResponse.employment?.name.orEmpty(),
                schedule = vacancyDetailsResponse.schedule.name.orEmpty(),
                description = vacancyDetailsResponse.description.orEmpty(),
                descriptionSkills = SkillsFormatter.format(vacancyDetailsResponse.keySkills),
                url = vacancyDetailsResponse.alternateUrl.orEmpty(),
                address = AddressFormatter.format(vacancyDetailsResponse.address)
            )
        }
}
