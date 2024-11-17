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
private lateinit var view: LinearLayout;
// Окно действий в формате "Метка действия"
/**
 * Значения intent
* special - Список действий определенных разработчиками
* all - Все действия даже без меток
*/

fun actionsMenu(node: Node? = Global.node, intent: String = "special") {
node ?: return;
val cxt = Global.cxt;
try {
val menu = Global.layoutInflater.inflate(R.layout.app_menu, null);
val listItems = menu.findViewById<ListView>(R.id.list);
val result = LogUtils.getActions(node, intent);
val log = "$result";
val items = log.split(";");
val adapter = ArrayAdapter(cxt, R.layout.list_item, items);
listItems.adapter = adapter;


val view = LinearLayout(cxt);
view.apply {
layoutParams = LinearLayout.LayoutParams(
LinearLayout.LayoutParams.MATCH_PARENT,
LinearLayout.LayoutParams.MATCH_PARENT);
gravity = Gravity.CENTER;
alpha = 0.25f;
addView(menu);
}
val paneTitle = view.findViewById<TextView>(R.id.pane_title);
paneTitle.text = cxt.getString(if (intent == "all") R.string.all_actions else R.string.special_actions);
// Это нужно скрыть
val cancelBtn = view.findViewById<Button>(R.id.cancel);
cancelBtn.visibility = View.GONE;
val closeBtn = view.findViewById<Button>(R.id.close);
closeBtn.apply {
layoutParams = LinearLayout.LayoutParams(
LinearLayout.LayoutParams.MATCH_PARENT,
LinearLayout.LayoutParams.WRAP_CONTENT);
}
closeBtn.setOnClickListener {
hideView(view); 
}

listItems.setOnItemClickListener {
parent, viewInstance, position, id ->
if (viewInstance is TextView) {
val view = (viewInstance as TextView);
val text = view.text;
closeBtn.performClick();
val actionSplit = text.split(": ");
val action = actionSplit.last().toInt();
if (action != null) {
val result = node.performAction(action.toInt());
Toast.makeText(Global.windowContext, "Execute status: $result", Toast.LENGTH_SHORT).show();
}
}
}
setViewOnScreen(view);
}
catch (e: Exception){
Log.d("scrcerr", "$e call from actionsMenu");
}
}
