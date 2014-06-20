package com.singuloid.docx2html;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import org.plutext.docx.DocxToHtml;
import org.plutext.docx.R;

public class DocxShow extends Activity {
    private final String TAG = "AndroidDocxToHtmlActivity";
    WebView webview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webview = (WebView) this.findViewById(R.id.webpage);
        webview.loadData("Loading docx file... ", "text/html", "UTF-8");
        final String path = getIntent().getStringExtra("dst");
        if (!TextUtils.isEmpty(path)) {
            setTitle(path);
        } else {
            Log.e(TAG, "path is null. use R.raw.sample  Resource!!");
            finish();
        }
        Log.d(TAG," onCreate()  file path = " + path);
        AsyncTask<String, Object, Object> at = new AsyncTask<String, Object, Object>() {
            String baseURL = "";

            @Override
            protected Object doInBackground(String... arg) {
                final String html = new DocxToHtml().convert(DocxShow.this, DocxShow.this, arg[0]);
                Log.d(TAG, "[html= ] " + html);
                return html;
            }

            @Override
            protected void onPostExecute(Object html) {
                webview.loadDataWithBaseURL(baseURL, (String) html, "text/html", null, null);
            }
        };
        if(!TextUtils.isEmpty(path))
            at.execute(path);
    }

}