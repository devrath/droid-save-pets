package com.realworld.android.petsave.common.domain.model.animal

import java.time.LocalDateTime

data class Animal(
    val id: Long,
    val name: String,
    val type: String,
    // A Media value object instance that handles photos and videos of the animal.
    val media: Media,
    val tags: List<String>,
    // An enum value from AdoptionStatus
    val adoptionStatus: AdoptionStatus,
    val publishedAt: LocalDateTime
)
