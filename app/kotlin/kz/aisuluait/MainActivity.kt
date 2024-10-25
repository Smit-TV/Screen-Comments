package kz.aisuluait;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
class MainActivity : Activity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
/*val webView = findViewById<WebView>(R.id.webview);
webView.settings.javaScriptEnabled = true;
webView.webViewClient = WebViewClient();
webView.loadUrl("file:///android_res/raw/index.html");*/
}
}
