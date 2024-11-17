package kz.aisuluait.compositor;
import android.content.Context;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.Event;
import kz.altairait.getRole;
import kz.aisuluait.a11yevents.NodeCompat;

// Содержит свойства узла в доступном для пользователя виде
class NodeProperties(private val node: NodeCompat,
private val event: Event?,
private val cxt: Context, 
private val isChild: Boolean = false, 
private val userLabel: String? = null,
private val childIndex: Int = 0) {
private val className = node.className ?: "";
// Открывает ли узел всплывающее окно, popup
val opensPopUpWindow = if (node.canOpenPopup()) cxt.getString(R.string.opens_pop_up_window) else "";
//  Состояния свернуто и развернуто
val collapseState = getCollapseState(node);
val collapse = if (collapseState is Int) cxt.getString(collapseState) else "";
// Этот узел является меткой для текущего узла (node)
val labeledBy = node.labeledBy;
// Метка элемента
val label = if (labeledBy != null && !node.equals(labeledBy)) {
NodeProperties(labeledBy, null, cxt).contentDescription;
} else { " " }
// Это для тех кто заполнил event.text и event.contentDescription но в узле нету этого
val t = event?.text?.getOrNull(childIndex) ?: "";
val c = event?.contentDescription ?: "";
val contentDescription: CharSequence = (if (label.isNotBlank()) {
label;
} else if (node.contentDescription?.isNotBlank() == true) {
node.contentDescription;
} else if (node.text?.isNotBlank() == true) {
node.text;
} else if (t.isNotBlank()) {
t;
} else if (c.isNotBlank()) {
c;
} else {
node.tooltipText ?: "";
}).toString();
// Обрезаем текст
val textContent = if (!contentDescription.isEmpty()) {
"${contentDescription.trim()}\n";
} else { "" }
// Определяем роль элемента
private val getRole = getRole(node, cxt);
val roleDescription = if ((node.childCount > 0
&& node.roleDescription != null)
|| !isChild) {
"\n${if (node.roleDescription?.isEmpty() == false) node.roleDescription else getRole}\n";
} else {
"";
}
private val isCheckable = node.isCheckable;
// Проверяем состояние элемента
// Оно не нужно для дочерних узлов
val isEnabled = if (!node.isEnabled
&& !isChild) {
"\n${cxt.getString(R.string.state_disable)}";
} else {
"";
}
// Состояния switch, radiobutton, checkbox и т.п.
private val state = node.stateDescription?.trim()
val stateDescription = if (state?.isEmpty() == false) {
"${node.stateDescription}.";
} else {
// Ручное определение
if (className == "android.widget.Switch") {
if (node.isChecked) {
cxt.getString(R.string.state_enabled);
} else {
cxt.getString(R.string.state_off);
}
} else if (className == "android.widget.CheckBox") {
if (node.isChecked) {
cxt.getString(R.string.state_checked);
} else {
cxt.getString(R.string.state_not_checked);
}
} else if (className == "android.widget.RadioButton"
|| className == "android.widget.CompoundButton") {
if (node.isChecked) {
cxt.getString(R.string.state_selected);
} else {
cxt.getString(R.string.state_not_selected);
}

} else if (isCheckable) {
if (node.isChecked) {
cxt.getString(R.string.state_checked);
} else {
cxt.getString(R.string.state_not_checked);
}
} else {
"";
}
}
// Для вкладок, текущих страниц и т.п.
val isSelected = if ((node.isSelected
&& stateDescription == "")
&& !isChild) {
"${cxt.getString(R.string.state_selected)}.";
} else {
"";
}
// Для полей ввода

val hintText = if (node.hintText != null
&& !node.isShowingHintText) {
"\n${node.hintText}";
} else {
"";
}
val rowTitle = (node.collectionItemInfo?.rowTitle ?: "").toString();
val columnTitle = (node.collectionItemInfo?.columnTitle ?: "").toString();
// Сборщик текста
fun getText(): String {
val textToReturn = ("$collapse\n $stateDescription $isSelected ${userLabel ?: textContent} $hintText $isEnabled $opensPopUpWindow\n $roleDescription \n$rowTitle\n $columnTitle").trim();
if (!textToReturn.isEmpty()) {
return "$textToReturn\n";
}
return "";
}
}
