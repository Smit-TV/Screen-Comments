package kz.aisuluait.a11yevents;
import android.content.Context;
import kz.aisuluait.compositor.compositor;
import kz.aisuluait.focus.getFocusableNode;
import kz.aisuluait.feedback.*;
import kz.aisuluait.log.getLog;
import kz.altairait.utils.getUniqueId;
interface GetFocusableNode {
fun run(node: NodeCompat);
}
fun a11yFocused(node: Node?) {
node ?: return;
val textToSpeak = compositor(node, Global.cxt);
Global.tts?.speak(textToSpeak, 0);
// Не будем мешать печатать
if (!node.isTextEntryKey) {
vibrate(31);
}
Global.currentUniqueId = getUniqueId(node);
Global.lastUniqueId = Global.currentUniqueId;
Global.node = node;
getLog(node);
/*getLog(node.parent ?: return);
getLog(node.parent?.parent ?: return);*/
}

