package com.udemy.lessons

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters


class WorkDatabase(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        //gonderilen degeri alıyoruz
        val getData = inputData
        val myNumber = getData.getInt("a",0)
        refReshDatabase(myNumber)
        applicationContext.startActivity(Intent(applicationContext,MainActivity::class.java))
        return Result.success()
    }

    private fun refReshDatabase(myNumber: Int) {
        val sharedPreferences =
            applicationContext.getSharedPreferences("com.udemy.lessons", Context.MODE_PRIVATE)
        var mySaveNumber = sharedPreferences.getInt("myNumber", 0)
        mySaveNumber = mySaveNumber + myNumber
        println(mySaveNumber)
        sharedPreferences.edit().putInt("myNumber", mySaveNumber).apply()
    }
}