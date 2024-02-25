package com.example.hack.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BackgroundColor = Color(0xFFFFFFFF)


val primary100 = Color(0xFF614DDF)

class Colors {
    companion object{
        @JvmStatic
        val text100 = Color(0xFF333333)
        @JvmStatic
        val text75 = text100.copy(alpha = 0.75f)
        @JvmStatic
        val text50 = text100.copy(alpha = 0.5f)
        @JvmStatic
        val text25 = text100.copy(alpha = 0.25f)
        @JvmStatic
        val text10 = text100.copy(alpha = 0.1f)
        @JvmStatic
        val text5 = text100.copy(alpha = 0.05f)
        @JvmStatic
        val textWhite = Color(0xFFFFFFFF)
        @JvmStatic
        val textWhite75 = Color(0xFFFFFFFF).copy(alpha = 0.75f)
        @JvmStatic
        val textPink = Color(0xFFFF4081)
        @JvmStatic
        val textRed = Color(0xFFFB3C3C)

        @JvmStatic
        val primary100 = Color(0xFF614DDF)
        @JvmStatic
        val primary75 = primary100.copy(alpha = 0.75f)
        @JvmStatic
        val primary50 = primary100.copy(alpha = 0.5f)
        @JvmStatic
        val primary25 = primary100.copy(alpha = 0.25f)
        @JvmStatic
        val primary10 = primary100.copy(alpha = 0.1f)
        @JvmStatic
        val primaryWhite = Color(0xFFFFFFFF)

        @JvmStatic
        val color21 = Color(0xFF212121)
        @JvmStatic
        val othersOrange = Color(0xFFFFB100)
        @JvmStatic
        val othersOrange40 = othersOrange.copy(alpha = 0.4f)
        @JvmStatic
        val othersPink = Color(0xFFFF4081)
        @JvmStatic
        val othersRed = Color(0xFFFF0000)

        @OptIn(ExperimentalMaterial3Api::class)
        @JvmStatic
        @Composable
        fun editTextColors() = TextFieldDefaults.textFieldColors(
            containerColor = primaryWhite,
            focusedIndicatorColor = Colors.primary100,
            unfocusedIndicatorColor = Colors.text25,
            errorIndicatorColor = textRed,
            cursorColor = primary100
        )

        @OptIn(ExperimentalMaterial3Api::class)
        @JvmStatic
        @Composable
        fun searchEditTextColors() = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            cursorColor = primary100
        )
    }
}
