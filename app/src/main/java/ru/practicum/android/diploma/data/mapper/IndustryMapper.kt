package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.industry.IndustryDto
import ru.practicum.android.diploma.data.dto.industry.IndustryItemDto
import ru.practicum.android.diploma.domain.models.Industry

class IndustryMapper {

    fun map(result: Result<List<IndustryItemDto>>): Result<List<Industry>> =
        result.map { group ->
            group.flatMap { item ->
                item.industries.map { dto -> map(dto) }
            }
        }

    fun map(dto: IndustryDto) = Industry(id = dto.id, name = dto.name)

    fun map(industry: Industry) = IndustryDto(id = industry.id, name = industry.name)
}

