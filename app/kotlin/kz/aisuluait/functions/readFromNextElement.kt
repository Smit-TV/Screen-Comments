package kz.aisuluait.functions;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;

// Читать со следующего элемента
fun readFromNextElement() {
Thread {
Global.readFromNextElementInterrupt = false;
var node: Node? = Global.node;
val currentNode = Global.nodeForDev;
currentNode.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS);
Thread.sleep(250);
while (node != null && !Global.readFromNextElementInterrupt) {
val nextNode = nodeByDirection(true) ?: break;
Global.readFromNextElement = true;
nextNode?.performAction(Node.ACTION_ACCESSIBILITY_FOCUS);
while (Global.readFromNextElement) {
Thread.sleep(10);
}
}
}.start();
}
