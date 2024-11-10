package kz.altairait;
import java.io.File;
import org.json.JSONObject;
fun getConfig(path: String = "/storage/emulated/0/reader.json"): String {
val file = File(path);
if (file.exists()
&& !file.isDirectory) {
return file.readText();
}
return """{"app-name": "reader"}""";
}
fun getParam(json: String, param: String): String {
val jsonObject = JSONObject(json);
return jsonObject.optString(param) ?: "";
}
