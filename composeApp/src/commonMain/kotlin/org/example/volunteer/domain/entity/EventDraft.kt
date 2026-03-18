package org.example.volunteer.domain.entity

data class EventDraft(
    val title: String = "",
    val description: String = "",
    val category: Category? = null,
    val location: Location? = null,
    val dateMs: Long? = null,
    val durationHours: Int = 0,
    val totalSlots: Int = 0,
    val isFree: Boolean = true,
    val price: Double? = null,
    val coverUrl: String? = null,
) {
    val isValid get() = title.isNotBlank()
            && category != null
            && location != null
            && dateMs != null
            && totalSlots > 0
}