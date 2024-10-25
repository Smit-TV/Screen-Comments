package kz.aisuluait.functions;
import java.util.Calendar;
import java.text.SimpleDateFormat;
fun getDate(): String {
val calendar = Calendar.getInstance();
val timeFormat = SimpleDateFormat("HH:mm");
val time = timeFormat.format(calendar.time);
val dateFormat = SimpleDateFormat("dd, MMM yyy");
val date = dateFormat.format(calendar.time);
return "$time $date";
}
