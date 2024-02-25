package com.example.hack.activites.composable

import android.content.Context
import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.hack.activites.Screen
import com.example.hack.activites.companies
import com.example.hack.network.getСompanies
import com.example.hack.network.login
import com.example.hack.network.registerCompany
import com.example.hack.network.registerFond
import com.example.hack.network.registerWorker
import com.example.hack.ui.Components
import com.example.hack.ui.Components.ActionToolbar
import com.example.hack.ui.Components.DropdownMenuWithItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun regestration(
    context: Context,
    switchScreen: (Screen) -> Unit = {},
    auth: (Screen) -> Unit = {}
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.2f))

        Components.ActionEditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = username,
            label = "Login",
            onTextChanged = { value ->
                username = value
            },
            interactionSource = remember { MutableInteractionSource() },
        )
        Components.ActionEditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = password,
            label = "Password",
            onTextChanged = { value ->
                password = value
            },
            interactionSource = remember { MutableInteractionSource() },
        )
        Components.ActiveButton(
            modifier = Modifier.padding(top = 16.dp),

            content = { Text("Login") },
            onClick = {
                GlobalScope.launch(Dispatchers.IO) {
                    login(username.text, password.text, context = context, auth = auth)
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))


        Components.ActiveButton(
            content = { Text("Register as an employee") },
            onClick = {
                switchScreen(Screen.registeredUser)


            }
        )
        Components.ActiveButton(
            content = { Text("Register a company") },
            onClick = {
                switchScreen(Screen.registeredCompany)

            }
        )
        Components.ActiveButton(
            modifier = Modifier.padding(bottom = 16.dp),

            content = { Text("Register a foundation") },
            onClick = {
                switchScreen(Screen.registeredFond )

            }
        )


    }
}


@Composable
fun regestrationWorker(
    context: Context, onSuccessRegestration: () -> Unit = {}, onBackPressed: () -> Unit = {},
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var id_company by remember { mutableStateOf(TextFieldValue("-1")) }

    Column {
        ActionToolbar(text = "Worker Registration ", action_back = onBackPressed)


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = username,
                label = "Login",
                onTextChanged = { value ->
                    username = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = password,
                label = "Password",
                onTextChanged = { value ->
                    password = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = name,
                label = "Name",
                onTextChanged = { value ->
                    name = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Log.e("testDropDownMenu", companies.value?.data.toString() )
            companies.value?.data?.companies?.let { DropdownMenuWithItems(items =  it.map { Pair(it.name,it.id) },{
                id_company=TextFieldValue(it.toString())
            }) }

            Spacer(modifier = Modifier.weight(1f))


            Components.ActiveButton(
                isEnabled = id_company.text!="-1",
                modifier = Modifier.padding(bottom = 16.dp),

                content = { Text("Register") },
                onClick = {
                    onBackPressed()

                    GlobalScope.launch(Dispatchers.IO) {

                        registerWorker(username.text, password.text,office_id = id_company.text.toInt(), context = context)
                    }
                }
            )
        }
    }
}

@Composable
fun regestrationFond(
    context: Context,
    onSuccessRegestration: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    Column {
        ActionToolbar(text = "Fond Registration ", action_back = onBackPressed)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = username,
                label = "Login",
                onTextChanged = { value ->
                    username = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = password,
                label = "Password",
                onTextChanged = { value ->
                    password = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = name,
                label = "Name",
                onTextChanged = { value ->
                    name = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )

            TextField(
                value = description,
                onValueChange = { newText -> description = newText },
                modifier = Modifier
                    .heightIn(min = (10 * MaterialTheme.typography.bodyLarge.fontSize.value).dp)
                    .fillMaxWidth(),
                maxLines = 10
            )

            Spacer(modifier = Modifier.weight(1f))
            Components.ActiveButton(
                modifier = Modifier.padding(bottom = 16.dp),

                content = { Text("Register") },
                onClick = {
                    onBackPressed()

                    GlobalScope.launch(Dispatchers.IO) {
                        registerFond(
                            username.text,
                            password.text,
                            description.text,
                            context = context
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun regestrationCompany(
    context: Context,
    onSuccessRegestration: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var balance by remember { mutableStateOf(TextFieldValue("0")) }


    Column {
        ActionToolbar(text = "Company Registration ", action_back = onBackPressed)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = username,
                label = "Login",
                onTextChanged = { value ->
                    username = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = password,
                label = "Password",
                onTextChanged = { value ->
                    password = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = name,
                label = "Name",
                onTextChanged = { value ->
                    name = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )
            TextField(
                value = description,
                onValueChange = { newText -> description = newText },
                modifier = Modifier
                    .heightIn(min = (10 * MaterialTheme.typography.bodyLarge.fontSize.value).dp)
                    .fillMaxWidth(),
                maxLines = 10
            )
            Components.ActionEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = balance,
                label = "Balance",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onTextChanged = { value ->
                    balance = value
                },
                interactionSource = remember { MutableInteractionSource() },
            )

            Spacer(modifier = Modifier.weight(1f))


            Components.ActiveButton(
                modifier = Modifier.padding(bottom = 16.dp),
                content = { Text("Register") },
                onClick = {
                    onBackPressed()
                    GlobalScope.launch(Dispatchers.IO) {

                        registerCompany(
                            username = username.text,
                            password = password.text,
                            name = name.text,
                            context = context,
                            description = description.text,
                            balance = balance.text.toInt(),

                        )
                    }
                }
            )
        }
    }
}


