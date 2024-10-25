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
 class Global {
companion object {
lateinit var audioManager: AudioManager;
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
var window: Window? = null;
var jsonConfig = getConfig();
var gestures = getGesturesFromConfig(jsonConfig);
lateinit var screenReceiver: ScreenReceiver;
lateinit var prefs: SharedPreferences;
lateinit var editor: Editor;
lateinit var cxt: Context;
lateinit var windowManager: WindowManager;
lateinit var batteryManager: BatteryManager;
}
}
