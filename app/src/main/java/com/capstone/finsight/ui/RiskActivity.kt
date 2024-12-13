package com.capstone.finsight.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.capstone.finsight.R
import com.capstone.finsight.data.ProfileVMF
import com.capstone.finsight.data.ProfileViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.ActivityRiskBinding
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class RiskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiskBinding
    private var index = 0
    private var uid = ""
    private var riskCount = 0
    private var currentPoint = 0
    private var question: Array<String> = arrayOf()
    private var answer10: Array<String> = arrayOf()
    private var answer8: Array<String> = arrayOf()
    private var answer6: Array<String> = arrayOf()
    private var answer4: Array<String> = arrayOf()
    private var answer2: Array<String> = arrayOf()

    private val settingVM by viewModels<SettingViewModel> {
        SettingVMF.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRiskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        question = resources.getStringArray(R.array.riskQuestion)
        answer10 = resources.getStringArray(R.array.riskAnswer1)
        answer8 = resources.getStringArray(R.array.riskAnswer2)
        answer6 = resources.getStringArray(R.array.riskAnswer3)
        answer4 = resources.getStringArray(R.array.riskAnswer4)
        answer2 = resources.getStringArray(R.array.riskAnswer5)

        setText()
        lifecycleScope.launch {
            uid = settingVM.getUser()
        }

        binding.btnNext.setOnClickListener{
            checkProgress()
            binding.progressBar2.setProgress(17*(index+1),true)
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            currentPoint = radioButton.tag.toString().toInt()
        }
    }

    private fun checkProgress(){
        if(index < question.size - 1){
            index++
            riskCount += currentPoint
            setText()
        }
        else{
            riskCount += currentPoint
            val risk = calculate()
            Log.d("RISK", risk.toString())
            settingVM.setProfileRisk(uid, risk).observe(this){
                when(it){
                    is Result.Error -> {
                        Toast.makeText(this, it.error, Toast.LENGTH_SHORT ).show()
                    }
                    Result.Loading -> {
                        binding.btnNext.isEnabled = false
                    }
                    is Result.Success -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun calculate() : String{
        return when (riskCount) {
            in 36..48 -> "Aggressive"
            in 24..35 -> "Moderate"
            in 12..23 -> "Conservative"
            else -> "Invalid Points" // Handle any invalid input
        }
    }

    private fun setText(){
        binding.txtQuestion.text = question[index]
        if(answer10[index] != ""){
            binding.rb1.visibility = View.VISIBLE
            binding.rb1.text = answer10[index]
        } else binding.rb1.visibility = View.GONE

        if(answer8[index] != ""){
            binding.rb2.visibility = View.VISIBLE
            binding.rb2.text = answer8[index]
        } else binding.rb2.visibility = View.GONE

        if(answer6[index] != ""){
            binding.rb3.visibility = View.VISIBLE
            binding.rb3.text = answer6[index]
        } else binding.rb3.visibility = View.GONE

        if(answer4[index] != ""){
            binding.rb4.visibility = View.VISIBLE
            binding.rb4.text = answer4[index]
        } else binding.rb4.visibility = View.GONE

        if(answer2[index] != ""){
            binding.rb5.visibility = View.VISIBLE
            binding.rb5.text = answer2[index]
        } else binding.rb5.visibility = View.GONE
        nextAnim()

        if(index == 5){
            binding.btnNext.text = "Finish"
        }
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