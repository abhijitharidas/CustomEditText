package com.codetantri.customedittext

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.custom_entertext_layout.view.*


@SuppressLint("NewApi")
/**
 * Created by abhijith on 21/10/18.
 */
class CustomEnterTextLayout : LinearLayout {

    var errorColor: Int = 0
    var titleColor: Int = 0

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        setUpView(context, attrs, defStyleAttr)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        setUpView(context, attrs, defStyleRes)
    }


    private fun setUpView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(context).inflate(R.layout.custom_entertext_layout, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.custom_component_attributes, 0, 0)
            val title = resources.getText(typedArray
                    .getResourceId(R.styleable.custom_component_attributes_component_title, R.string.default_value))
            val hint = resources.getText(typedArray
                    .getResourceId(R.styleable.custom_component_attributes_component_hint, R.string.default_value))
            errorColor = resources.getColor(typedArray
                    .getResourceId(R.styleable.custom_component_attributes_error_color, R.color.errortext_color))
            titleColor = resources.getColor(typedArray
                    .getResourceId(R.styleable.custom_component_attributes_title_color, R.color.black))
            titleText.setTextColor(titleColor)

            clearError()

            titleText.text = title
            hintText.hint = hint

            typedArray.recycle()
        }
    }


    fun setError(errorMessage: String) {
        errorText.visibility = View.VISIBLE
        errorText.setTextColor(errorColor)
        titleText.setTextColor(errorColor)
        errorText.text = errorMessage
    }

    fun clearError() {
        titleText.setTextColor(titleColor)
        errorText.visibility = GONE
    }

    fun getText(): String = hintText.text.toString()


}