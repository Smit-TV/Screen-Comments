package kz.aisuluait.functions;
import android.accessibilityservice.AccessibilityService;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.content.Intent;
import android.provider.Settings;
import kz.aisuluait.R;
import kz.aisuluait.MainActivity;
import kz.aisuluait.log.LogUtils;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
import kz.altairait.utils.setBacklight;
private var views = mutableListOf<LinearLayout>();
// Главное меню
fun mainMenu() {
val cxt = Global.cxt;
try {
val menu = Global.layoutInflater.inflate(R.layout.app_menu, null);
val listItems = menu.findViewById<ListView>(R.id.list);
val menuPoints = getMenuPoints();
val menuPointsIds = getMenuPointsIds().split(";");
val items = menuPoints.split(";");
val adapter = ArrayAdapter(cxt, R.layout.list_item, items);
listItems.adapter = adapter;


val view = LinearLayout(cxt);
views.add(view);
view.apply {
layoutParams = LinearLayout.LayoutParams(
LinearLayout.LayoutParams.MATCH_PARENT,
LinearLayout.LayoutParams.MATCH_PARENT);
gravity = Gravity.CENTER;
alpha = 0.25f;
addView(menu);
}
val paneTitle = view.findViewById<TextView>(R.id.pane_title);
paneTitle.text = cxt.getString(R.string.main_menu);
val cancelBtn = view.findViewById<Button>(R.id.cancel);
cancelBtn.visibility = View.GONE;
val closeBtn = view.findViewById<Button>(R.id.close);
closeBtn.setOnClickListener {
views.forEach { 
hideView(it); 
}
views.clear();
}

listItems.setOnItemClickListener {
parent, viewInstance, position, id ->
closeBtn.performClick();
val pointValue: Any = menuPointsIds[position];
if (pointValue is Int) {
Global.serviceInterface.performGlobalAction(pointValue.toInt());
} else if (pointValue is String
|| pointValue is Char) {
actionInterpreter(pointValue.toString());
}
}
setViewOnScreen(view);
}
catch (e: Exception){
Log.d("scrcerr", "$e из MainMenu");
}
}
/*
* Получает R.string метки действия подсветки экрана
*  Включить подсветку экрана
* Отключить подсветку экрана
*/ 
private fun getScreenBacklightAction(): Int {
/** true - подсветка отключена */
val backlightState = Global.prefs.getBoolean("screen_backlight", false);
val result = if (!backlightState) {
R.string.turn_off_screen_backlight;
} else {
R.string.turn_on_screen_backlight;
}
return result;
}
private val defaultMenuPoints =
"change-backlight;${R.string.read_from_next_element};${R.string.all_actions};${R.string.special_actions};${R.string.dev_menu};${R.string.next_window};${R.string.previous_window};${R.string.app_settings};${R.string.tts_settings}";
// Получает элементы главного меню через запятую
private fun getMenuPoints(): String {
val cxt = Global.cxt;
val pref = (Global.prefs.getString("menu_points", null) ?: defaultMenuPoints);
val points = pref.replace("change-backlight", "${getScreenBacklightAction()}");
val items = points.split(";");
var s = "";
for (keyId in 0 until items.size) {
val subStr = cxt.getString(items[keyId].toInt()) ?: continue;
if (s != "") {
s = ("$s;$subStr");
} else {
s = "$subStr";
}
}
return s;
}
// Меню разработки
const val ACTION_DEV_MENU = "dev-menu";
// Все действия
const val ACTION_ALL_ACTIONS = "all-actions";
// Специальные действия
const val ACTION_SPECIAL_ACTIONS = "special-actions";
// Читать со следующего элемента
const val ACTION_READ_FROM_NEXT_ELEMENT = "read-from-next-element";
// Перейти к следующему окну
const val ACTION_NEXT_WINDOW = "next-window";
// Перейти к предыдущему окну
const val ACTION_PREVIOUS_WINDOW = "previous-window";
// Включить или отключить подсветку экрана
const val ACTION_SCREEN_BACKLIGHT = "change-backlight";
// Открыть приложение
const val ACTION_OPEN_APP_SETTINGS = "open-app-settings";
// Откройте настройки TTS
const val ACTION_OPEN_TTS_SETTINGS = "open-tts-settings";
private val defaultMenuPointsIds = 
"$ACTION_SCREEN_BACKLIGHT;$ACTION_READ_FROM_NEXT_ELEMENT;$ACTION_ALL_ACTIONS;$ACTION_SPECIAL_ACTIONS;$ACTION_DEV_MENU;$ACTION_NEXT_WINDOW;$ACTION_PREVIOUS_WINDOW;$ACTION_OPEN_APP_SETTINGS;$ACTION_OPEN_TTS_SETTINGS";
// Получает id элементов главного меню через запятую
private fun getMenuPointsIds(): String {
val cxt = Global.cxt;
val points = Global.prefs.getString("menu_points_ids", null) ?: defaultMenuPointsIds;
return points;
}
// Выполняет действие
private fun actionInterpreter(action: String) {
val cxt = Global.cxt;
if (action == ACTION_DEV_MENU) {
devMenu();
}
if (action == ACTION_ALL_ACTIONS) {
actionsMenu(Global.node, "all");
}
if (action == ACTION_SPECIAL_ACTIONS) {
actionsMenu(Global.node, "special");
}
if (action == ACTION_READ_FROM_NEXT_ELEMENT) {
readFromNextElement();
}
if (action == ACTION_NEXT_WINDOW) {
nextWindow();
}
if (action == ACTION_PREVIOUS_WINDOW) {
previousWindow();
}
if (action == ACTION_SCREEN_BACKLIGHT) {
setBacklight();
}
if (action == ACTION_OPEN_APP_SETTINGS) {
openAppSettings();
}
if (action == ACTION_OPEN_TTS_SETTINGS) {
openTTSSettings();
}
}
// Откройте настройки приложения
fun openAppSettings() {
val intent = Intent(Global.cxt, MainActivity::class.java);
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
Global.cxt.startActivity(intent);
}
// Открывает настройки TTS
fun openTTSSettings() {
val intent = Intent("com.android.settings.TTS_SETTINGS");
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
Global.cxt.startActivity(intent);
}

