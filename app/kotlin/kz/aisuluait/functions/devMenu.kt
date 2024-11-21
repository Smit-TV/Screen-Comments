package kz.aisuluait.functions;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.util.Log;
import kz.aisuluait.R;
import kz.aisuluait.log.LogUtils;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
private var views = mutableListOf<LinearLayout>();
// Меню разработчика
/**
* Значения intent
* info - Посмотреть свойства узла
* refresh - Посмотреть свежие данные узла
* actions - Посмотреть доступные действия в формате "Метка константа действия: id действия"
* childs - Посмотреть дочерние узлы в формате "Метка пакет.класс"
* parent - Получите родительский узел
*/

fun devMenu(node: Node? = Global.nodeForDev, intent: String = "info") {
node ?: return;
val cxt = Global.cxt;
try {
val menu = Global.layoutInflater.inflate(R.layout.app_menu, null);
val listItems = menu.findViewById<ListView>(R.id.list);
val result = if (intent == "info") {
LogUtils.getStringLog(node);
} else if (intent == "actions") {
LogUtils.getActions(node);
} else if (intent == "refresh") {
" refresh: ${node.refresh()};${LogUtils.getStringLog(node)}";
} else if (intent == "childs") {
LogUtils.getChilds(node);
} else {
"";
}
val topBar = if (intent == "info" || intent == "refresh") {
"${if (node.parent != null) "parent" else "isRootView: true"}${if (intent == "refresh") "" else "; refresh"};";
} else { ""; }
val log = "$topBar$result";
val items = log.split(";");
val adapter = ArrayAdapter(cxt, R.layout.list_item, items);
listItems.adapter = adapter;


val view = LinearLayout(cxt);
views.add(view);
view.apply {
layoutParams = LinearLayout.LayoutParams(
LinearLayout.LayoutParams.MATCH_PARENT,
LinearLayout.LayoutParams.MATCH_PARENT);
gravity = Gravity.CENTER;
alpha = 0.25f;
addView(menu);
}
val paneTitle = view.findViewById<TextView>(R.id.pane_title);
paneTitle.text = cxt.getString(R.string.dev_menu);
val cancelBtn = view.findViewById<Button>(R.id.cancel);
cancelBtn.setOnClickListener {
hideView(view);
}
val closeBtn = view.findViewById<Button>(R.id.close);
closeBtn.setOnClickListener {
views.forEach { 
hideView(it); 
}
views.clear();
}

listItems.setOnItemClickListener {
parent, viewInstance, position, id ->
if (viewInstance is TextView) {
val view = (viewInstance as TextView);
val text = view.text;
if (intent == "refresh" || intent == "info") {
if (text == "parent") {
devMenu(node.parent);
}
if (text.indexOf(" actions:") == 0) {
devMenu(node, "actions");
}
if (text.indexOf(" refresh") == 0) {
devMenu(node, "refresh");
}
if (text.indexOf(" childCount:") == 0
&& text != " childCount: 0") {
devMenu(node, "childs");
}
} else if (intent == "childs") {
devMenu(node.getChild(position));
} else if (intent == "actions") {
closeBtn.performClick();
val actionSplit = text.split(": ");
val action = actionSplit.last().toInt();
if (action != null) {
val result = node.performAction(action.toInt());
Toast.makeText(Global.windowContext, "Execute status: $result", Toast.LENGTH_SHORT).show();
}
}
}
}
setViewOnScreen(view);
}
catch (e: Exception){
Log.d("scrcerr", "$e");
}
}
