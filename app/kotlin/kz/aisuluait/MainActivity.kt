package kz.aisuluait;
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.app.Activity

class MainActivity : Activity() {
    
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        // Создание списка с 1000 элементами
        val items = List(1000) { "Item ${it + 1}" }

        // Настройка адаптера для ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter
    }
}
