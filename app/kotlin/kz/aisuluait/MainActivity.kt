
package kz.aisuluait

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.app.Activity
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo

class MainActivity : Activity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        // Создание списка с 1000 элементами
        val items = List(1000) { "Item ${it + 1}" }

        // Настройка пользовательского адаптера для ListView
        val adapter = CustomAdapter(this, R.layout.list_item, items)
        listView.adapter = adapter
    }

    private class CustomAdapter(context: Context, layout: Int,items: List<String>) :
        ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Получаем представление текущего элемента списка
            val view = super.getView(position, convertView, parent) as TextView

            // Установка текстового содержимого
            view.text = getItem(position)

            // Установка параметров доступности
            view.accessibilityDelegate = object : View.AccessibilityDelegate() {
                override fun onInitializeAccessibilityNodeInfo(v: View, info: AccessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(v, info)
                    info.setFocusable(false);
                    info.setClickable(false);
                    info.setClassName("com.aisuluaiva.ui.CollectionItem");
                    info.setLongClickable(false);
                    info.addAction(AccessibilityNodeInfo.AccessibilityAction(16, "https://github.com"));
                }
            }

            return view
        }
    }
}
