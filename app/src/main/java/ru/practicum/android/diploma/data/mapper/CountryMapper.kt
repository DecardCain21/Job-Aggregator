package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.area.AreaDto
import ru.practicum.android.diploma.domain.models.Country

class CountryMapper(
    private val regionMapper: RegionMapper
) {
    fun map(result: Result<List<AreaDto>>): Result<List<Country>> {
        return result.map { areaDtoList ->
            val countries = areaDtoList.map { areaDto ->
                map(areaDto)
            }.toMutableList()

            countries.find { it.id == OTHER_COUNTRY_ITEM_ID }?.let { otherCountry ->
                countries.remove(otherCountry)
                countries.add(otherCountry)
            }
            countries
        }
    }

    fun map(dto: AreaDto): Country {
        val regions = dto.areas.map { regionMapper.map(it) }
        return Country(id = dto.id, name = dto.name, regions = regions)
    }

    fun map(country: Country): AreaDto {
        val areas = country.regions.map { regionMapper.map(it, country.id) }
        return AreaDto(id = country.id, name = country.name, areas = areas)
    }

    companion object {
        private const val OTHER_COUNTRY_ITEM_ID = "1001"
    }
}
