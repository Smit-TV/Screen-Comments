package kz.aisuluait.log;
import android.util.Log;
import kz.aisuluait.a11yevents.Event;
fun getEventLog(event: Event, tag: String = "scrcevent") {
Thread {
val log = "$event";
Log.d(tag, log);
}.start();
}
