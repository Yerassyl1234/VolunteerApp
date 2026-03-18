package org.example.volunteer.core.ui

import androidx.compose.runtime.Composable

sealed class UiText {

    data class DynamicString(val value: String) : UiText()

    data class StringResource(
        val resource: org.jetbrains.compose.resources.StringResource,
        val args: List<Any> = emptyList(),
    ) : UiText()

    @Composable
    fun asString(): String = when (this) {
        is DynamicString  -> value
        is StringResource -> stringResource(resource, *args.toTypedArray())
    }
}