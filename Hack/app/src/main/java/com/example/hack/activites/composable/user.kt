package com.example.hack.activites.composable

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hack.activites.Screen
import com.example.hack.activites.activeWorker
import com.example.hack.activites.balanceWorker
import com.example.hack.activites.companies
import com.example.hack.activites.foundes
import com.example.hack.activites.listActivites
import com.example.hack.activites.listDonaters
import com.example.hack.activites.listSport
import com.example.hack.ui.Components
import com.example.hack.ui.Components.ActiveAlertDialog
import com.example.hack.ui.Components.ActiveText

@Composable
fun mainScreenUser(
    context: Context,
    switchScreen: (Screen) -> Unit = {},
) {
    Column {
        Components.ActionToolbar(text = "")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp)
        ) {
            //название
            var observableInfo = activeWorker.observeAsState()
            when (observableInfo.value?.state) {
                1 -> {
                    Components.ActionProgress(modifier = Modifier.fillMaxSize())
                }
                else -> {
                    ActiveText(
                        text = "Name: " + observableInfo.value?.data?.workerData?.name.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        style =  TextStyle.Default.copy(fontSize = 30.sp),
                    )
                    ActiveText(
                        text = "Company: " + companies.value?.data?.companies?.firstOrNull { it.id == observableInfo.value?.data?.workerData?.officeId }?.name
                            .toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        style =  TextStyle.Default.copy(fontSize = 30.sp),
                    )
                    //баланс
                    var observablebalance = balanceWorker.observeAsState()
                    when (observablebalance.value?.state) {
                        1 -> {
                            Components.ActionProgress(modifier = Modifier.fillMaxSize())
                        }
                        else -> {
                            ActiveText(
                                text = "Balance: " + balanceWorker.value?.data?.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                                style =  TextStyle.Default.copy(fontSize = 30.sp),
                            )
                        }
                    }
                    //описнаие
                }
            }
        }
    }
}

@Composable
fun activitySelectionUser(
    context: Context,
    sportSend: (Int) -> Unit = {},
) {

    Column {

        Components.ActionToolbar(text = "")


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn {
                listSport.value?.data?.let {
                    items(items = it.sport_activities) { acivity ->
                        var progress by remember{ mutableStateOf(TextFieldValue("0")) }

                        Row(modifier = Modifier

                            .padding(vertical = 9.dp)) {
                            Column {
                                Text(text = acivity.name)
                                Components.ActionEditText(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    text = progress,
                                    label = "Balance",
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    onTextChanged = { value ->
                                        progress = value
                                    },
                                    interactionSource = remember { MutableInteractionSource() },
                                )
                                Components.ActiveButton(
                                    modifier = Modifier.padding(bottom = 16.dp),

                                    content = { Text("Check in") },
                                    onClick = {
                                        sportSend(progress.text.toInt()
                                        )
                                        progress = TextFieldValue("0")

                                    })



                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun eventSelection(
    context: Context,
    events: (Int, Boolean, Int) -> Unit = { i: Int, b: Boolean, m: Int -> },
) {
    var activeEvent by remember { mutableStateOf(0) }

    Column {

        Components.ActionToolbar(text = "Events")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            //поиск по городу мб
            LazyColumn {
                listActivites.value?.data?.let {
                    items(items = it.activities) { acivity ->
                        Row(modifier = Modifier
                            .padding(vertical = 9.dp)
                            .clickable {
                                activeEvent = acivity.id
                            }) {
                            Column {
                                Text(text = acivity.name)
                                Row {
                                    Text(text = acivity.start_date + " - ")
                                    Text(text = acivity.end_date)
                                }

                                Text(text = "${acivity.description}")
                                Text(text = "${acivity.place}")
                                Text(text = "${acivity.money}  Coins")


                            }

                        }
                    }
                }

            }
            AnimatedVisibility(visible = activeEvent != 0) {
                ActiveAlertDialog(
                    onDismissRequest = {
                        activeEvent = 0
                    },
                    dismissButtonClick = {
                        events(activeEvent, false, 0 )
                        activeEvent = 0
                    },
                    confirmButtonClick = {
                        events(activeEvent, true,
                            listActivites.value?.data?.activities?.firstOrNull{it.id == activeEvent}?.money
                                ?: 0
                        )
                        activeEvent = 0
                    },
                    text = "How was your event?",
                    headerText = "Entering results",
                )
            }
        }
    }
}

@Composable
fun fondSelection(
    context: Context,
    donated: (Int,Int) -> Unit = {id: Int,sum: Int -> },
) {

    Column {

        Components.ActionToolbar(text = "Foundations")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp).padding(bottom = 25.dp)
        ) {

            LazyColumn {
                foundes.value?.data?.let {
                    items(items = it.fonds) { fond ->
                        var summ by remember{ mutableStateOf(TextFieldValue("0")) }

                        Row(modifier = Modifier.padding(vertical = 5.dp)) {
                            Column {
                                Text(text = fond.name)
                                Text(text = fond.description)

                                Components.ActionEditText(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    text = summ,
                                    label = "Balance",
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    onTextChanged = { value ->
                                        summ = value
                                    },
                                    interactionSource = remember { MutableInteractionSource() },
                                )
                                Components.ActiveButton(
                                    modifier = Modifier.padding(bottom = 16.dp),

                                    content = { Text("Donated") },
                                    onClick = {
                                    donated(fond.id,summ.text.toInt()
                                    )
                                        summ = TextFieldValue("0")

                                })



                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ratingForUser(
    context: Context,
    switchScreen: (Screen) -> Unit = {},
) {

    Column {

        Components.ActionToolbar(text = "Rating")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn {
                listDonaters.value?.data?.let {
                    items(items = it.topDonators) { topDonator ->
                        Row(modifier = Modifier.padding(vertical = 5.dp)) {
                            Column {
                                Text(text = topDonator.workerName)

                                Text(text = "Total Donation: ${topDonator.totalDonation}")


                            }

                        }
                    }
                }

            }
        }
    }
}


