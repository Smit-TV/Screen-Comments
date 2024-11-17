package kz.aisuluait.log;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.Action;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.a11yevents.CollectionInfo;
import kz.aisuluait.a11yevents.CollectionItemInfo;
import kz.aisuluait.a11yevents.RangeInfo;
import kz.aisuluait.a11yevents.TouchDelegateInfo;
import kz.aisuluait.a11yevents.Global;
class LogUtils {
companion object {
val cxt = Global.cxt;
// Получите строчку для логов

fun getStringLog(node: Node): String {
val nodeInfo = NodeCompat.wrap(node);
val log = 
"labeledBy: ${if (node.labeledBy != null) true else false}; labelFor: ${if (node.labelFor != null) true else false}; paneTitle: ${node.paneTitle}; viewIdResourceName: ${node.viewIdResourceName}; tooltipText: ${node.tooltipText}; containerTitle: ${node.containerTitle}; error: ${node.error}; text: ${node.text}; hintText: ${node.hintText}; contentDescription: ${node.contentDescription}; isScrollable: ${node.isScrollable}; isDismissable: ${node.isDismissable}; isTextEntryKey: ${node.isTextEntryKey}; isHeading: ${node.isHeading}; isContentinvalid: ${node.isContentInvalid}; isCheckable: ${node.isCheckable}; isClickable: ${node.isClickable}; isLongclickable: ${node.isLongClickable}; isContextClickable: ${node.isContextClickable}; isFocusable: ${node.isFocusable}; isFocused: ${node.isFocused}; isA11y focused: ${node.isAccessibilityFocused}; isEditable: ${node.isEditable}; isVisibleToUser: ${node.isVisibleToUser}; isImportantForAccessibility: ${node.isImportantForAccessibility}; isScreenReaderFocusable: ${node.isScreenReaderFocusable}; className: ${node.className}; roleDescription: ${nodeInfo.roleDescription}; stateDescription: ${node.stateDescription}; touchDelegateInfo: ${if (node.touchDelegateInfo != null) true else false}; childCount: ${node.childCount}; extraRenderingInfo: ${if(node.extraRenderingInfo != null) true else false}; availableExtraData: ${node.availableExtraData}; inputType: ${node.inputType}; canOpenPopup: ${node.canOpenPopup()}; liveRegion: ${node.liveRegion}; traversalBefore: ${if (node.getTraversalBefore() != null) true else false}; traversalAfter: ${if (node.getTraversalAfter() != null) true else false}; hasRequestInitialAccessibilityFocus: ${node.hasRequestInitialAccessibilityFocus()}; isShowingHintText: ${node.isShowingHintText}; isMultiLine: ${node.isMultiLine}; isTextSelectable: ${node.isTextSelectable}; collection: ${if (node.collectionInfo != null) true else false}; item: ${node.collectionItemInfo ?: run { "none" }}; selected: ${node.isSelected}; checked: ${node.isChecked}; rowIndex: ${node.collectionItemInfo?.rowIndex ?: ""}; columnIndex: ${node.collectionItemInfo?.columnIndex ?: ""}; columnCount: ${node.collectionInfo?.columnCount ?: ""}; rowCount: ${node.collectionInfo?.rowCount ?: ""}; extras: ${node.extras}; minDurationBetweenContentChangesMillis: ${nodeInfo.minDurationBetweenContentChangesMillis}; actions: ${nodeInfo.actionList?.size ?: 0}";
return log;
}
// Получите список действий
/**
* Значения intent
* all - Все действия например: ACTION_CLICK это нажатие 
* special - Действия определенные разработчиками (В формате "Метка")
* ids - Получите список id
* default - Стандартный формат возврата (В формате "ярлык название действия: id")
*/
// getIds - Получите список id для указанного intent
fun getActions(node: Node, intent: String = "default", getIds: Boolean = false): String {
val actions = node.actionList ?: mutableListOf<ActionInfo>();
var result = "";
for (action in actions) {
val id = action.id;
val userLabel = action.label;
var label = "";
if (intent == "ids") {
label = "$id";
} else if (intent == "default") {
label = "$userLabel ${getActionNameById(id)}: $id";
} else if (intent == "special") {
if (!getIds) {
label = "${userLabel ?: ""}";
} else {
label = if (userLabel != null) {
"$id";
} else { ""; }
}
} else if (intent == "all") {
if (!getIds) {
label = "${userLabel ?: getActionLabelAliasById(id)}";
} else {
label = "$id";
}
} else {
throw IllegalArgumentException("Указано неверное значение intent в LogUtils#getActions. Указано: getActions(node, $intent, $getIds");
}
result = if (result != "") "$result;$label" else "$label";
}
return result;
}
// Часть кода взята у SpeakTouch.
// Получите название действия по id
fun getActionNameById(id: Int): String {
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
// Получите псевдоним для действий без меток
fun getActionLabelAliasById(id: Int): String {
    return when (id) {
        NodeCompat.ACTION_FOCUS -> cxt.getString(R.string.action_focus)
        NodeCompat.ACTION_CLEAR_FOCUS -> cxt.getString(R.string.action_clear_focus)
        NodeCompat.ACTION_SELECT -> cxt.getString(R.string.action_select)
        NodeCompat.ACTION_CLEAR_SELECTION -> cxt.getString(R.string.action_clear_selection)
        NodeCompat.ACTION_CLICK -> cxt.getString(R.string.action_click)
        NodeCompat.ACTION_LONG_CLICK -> cxt.getString(R.string.action_long_click)
        NodeCompat.ACTION_ACCESSIBILITY_FOCUS -> cxt.getString(R.string.action_accessibility_focus)
        NodeCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS -> cxt.getString(R.string.action_clear_accessibility_focus)
        NodeCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY -> cxt.getString(R.string.action_next_at_movement_granularity)
        NodeCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY -> cxt.getString(R.string.action_previous_at_movement_granularity)
        NodeCompat.ACTION_NEXT_HTML_ELEMENT -> cxt.getString(R.string.action_next_html_element)
        NodeCompat.ACTION_PREVIOUS_HTML_ELEMENT -> cxt.getString(R.string.action_previous_html_element)
        NodeCompat.ACTION_SCROLL_FORWARD -> cxt.getString(R.string.action_scroll_forward)
        NodeCompat.ACTION_SCROLL_BACKWARD -> cxt.getString(R.string.action_scroll_backward)
        NodeCompat.ACTION_CUT -> cxt.getString(R.string.action_cut)
        NodeCompat.ACTION_COPY -> cxt.getString(R.string.action_copy)
        NodeCompat.ACTION_PASTE -> cxt.getString(R.string.action_paste)
        NodeCompat.ACTION_SET_SELECTION -> cxt.getString(R.string.action_set_selection)
        NodeCompat.ACTION_EXPAND -> cxt.getString(R.string.action_expand)
        NodeCompat.ACTION_COLLAPSE -> cxt.getString(R.string.action_collapse)
        NodeCompat.ACTION_SET_TEXT -> cxt.getString(R.string.action_set_text)
        NodeCompat.ACTION_DISMISS -> cxt.getString(R.string.action_dismiss)
        Action.ACTION_CONTEXT_CLICK.id -> cxt.getString(R.string.action_context_click)
        Action.ACTION_DRAG_CANCEL.id -> cxt.getString(R.string.action_drag_cancel)
        Action.ACTION_DRAG_START.id -> cxt.getString(R.string.action_drag_start)
        Action.ACTION_DRAG_DROP.id -> cxt.getString(R.string.action_drag_drop)
        Action.ACTION_HIDE_TOOLTIP.id -> cxt.getString(R.string.action_hide_tooltip)
        Action.ACTION_IME_ENTER.id -> cxt.getString(R.string.action_ime_enter)
        Action.ACTION_MOVE_WINDOW.id -> cxt.getString(R.string.action_move_window)
        Action.ACTION_PAGE_DOWN.id -> cxt.getString(R.string.action_page_down)
        Action.ACTION_PAGE_LEFT.id -> cxt.getString(R.string.action_page_left)
        Action.ACTION_PAGE_RIGHT.id -> cxt.getString(R.string.action_page_right)
        Action.ACTION_PAGE_UP.id -> cxt.getString(R.string.action_page_up)
        Action.ACTION_PRESS_AND_HOLD.id -> cxt.getString(R.string.action_press_and_hold)
        Action.ACTION_SCROLL_DOWN.id -> cxt.getString(R.string.action_scroll_down)
        // Action.ACTION_SCROLL_IN_DIRECTION.id -> cxt.getString(R.string.action_scroll_in_direction)
        Action.ACTION_SCROLL_LEFT.id -> cxt.getString(R.string.action_scroll_left)
        Action.ACTION_SCROLL_RIGHT.id -> cxt.getString(R.string.action_scroll_right)
        Action.ACTION_SCROLL_TO_POSITION.id -> cxt.getString(R.string.action_scroll_to_position)
        Action.ACTION_SCROLL_UP.id -> cxt.getString(R.string.action_scroll_up)
        Action.ACTION_SET_PROGRESS.id -> cxt.getString(R.string.action_set_progress)
        Action.ACTION_SHOW_ON_SCREEN.id -> cxt.getString(R.string.action_show_on_screen)
        Action.ACTION_SHOW_TOOLTIP.id -> cxt.getString(R.string.action_show_tooltip)
        else -> cxt.getString(R.string.action_unknown)
    }
}
// Получите дочерние узлы в формате "метка пакет.класс"
fun getChilds(node: Node): String {
var childs = "";
for (childId in 0 until node.childCount) {
val child = node.getChild(childId) ?: return childs;
val text = child.text;
val conDes = child.contentDescription;
val result = if (conDes?.isNotBlank() == true) {
conDes;
} else {
text;
}
childs = if (childs != "") "$childs;$result ${child.className}" else "$result ${child.className}";
}
return childs;
}
}
}
