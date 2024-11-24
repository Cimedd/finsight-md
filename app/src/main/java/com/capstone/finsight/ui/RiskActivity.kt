package com.capstone.finsight.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.finsight.R
import com.capstone.finsight.databinding.ActivityRiskBinding

class RiskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiskBinding
    private var index = 0
    private val question = arrayListOf("Question 1","Question 2","Question 3", "Question 4")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRiskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNext.setOnClickListener{
            checkProgress()
            binding.progressBar2.setProgress(index*15,true)
            nextAnim()
        }

    }

    private fun checkProgress(){
        if(index<6){
            index++
        }
    }

    private fun startAnim(){

    }

    private fun nextAnim(){
        val move1 = ObjectAnimator.ofFloat(binding.txtQuestion, View.TRANSLATION_X,0f, -1000f)
        val move2 = ObjectAnimator.ofFloat(binding.radioGroup, View.TRANSLATION_X,0f, -1000f)
        val anim1 = ObjectAnimator.ofFloat(binding.txtQuestion, View.ALPHA,1f, 0f)
        val anim2 = ObjectAnimator.ofFloat(binding.radioGroup, View.ALPHA,1f, 0f)
        AnimatorSet().apply {
            playTogether(move1,move2,anim1,anim2)
            setDuration(300)
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.txtQuestion.text = question[index]

                    binding.txtQuestion.translationX = 1000f
                    binding.txtQuestion.translationX = 1000f

                    AnimatorSet().apply {
                        playTogether(
                            ObjectAnimator.ofFloat(binding.txtQuestion, View.TRANSLATION_X,1000f, 0f),
                            ObjectAnimator.ofFloat(binding.radioGroup, View.TRANSLATION_X,1000f, 0f),
                            ObjectAnimator.ofFloat(binding.txtQuestion, View.ALPHA,0f, 1f),
                            ObjectAnimator.ofFloat(binding.radioGroup, View.ALPHA,0f, 1f)
                        )
                        setDuration(300)
                        start()
                    }
                }
            })
            start()
        }

    }
}