package com.example.a2parciall.data.mapper

import com.example.a2parciall.data.remote.dto.FrankfurterDto
import com.example.a2parciall.data.remote.dto.JsonPlaceholderDto
import com.example.a2parciall.model.PostModel

/**
 * MAPPER COMBINADO 2:
 * Combina datos de API B (Frankfurter) y API C (JSONPlaceholder).
 */
object CombinedRatePostMapper {

    fun map(
        frankfurterDto: FrankfurterDto,
        postsDto: List<JsonPlaceholderDto>
    ): List<PostModel> {
        val eurRate = frankfurterDto.rates?.get("EUR") ?: 0.92
        val bobRate = frankfurterDto.rates?.get("BOB") ?: 6.96
        val summary = "Tasas oficiales: 1 USD = %.2f EUR | 1 USD = %.2f BOB".format(eurRate, bobRate)

        return postsDto.map { dto ->
            PostModel(
                id = dto.id,
                title = dto.title,
                body = dto.body,
                marketRatesSummary = summary
            )
        }
    }
}
