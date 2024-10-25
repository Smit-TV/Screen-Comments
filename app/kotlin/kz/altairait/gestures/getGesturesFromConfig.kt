package kz.altairait.gestures;
import kz.altairait.getParam;
fun getGesturesFromConfig(config: String?): HashMap<String, String> {
val params = HashMap<String, String>();
val gestures = arrayOf("left", "right", "top", "bottom",
"top-bottom", "top-right", "top-left",
"bottom-right", "bottom-left", "bottom-top",
"left-right", "left-bottom", "left-top",
"right-top", "right-left", "right-bottom"
);
for (gesture in gestures) {
if (config != null) {
params[gesture] = getParam(config, gesture);
} else {
params[gesture] = "DEFAULT";
}
}
return params;
}