package kz.aisuluait.functions;
import android.os.Bundle;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
/** Читает текст узла
*  direction true ->  Node#ACTION_NEXT_AT_MOVEMENT_GRANULARITY
* direction false -> Node#ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY
* Значения type:
* Node#MOVEMENT_GRANULARITY_CHARACTER - Чтение по символам
* Node#MOVEMENT_GRANULARITY_WORD - По словам
* Node#MOVEMENT_GRANULARITY_LINE - По строкам
* Node#MOVEMENT_GRANULARITY_PARAGRAPH - По абзацам
* Node#MOVEMENT_GRANULARITY_PAGE - По страницам
*/

fun reading(direction: Boolean, type: Int, extendSelection: Boolean = false) {
val node = Global.node ?: return;
val args = Bundle().apply {
putInt(Node.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT, type);
/**
* Указывает нужно ли выделять текст в EditText при чтении
* Добавлено в API 35.
*/
putBoolean(Node.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, extendSelection);
}
val action = if (direction) {
Node.ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
} else {
Node.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
}
val result = node.performAction(action, args);
}

/** Абстрактные функции */
// Чтение по символам
// Следующий символ
fun nextSymbol() {
reading(true, Node.MOVEMENT_GRANULARITY_CHARACTER);
}
// Предыдущий символ
fun previousSymbol() {
reading(false, Node.MOVEMENT_GRANULARITY_CHARACTER);
}
// Чтение по словам
// Следующее
fun nextWord() {
reading(true, Node.MOVEMENT_GRANULARITY_WORD);
}
// Предыдущий
fun previousWord() {
reading(false, Node.MOVEMENT_GRANULARITY_WORD);
}
// Чтение по строкам
// Следующая
fun nextLine() {
reading(true, Node.MOVEMENT_GRANULARITY_LINE);
}
// Предыдущее
fun previousLine() {
reading(false, Node.MOVEMENT_GRANULARITY_LINE);
}
// Чтение по страницам
// Следующая
fun nextPage() {
reading(true, Node.MOVEMENT_GRANULARITY_PAGE);
}
// Предыдущая
fun previousPage() {
reading(false, Node.MOVEMENT_GRANULARITY_PAGE);
}
// Чтение по абзацам (параграфам)
// Следующий
fun nextParagraph() {
reading(true, Node.MOVEMENT_GRANULARITY_PARAGRAPH);
}
// Предыдущий
fun previousParagraph() {
reading(false, Node.MOVEMENT_GRANULARITY_PARAGRAPH);
}