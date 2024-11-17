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
import kz.aisuluait.feedback.play;
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
if (result.isEmpty()) {
Global.tts.speak(cxt.getString(R.string.empty));
val emptySound = Global.prefs.getString("empty_sound", null);
play(emptySound ?: R.raw.end);
return;
}
// true указывает что нужно получить только id
val ids = LogUtils.getActions(node, intent, true).split(";");
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
val action = ids[position].toInt();
closeBtn.performClick();
node.performAction(action);
}
setViewOnScreen(view);
}
catch (e: Exception){
Log.d("scrcerr", "$e call from actionsMenu");
}
}
