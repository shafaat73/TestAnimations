package com.phaedra.myanimationsapplication.utill


import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.phaedra.myanimationsapplication.R


class MyLoadingButton(context: Context) : RelativeLayout(context),View.OnClickListener {

    var DEFAULT_LOADER_COLOR = resources.getColor(R.color.design_default_color_on_secondary)
    var DEFAULT_BUTTON_COLOR = resources.getColor(R.color.design_default_color_on_primary)
    var DEFAULT_BUTTON_LABEL_COLOR = resources.getColor(R.color.white)

    /**
     * all the default values to be used
     */
    var DEFAULT_BUTTON_LABEL_SIZE = 18f
    val BUTTON_STATE_NORMAL = 1
    val BUTTON_STATE_LOADING = 2
    val BUTTON_STATE_DONE = 3
    val BUTTON_STATE_ERROR = 4

    var TAG = MyLoadingButton::class.java.simpleName

    /**
     * the width for button. 1500 by default.
     */
    var buttonExpandedWidth = 1500

    /**
     * the color for loading button at background. DEFAULT_BUTTON_COLOR by default.
     */
    var buttonBackgroundColor = 0

    /**
     * the color for loader circle. DEFAULT_LOADER_COLOR by default .
     */
    var loaderColor = 0

    /**
     * the size in float value for button label/text. DEFAULT_BUTTON_LABEL_SIZE by default.
     */
    var buttonLabelSize = 0f

    /**
     * the color for button label/text. DEFAULT_BUTTON_LABEL_COLOR by default.
     */
    var buttonLabelColor = 0

    /**
     * Button error icon.
     */
    var progressErrorDrawable: Drawable? = null

    /**
     * Button done icon.
     */
    var progressDoneDrawable: Drawable? = null

    /**
     * Set button label. Button by default.
     */
    var buttonLabel = "Button"

    /**
     * When button in error state set it to normal automatically after 2 sec. true by default.
     */
    var normalAfterError = true

    /**
     * Set button animation duration. 300 by default.
     */
    var animationTime = 300

    /**
     * Set button currentState. by default in normal State.
     * State like normal, error, loading, done.
     */
    var currentState = BUTTON_STATE_NORMAL

    /**
     * all views
     */
    var buttonLayout: RelativeLayout? = null
    var buttonLabelTextView: TextView? = null
    var progressBar: ProgressBar? = null
    var buttonAnimation: ButtonAnimation? = null
    var progressDoneLayout: LinearLayout? = null
    var progressErrorLayout: LinearLayout? = null
    var myLoadingButtonClick: MyLoadingButtonClick? = null

    init {

        initView()
    }

    interface MyLoadingButtonClick {
        fun onMyLoadingButtonClick()
    }

    fun setMyButtonClickListener(myButtonClickListener: MyLoadingButtonClick?) {
        myLoadingButtonClick = myButtonClickListener
    }

    /**
     * Inflate layout to view and initialize view
     */
    fun initView() {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.my_loding_button_layout, this)
        buttonAnimation = ButtonAnimation(buttonExpandedWidth, animationTime.toLong())
        buttonLayout = view.findViewById(R.id.button_layout)
        buttonLabelTextView = view.findViewById(R.id.button_label_tv)
        progressBar = view.findViewById(R.id.progress_bar)
        progressDoneLayout = view.findViewById(R.id.progress_done_layout)
        progressErrorLayout = view.findViewById(R.id.progress_error_layout)
        buttonLabelTextView!!.text = buttonLabel
        Log.d(TAG, "initView: " + buttonLayout!!.measuredWidthAndState)
        buttonLayout!!.setOnClickListener(this)
    }


    /**
     * Set custom attributes
     *
     * @param attrs
     * @param context
     */
    /*private fun setAttrs(attrs: AttributeSet, context: Context) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MyLoadingButton,
            0,
            0
        )
        buttonLabel = typedArray.getString(R.styleable.MyLoadingButton_mlb_label)!!
        animationTime =
            typedArray.getInteger(R.styleable.MyLoadingButton_mlb_animationDuration, animationTime)
        buttonBackgroundColor = typedArray.getColor(
            R.styleable.MyLoadingButton_mlb_backgroundColor,
            DEFAULT_BUTTON_COLOR
        )
        loaderColor =
            typedArray.getColor(R.styleable.MyLoadingButton_mlb_loaderColor, DEFAULT_LOADER_COLOR)
        buttonLabelSize = typedArray.getDimension(
            R.styleable.MyLoadingButton_mlb_labelSize,
            DEFAULT_BUTTON_LABEL_SIZE
        )
        buttonLabelColor = typedArray.getColor(
            R.styleable.MyLoadingButton_mlb_labelColor,
            DEFAULT_BUTTON_LABEL_COLOR
        )
        progressErrorDrawable = typedArray.getDrawable(R.styleable.MyLoadingButton_mlb_setErrorIcon)
        progressDoneDrawable = typedArray.getDrawable(R.styleable.MyLoadingButton_mlb_setDoneIcon)
        normalAfterError =
            typedArray.getBoolean(R.styleable.MyLoadingButton_mlb_setNormalAfterError, true)
        Log.d(TAG, "setAttrs: $buttonLabel")


        //Set loading button label
        if (buttonLabel != null) buttonLabelTextView!!.text = buttonLabel

        //Set loading button background color
        val bgShape = buttonLayout!!.background as GradientDrawable
        bgShape.setColor(buttonBackgroundColor)

        //Set progressBar color
        setProgressLoaderColor(loaderColor)

        //Set button label text size
        buttonLabelTextView!!.textSize = buttonLabelSize

        //Set button label text color
        buttonLabelTextView!!.setTextColor(buttonLabelColor)

        //Set progress error icon
        if (progressErrorDrawable != null) progressErrorLayout!!.background = progressErrorDrawable

        //Set progress done icon
        if (progressDoneDrawable != null) progressDoneLayout!!.background = progressDoneDrawable
        typedArray.recycle()
    }
*/

    /**
     * Set label to button
     *
     * @param string
     * @return
     */
    fun setButtonLabel(string: String?): MyLoadingButton? {
        buttonLabelTextView!!.text = string
        return this
    }


    /**
     * Set duration for button animation
     *
     * @param animDuration
     * @return
     */
    fun setAnimationDuration(animDuration: Int): MyLoadingButton? {
        buttonAnimation = ButtonAnimation(buttonExpandedWidth, animDuration.toLong())
        return this
    }


    /**
     * Set button background color
     *
     * @param buttonColor
     * @return
     */
    fun setButtonColor(buttonColor: Int): MyLoadingButton? {
        val bgShape = buttonLayout!!.background as GradientDrawable
        bgShape.setColor(ContextCompat.getColor(context, buttonColor))
        return this
    }


    /**
     * Set loading button progress color
     *
     * @param mColor
     */
    fun setProgressLoaderColor(mColor: Int): MyLoadingButton? {
        progressBar!!.indeterminateDrawable
            .setColorFilter(mColor, PorterDuff.Mode.SRC_IN)
        return this
    }


    /**
     * Set loading button label/text size.
     * @param size
     * @return
     */
    fun setButtonLabelSize(size: Float): MyLoadingButton? {
        buttonLabelTextView!!.textSize = size
        return this
    }


    /**
     * Set loading button label/text colot
     * @param mColor
     * @return
     */
    fun setButtonLabelColor(mColor: Int): MyLoadingButton? {
        buttonLabelTextView!!.setTextColor(resources.getColor(mColor))
        return this
    }


    /**
     * Set loading button progress error icon.
     * @param errorIcon
     * @return
     */
    fun setProgressErrorIcon(errorIcon: Drawable?): MyLoadingButton? {
        if (errorIcon != null) progressErrorLayout!!.background = errorIcon
        return this
    }


    /**
     * Set loading button progress done icon.
     * @param doneIcon
     * @return
     */
    fun setProgressDoneIcon(doneIcon: Drawable?): MyLoadingButton? {
        if (doneIcon != null) progressDoneLayout!!.background = doneIcon
        return this
    }


    /**
     * Set to normal state after error. true by default.
     * @param toNormal
     * @return
     */
    fun setNormalAfterError(toNormal: Boolean): MyLoadingButton? {
        normalAfterError = toNormal
        return this
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.button_layout) {
            when (currentState) {
                BUTTON_STATE_NORMAL -> {
                    showLoadingButton()
                    currentState = BUTTON_STATE_LOADING
                }
                BUTTON_STATE_LOADING -> {
                    showNormalButton()
                    currentState = BUTTON_STATE_NORMAL
                }
            }
            myLoadingButtonClick!!.onMyLoadingButtonClick()
        }
    }


    /**
     * Start animation to get back to normal button from loading view.
     */
    fun showProgressToNormalButton() {
        buttonAnimation!!.animateShapeExpand(buttonLayout!!)
        buttonAnimation!!.onFadeOutVisibleView(progressBar!!, buttonLabelTextView!!)
        buttonAnimation!!.fadeOutView(buttonLabelTextView!!)
        buttonLayout!!.isClickable = true
        currentState = BUTTON_STATE_NORMAL
    }


    /**
     * Start animation to convert button to loading button
     */
    fun showLoadingButton() {
        buttonAnimation!!.animateShapeShrink(buttonLayout!!)
        buttonAnimation!!.onFadeInVisibleView(buttonLabelTextView!!, progressBar!!)
        buttonAnimation!!.fadeOutView(progressBar!!)
        buttonAnimation!!.fadeInView(progressErrorLayout!!)
        buttonAnimation!!.fadeInView(progressDoneLayout!!)
        buttonLayout!!.isClickable = false
        currentState = BUTTON_STATE_LOADING
    }


    /**
     * Start animation to get back to normal button from done button.
     */
    fun showDoneToNormalButton() {
        buttonAnimation!!.animateShapeExpand(buttonLayout!!)
        buttonAnimation!!.onFadeOutVisibleView(progressDoneLayout!!, buttonLabelTextView!!)
        buttonAnimation!!.fadeOutView(buttonLabelTextView!!)
        buttonLayout!!.isClickable = true
        currentState = BUTTON_STATE_NORMAL
    }


    /**
     * Start animation to convert button to done view.
     */
    fun showDoneButton() {
        buttonAnimation!!.animateShapeShrink(buttonLayout!!)
        buttonAnimation!!.onFadeInVisibleView(buttonLabelTextView!!, progressDoneLayout!!)
        buttonAnimation!!.fadeOutView(progressDoneLayout!!)
        buttonAnimation!!.fadeInView(progressBar!!)
        buttonAnimation!!.fadeInView(progressErrorLayout!!)
        buttonLayout!!.isClickable = false
        currentState = BUTTON_STATE_DONE
    }


    /**
     * Start animation to get back to normal button from error view
     */
    fun showErrorToNormalButton() {
        buttonAnimation!!.animateShapeExpand(buttonLayout!!)
        buttonAnimation!!.onFadeOutVisibleView(progressErrorLayout!!, buttonLabelTextView!!)
        buttonAnimation!!.fadeOutView(buttonLabelTextView!!)
        buttonLayout!!.isClickable = true
        currentState = BUTTON_STATE_NORMAL
    }


    /**
     * Start animation to convert button to error view
     */
    fun showErrorButton() {
        buttonAnimation!!.animateShapeShrink(buttonLayout!!)
        buttonAnimation!!.onFadeInVisibleView(buttonLabelTextView!!, progressErrorLayout!!)
        buttonAnimation!!.fadeOutView(progressErrorLayout!!)
        buttonAnimation!!.fadeInView(progressDoneLayout!!)
        buttonAnimation!!.fadeInView(progressBar!!)
        buttonLayout!!.isClickable = false
        currentState = BUTTON_STATE_ERROR
        if (normalAfterError == true) {
            gotoNormalAfterDelay()
        }
    }


    /**
     * Start animation to get normal button.
     */
    fun showNormalButton() {
        showProgressToNormalButton()
        showDoneToNormalButton()
        showErrorToNormalButton()
        buttonLayout!!.isClickable = true
        currentState = BUTTON_STATE_NORMAL
    }

    /**
     * button set to normal with delay of 2 sec.
     */
    private fun gotoNormalAfterDelay() {
        Handler().postDelayed(Runnable { // add your code here
            Log.d(TAG, "run: 2345")
            showNormalButton()
        }, 2000)
    }

}