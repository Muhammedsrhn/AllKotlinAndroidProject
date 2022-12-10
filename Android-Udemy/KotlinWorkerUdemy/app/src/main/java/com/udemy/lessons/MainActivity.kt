package com.udemy.lessons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.udemy.lessons.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // send data to worker class

        val data = Data.Builder().putInt("a", 1).build()
        val constrains = Constraints.Builder()
            .setRequiresCharging(false)
            .build()

        val myWorkRequets: WorkRequest = OneTimeWorkRequestBuilder<WorkDatabase>()
            .setConstraints(constrains)
            .setInputData(data).build()

        // val myWorkRequets:WorkRequest = PeriodicWorkRequestBuilder<WorkDatabase>(15,TimeUnit.MINUTES)
        //   .setConstraints(constrains)
        // .setInputData(data)
        //.build()
        WorkManager.getInstance(this).enqueue(myWorkRequets)

    }
}