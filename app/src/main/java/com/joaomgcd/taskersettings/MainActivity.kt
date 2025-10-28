package com.joaomgcd.taskersettings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.joaomgcd.taskersettings.util.ShizukuTaskerSettings
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.OnRequestPermissionResultListener


class MainActivity : Activity(), OnRequestPermissionResultListener {
    private var updateShizukuUi: () -> Unit = {}

    companion object {
        private const val SHIZUKU_REQUEST_CODE = 156
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(40, 40, 40, 40)
        }

        addShizukuLayoutIfNeeded(layout)
        addDndButtonIfNeeded(layout)

        setContentView(layout)

    }

    private fun createSectionContainer(): LinearLayout {
        return LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 20
                bottomMargin = 20
            }
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(40, 40, 40, 40)

            val border = GradientDrawable()
            border.setColor(Color.TRANSPARENT)
            border.setStroke(2, Color.DKGRAY)
            border.cornerRadius = 20f
            background = border
        }
    }

    private class SectionScope(val context: Context, private val container: LinearLayout) {
        fun <T : View> T.add(): T {
            container.addView(this)
            return this
        }

        fun addButton(buttonText: String, action: () -> Unit) = Button(context).apply {
            text = buttonText
            setOnClickListener { action() }
        }.add()

        fun addText(label: String? = null) = TextView(context).apply {
            text = label
            gravity = Gravity.CENTER
        }.add()
    }

    private fun section(minApi: Int, layout: LinearLayout, addContent: SectionScope.() -> Unit) {
        if (Build.VERSION.SDK_INT < minApi) return

        val sectionLayout = createSectionContainer()
        SectionScope(this, sectionLayout).addContent()
        layout.addView(sectionLayout)
    }

    private fun addShizukuLayoutIfNeeded(layout: LinearLayout) = section(Build.VERSION_CODES.BAKLAVA, layout) {
        addText("On Anadroid 16+, Shizuku is needed for the Wifi Tether action.")
        val statusTextView = addText().apply {
            textSize = 18f
            setPadding(0, 20, 0, 20)
        }
        val requestShizukuPermissionButton = addButton("Request Shizuku Permission") { Shizuku.requestPermission(SHIZUKU_REQUEST_CODE) }

        fun update() {
            if (ShizukuTaskerSettings.isAvailable) {
                statusTextView.text = "Shizuku status: Available"
                requestShizukuPermissionButton.visibility = View.GONE
                return
            }

            requestShizukuPermissionButton.visibility = View.VISIBLE
            val status = when {
                !ShizukuTaskerSettings.isInstalled -> "Shizuku app not installed."
                !ShizukuTaskerSettings.isRunning -> "Shizuku is installed but not running."
                !ShizukuTaskerSettings.hasPermission -> "Shizuku is running but permission not granted."
                else -> "Shizuku is not available for an unknown reason."
            }
            statusTextView.text = "Shizuku status: $status"
        }
        updateShizukuUi = ::update
    }


    private fun addDndButtonIfNeeded(layout: LinearLayout) = section(Build.VERSION_CODES.VANILLA_ICE_CREAM, layout) {

        addText("On Android 15+, Tasker Settings is needed to turn off Do Not Disturb. Please enable the DND permission for Tasker Settings.").apply {
            setPadding(0, 0, 0, 20)
        }
        addButton("Open DND Settings") { startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)) }
    }

    override fun onResume() {
        super.onResume()
        Shizuku.addRequestPermissionResultListener(this)
        updateUi()
    }

    override fun onPause() {
        super.onPause()
        Shizuku.removeRequestPermissionResultListener(this)
    }

    private fun updateUi() {
        updateShizukuUi()
    }


    override fun onRequestPermissionResult(requestCode: Int, grantResult: Int) {
        when (requestCode) {
            SHIZUKU_REQUEST_CODE -> updateUi()
        }
    }
}