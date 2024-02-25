package com.example.hack.activites.composable

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hack.activites.Screen
import com.example.hack.activites.activeCompany
import com.example.hack.activites.balanceCompany
import com.example.hack.ui.Components
import com.example.hack.ui.Components.ActionProgress
import com.example.hack.ui.Components.ActiveText

@Composable
fun mainScreenCompany(
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
            //название
            var observableInfo = activeCompany.observeAsState()
            when (observableInfo.value?.state) {
                1 -> {
                    ActionProgress(modifier = Modifier.fillMaxSize())
                }
                else -> {
                    ActiveText(
                        text = "Name: " + observableInfo.value?.data?.companyData?.name.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        style =  TextStyle.Default.copy(fontSize = 30.sp),
                    )
                    ActiveText(
                        text = "Description: " + observableInfo.value?.data?.companyData?.description.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        style =  TextStyle.Default.copy(fontSize = 30.sp),

                        )
                    //баланс
                    var observablebalance = balanceCompany.observeAsState()
                    when (observablebalance.value?.state) {
                        1 -> {
                            ActionProgress(modifier = Modifier.fillMaxSize())
                        }
                        else -> {
                            ActiveText(
                                text = "Balance: " + balanceCompany.value?.data?.balance.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                                style =  TextStyle.Default.copy(fontSize = 30.sp),

                                )
                        }
                    }
                    //описание
                }
            }
        }
    }
}

@Composable
fun companyEditing(
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
            //редактирование баланса
            //редактирование описания
        }
    }
}