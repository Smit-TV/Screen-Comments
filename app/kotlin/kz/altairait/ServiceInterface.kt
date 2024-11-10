package kz.altairait;
import kz.aisuluait.a11yevents.Window;
import kz.aisuluait.a11yevents.Node;
// Метод связи с сервисом
interface ServiceInterface {
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
}
