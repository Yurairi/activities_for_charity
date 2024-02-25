package com.example.hack.activites.composable

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hack.activites.Screen
import com.example.hack.activites.activeCompany
import com.example.hack.activites.activeFond
import com.example.hack.activites.balanceCompany
import com.example.hack.activites.balanceFont
import com.example.hack.activites.balanceWorker
import com.example.hack.activites.listDonaters
import com.example.hack.ui.Components
import com.example.hack.ui.Components.ActiveText

@Composable
fun mainScreenFond(
    context: Context,
    switchScreen: (Screen) -> Unit = {},
) {
    Column {
        Components.ActionToolbar(text = "")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            // название
            var observableInfo = activeFond.observeAsState()
            when (observableInfo.value?.state) {
                1 -> {
                    Components.ActionProgress(modifier = Modifier.fillMaxSize())
                }
                else -> {
                    ActiveText(
                        text = "Name: " + observableInfo.value?.data?.fondData?.name.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        style =  TextStyle.Default.copy(fontSize = 30.sp),

                        )
                    ActiveText(
                        text = "Description: " + observableInfo.value?.data?.fondData?.description.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        style =  TextStyle.Default.copy(fontSize = 30.sp),

                        )
                    // баланс
                    var observableBalance = balanceFont.observeAsState()
                    when (observableBalance.value?.state) {
                        1 -> {
                            Components.ActionProgress(modifier = Modifier.fillMaxSize())
                        }
                        else -> {
                            ActiveText(
                                text = "Balance: " + balanceFont.value?.data?.balance.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                                style =  TextStyle.Default.copy(fontSize = 30.sp),

                                )
                        }
                    }
                    // описание
                }
            }
        }
    }
}

@Composable
fun donatersList(
    context: Context,
    switchScreen: (Screen) -> Unit = {},
) {
    Column {
        Components.ActionToolbar(text = "Top Donors")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn {
                listDonaters.value?.data?.let { donators ->
                    items(items = donators.topDonators) { topDonator ->
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

@Composable
fun fondEditing(
    context: Context,
    switchScreen: (Screen) -> Unit = {},
) {
    Column {
        Components.ActionToolbar(text = "Editing")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            // имя
            // баланс
            // компания
        }
    }
}