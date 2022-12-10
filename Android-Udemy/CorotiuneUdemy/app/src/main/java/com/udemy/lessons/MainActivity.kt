package com.udemy.lessons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udemy.lessons.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Light weightness

        /*
        GlobalScope.launch {
            repeat(100000){
                launch {
                    println("android")
                }
            }
        }
         */

        //Scope(Kapsam)
        //Global Scope -> in the all application ,runBlocking -> bitene kadar obur kısımlar alt satırlar calıstırılmaz,CoroutineScope

        //RunBlocking
        /*

        println("run blocking start")
        runBlocking {
            launch {
                delay(5000)
                println("runBlocking")
            }
        }
        println("run blocking end")
         */

        /*
        Global Scope
            println("global scope start")
        GlobalScope.launch {
            delay(5000)
            println("global scope")
        }
        println("global scope end")
         */

        //Coroutine Context
        // CoroutineScope
        /*
             println("CoroutineScope start")
             CoroutineScope(Dispatchers.Default).launch {
                 delay(5000)
                 println("CoroutineScope")
             }
             println("CoroutineScope start")
         */


    }

}