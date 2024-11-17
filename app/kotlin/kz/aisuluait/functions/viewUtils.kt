package kz.aisuluait.functions;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import kz.aisuluait.a11yevents.Global;
 val params = LayoutParams(
LayoutParams.MATCH_PARENT,
LayoutParams.WRAP_CONTENT);
// Установить view
fun setViewOnScreen(view: View, params: LayoutParams = Global.windowParams) {
Global.windowManager.addView(view, params);
}
// Удалить view
fun hideView(view: View) {
try {
Global.windowManager.removeView(view);
}
catch (e: Exception) {
Log.d("scrcerr", "$e");
}
}
