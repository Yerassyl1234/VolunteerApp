package org.example.volunteer.core.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

sealed class UiText {

    data class DynamicString(val value: String) : UiText()

    data class ResString(
        val resource: StringResource,
        val args: List<Any> = emptyList(),
    ) : UiText()

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is ResString     -> stringResource(resource, *args.toTypedArray())
    }

    suspend fun asStringAsync(): String = when (this) {
        is DynamicString -> value
        is ResString     -> getString(resource, *args.toTypedArray())
    }
}