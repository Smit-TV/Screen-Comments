package kz.aisuluait.log
import android.os.Environment;
import android.util.Log
import kz.aisuluait.a11yevents.NodeCompat
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Action;
import java.io.File;

fun getLog(nodeInfo: Node, tag: String = "scrc") {
//return;
    Thread {
//Log.d("scrclayout", takeXmlLayout(nodeInfo));
var logStr = "";
val node = NodeCompat.wrap(nodeInfo);
val log = "labeledby ${if (node.labeledBy != null) true else false} labelFor ${if (node.labelFor != null) true else false} paneTitle ${node.paneTitle} viewResourceName ${node.viewIdResourceName} tooltip ${node.tooltipText} containerTitle ${node.containerTitle} error ${node.error} text ${node.text} hint Text ${node.hintText} content description ${node.contentDescription} scrollable ${node.isScrollable} dismissable ${node.isDismissable} key ${node.isTextEntryKey} heading ${node.isHeading} content invalid ${node.isContentInvalid} checkable ${node.isCheckable} clickable ${node.isClickable}  long clickable ${node.isLongClickable}  context clickable ${node.isContextClickable} focusable ${node.isFocusable} focused ${node.isFocused} a11y focused ${node.isAccessibilityFocused} editable ${node.isEditable} visible to user ${node.isVisibleToUser} important for accessibility ${node.isImportantForAccessibility} screen reader focusable ${node.isScreenReaderFocusable} className ${node.className} role ${node.roleDescription}  state description ${node.stateDescription} touchdelegateinfo ${if (node.touchDelegateInfo != null) true else false} childCount ${node.childCount} extraRenderingInfo ${if(node.extraRenderingInfo != null) true else false} availableExtraData ${node.availableExtraData} inputType ${node.inputType} canOpenPopup ${node.canOpenPopup()} liveRegion ${node.liveRegion} travel ${if (node. getTraversalBefore() != null) true else false} getTraversalAfter ${if (node.getTraversalAfter() != null) true else false} hasRequestInitialAccessibilityFocus ${node.hasRequestInitialAccessibilityFocus()} isShowingHintText ${node.isShowingHintText} isMultiLine ${node.isMultiLine} isTextSelectable ${node.isTextSelectable} collection ${if (node.collectionInfo != null) true else false} item ${node.collectionItemInfo?.let { true}} selected ${node.isSelected} checked ${node.isChecked} rowIndex ${node.collectionItemInfo?.rowIndex} columnIndex ${node.collectionItemInfo?.columnIndex} columnCount ${node.collectionInfo?.columnCount} rowCount ${node.collectionInfo?.rowCount} extras ${node.extras} getMinDurationBetweenContentChangesMillis ${node.minDurationBetweenContentChangesMillis}"// parent ${node.parent} grandParent ${node.parent?.parent}"
logStr = "$logStr Node: $log";
        Log.d(tag, log)
        getActions(node)
/*try {
val path = "${Environment.getExternalStorageDirectory()}/a11ytechnologies/logs/$tag ${node.packageName ?: "unknown_package"}.txt";
logStr = logStr.replace(" ", "\n");
val file = File(path);
if (!file.exists()) {
file.writeText("$logStr\n\n\n\n");
} else {
file.appendText("\n$logStr\n\n\n\n");
}
}
catch (e: Exception) {
e.printStackTrace();
}*/
    }.start()
}

fun getActions(node: NodeCompat) {
    val actionList = node.actionList
    var list = ""
    for (action in actionList) {
        list = "$list ${action.id} ${action.label ?: getActionLabelById(action.id)}"
    }
    Log.d("scrcactions", list)
}

// Часть кода взята у SpeakTouch.
fun getActionLabelById(id: Int): String {
    return when (id) {
        NodeCompat.ACTION_FOCUS -> "ACTION_FOCUS"
        NodeCompat.ACTION_CLEAR_FOCUS -> "ACTION_CLEAR_FOCUS"
        NodeCompat.ACTION_SELECT -> "ACTION_SELECT"
        NodeCompat.ACTION_CLEAR_SELECTION -> "ACTION_CLEAR_SELECTION"
        NodeCompat.ACTION_CLICK -> "ACTION_CLICK"
        NodeCompat.ACTION_LONG_CLICK -> "ACTION_LONG_CLICK"
        NodeCompat.ACTION_ACCESSIBILITY_FOCUS -> "ACTION_ACCESSIBILITY_FOCUS"
        NodeCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS -> "ACTION_CLEAR_ACCESSIBILITY_FOCUS"
        NodeCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY -> "ACTION_NEXT_AT_MOVEMENT_GRANULARITY"
        NodeCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY -> "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY"
        NodeCompat.ACTION_NEXT_HTML_ELEMENT -> "ACTION_NEXT_HTML_ELEMENT"
        NodeCompat.ACTION_PREVIOUS_HTML_ELEMENT -> "ACTION_PREVIOUS_HTML_ELEMENT"
        NodeCompat.ACTION_SCROLL_FORWARD -> "ACTION_SCROLL_FORWARD"
        NodeCompat.ACTION_SCROLL_BACKWARD -> "ACTION_SCROLL_BACKWARD"
        NodeCompat.ACTION_CUT -> "ACTION_CUT"
        NodeCompat.ACTION_COPY -> "ACTION_COPY"
        NodeCompat.ACTION_PASTE -> "ACTION_PASTE"
        NodeCompat.ACTION_SET_SELECTION -> "ACTION_SET_SELECTION"
        NodeCompat.ACTION_EXPAND -> "ACTION_EXPAND"
        NodeCompat.ACTION_COLLAPSE -> "ACTION_COLLAPSE"
        NodeCompat.ACTION_SET_TEXT -> "ACTION_SET_TEXT"
        NodeCompat.ACTION_DISMISS -> "ACTION_DISMISS"
        Action.ACTION_CONTEXT_CLICK.id -> "ACTION_CONTEXT_CLICK"
        Action.ACTION_DRAG_CANCEL.id -> "ACTION_DRAG_CANCEL"
        Action.ACTION_DRAG_START.id -> "ACTION_DRAG_START"
        Action.ACTION_DRAG_DROP.id -> "ACTION_DRAG_DROP"
        Action.ACTION_HIDE_TOOLTIP.id -> "ACTION_HIDE_TOOLTIP"
        Action.ACTION_IME_ENTER.id -> "ACTION_IME_ENTER"
        Action.ACTION_MOVE_WINDOW.id -> "ACTION_MOVE_WINDOW"
        Action.ACTION_PAGE_DOWN.id -> "ACTION_PAGE_DOWN"
        Action.ACTION_PAGE_LEFT.id -> "ACTION_PAGE_LEFT"
        Action.ACTION_PAGE_RIGHT.id -> "ACTION_PAGE_RIGHT"
        Action.ACTION_PAGE_UP.id -> "ACTION_PAGE_UP"
        Action.ACTION_PRESS_AND_HOLD.id -> "ACTION_PRESS_AND_HOLD"
        Action.ACTION_SCROLL_DOWN.id -> "ACTION_SCROLL_DOWN"
//        Action.ACTION_SCROLL_IN_DIRECTION.id -> "ACTION_SCROLL_IN_DIRECTION"
        Action.ACTION_SCROLL_LEFT.id -> "ACTION_SCROLL_LEFT"
        Action.ACTION_SCROLL_RIGHT.id -> "ACTION_SCROLL_RIGHT"
        Action.ACTION_SCROLL_TO_POSITION.id -> "ACTION_SCROLL_TO_POSITION"
        Action.ACTION_SCROLL_UP.id -> "ACTION_SCROLL_UP"
        Action.ACTION_SET_PROGRESS.id -> "ACTION_SET_PROGRESS"
        Action.ACTION_SHOW_ON_SCREEN.id -> "ACTION_SHOW_ON_SCREEN"
        Action.ACTION_SHOW_TOOLTIP.id -> "ACTION_SHOW_TOOLTIP"
        else -> "ACTION_UNKNOWN"
    }
}
