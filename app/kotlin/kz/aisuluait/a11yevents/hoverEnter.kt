package kz.aisuluait.a11yevents;
import android.content.Context;
import kz.aisuluait.R;
import kz.aisuluait.feedback.vibrate;
import kz.aisuluait.focus.getFocusableNode;
import kz.aisuluait.focus.isScrollable;
import kz.altairait.utils.getUniqueId;
import kz.altairait.soundByEvent;
fun hoverEnter(node: Node?, cxt: Context) {
node ?: return;
if (node.isAccessibilityFocused
&& Global.lastUniqueId != Global.currentUniqueId) {
node.performAction(
Node.ACTION_CLEAR_ACCESSIBILITY_FOCUS
);
}
val preferredNode = getFocusableNode(node) ?: run {
Global.node?.refresh();
if (!node.equals(Global.node)) {
soundByEvent(null);
}
return;
}
if (preferredNode.isAccessibilityFocused
&& Global.lastUniqueId != Global.currentUniqueId) {
preferredNode.performAction(
Node.ACTION_CLEAR_ACCESSIBILITY_FOCUS
);
}
preferredNode.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS
);
}
