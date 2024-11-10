package kz.aisuluait.a11yevents;
import android.os.BatteryManager;
import android.content.Context;
import android.view.WindowManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import kz.aisuluait.feedback.TTS;
import kz.altairait.getConfig;
import kz.altairait.gestures.getGesturesFromConfig;
import kz.altairait.service.*;
import kz.altairait.ServiceInterface;
 class Global {
companion object {
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
lateinit var windowManager: WindowManager;
lateinit var batteryManager: BatteryManager;
// Последний заголовок окна
var lastWindowTitle: CharSequence = "";
fun getA11yFocusedNode(): Node? {
return serviceInterface.getA11yFocusedNode();
}
}
}
