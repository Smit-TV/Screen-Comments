package kz.altairait;
import kz.aisuluait.a11yevents.Window;
import kz.aisuluait.a11yevents.Node;
import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Bundle;
// Метод связи с сервисом
interface ServiceInterface{
// Ищет окно по id
fun findWindowById(id: Int): Window?;
// Получите корень из окна имеющего фокус
// 0 -> a11y
// 1 -> input
fun getFocusedWindow(type: Int = 0): Window?;
// Получите список окон
fun getWindowsList(): MutableList<Window>;
// Получите узел с фокусом доступности
fun getA11yFocusedNode(): Node?;
// Удалить узел из кэша
fun clearCachedSubtree(node: Node): Boolean;
// Поиск узла по типу фокуса
fun findFocus(focusType: Int): Node?;
// Выполнить глобальное действие AccessibilityService.GLOBAL_ACTION_*
fun performGlobalAction(action: Int): Boolean;
// Получите контекст окна
fun  createWindowContext(type: Int, options: Bundle?): Context;
                
}
