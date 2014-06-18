package org.plutext.docx;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.docx4j.XmlUtils;
import org.docx4j.convert.out.html.HtmlExporterNonXSLT;
import org.docx4j.model.images.ConversionImageHandler;
import org.docx4j.openpackaging.io.LoadFromZipNG;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import java.io.File;

/**
 * Created by temp on 14-6-17.
 */
public class DocxToHtml{
    private static String TAG="DocxToHtml";

    public DocxToHtml(){
        Log.d(TAG,"new DocxToHtml() .");
    }

    public String test(String arg){
        if(arg!=null)
            return arg.toLowerCase();
        else
            return "arg NULL.";
    }

    public String convert(Activity act,Context context,String path){
        String baseURL="";
        final long startTime = System.currentTimeMillis();
        final long endTime;
        String html = "";
        try {
            final LoadFromZipNG loader = new LoadFromZipNG();
            logd("new LoadFromZipNG  ok -----1");
            WordprocessingMLPackage wordMLPackage;
            wordMLPackage = (WordprocessingMLPackage) loader.get(new File(path));
            logd("get  WordprocessingMLPackage ok --------2");
            String IMAGE_DIR_NAME = "images";

            baseURL = context.getDir(IMAGE_DIR_NAME, Context.MODE_WORLD_READABLE).toURL().toString();
            logd("baseURL=" + baseURL+" ------- 3");

            ConversionImageHandler conversionImageHandler = new AndroidFileConversionImageHandler(IMAGE_DIR_NAME, // <-- don't use a path separator here
                    baseURL, false, act);
            logd("new COnversionImageHandler()  ok --------4");
            HtmlExporterNonXSLT withoutXSLT = new HtmlExporterNonXSLT(wordMLPackage, conversionImageHandler);
            logd("new HtmlExporterNonXSLT() ok -------5");
            html = XmlUtils.w3CDomNodeToString(withoutXSLT.export());
            logd("success! --------- 6");
        } catch (Exception e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        final long duration = endTime - startTime;
        logd("Total time: " + duration + "ms");
        return html;
    }

    private void logd(String s){
        Log.d(TAG,""+s);
    }


}
