package com.google.android.marvin.talkback;
import android.util.Log;
import android.os.BatteryManager;
import android.os.VibrationEffect;
import android.os.Build;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Path;
import android.view.ViewConfiguration;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.view.LayoutInflater;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.media.AudioManager;
import kz.aisuluait.R;
import kz.aisuluait.log.getLog;
import kz.aisuluait.feedback.*;
import kz.aisuluait.a11yevents.a11yEvent;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.a11yevents.windowStateChanged;
import kz.altairait.gestures.gestureEvent;
import kz.altairait.gestures.getOrExecuteAction;
import kz.altairait.service.ScreenReceiver;
import kz.altairait.utils.setBacklight;
import kz.altairait.getParam;
import kz.altairait.soundByEvent;
import kz.altairait.farwardNode;
import kz.altairait.backwardNode;
import kz.altairait.ServiceInterface;

public open class TalkBackService : AccessibilityService(), ServiceInterface {
private lateinit var cxt: Context;
// Нужна для проверки на готовность службы
var serviceStarted = false;
override fun onServiceConnected() {
super.onCreate();
super.onServiceConnected();
if (Build.VERSION.SDK_INT >= 33) {
setCacheEnabled(true);
}
cxt = applicationContext;
Global.cxt = cxt;
Global.rootView = Global.getRootLayout();

Global.windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager;
val defaultDisplay = Global.windowManager.defaultDisplay;
Global.windowContext = if (Build.VERSION.SDK_INT >= 31) {
createWindowContext(defaultDisplay, WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, null);
} else {
this;
}
Global.layoutInflater = LayoutInflater.from(this);
Global.batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager;
Global.tts = TTS(cxt, cxt.getString(R.string.service_on));
Global.audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager;
Global.serviceInterface = this;
play(R.raw.service_on);

vibrate(VibrationEffect.createWaveform(
longArrayOf(12, 21, 32, 41, 58, 80),
intArrayOf(30, 53, 71, 93, 127, 149), -1), this);
val info = serviceInfo;
info.flags = (info.flags
or AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE
or AccessibilityServiceInfo.FLAG_REQUEST_MULTI_FINGER_GESTURES
or AccessibilityServiceInfo.FLAG_REQUEST_2_FINGER_PASSTHROUGH
or AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
//or AccessibilityServiceInfo.FLAG_SERVICE_HANDLES_DOUBLE_TAP
//or AccessibilityServiceInfo.FLAG_SEND_MOTION_EVENTS
or AccessibilityServiceInfo.FLAG_INPUT_METHOD_EDITOR
//or AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
);
Global.prefs = cxt.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
Global.editor = Global.prefs.edit();
//setCacheEnabled(false);
serviceInfo = info;
Global.screenReceiver = ScreenReceiver(cxt);
val intentFilter = IntentFilter();
intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
intentFilter.addAction(Intent.ACTION_SCREEN_ON);
cxt.registerReceiver(Global.screenReceiver, intentFilter);
setBacklight(true);
Global.node = findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);
serviceStarted = true;
}
override fun onDestroy() {
super.onDestroy();
serviceStarted = false;
cxt?.unregisterReceiver(Global.screenReceiver);
Global.tts?.speak(cxt.getString(R.string.service_off), 0);
play(getParam(Global.jsonConfig, "service-off-sound"));
}
override fun onInterrupt() {
Log.d("scrc", "onInterrupt()");
}
override fun onKeyEvent(keyEvent: KeyEvent): Boolean {
//vibrate(58);
return false;
}
private var endNode = 0;
override fun onGesture(gesture: Int): Boolean {
// Прерываем чтение со след. элем.
Global.readFromNextElementInterrupt = true;
try {
val action = gestureEvent(gesture);
val execute: Any? = getOrExecuteAction(action, this) ?: throw Exception();
if (execute is Int) {
performGlobalAction(execute);
}
if (execute is Int
|| execute is Boolean
|| execute is Unit
|| execute is CharSequence) {
vibrate(52);
}
if (execute is String
&& execute == "node_is_invalid") {
vibrate(100);
endNode = 2;
throw Exception();
}
if (execute is String
&& execute == "using_system_gesture") {
usingSystemGesture();
throw Exception();
}
if (execute is String
&& execute == "touch_exploration_mode") {
touchExplorationMode();
throw Exception();
}
if (execute is String
&& (execute == "true"
|| execute == "false")) {
val result = nextWindow(execute.toBoolean());
if (!result) {
val sound = Global.prefs.getString("end_sound", null);
play(sound ?: R.raw.end);
endNode += 1;
if (endNode >= 1) {
endNode = 0;
farwardNode(Global.node ?: throw Exception(), true);
}
}
}
}
catch (e: Exception) {}
return true;
}
fun nextWindow(direction: Boolean = true, isChild: Boolean= false): Boolean {
try {
val allWindows = windows;
val currentWindow = (Global.node ?: rootInActiveWindow).window;
var currentWindowFound = false || isChild;
var index = if (direction) 0 else allWindows.size;
var item = allWindows[0];
var condition = true;
val currentWindowsCount = currentWindow.childCount;
if (currentWindowsCount > 0) {
var k = if (direction) 0 else currentWindowsCount;
var a11yFocusedWindowFound = false;
while (k <= currentWindowsCount) {
val window = if (direction) {
currentWindow.getChild(k);
} else {
currentWindow.getParent()
}
if (window == null) {
break;
}
if (a11yFocusedWindowFound) {
val root = window.root;
val node = if (direction) {
farwardNode(root, true);
} else {
backwardNode(root, true);
}
if (node?.performAction(
AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS
) == true) {
return true;
}
}
if (direction) {
k++;
} else {
k--;
}
if (window.isAccessibilityFocused) {
a11yFocusedWindowFound = true;
}
if (k >= currentWindowsCount 
|| k < 0) {
k = 0;
a11yFocusedWindowFound = true;
}
}
}
while (condition) {
if (index < 0) {
//break;
}
if (direction) {
index++;
} else {
--index;
}
if (item.isAccessibilityFocused) {
currentWindowFound = true;
//continue;
}
if (currentWindowFound
&& !item.isAccessibilityFocused) {
val rootNode = item.root;
val result = if (direction) {
farwardNode(rootNode, true);
} else {
backwardNode(rootNode, true);
}?.performAction(
AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS
);
if (result == true) {
return true;
}
}
item = allWindows.getOrNull(index) ?: break;
}
if (isChild) {
return false;
}
return nextWindow(direction, true);
}
catch (e: Exception) {
return false;
}
}
private fun tap(point: PointF) {
    val tap = GestureDescription.StrokeDescription(path(point), 0, ViewConfiguration.getTapTimeout().toLong())
    val builder = GestureDescription.Builder()
    builder.addStroke(tap)
    dispatchGesture(builder.build(), null, null)
}

// Метод для создания пути (Path)
private fun path(point: PointF): Path {
    val path = Path()
    path.moveTo(point.x, point.y)
    return path
}
override fun findWindowById(id: Int): AccessibilityWindowInfo? {
windows.forEach {
val wId = it.id;
if (wId == id) {
return it;
}
}
return null;
}
// В целом, эта функция частично отвечает за оповещение пользователя об открытии приложений и т.д.
// Например: Открыв любое приложение, мы услышем метку из android:label находящегося в <activity> 
// проверки на equals нужны для тех случаев когда ту же логику уже выполнила TYPE_WINDOWS_CHANGED

fun windowsChanged(event: AccessibilityEvent) {

val windowChanges = event.windowChanges;
if (windowChanges and AccessibilityEvent.WINDOWS_CHANGE_TITLE != 0
|| windowChanges and AccessibilityEvent.WINDOWS_CHANGE_ADDED != 0) {
val wId = event.windowId ?: return;
val window = findWindowById(wId) ?: return;
if (window.layer != 0
|| window.type != AccessibilityWindowInfo.TYPE_APPLICATION && window.type != AccessibilityWindowInfo.TYPE_SYSTEM
&& window.type != AccessibilityWindowInfo.TYPE_ACCESSIBILITY_OVERLAY
|| window.equals(Global.window)) {
return;
}
val title = window.title ?: return;
if (title.isEmpty()) {
return;
}
Global.window = window;

Global.tts.speak(
title.toString(), 1);
}
}
// Для проверки на использование
// исходного системного жеста
var usingSystemGesture = false;
fun usingSystemGesture() {
Thread {
disableFlag(AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE);
usingSystemGesture = true;
Thread.sleep(750);
if (usingSystemGesture) {
enableFlag(AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE);
}
}.start();
}
fun enableFlag(flag: Int) {
val info = serviceInfo;
info.flags = info.flags or flag;
serviceInfo = info;
// Уведомляем пользователя
if (flag == AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE) {
usingSystemGesture = false;
play(
Global.prefs.getString("touch_exploration_mode_enable_sound", null) ?: R.raw.link);
// Указываем что мы больше не используем системной жест
}
}
fun disableFlag(flag: Int) {
val info = serviceInfo;
info.flags = (info.flags
and flag.inv());
serviceInfo = info;
// Воспроизводим сигнал пользователю
if (flag == AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE) {
play(
Global.prefs.getString("touch_exploration_mode_disable_sound", null) ?: R.raw.speech_error);
}
}
fun touchExplorationMode() {
val mask = serviceInfo.flags;
if (mask and AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE != 0) {
disableFlag(AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE);
} else {
enableFlag(AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE);
}
}
override fun getWindowsList(): MutableList<AccessibilityWindowInfo> {
return windows;
}
override fun getFocusedWindow(type: Int): AccessibilityWindowInfo? {
windows.forEach {
if (it.isAccessibilityFocused && type == 0
|| it.isFocused && type == 1) {
return it;
}
}
return null;
}
override fun getA11yFocusedNode(): AccessibilityNodeInfo? {
return findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);
}
override fun onAccessibilityEvent(event: AccessibilityEvent) {
if (!serviceStarted) {
return;
}
val eventType = event.eventType;
var stop = false;
when (eventType) {

AccessibilityEvent.TYPE_WINDOWS_CHANGED -> {
windowsChanged(event);
return;
}
// Не нужные события
AccessibilityEvent.TYPE_VIEW_HOVER_EXIT,
AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED,
AccessibilityEvent.TYPE_GESTURE_DETECTION_START -> return;

AccessibilityEvent.TYPE_VIEW_HOVER_ENTER,
AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> {
a11yEvent(event);
stop = true;
}
}
soundByEvent(event);
if (stop) {
return;
}
Thread {
a11yEvent(event);
}.start();
}
}


