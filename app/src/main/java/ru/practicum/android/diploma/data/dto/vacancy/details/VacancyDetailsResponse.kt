package ru.practicum.android.diploma.data.dto.vacancy.details

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.vacancy.nested.AddressDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.AreaDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.EmployerDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.EmploymentDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ExperienceDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.KeySkillDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.SalaryDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ScheduleDto

@Suppress("LongParameterList")
class VacancyDetailsResponse(
    val id: String,
    val address: AddressDto?,
    val alternateUrl: String?,
    val area: AreaDto,
    val description: String?,
    val employer: EmployerDto?,
    val employment: EmploymentDto?,
    val experience: ExperienceDto?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>,
    val name: String?,
    val salary: SalaryDto?,
    val schedule: ScheduleDto,
) : Response() {

    fun isEmpty(): Boolean {
        return id.isBlank() &&
            keySkills.isEmpty() &&
            description.isNullOrBlank() &&
            employer == null &&
            salary == null
    }
}
