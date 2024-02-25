package com.example.hack.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hack.DataHolder
import com.example.hack.activites.Screen
import com.example.hack.activites.activeCompany
import com.example.hack.activites.activeFond
import com.example.hack.activites.activeWorker
import com.example.hack.activites.balanceCompany
import com.example.hack.activites.balanceFont
import com.example.hack.activites.balanceWorker
import com.example.hack.activites.companies
import com.example.hack.activites.foundes
import com.example.hack.activites.id
import com.example.hack.activites.listActivites
import com.example.hack.activites.listCities
import com.example.hack.activites.listActivitesForSearchCity
import com.example.hack.activites.listDonaters
import com.example.hack.activites.listSport
import com.example.hack.activites.startCompany
import com.example.hack.activites.startFond
import com.example.hack.activites.startUser
import com.example.hack.network.dto.ActivitesResponse
import com.example.hack.network.dto.Balance
import com.example.hack.network.dto.BaseResponse
import com.example.hack.network.dto.Cities
import com.example.hack.network.dto.Company
import com.example.hack.network.dto.CompanyFull
import com.example.hack.network.dto.CompanyResponse
import com.example.hack.network.dto.FondData
import com.example.hack.network.dto.FondResponse
import com.example.hack.network.dto.FoundesResponse
import com.example.hack.network.dto.SportResponse
import com.example.hack.network.dto.TopDonatorsResponse
import com.example.hack.network.dto.UserData
import com.example.hack.network.dto.UserInfo
import com.example.hack.network.dto.UserUpdateBalanceResponse
import com.example.hack.network.dto.WorkerResponse
import com.google.gson.Gson
import org.json.JSONObject


const val BASE_URL = "http://31.129.105.86:8000/"
val gson = Gson()
fun registerWorker(
    username: String,
    password: String = "",
    name: String = "",
    office_id: Int = 0,
    context: Context,
    url: String = "post/register-worker/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("result123", "")
    Log.d("result123", "url = " + BASE_URL + url)
    Log.d("result123", username + " / " + password)

    requestData.put("username", username)
    requestData.put("password", password)
    requestData.put("office_id", office_id)
    requestData.put("name", name)

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("result123", result)
        },
        { error ->
            Log.e("result123", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}

fun registerCompany(
    username: String,
    password: String = "",
    name: String = "",
    description: String = "",
    balance: Int = 0,
    context: Context,
    url: String = "post/register-company/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("result123", "")
    Log.d("result123", "url = " + BASE_URL + url)
    Log.d("result123", username + " / " + password)

    requestData.put("username", username)
    requestData.put("password", password)
    requestData.put("name", name)
    requestData.put("description", description)
    requestData.put("balance", balance)


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("result123", result)
        },
        { error ->
            Log.e("result123", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}


fun registerFond(
    username: String,
    password: String = "",
    name: String = "",
    description: String = "",
    context: Context,
    url: String = "post/register-fond/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("result123", "")
    Log.d("result123", "url = " + BASE_URL + url)
    Log.d("result123", username + " / " + password)

    requestData.put("username", username)
    requestData.put("password", password)
    requestData.put("description", description)
    requestData.put("name", name)

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("result123", result)
        },
        { error ->
            Log.e("result123", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}


fun login(
    username: String = "",
    password: String = "",
    context: Context,
    url: String = "post/login/",
    auth: (Screen) -> Unit = {},
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("regestration", "")
    Log.d("regestration", "url = " + BASE_URL + url)
    Log.d("regestration", username + " / " + password)

    requestData.put("username", username)
    requestData.put("password", password)


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("regestration ", result)

            var temp =
                gson.fromJson(response.getJSONObject("data").toString(), UserData::class.java)
            Log.e("regestration ", " = " + temp.toString())
            if (temp.status != null) {
                id.postValue(DataHolder.withData(temp.id))
                when (temp.status) {
                    "worker" -> {
                        getMoney(userId = temp.id , context = context)
                        getWorkerUsingId(user_id = temp.id , context = context)

                        Log.e("regestration ", " = worker")
                        auth(Screen.mainScreenUser)
                        startUser(context)
                    }

                    "company" -> {
                        getCompanyBalance(userId = temp.id , context = context)
                        getComponyUsingId(user_id = temp.id , context = context)
                        Log.e("regestration ", " = company")

                        startCompany(context)
                        auth(Screen.mainScreenCompany)
                    }

                    "fond" -> {
                        getFondBalance(userId = temp.id , context = context)
                        getFondsUsingId(user_id = temp.id , context = context)

                        Log.e("regestration ", " = fond")
                        startFond(context)
                        auth(Screen.mainScreenFond)
                    }
                    else -> {
                        Log.e("regestration ", " = else")
                    }
                }
            } else {
                Log.e("regestration ", " = else")

            }


        },
        { error ->
            Log.e("regestration ", error.toString())
            Toast.makeText(context, "Authentication error", Toast.LENGTH_SHORT).show()
        }
    )

    requestQueue.add(jsonObjectRequest)
}

fun getMoney(context: Context, userId: Int = 0, url: String = "get/worker-balance/") {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url + userId, requestData,
        { response ->
            val result = response.toString()
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                Balance::class.java
            )
            Log.d("getСompanies ", "---" + temp.toString())
            balanceWorker.postValue(DataHolder.withData(temp.balance))

            Log.d("getMoney ", result)
            Log.d("getMoney ", "-------------")
        },
        { error ->
            Log.e("getMoney ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}

fun getСompanies(context: Context, url: String = "get/companies/") {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("getСompanies ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getСompanies ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                CompanyResponse::class.java
            )
            Log.d("getСompanies ", "---" + temp.toString())
            companies.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getСompanies ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

//на учетке куратора проверять его а не баланс пользователя
fun getFondBalance(context: Context, userId: Int = 0, url: String = "get/fond-balance/") {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url + userId.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getBalanceFond ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getBalanceFond ", jsonResponse.toString())
            var temp = gson.fromJson(response.getJSONObject("data").toString(), Balance::class.java)
            Log.d("getBalanceFond ", "---" + temp.toString())
            balanceFont.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getСompanies ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

///get/fonds/ - возвращает список всех фондов
fun getFonundeds(context: Context, url: String = "get/fonds") {
    Log.d("getFonds ", "start")

    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getFonds ", BASE_URL + url.toString())

    val jsonObjectRequest = JsonObjectRequest(

        Request.Method.GET, BASE_URL + url.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getFonds ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getFonds ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                FoundesResponse::class.java
            )
            Log.d("getFonds ", "---" + temp.toString())
            foundes.postValue(DataHolder.withData(temp))

        },
        { error ->
            Log.e("getFonds ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

//get/company-balance/{user_id} - возвращает баланс компании по user_id
fun getCompanyBalance(context: Context, userId: Int = 0, url: String = "get/company-balance/") {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url + userId.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getCompanyBalance ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getCompanyBalance ", jsonResponse.toString())
            var temp = gson.fromJson(response.getJSONObject("data").toString(), Balance::class.java)
            Log.d("getCompanyBalance ", "---" + temp.toString())
            balanceCompany.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getCompanyBalance ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

///get/top-donators
fun getTopDonators(context: Context, url: String = "get/top-donators") {
    Log.d("getFonds ", "start")

    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getTopDonators ", BASE_URL + url.toString())

    val jsonObjectRequest = JsonObjectRequest(

        Request.Method.GET, BASE_URL + url.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getTopDonators ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getTopDonators ", jsonResponse.toString())
            var temp = gson.fromJson(response.getJSONObject("data").toString(), TopDonatorsResponse::class.java)
            Log.d("getTopDonators ", "---" + temp.toString())
            listDonaters.postValue(DataHolder.withData(temp))

        },
        { error ->
            Log.e("getTopDonators ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

///get/activities - возвращает список всех активностей в списке, на часть он записаться не может уже
fun getActivities(context: Context, url: String = "get/activities") {
    Log.d("getActivities ", "start")

    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getActivities ", BASE_URL + url.toString())

    val jsonObjectRequest = JsonObjectRequest(

        Request.Method.GET, BASE_URL + url.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getActivities ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getActivities ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                ActivitesResponse::class.java
            )
            Log.d("getActivities ", "---" + temp.toString())
            listActivites.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getActivities ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

///get/sport/ - возвращает список всех спортивных активностей из заранее заданной таблицы sport_activities
fun getSport(context: Context, url: String = "get/sport") {
    Log.d("getSport ", "start")

    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getSport ", BASE_URL + url.toString())

    val jsonObjectRequest = JsonObjectRequest(

        Request.Method.GET, BASE_URL + url.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getSport ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getSport ", jsonResponse.toString())
            var temp =
                gson.fromJson(response.getJSONObject("data").toString(), SportResponse::class.java)
            Log.d("getSport ", "---" + temp.toString())
            listSport.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getActivities ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

///get/cities/ - список всех городов где есть активности
fun getCitiesWithActivities(context: Context, url: String = "get/cities") {
    Log.d("getCitiesWithActivities ", "start")

    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getCitiesWithActivities ", BASE_URL + url.toString())

    val jsonObjectRequest = JsonObjectRequest(

        Request.Method.GET, BASE_URL + url.toString(), requestData,
        { response ->
            val result = response.toString()
            Log.d("getCitiesWithActivities ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getCitiesWithActivities ", jsonResponse.toString())
            var temp = gson.fromJson(response.getJSONObject("data").toString(), Cities::class.java)
            Log.d("getCitiesWithActivities ", "---" + temp.toString())
            listCities.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getActivities ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}

///get/activities/{place} - возвращает список активностей по месту ОПЦИОНАЛЬНО
fun getActivitiesForSearchCity(
    context: Context,
    cities: String = "",
    url: String = "get/activities/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url + cities, requestData,
        { response ->
            val result = response.toString()
            Log.d("getActivitiesForSearchCity ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getActivitiesForSearchCity ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                ActivitesResponse::class.java
            )
            Log.d("getActivitiesForSearchCity ", "---" + temp.toString())
            listActivitesForSearchCity.postValue(DataHolder.withData(temp))
        },
        { error ->
            Log.e("getActivitiesForSearchCity ", error.toString())
        }
    )
    requestQueue.add(jsonObjectRequest)
}
//post/donate возвращает пстую дейту

fun donate(
    user_id: Int = 0,
    fond_id: Int = 0,
    donationAmount: Int = 0,
    context: Context,
    url: String = "post/donate"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("donate", "")
    Log.d("donate", "url = " + BASE_URL + url)


    requestData.put("user_id", user_id)
    requestData.put("fond_id", fond_id)
    requestData.put("donationAmount", donationAmount)


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("donate ", result)

            var temp = gson.fromJson(response.toString(), BaseResponse::class.java)
            Log.e("donate ", " = " + temp.toString())
            Log.e("donate ", " = " + (temp.status_code == 200).toString())

        },
        { error ->
            Log.e("donate ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}

///post/update-worker-balance/

fun updateWorkerBalance(
    user_id: Int = 0,
    newBalance: Int = 0,
    context: Context,
    url: String = "post/update-worker-balance/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("donate", "")
    Log.d("donate", "url = " + BASE_URL + url)


    requestData.put("id", user_id)
    requestData.put("balance", newBalance)


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("getMoney ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getMoney ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                UserUpdateBalanceResponse::class.java
            )
            Log.d("getMoney ", "---" + temp.toString())
            balanceWorker.postValue(DataHolder.withData(temp.balance.toInt()))

        },
        { error ->
            Log.e("donate ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}



fun getComponyUsingId(
    user_id: Int = 0,
    context: Context,
    url: String = "get/companies/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getComponyUsingId", "")
    Log.d("getComponyUsingId", "url = " + BASE_URL + url + user_id)



    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url + user_id , requestData,
        { response ->
            val result = response.toString()
            Log.d("getComponyUsingId ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getComponyUsingId ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                CompanyFull::class.java
            )
            Log.d("getComponyUsingId ", "---" + temp.toString())

            activeCompany.postValue(DataHolder.withData(temp))

        },
        { error ->
            Log.e("getComponyUsingId ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}


fun getFondsUsingId(
    user_id: Int = 0,

    context: Context,
    url: String = "get/fonds/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("getFondsUsingId", "")
    Log.d("getFondsUsingId", "url = " + BASE_URL + url+ user_id)




    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url+ user_id, requestData,
        { response ->
            val result = response.toString()
            Log.d("getFondsUsingId ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getFondsUsingId ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                FondResponse::class.java
            )
            Log.d("getFondsUsingId ", "---" + temp.toString())
            activeFond.postValue(DataHolder.withData(temp))
//            Log.d("getFondsUsingId ", "---activeFond    " + FondData.toString())


        },
        { error ->
            Log.e("donate ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}


fun getWorkerUsingId(
    user_id: Int = 0,
    context: Context,
    url: String = "get/workers/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("donate", "")
    Log.d("donate", "url = " + BASE_URL + url)




    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, BASE_URL + url + user_id, requestData,
        { response ->
            val result = response.toString()
            Log.d("getWorkerUsingId ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("getWorkerUsingId ", jsonResponse.toString())
            var temp = gson.fromJson(
                response.getJSONObject("data").toString(),
                WorkerResponse::class.java
            )

            Log.d("getWorkerUsingId ", "---" + temp.toString())
            activeWorker.postValue(DataHolder.withData(temp))

        },
        { error ->
            Log.e("getWorkerUsingId ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}



fun updateActivitystatus(
    user_id: Int = 0,
    activity_id: Int = 0,
    flag: Boolean = false,
    context: Context,
    url: String = "post/update-activity-status/"
) {
    val requestQueue = Volley.newRequestQueue(context)
    val requestData = JSONObject()
    Log.d("updateActivitystatus", "")
    Log.d("updateActivitystatus", "url = " + BASE_URL + url)


    requestData.put("user_id", user_id)
    requestData.put("activity_id", activity_id)
    requestData.put("flag", if(flag) 0 else 1 )
    Log.d("updateActivitystatus ", "fsa")


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.POST, BASE_URL + url, requestData,
        { response ->
            val result = response.toString()
            Log.d("updateActivitystatus ", result)
            val jsonResponse = response.getJSONObject("data")
            Log.d("updateActivitystatus ", jsonResponse.toString())
//            var temp = gson.fromJson(
//                response.getJSONObject("data").toString(),
//                UserUpdateBalanceResponse::class.java
//            )
//            Log.d("getMoney ", "---" + temp.toString())
//            balanceWorker.postValue(DataHolder.withData(temp.balance.toInt()))

        },
        { error ->
            Log.e("updateActivitystatus ", error.toString())

        }
    )

    requestQueue.add(jsonObjectRequest)
}


///post/update-activity-status/ 0


//
//
//
//


///post/update-fond-description/ принимает айди пользователя
///post/update-company-description/ оке id юзера на который зарегна
//


//
///post/donate возвращает пстую дейту
//
///get/donations/{user_id} - возвращает донаты по user_id ХУЙ ПОЙМИ ЗАЧЕМ