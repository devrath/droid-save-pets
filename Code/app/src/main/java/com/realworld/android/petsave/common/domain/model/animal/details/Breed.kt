package com.realworld.android.petsave.common.domain.model.animal.details

data class Breed(
  // The primary breed.
  val primary: String,
  // The secondary breed.
  val secondary: String
  ) {
  // Only the variables in constructor are auto-generated but other values(mixed,unknown) are not auto-generated
  // By having some properties depend on others, you increase the cohesion of the class. This increases the amount of information conveyed when you read the class.

  // Tells you if the animal has mixed breeds — it has both a primary and a secondary breed.
  val mixed: Boolean
    get() = primary.isNotEmpty() && secondary.isNotEmpty()
  // Tells you if the animal’s breed is unknown — it has neither a primary nor a secondary breed.
  val unknown: Boolean
    get() = primary.isEmpty() && secondary.isEmpty()
}