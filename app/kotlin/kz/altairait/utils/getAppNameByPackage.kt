package kz.altairait.utils;
import android.content.Context;
import android.util.Log;
import android.content.pm.PackageManager;
fun getAppNameByPackage(cxt: Context, packageName: String): String? {
return try {
val packageManager = cxt.packageManager;
val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
packageManager.getApplicationLabel(info).toString();
}
catch (e: PackageManager.NameNotFoundException) {
Log.e("scrcwarn", "Не удалось получить информацию из функции getPackageName.");
null;
}
}
