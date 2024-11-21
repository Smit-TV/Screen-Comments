package kz.aisuluait.a11yevents;
import android.os.BatteryManager;
import android.content.Context;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.widget.LinearLayout;
import android.graphics.PixelFormat;
import kz.aisuluait.feedback.TTS;
import kz.altairait.getConfig;
import kz.altairait.gestures.getGesturesFromConfig;
import kz.altairait.service.*;
import kz.altairait.ServiceInterface;
 class Global {
companion object {
// Узел для отладки в dev menu
lateinit var nodeForDev: Node;
// Обратная связь о том что метка узла прочитана
var readFromNextElement = false;
// Прерывание для чтения со следующего элемента
var readFromNextElementInterrupt = false;

// Контекст окна
lateinit var windowContext: Context;
// Стандартные параметры для windowManager
val windowParams: LayoutParams
get() { 
val lp = LayoutParams(
LayoutParams.MATCH_PARENT,
LayoutParams.MATCH_PARENT,
LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
//LayoutParams.FLAG_NOT_TOUCH_MODAL or
//LayoutParams.FLAG_ALT_FOCUSABLE_IM or
//LayoutParams.FLAG_LOCAL_FOCUS_MODE,
LayoutParams.FLAG_DIM_BEHIND or
LayoutParams.FLAG_NOT_FOCUSABLE,
PixelFormat.OPAQUE);
lp.gravity = Gravity.CENTER;
return lp;
}

// Отмена поиска следующего узла, так как это уже было сделано
var searchNodeInterrupt = false;
// Метод связи
lateinit var serviceInterface: ServiceInterface;
// Последний AccessibilityEvent
lateinit var lastEvent: Event;
lateinit var audioManager: AudioManager;
// Проверяет событие TYPE_VIEW_SCROLLED на вызов с помощью ACTION_SCROLL_*
var scrollInitByGesture = false;
// Прекосновений к одному и тому же узлу
var tapToNode = 0;
// Текущего узла
var currentUniqueId: String? = "";
// Последнего узла
var lastUniqueId: String? = "";
lateinit var tts: TTS;
// Узел на котором находится фокус доступности
var node: Node? = null;
// Узел с фокусом ACTION_FOCUS
var lastFocusedNode: Node? = null;
// Последнее окно из TYPE_WINDOWS_CHANGED и TYPE_WINDOW_STATE_CHANGED
var window: Window? = null;
var jsonConfig = getConfig();
var gestures = getGesturesFromConfig(jsonConfig);
lateinit var screenReceiver: ScreenReceiver;
lateinit var prefs: SharedPreferences;
lateinit var editor: Editor;
lateinit var cxt: Context;
lateinit var layoutInflater: LayoutInflater;

lateinit var windowManager: WindowManager;
lateinit var batteryManager: BatteryManager;
// Последний заголовок окна
var lastWindowTitle: CharSequence = "";
fun getA11yFocusedNode(): Node? {
return serviceInterface.getA11yFocusedNode();
}
lateinit var rootView: ViewGroup;
fun getRootLayout(): ViewGroup {
val newRoot: LinearLayout = LinearLayout(cxt);
if (!::rootView.isInitialized) {

newRoot.apply {
setLayoutParams(
LayoutParams(LayoutParams.MATCH_PARENT,
LayoutParams.MATCH_PARENT));
}
rootView = newRoot;
}
return rootView ?: newRoot;
}
}
}
