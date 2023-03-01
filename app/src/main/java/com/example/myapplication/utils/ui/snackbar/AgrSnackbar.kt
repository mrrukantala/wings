package com.example.bossku.utils.ui.snackbar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutSnackbarBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar

/**
 * APL Toast using Material Custom Snack Bar
 */
abstract class AgrSnackbar(private val activity: FragmentActivity) {

    private constructor(builder: Builder) : this(builder.activity)

    /**
     * Type Variant of Toast in APL
     */
    abstract val toastType: ToastType

    /**
     * Anchor View Snack Bar to Show
     */
    abstract val parentView: View

    /**
     * Message Description Text to Show in Snack Bar
     */
    abstract val description: String?

    /**
     * Message Title Text to Show in Snack Bar
     */
    abstract val title: String

    /**
     * Action Button Text
     * If Null Icon Button will show on Snack Bar
     */
    abstract val actionText: String?

    /**
     * Snack Bar Icon
     * If null icon will not visible
     */
    abstract val icon: Drawable?

    /**
     * Load Bitmap into Snack Bar Icon
     * If null avatar image will not visible
     */
    abstract val avatarUrl: String?

    /**
     * Callback for action button listener
     */
    abstract val callback: (() -> Unit)?

    /**
     * Snack Bar Duration
     */
    @Duration
    abstract val duration: Int

    private val snackBar: Snackbar by lazy {
        Snackbar.make(parentView, title, duration)
    }

    private val context: Context by lazy {
        activity.applicationContext
    }

    private val viewContainer: View by lazy {
        activity.layoutInflater.inflate(R.layout.layout_snackbar, null)
    }

    private val binding: LayoutSnackbarBinding by lazy {
        LayoutSnackbarBinding.bind(viewContainer)
    }

    private fun initView() {
        with(binding) {
            val root = this.containerCard
            // Setup Background Color
            root.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    when (toastType) {
                        ToastType.NORMAL -> {
                            R.color.white
                        }
                        ToastType.WARNING -> {
                            R.color.warning_normal
                        }
                        ToastType.ERROR -> {
                            R.color.error_normal
                        }
                        ToastType.SUCCESS -> {
                            R.color.success_normal
                        }
                    }
                )
            )

            // Setup Stroke Colors
            root.strokeColor = ContextCompat.getColor(
                context,
                when (toastType) {
                    ToastType.NORMAL -> {
                        R.color.white
                    }
                    ToastType.WARNING -> {
                        R.color.warning_normal
                    }
                    ToastType.ERROR -> {
                        R.color.error_normal
                    }
                    ToastType.SUCCESS -> {
                        R.color.success_normal
                    }
                }
            )

            // Validate if only one of the icon and avatar url is filled in
            if (icon != null && avatarUrl != null) {
                throw IllegalStateException("You can only choose one, use an avatar or use an icon")
            }

            // Build Avatar if Avatar not null and remove views if null
            /**
            if (avatarUrl != null && avatarUrl?.isNotEmpty() == true) {
            ivAvatar.visible()
            ivAvatar.loadImage(avatarUrl.orEmpty())
            } else {
            runCatching {
            containerContent.children
            .toList()
            .indexOfFirst { it.tag == "ivAvatar" }
            .let { containerContent.removeViewAt(it) }
            }
            }
             **/

            // Build Icon if Icon not null and remove views if null
            if (icon != null) {
                ivIcon.visibility = View.VISIBLE
                ivIcon.setImageDrawable(icon)
                ivIcon.setColorFilter(
                    if (
                        toastType == ToastType.NORMAL || toastType == ToastType.WARNING
                    ) {
                        ContextCompat.getColor(context, R.color.black_font)
                    } else {
                        ContextCompat.getColor(context, R.color.white)
                    },
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                runCatching {
                    containerContent.children
                        .toList()
                        .indexOfFirst { it.tag == "ivIcon" }
                        .let { containerContent.removeViewAt(it) }
                }
            }

            // Build Content Text
            val titleView = TextView(context).apply {
                R.style.AgrToastTitle
                setTextColor(
                    if (
                        toastType == ToastType.NORMAL || toastType == ToastType.WARNING
                    ) {
                        ContextCompat.getColor(context, R.color.black_font)
                    } else {
                        ContextCompat.getColor(context, R.color.white)
                    }
                )
                text = title
            }

            val descriptionView = TextView(context).apply {
                R.style.AgrToastMessage
                setTextColor(
                    if (
                        toastType == ToastType.NORMAL || toastType == ToastType.WARNING
                    ) {
                        ContextCompat.getColor(context, R.color.black_font)
                    } else {
                        ContextCompat.getColor(context, R.color.white)
                    }
                )
                text = description.orEmpty()
            }

            containerText.removeAllViews()

            // Validate if title not empty render title view's
            if (title.isNotEmpty()) {
                containerText.addView(titleView)
            }

            // Validate if description not empty render description view's
            if (description != null) {
                containerText.addView(descriptionView)
            }

            // Build Action Button
            if (actionText != null && actionText?.isNotEmpty() == true) {
                val actionView = TextView(context).apply {
                    when (toastType) {
                        ToastType.NORMAL -> {
                            R.style.AgrToastTextAction_Primary
                        }
                        ToastType.WARNING -> {
                            R.style.AgrToastTextAction_BlackFont
                        }
                        else -> {
                            R.style.AgrToastTextAction_White
                        }
                    }
                    text = actionText
                }
                containerAction.addView(actionView)
                containerAction.setOnClickListener {
                    callback?.invoke()
                    snackBar.dismiss()
                }
            } else {
                val actionView = ImageView(context).apply {
                    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close))
                    setColorFilter(
                        if (
                            toastType == ToastType.NORMAL || toastType == ToastType.WARNING
                        ) {
                            ContextCompat.getColor(context, R.color.black_font)
                        } else {
                            ContextCompat.getColor(context, R.color.white)
                        },
                        PorterDuff.Mode.SRC_IN
                    )
                }
                containerAction.addView(actionView)
                containerAction.setOnClickListener {
                    callback?.invoke()
                    snackBar.dismiss()
                }
            }
        }
    }

    fun show() {
        // Init Custom Snack Bar View
        initView()

        // Override Snack Bar View with Custom View
        (snackBar.view as Snackbar.SnackbarLayout).apply {
            setBackgroundColor(Color.TRANSPARENT)
            setPadding(0, 0, 0, 0)
            removeAllViews()
            addView(viewContainer, 0)
        }

        // Set Snack Bar Animation
        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE

        // Show Snack Bar
        snackBar.show()
    }

    companion object {
        inline fun setup(activity: FragmentActivity, block: Builder.() -> Unit): AgrSnackbar {
            return Builder(activity).apply(block).build()
        }
    }

    enum class ToastType {
        NORMAL,
        WARNING,
        ERROR,
        SUCCESS
    }

    class Builder(val activity: FragmentActivity) {

        /**
         * Type Variant of Toast in APL
         */
        var type: ToastType = ToastType.NORMAL

        /**
         * Anchor View Snack Bar to Show
         */
        var view: View = activity.findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0)

        /**
         * Message Description Text
         */
        var description: String? = null

        /**
         * Message Title Text
         */
        var title: String = ""

        /**
         * Snack Bar Duration
         */
        @Duration
        var duration: Int = Snackbar.LENGTH_SHORT

        /**
         * Action Button Text
         * If Null Icon Button will show on Snack Bar
         */
        var actionText: String? = null

        /**
         * Snack Bar Icon
         * If null icon will not visible
         */
        var icon: Drawable? = null

        /**
         * Load Bitmap into Snack Bar Icon
         * If null avatar image will not visible
         */
        var avatarUrl: String? = null

        /**
         * Callback for action button listener
         */
        var callback: (() -> Unit)? = null

        /**
         * Action Button Listener
         */
        fun setActionButtonListener(callback: () -> Unit): Builder {
            this.callback = callback
            return this
        }

        /**
         * Build AgrToast with Builder Pattern
         */
        fun build(): AgrSnackbar {
            return object : AgrSnackbar(this@Builder) {
                override val toastType: ToastType = this@Builder.type
                override val parentView: View = this@Builder.view
                override val description: String? = this@Builder.description
                override val title: String = this@Builder.title
                override val duration: Int = this@Builder.duration
                override val actionText: String? = this@Builder.actionText
                override val icon: Drawable? = this@Builder.icon
                override val avatarUrl: String? = this@Builder.avatarUrl
                override val callback: (() -> Unit)? = this@Builder.callback
            }
        }
    }
}
