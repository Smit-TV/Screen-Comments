package kz.aisuluait.functions;

import android.content.Intent
import android.view.KeyEvent
import android.os.SystemClock
import kz.aisuluait.a11yevents.Global;

import android.content.Context
import android.media.AudioManager


fun sendKeyEvent(keyCode: Int) {
val context = Global.cxt;
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val eventTime = SystemClock.uptimeMillis()

    val downEvent = KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0)
    audioManager.dispatchMediaKeyEvent(downEvent)

    val upEvent = KeyEvent(eventTime, eventTime, KeyEvent.ACTION_UP, keyCode, 0)
    audioManager.dispatchMediaKeyEvent(upEvent)
}
