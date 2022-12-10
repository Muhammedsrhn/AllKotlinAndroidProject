package com.udemy.brodcastreceiver.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.udemy.brodcastreceiver.entities.Category
import com.udemy.brodcastreceiver.R
import com.udemy.brodcastreceiver.database.RecipieDatabase
import com.udemy.brodcastreceiver.entities.Meals
import com.udemy.brodcastreceiver.interfaces.GetDataService
import com.udemy.brodcastreceiver.retrofitclient.RetrofitClientIntance
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {
    private val READ_STORAGE_PERM = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnGetStarted.setOnClickListener {
            btnGetStarted.visibility = View.INVISIBLE
            readStorageTask()
        }
    }

    private fun getCategories() {
        val service = RetrofitClientIntance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                response.body()?.let {
                    InsertDataInRoomDb(it)
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                    finish()
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                println(t)
                Toast.makeText(this@SplashActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                btnGetStarted.visibility = View.INVISIBLE
            }

        })
    }

    private fun getMeals() {
        val service = RetrofitClientIntance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getMealList()
        call.enqueue(object : Callback<List<Meals>> {
            override fun onResponse(
                call: Call<List<Meals>>,
                response: Response<List<Meals>>
            ) {
                response.body()?.let {
                    InsertMealDataInRoomDb(it)
                }
            }

            override fun onFailure(call: Call<List<Meals>>, t: Throwable) {
                println(t.localizedMessage)
                Toast.makeText(this@SplashActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                btnGetStarted.visibility = View.INVISIBLE
            }


        })
    }

    fun InsertDataInRoomDb(category: List<Category>) {
        CoroutineScope(Dispatchers.Default).launch {
            this.let {
                RecipieDatabase.getDatabase(this@SplashActivity).recipieDao().clearCategoryDb()

                for (arr in category) {
                    RecipieDatabase.getDatabase(this@SplashActivity).recipieDao()
                        .insertCategory(arr)
                }
            }
        }
    }


    fun InsertMealDataInRoomDb(meals: List<Meals>) {
        CoroutineScope(Dispatchers.Default).launch {
            this.let {
                RecipieDatabase.getDatabase(this@SplashActivity).recipieDao().clearMealDb()

                for (meal in meals) {
                    RecipieDatabase.getDatabase(this@SplashActivity).recipieDao()
                        .insertMeal(meal)
                }
            }
        }
    }

    private fun hashReadStorage(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask() {
        if (hashReadStorage()) {
            getCategories()
            getMeals()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access you storage",
                READ_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        readStorageTask()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}