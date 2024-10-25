package kz.altairait.gestures;
import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.KeyEvent;
import android.media.AudioManager;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.functions.*;
import kz.altairait.utils.*;
fun getOrExecuteAction(action: String, cxt: Context): Any? {
    return when (action) {
        "open-app-list" -> AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS
        "dpad-center" -> AccessibilityService.GLOBAL_ACTION_DPAD_CENTER
        "dpad-left" -> AccessibilityService.GLOBAL_ACTION_DPAD_LEFT
        "dpad-right" -> AccessibilityService.GLOBAL_ACTION_DPAD_RIGHT
        "dpad-down" -> AccessibilityService.GLOBAL_ACTION_DPAD_DOWN
        "dpad-up" -> AccessibilityService.GLOBAL_ACTION_DPAD_UP
        "quick-settings" -> AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS
        "power-dialog" -> AccessibilityService.GLOBAL_ACTION_POWER_DIALOG
        "take-screenshot" -> AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT
        "lock-screen" -> AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN
        "notifications" -> AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS
        "to-home" -> AccessibilityService.GLOBAL_ACTION_HOME
        "back" -> AccessibilityService.GLOBAL_ACTION_BACK
        "play-or-stop-music-or-answer-to-call" -> AccessibilityService.GLOBAL_ACTION_KEYCODE_HEADSETHOOK
        "opened-app-list" -> AccessibilityService.GLOBAL_ACTION_RECENTS
        "volume-up", "volume-down" -> setVolume(action, cxt)
"next-sound" -> sendKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT);
"previous-sound" -> sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
"change-backlight" -> setBacklight();
"media-volume-up" -> setVolume("volume-up", cxt, AudioManager.STREAM_MUSIC);
"media-volume-down" -> setVolume("volume-down", cxt, AudioManager.STREAM_MUSIC);
"system-volume-up" -> setVolume("volume-up", cxt, AudioManager.STREAM_SYSTEM);
"system-volume-down" -> setVolume("volume-down", cxt, AudioManager.STREAM_SYSTEM);
"ring-volume-up" -> setVolume("volume-up", cxt, AudioManager.STREAM_RING);
"ring-volume-down" -> setVolume("volume-down", cxt, AudioManager.STREAM_RING);
"voice-call-volume-up" -> setVolume("volume-up", cxt, AudioManager.STREAM_VOICE_CALL);
"voice-call-volume-down" -> setVolume("volume-down", cxt, AudioManager.STREAM_VOICE_CALL);
"alarm-volume-up" -> setVolume("volume-up", cxt, AudioManager.STREAM_ALARM);
"alarm-volume-down" -> setVolume("volume-down", cxt, AudioManager.STREAM_ALARM);
"notification-volume-up" -> setVolume("volume-up", cxt, AudioManager.STREAM_NOTIFICATION);
"notification-volume-down" -> setVolume("volume-down", cxt, AudioManager.STREAM_NOTIFICATION);
"backward-node" -> {
(nodeByDirection(false)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS
) ?: return "false")
|| return "node_is_invalid";
}
"farward-node" -> {
(nodeByDirection(true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS
) ?: return "true")
|| return "node_is_invalid"
}
        else -> {
if (action is String
&& action.isNotEmpty()) {
return action;
}
null;
}
    }
}
