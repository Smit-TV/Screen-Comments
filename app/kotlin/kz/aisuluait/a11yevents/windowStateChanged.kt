package kz.aisuluait.a11yevents;
import android.util.Log;
import android.view.accessibility.AccessibilityWindowInfo;
import kz.aisuluait.feedback.vibrate;
import kz.aisuluait.functions.nodeByDirection;
import kz.aisuluait.log.getLog;
import kz.aisuluait.R;
import kz.altairait.farwardNode;
import kz.altairait.utils.getAppNameByPackage;
typealias Window = AccessibilityWindowInfo;
// В целом, эта функция частично отвечает за оповещение пользователя об открытии приложений и т.д.
// Например: Открыв любое приложение, мы услышем метку из android:label находящегося в <activity> 
// проверки на equals нужны для тех случаев когда ту же логику уже выполнила TYPE_WINDOWS_CHANGED
fun windowStateChanged(event: Event) {
if (!Global.screenReceiver.screenWorking()) {
return;
}
val contentChangeTypes = event.contentChangeTypes;
if (contentChangeTypes and Event.CONTENT_CHANGE_TYPE_PANE_DISAPPEARED != 0) {
//return;
}
val wId = event.windowId;
val node = event.source;
val window = node?.window ?: Global.serviceInterface.findWindowById(wId) ?: return;
val lastPaneTitle = Global.lastWindowTitle.toString();
val appPackageName = (node?.packageName ?: "").toString();
val appName = getAppNameByPackage(Global.cxt, appPackageName);
val paneTitle = (event.text?.getOrNull(0)
?: node?.paneTitle
?: window.title
?: appName
?: return).toString();
if (!window.isActive
// equals не сравнивает заголовки
|| window.equals(Global.window) && lastPaneTitle.equals(paneTitle)) {
return;
}

if (contentChangeTypes and Event.CONTENT_CHANGE_TYPE_PANE_TITLE != 0
|| contentChangeTypes and Event.CONTENT_CHANGE_TYPE_PANE_APPEARED != 0
|| !window.equals(Global.window)) {
if (!window.equals(Global.window)) {
Global.lastWindowTitle = paneTitle;
}
Global.window = window;
Global.tts.speak(paneTitle, 1);
}
}
