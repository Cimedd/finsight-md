package com.capstone.finsight.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.finsight.R

class EmailField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private val startDraw = ContextCompat.getDrawable(context, R.drawable.baseline_email_24)

    init {
        setOnTouchListener(this)
        setButtonDrawables()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    setError(ContextCompat.getString(context, R.string.app_name), null)
                    setButtonDrawables()
                } else {
                    error = null
                    setButtonDrawables(endOfTheText = ContextCompat.getDrawable(context, R.drawable.baseline_home_24))
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setButtonDrawables(@Suppress("SameParameterValue") startOfTheText: Drawable? = startDraw, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }
}