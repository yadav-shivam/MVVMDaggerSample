package com.shivam.ecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.shivam.ecom.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animateView(ivLogo,tvName)
    }


    /*
    * method to set animation...
    * */
    private fun animateView(ivLogo: ImageView, ivName: TextView) {
        val flowerAnimation = AnimationUtils.loadAnimation(this, R.anim.flower_animation)
        ivLogo.visibility = View.VISIBLE
        ivLogo.animate().rotation(360f).setDuration(800).start()
        ivLogo.startAnimation(flowerAnimation)
        val height = resources.displayMetrics.heightPixels
        val translateAnimation = TranslateAnimation(ivName.x, ivName.x, (height / 2).toFloat(), 0f)
        translateAnimation.duration = 600
        flowerAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                ivName.visibility = View.VISIBLE
                ivName.startAnimation(translateAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })


        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                Handler().postDelayed(Runnable {
                    DashboardActivity.starter(this@SplashActivity)
                    finish()
                },2000)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

    }

}
