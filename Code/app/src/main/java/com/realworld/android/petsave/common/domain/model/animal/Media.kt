package com.realworld.android.petsave.common.domain.model.animal

// Both Photo and Video classes are nested in Media for ease of access.
data class Media(
    // A list of Photo objects.
    val photos: List<Photo>,
    // A list of Video objects.
    val videos: List<Video>
) {

  companion object {
    private const val EMPTY_MEDIA = ""
  }

  fun getFirstSmallestAvailablePhoto(): String {
    if (photos.isEmpty()) return EMPTY_MEDIA

    return photos.first().getSmallestAvailablePhoto()
  }

  data class Photo(
      // A link for the medium-sized photo.
      val medium: String,
      // A link for the full-sized photo.
      val full: String
  ) {

    companion object {
      private const val EMPTY_PHOTO = ""
    }

    fun getSmallestAvailablePhoto(): String {
      return when {
        isValidPhoto(medium) -> medium
        isValidPhoto(full) -> full
        else -> EMPTY_PHOTO
      }
    }

    private fun isValidPhoto(photo: String): Boolean {
      return photo.isNotEmpty()
    }
  }

  data class Video(val video: String)
}
