package kz.altairait.utils;
import android.os.Build;
import kz.aisuluait.a11yevents.Node;
fun getUniqueId(node: Node): String {
return "${node.text}${node.childCount}${if (Build.VERSION.SDK_INT >= 33) node.uniqueId else null}${node.viewIdResourceName}${node.className}${node.packageName}";
}
