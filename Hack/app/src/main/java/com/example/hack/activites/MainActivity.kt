package com.example.hack.activites

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import com.example.hack.DataHolder
import com.example.hack.activites.composable.activitySelectionUser
import com.example.hack.activites.composable.companyEditing
import com.example.hack.activites.composable.donatersList
import com.example.hack.activites.composable.eventSelection
import com.example.hack.activites.composable.fondEditing
import com.example.hack.activites.composable.fondSelection
import com.example.hack.activites.composable.mainScreenCompany
import com.example.hack.activites.composable.mainScreenUser
import com.example.hack.activites.composable.ratingForUser
import com.example.hack.activites.composable.regestration
import com.example.hack.activites.composable.regestrationCompany
import com.example.hack.activites.composable.regestrationFond
import com.example.hack.activites.composable.regestrationWorker
import com.example.hack.network.dto.CompanyResponse
import com.example.hack.ui.Components.BottomNavigationViewCompany
import com.example.hack.ui.Components.BottomNavigationViewFoundations
import com.example.hack.ui.Components.BottomNavigationViewUser
import com.example.hack.ui.theme.HackTheme
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MediatorLiveData
import com.example.hack.activites.composable.mainScreenFond
import com.example.hack.network.donate
import com.example.hack.network.dto.ActivitesResponse
import com.example.hack.network.dto.Balance
import com.example.hack.network.dto.Cities
import com.example.hack.network.dto.Company
import com.example.hack.network.dto.CompanyFull
import com.example.hack.network.dto.FondResponse
import com.example.hack.network.dto.FoundesResponse
import com.example.hack.network.dto.SportResponse
import com.example.hack.network.dto.TopDonatorsResponse
import com.example.hack.network.dto.UserInfo
import com.example.hack.network.dto.WorkerResponse
import com.example.hack.network.getActivities
import com.example.hack.network.getComponyUsingId
import com.example.hack.network.getFondsUsingId
import com.example.hack.network.getFonundeds
import com.example.hack.network.getSport
import com.example.hack.network.getTopDonators
import com.example.hack.network.getWorkerUsingId
import com.example.hack.network.getСompanies
import com.example.hack.network.updateActivitystatus
import com.example.hack.network.updateWorkerBalance
import kotlinx.coroutines.flow.MutableStateFlow


enum class Screen {
    //authorization
    startScreen, //9
    registeredUser,
    registeredFond,
    registeredCompany,

    //user
    mainScreenUser,
    activitySelectionUser,
    eventSelection,
    fondSelection,
    ratingForUser,

    //fond
    mainScreenFond,
    donatersList,
    fondEditing,

    //company
    mainScreenCompany,
    companyEditing,
}

val id: MutableLiveData<DataHolder<Int>> by lazy {
    MutableLiveData<DataHolder<Int>>(DataHolder.loading())
}

val companies: MutableLiveData<DataHolder<CompanyResponse>> by lazy {
    MutableLiveData<DataHolder<CompanyResponse>>(DataHolder.loading())
}

val balanceWorker: MutableLiveData<DataHolder<Int>> by lazy {
    MutableLiveData<DataHolder<Int>>(DataHolder.loading())
}

val activeCompany: MutableLiveData<DataHolder<CompanyFull>> by lazy {
    MutableLiveData<DataHolder<CompanyFull>>(DataHolder.loading())
}

val activeWorker: MutableLiveData<DataHolder<WorkerResponse>> by lazy {
    MutableLiveData<DataHolder<WorkerResponse>>(DataHolder.loading())
}

val activeFond: MutableLiveData<DataHolder<FondResponse>> by lazy {
    MutableLiveData<DataHolder<FondResponse>>(DataHolder.loading())
}

val balanceFont: MutableLiveData<DataHolder<Balance>> by lazy {
    MutableLiveData<DataHolder<Balance>>(DataHolder.loading())
}

val balanceCompany: MutableLiveData<DataHolder<Balance>> by lazy {
    MutableLiveData<DataHolder<Balance>>(DataHolder.loading())
}

val foundes: MutableLiveData<DataHolder<FoundesResponse>> by lazy {
    MutableLiveData<DataHolder<FoundesResponse>>(DataHolder.loading())
}

val listDonaters: MutableLiveData<DataHolder<TopDonatorsResponse>> by lazy {
    MutableLiveData<DataHolder<TopDonatorsResponse>>(DataHolder.loading())
}

val listActivites: MutableLiveData<DataHolder<ActivitesResponse>> by lazy {
    MutableLiveData<DataHolder<ActivitesResponse>>(DataHolder.loading())
}

val listSport: MutableLiveData<DataHolder<SportResponse>> by lazy {
    MutableLiveData<DataHolder<SportResponse>>(DataHolder.loading())
}

val listCities: MutableLiveData<DataHolder<Cities>> by lazy {
    MutableLiveData<DataHolder<Cities>>(DataHolder.loading())
}
val listActivitesForSearchCity: MutableLiveData<DataHolder<ActivitesResponse>> by lazy {
    MutableLiveData<DataHolder<ActivitesResponse>>(DataHolder.loading())
}
val phone = MutableLiveData(TextFieldValue())
var mainStat = MutableStateFlow(Screen.startScreen)


val observable = MutableLiveData<Screen>()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getСompanies(applicationContext)
        getTopDonators(applicationContext)
        getActivities(applicationContext)
        getFonundeds(applicationContext)
        getSport(applicationContext)

        setContent {
            HackTheme {

                val observableState by remember { mutableStateOf(observable.value) }

                LaunchedEffect(observableState) {
                    Log.e("testLauched", "LaunchedEffect:  ${observable.value}")

                }


                val currentScreen = remember { mutableStateOf(mainStat.value) }


                when (currentScreen.value) {
                    Screen.startScreen -> {
                        regestration(
                            applicationContext,
                            auth = {
                                currentScreen.value = it
                            },
                            switchScreen = {
                                currentScreen.value = it
                            }
                        )
                    }
                    Screen.registeredUser -> {
                        getСompanies(applicationContext)

                        regestrationWorker(
                            context = applicationContext,
                            onBackPressed = { currentScreen.value = Screen.startScreen })
                    }
                    Screen.registeredFond -> {
                        regestrationFond(
                            context = applicationContext,
                            onBackPressed = { currentScreen.value = Screen.startScreen })

                    }
                    Screen.registeredCompany -> {
                        regestrationCompany(
                            context = applicationContext,
                            onBackPressed = { currentScreen.value = Screen.startScreen })

                    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    Screen.mainScreenUser -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewUser(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            mainScreenUser(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }

                    }
                    Screen.activitySelectionUser -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewUser(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            activitySelectionUser(
                                context = applicationContext,
                                sportSend = { it->
                                    activeWorker.value?.data?.workerData?.let { it1 ->
                                        updateWorkerBalance(
                                            user_id = it1.userId, newBalance = balanceWorker.value?.data!! + it/10, context = applicationContext)
                                    }

                                })
                        }
                    }
                    Screen.eventSelection -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewUser(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            eventSelection(
                                context = applicationContext,
                                events = {i,b,p ->
                                    if(b){
                                        balanceWorker.value?.data?.plus(p)
                                    }
                                    activeWorker.value?.data?.workerData?.let { it1 ->
                                        updateActivitystatus(
                                            it1.id,i,b, context = applicationContext)
                                    }


                                })
                        }
                    }
                    Screen.fondSelection -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewUser(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            fondSelection(
                                context = applicationContext,
                                donated = {id,sum ->
                                    Log.e("fdsfsdfsd", id.toString() +" " + sum.toString() )
                                    donate(activeWorker.value?.data?.workerData?.id ?: 0,id, sum, applicationContext)
                                    //currentScreen.value = it
                                })
                        }
                    }
                    Screen.ratingForUser -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewUser(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            ratingForUser(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }
                    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
                    Screen.mainScreenFond -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewFoundations(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            mainScreenFond(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }
                    }
                    Screen.fondEditing -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewFoundations(switchScreen = {
                                    currentScreen.value = it
                                })
                                
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            fondEditing(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }
                    }
                    Screen.donatersList -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewFoundations(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            donatersList(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }
                    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    Screen.mainScreenCompany -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewCompany(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            mainScreenCompany(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }
                    }
                    Screen.companyEditing -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationViewCompany(switchScreen = {
                                    currentScreen.value = it
                                })
                            }
                        ) { it ->
                            Log.e("sf", it.toString())
                            companyEditing(
                                context = applicationContext,
                                switchScreen = {
                                    currentScreen.value = it
                                })
                        }
                    }

                    null -> {}
                }

            }

        }
    }


}

fun startUser(context: Context) {
    Log.e("testLauched", "mainStat.value : ${observable.value } ", )
    observable.value = Screen.mainScreenUser
    Log.e("testLauched", "mainStat.value : ${observable.value } ", )

}


fun startCompany(context: Context) {}


fun startFond(context: Context) {}