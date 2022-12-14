package com.udemy.lessons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ImageArray
        imageArray.add(imageView)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)

        hideImages()



        //CountDown Timer

        object : CountDownTimer(15500, 1000) {
            override fun onTick(p0: Long) {
                gameTime.text = "Time : ${p0 / 1000}"
            }

            override fun onFinish() {
                gameTime.text = "Time : 0"
                handler.removeCallbacks(runnable)
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over!")
                alert.setMessage("Restart The Game..")
                alert.setPositiveButton("yes") { dialog, which ->
                    //restart game
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("no") { dialog, which ->
                    //game done
                    Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_SHORT).show()
                    scoreText.text = "Score : 0"
                    handler.removeCallbacks(runnable)
                }
                alert.setCancelable(false)
                alert.show()

            }
        }.start()
    }

    fun increaseScore(view: View) {
        score = score + 1
        scoreText.text = "Score : ${score}"
    }
    fun hideImages(){
        runnable = object :Runnable{
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                println(randomIndex)
                handler.postDelayed(runnable,700)
            }
        }
        handler.post(runnable)




    }
}