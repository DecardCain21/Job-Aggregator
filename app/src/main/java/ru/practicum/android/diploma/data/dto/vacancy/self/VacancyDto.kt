package ru.practicum.android.diploma.data.dto.vacancy.self

import ru.practicum.android.diploma.data.dto.vacancy.nested.AreaDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.EmployerDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.EmploymentDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ExperienceDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.SalaryDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ScheduleDto

@Suppress("LongParameterList")
class VacancyDto(
    val id: String,
    val area: AreaDto?,
    val employer: EmployerDto,
    val employment: EmploymentDto?,
    val experience: ExperienceDto?,
    val name: String,
    val salary: SalaryDto?,
    val schedule: ScheduleDto?,
)
