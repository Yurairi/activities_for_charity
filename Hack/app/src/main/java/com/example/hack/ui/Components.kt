package com.example.hack.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.hack.R
import com.example.hack.activites.Screen
import com.example.hack.activites.companies
import com.example.hack.ui.theme.Colors

object Components {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActionToolbar(
        text: String,
        topText: String = "",
        modifier: Modifier = Modifier,
        @DrawableRes icon: Int = R.drawable.ic_back,
        action_back: (() -> Unit)? = null,
        action_secondary: (() -> Unit)? = null,
        idSecondaryImg: Int = 0
    ) {
        var textAlign = TextAlign.Center
        SmallTopAppBar(
            title = {
                Column(
                    modifier = Modifier.padding(
                        start = if (action_back == null && action_secondary != null)
                            40.dp
                        else 0.dp
                    ),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (topText != "") {
                        textAlign = TextAlign.Start
                        Text(
                            text = topText,
                            Modifier
                                .fillMaxWidth()
                                .padding(end = 40.dp),
                            textAlign = textAlign,
                            color = Colors.color21,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = text,
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp),
                        textAlign = textAlign,
                        color = Colors.color21,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            },
            navigationIcon = {
                if (action_back != null)
                    IconButton(
                        onClick = { action_back.invoke() },
                        content = {
                            Icon(
                                painter = painterResource(id = icon),
                                tint = Color.Black,
                                contentDescription = null
                            )
                        })
            },
            actions = {
                if (action_secondary != null)
                    IconButton(
                        onClick = {
                            action_secondary.invoke()
                        },
                    ) {

                        Image(
                            painter = painterResource(idSecondaryImg),
                            contentDescription = null
                        )
                    }

            },

            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF495D92))
        )
    }

    @Composable
    fun ActionDivider(modifier: Modifier = Modifier, color: Color = Colors.text25) {
        Divider(thickness = 0.5.dp, color = color, modifier = modifier)
    }
    @Composable
    fun ActionProgress(modifier: Modifier = Modifier) {
        Box(modifier = Modifier
            .fillMaxSize()
            .then(modifier)) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Colors.primary100)
        }
    }
    @Composable
    fun ActiveText(
        text: String,
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle.Default,
        color: Color = Color.Black
    ) {

        Text(
            modifier = Modifier.then(modifier),
            text = text,
            color = color,
            style = style
        )

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActiveToolbar(
        text: String,
        topText: String = "",
        modifier: Modifier = Modifier,
        @DrawableRes icon: Int = R.drawable.ic_back,
        action_back: (() -> Unit)? = null,
        action_secondary: (() -> Unit)? = null,
        idSecondaryImg: Int = 0
    ) {
        var textAlign = TextAlign.Center
        SmallTopAppBar(
            title = {
                Column(
                    modifier = Modifier.padding(
                        start = if (action_back == null && action_secondary != null)
                            40.dp
                        else 0.dp
                    ),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (topText != "") {
                        textAlign = TextAlign.Start
                        Text(
                            text = topText,
                            Modifier
                                .fillMaxWidth()
                                .padding(end = 40.dp),
                            textAlign = textAlign,
                            color = Colors.color21,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = text,
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp),
                        textAlign = textAlign,
                        color = Colors.color21,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            },
            navigationIcon = {
                if (action_back != null)
                    IconButton(
                        onClick = { action_back.invoke() },
                        content = {
                            Icon(painter = painterResource(id = icon), contentDescription = null)
                        })
            },
            actions = {
                if (action_secondary != null)
                    IconButton(
                        onClick = {
                            action_secondary.invoke()
                        },
                    ) {

                        Image(
                            painter = painterResource(idSecondaryImg),
                            contentDescription = null
                        )
                    }

            },

            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }

    @Composable
    fun ActiveDivider(modifier: Modifier = Modifier, color: Color = Colors.text25) {
        Divider(thickness = 0.5.dp, color = color, modifier = modifier)
    }

    @Composable
    fun ActiveActionButton(
        text: String,
        modifier: Modifier = Modifier,
        isEnabled: Boolean = true,
        textColor: Color = Color.White,
        uppercase: Boolean = true,
        onClick: () -> Unit = {}
    ) {
        ActiveButton(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .height(36.dp)
                .then(modifier),
            isEnabled = isEnabled,
            onClick = onClick,
            content = {
                Text(
                    text = if (uppercase) text.uppercase() else text,
                    color = textColor
                )
            })
    }

    @Composable
    fun ActiveButton(
        modifier: Modifier = Modifier,
        isEnabled: Boolean = true,
        content: @Composable RowScope.() -> Unit,
        onClick: () -> Unit = {}
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            onClick = onClick,
            content = content,
            shape = MaterialTheme.shapes.extraSmall,
            enabled = isEnabled
        )
    }

    @Composable
    fun ActiveProgress(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Colors.primary100
            )
        }
    }

    @Composable
    fun ActiveRetry(
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {},
        errorMessage: String = ""
    ) {

        Column(modifier = Modifier.then(modifier), verticalArrangement = Arrangement.Center) {

            ClickableText(
                text = AnnotatedString("Retry"),
                onClick = { onClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            )

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

    }

    @Composable
    fun DropdownMenuWithItems(
        items: List<Pair<String, Int>>,
        onItemSelected: (Int) -> Unit
    ) {

        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(modifier = Modifier.fillMaxWidth(), onClick = { expanded = true }) {
                Text("Select a company from the list")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = 20.dp, y = 10.dp)
            ) {
                items.forEach {
                    DropdownMenuItem(onClick = {
                        onItemSelected(it.second)
                        expanded = false
                    }, text = { Text(it.first) })
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActionEditText(
        modifier: Modifier = Modifier,
        text: TextFieldValue,
        label: String? = null,
        placeholder: String? = null,
        readOnly: Boolean = false,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        onTextChanged: (TextFieldValue) -> Unit = {},
        interactionSource: MutableInteractionSource,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        simpleClear: Boolean = true,
        isError: Boolean = false,
        textPadding: Dp = 8.dp,
        visualActive: Boolean = false,
        textAlign: TextAlign = TextAlign.Start,
        clearSection: @Composable (() -> Unit)? = null,
        maxLines: Int = 1
    ) {
        var isFocused by remember { mutableStateOf(false) }

        var value by remember { mutableStateOf(text.text) }

        val stateLabel = !if (value == "" && !visualActive) !isFocused
        else false

        val _label: @Composable (() -> Unit)? = if (label != null) {
            {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (!stateLabel && textAlign == TextAlign.Center) textAlign
                    else TextAlign.Start,
                    text = label,
                    color = if (stateLabel) Colors.primary100
                    else Colors.text50,
                    maxLines = 1
                )
            }
        } else {
            null
        }

        val _placeholder: @Composable (() -> Unit)? = if (placeholder != null) {
            {
                Text(
                    text = placeholder,
                    Modifier.padding(start = textPadding),
                    color = Colors.text50,
                    maxLines = 1
                )
            }
        } else {
            null
        }

        val _clear: @Composable (() -> Unit)? =
            if (simpleClear && !readOnly && clearSection == null) {
                {
                    AnimatedVisibility(visible = value.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                value = ""
                                onTextChanged(TextFieldValue(""))
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_reset),
                                    contentDescription = null
                                )
                            })
                    }
                }
            } else clearSection

        val colors: TextFieldColors = Colors.editTextColors()

        val contentPadding: PaddingValues =
            if (_label == null) {
                TextFieldDefaults.textFieldWithoutLabelPadding()
            } else {
                TextFieldDefaults.textFieldWithLabelPadding()
            }
        val customTextSelectionColors = TextSelectionColors(
            handleColor = Colors.othersOrange,
            backgroundColor = Colors.othersOrange
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                value = text,
                modifier = modifier
                    .defaultMinSize(
                        minWidth = TextFieldDefaults.MinWidth,
                        minHeight = TextFieldDefaults.MinHeight
                    )
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                onValueChange = {
                    onTextChanged(it)
                    value = it.text
                },
                readOnly = readOnly,
                cursorBrush = SolidColor(Colors.othersOrange),
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                interactionSource = interactionSource,
                maxLines = maxLines,
                decorationBox = @Composable { innerTextField ->
                    // places leading icon, text field with label and placeholder, trailing icon
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = text.text,
                        visualTransformation = visualTransformation,
                        innerTextField = { Box(modifier.padding(start = textPadding)) { innerTextField() } },
                        placeholder = _placeholder,
                        label = _label,
                        leadingIcon = null,
                        trailingIcon = _clear,
                        supportingText = null,
                        shape = TextFieldDefaults.filledShape,
                        singleLine = true,
                        enabled = true,
                        isError = isError,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(
                            start =
                            if (visualTransformation != VisualTransformation.None || textPadding != 8.dp) {
                                0.dp
                            } else {
                                if (value == "") {
                                    if (!isFocused) {
                                        contentPadding.calculateLeftPadding(LayoutDirection.Ltr)
                                    } else {
                                        0.dp
                                    }
                                } else {
                                    0.dp
                                }
                            },
                            top = contentPadding.calculateTopPadding(),
                            end = if (textPadding != 8.dp) textPadding
                            else contentPadding.calculateRightPadding(LayoutDirection.Ltr),
                            bottom = contentPadding.calculateBottomPadding()
                        ),
                        colors = colors
                    )
                }
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun ActiveAlertDialog(
        onDismissRequest: () -> Unit = {},
        dismissButtonClick: () -> Unit = {},
        confirmButtonClick: () -> Unit = {},
        text: String = "",
        headerText: String = ""
    ) {
        AlertDialog(
            modifier = Modifier.padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),
            onDismissRequest = {
                onDismissRequest()
            },
            dismissButton = {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 14.dp, end = 8.dp)
                        .clickable { dismissButtonClick() },
                    text = "I failed".uppercase(),
                    color = Colors.primary100
                )
            },
            confirmButton = {

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 14.dp, end = 16.dp)
                        .clickable { confirmButtonClick() },
                    text = "I've done".uppercase(),
                    color = Colors.primary100
                )
            },
            text = {
                Column() {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        text = headerText,
                        color = Colors.text100
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                    )
                }

            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        )
    }

    @Composable
    fun BottomNavigationViewUser(switchScreen: (Screen) -> Unit = {}) {


        Row(
            modifier = Modifier
                .height(50.dp)
                .background(Color(0xFF495D92)),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.mainScreenUser)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Main Menu",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.activitySelectionUser)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Activities",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.eventSelection)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Events",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.fondSelection)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Foundations",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.ratingForUser)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Rating",
                )
            }

        }
    }

    @Composable
    fun BottomNavigationViewFoundations(switchScreen: (Screen) -> Unit = {}) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .background(Color(0xFF495D92)),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.mainScreenFond)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Main Menu",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    ),
                )
            }


            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.donatersList)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Rating",
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.fondEditing)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Editing",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }

        }
    }

    @Composable
    fun BottomNavigationViewCompany(switchScreen: (Screen) -> Unit = {}) {


        Row(
            modifier = Modifier
                .height(50.dp)
                .background(Color(0xFF495D92)),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.mainScreenCompany)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Main Menu",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        switchScreen(Screen.companyEditing)
                        Log.e("bottomBar", "Button 1")
                    }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7491DD),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Editing",
                )
            }

        }
    }
}
