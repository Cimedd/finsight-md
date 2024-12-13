package com.capstone.finsight.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.finsight.R

class PasswordField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private val startDraw = ContextCompat.getDrawable(context, R.drawable.baseline_lock_24)
    private val visible = ContextCompat.getDrawable(context, R.drawable.baseline_visibility_24)
    private val notVisible = ContextCompat.getDrawable(context, R.drawable.baseline_visibility_24)
    private var isPasswordVisible = false

    init {
        setOnTouchListener(this)
        setButtonDrawables(endOfTheText = visible)
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    setError(context.getString(R.string.password_error), null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setButtonDrawables(startOfTheText: Drawable? = startDraw, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null){
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClicked = false

            if ( event!= null){
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    clearButtonEnd = (compoundDrawables[2].intrinsicWidth + paddingStart).toFloat()
                    when {
                        event.x < clearButtonEnd -> isClicked = true
                    }
                } else {
                    clearButtonStart = (width - paddingEnd - compoundDrawables[2].intrinsicWidth).toFloat()
                    when {
                        event.x > clearButtonStart -> isClicked = true
                    }
                }
            }

            if (isClicked && event != null) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (!isPasswordVisible) {
                            inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            transformationMethod = PasswordTransformationMethod.getInstance()
                            setButtonDrawables(endOfTheText = visible)
                        } else {
                            inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                            setButtonDrawables(endOfTheText = notVisible)
                        }
                        isPasswordVisible = !isPasswordVisible
                        return true
                    }
                    else -> return false
                }
            }
        }
        return false
    }
}