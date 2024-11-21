package kz.aisuluait.functions;
import kz.aisuluait.a11yevents.Global;
import kz.altairait.gestures.getOrExecuteAction;
/** Отвечает за переход к след. или пред. типу навигации а так же за его выполнение */

object SpeedNav {
// Получите список доступных действий
fun getNavTypes(): List<String> {
return (Global.prefs.getString("nav_types", null) ?: "word;symbol;line;paragraph").split(";");
}
// Текущий тип навигации
var currentNavType: Int = 0;
// Переход к следующему типу навигации
fun nextNavType() {
val maxNavTypes = getNavTypes().size;
if (currentNavType < maxNavTypes - 1) {
currentNavType += 1;
} else {
currentNavType = 0;
}
Global.tts.speak(
getNavTypes()[currentNavType]);
}
// Переход к предыдущему типу навигации
fun previousNavType() {
val maxNavTypes = getNavTypes().size;
if (currentNavType > 0) {
currentNavType -= 1;
} else {
currentNavType = maxNavTypes - 1;
}
Global.tts.speak(
getNavTypes()[currentNavType]);
}
// Предыдущее действие навигации
fun previousNavAction() {
val action = getNavTypes()[currentNavType];
getOrExecuteAction("previous-${action}");
}
// Следующее действие навигации
fun nextNavAction() {
val action = getNavTypes()[currentNavType];
getOrExecuteAction("next-${action}");
}
}
