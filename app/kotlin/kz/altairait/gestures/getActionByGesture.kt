package kz.altairait.gestures;
import android.accessibilityservice.AccessibilityService;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.feedback.*;
import kz.altairait.getParam;
import kz.aisuluait.a11yevents.Global;
fun gestureEvent(gesture: Int): String {
Global.jsonConfig ?: return "";
return when (gesture) {
AccessibilityService.GESTURE_SWIPE_LEFT -> getParam(Global.jsonConfig ?: return "", "left")
AccessibilityService.GESTURE_SWIPE_RIGHT -> getParam(Global.jsonConfig ?: return "", "right")
AccessibilityService.GESTURE_SWIPE_DOWN_AND_LEFT -> getParam(Global.jsonConfig ?: return "", "bottom-left")
AccessibilityService.GESTURE_SWIPE_UP_AND_LEFT -> getParam(Global.jsonConfig ?: return "", "top-left")
AccessibilityService.GESTURE_SWIPE_LEFT_AND_UP -> getParam(Global.jsonConfig ?: return "", "left-top")
AccessibilityService.GESTURE_SWIPE_DOWN_AND_RIGHT -> getParam(Global.jsonConfig ?: return "", "bottom-right")
AccessibilityService.GESTURE_SWIPE_UP -> getParam(Global.jsonConfig ?: return "", "top")
AccessibilityService.GESTURE_SWIPE_DOWN -> getParam(Global.jsonConfig ?: return "", "bottom")
AccessibilityService.GESTURE_SWIPE_LEFT_AND_RIGHT -> getParam(Global.jsonConfig ?: return "", "left-right")
AccessibilityService.GESTURE_SWIPE_RIGHT_AND_LEFT -> getParam(Global.jsonConfig ?: return "", "right-left");
AccessibilityService.GESTURE_SWIPE_LEFT_AND_DOWN -> getParam(Global.jsonConfig ?: return "", "left-bottom")
AccessibilityService.GESTURE_SWIPE_RIGHT_AND_UP -> getParam(Global.jsonConfig ?: return "", "right-top")
AccessibilityService.GESTURE_SWIPE_RIGHT_AND_DOWN -> getParam(Global.jsonConfig ?: return "", "right-bottom")
AccessibilityService.GESTURE_SWIPE_UP_AND_DOWN -> getParam(Global.jsonConfig ?: return "", "top-bottom")
AccessibilityService.GESTURE_SWIPE_DOWN_AND_UP -> getParam(Global.jsonConfig ?: return "", "bottom-top")
AccessibilityService.GESTURE_SWIPE_UP_AND_RIGHT -> getParam(Global.jsonConfig ?: return "", "top-right")
else -> {
vibrate(78);
"DEFAULT"
}
}
}
