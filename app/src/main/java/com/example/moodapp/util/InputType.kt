package com.example.moodapp.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation,
    val initialValue: String = ""
) {
    class Date(initialValue: String = "") : InputType(
        label = "Data",
        icon = Icons.Default.DateRange,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None,
        initialValue = initialValue
    )

    object Mood : InputType(
        label = "Como você está hoje?",
        icon = Icons.Default.Favorite,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun TextInput(inputType: InputType, onMoodChange: (String) -> Unit, focusRequester: FocusRequester? = null, keyboardActions: KeyboardActions) {
    var value by rememberSaveable { mutableStateOf("") }

    if (inputType is InputType.Date) {
        value = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    TextField(
        value = value,
        onValueChange = {
            value = it
            if (inputType is InputType.Mood) {
                onMoodChange(it)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label) },
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}