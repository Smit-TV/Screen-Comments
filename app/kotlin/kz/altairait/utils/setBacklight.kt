package kz.altairait.utils;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.Global;
import android.content.Context;
import android.view.WindowManager;
import android.os.Build
import android.view.View;
import android.graphics.Color;
import android.graphics.PixelFormat;

fun setBacklight(firstCall: Boolean = false) {
val currentState = Global.prefs.getBoolean("screen_backlight", false);
if (!firstCall) {
Global.editor.putBoolean("screen_backlight", currentState != true).apply();
}
val finalState = if (firstCall) currentState else currentState != true;
onBacklight(finalState);
}
private var overlayView: View? = null;
fun onBacklight(state: Boolean) {
if (state) {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSPARENT
        )

        overlayView = View(Global.cxt)
        overlayView?.setBackgroundColor(Color.BLACK)

        Global.windowManager.addView(overlayView, params)
Global.tts.speak(
Global.cxt.getString(R.string.screen_backlight_disabled), 0
);
} else {
        if (overlayView != null) {
            Global.windowManager.removeView(overlayView)
Global.tts.speak(
Global.cxt.getString(R.string.screen_backlight_enabled), 0
);
        }

}
}

