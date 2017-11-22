package com.shishiTec.HiMaster.Utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.shishiTec.HiMaster.Model.bean.EmojiBean;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pursue on 16/4/28.
 */
public class FileUtil {

    /**
     * 读取 assets 下的表情
     */
    public static List<List<EmojiBean>> getAssetsFaceList() {
        if(BaseApplication.emojiBeans != null){
            return BaseApplication.emojiBeans;
        }
        AssetManager assetsManager = BaseApplication.getInstance().getAssets();
        String[] faces = null;
        List<List<EmojiBean>> emojiBeans = new ArrayList<List<EmojiBean>>();
        try {
            faces = assetsManager.list("face");

            Properties pro = new Properties();
            InputStream is = assetsManager.open("emoji.properties");
            pro.load(is);
            int count = 21;
            List<EmojiBean> list = null;
            int pageCount =  faces.length / count;
            if( faces.length % count !=0)pageCount++;
            for (int i = 0; i < pageCount; i++) {
                list = new ArrayList<EmojiBean>();
                for (int j = i * count; j < (count * i + count) && j<faces.length ;j++) {
                    String faceText = pro.getProperty(faces[j]);
                    list.add(new EmojiBean(faces[j], faces[j], faceText));
                }
                emojiBeans.add(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return emojiBeans;
        }
    }

    /**
     * 从Assets中读取图片
     *
     * @param text 文件名或表情字符
     * @return
     */
    public static Bitmap getImageFromAssetsFile(String text, boolean isFileName) {
        Bitmap image = null;
        AssetManager am = BaseApplication.getInstance().getAssets();
        try {
            String fileName = text;
            if(!isFileName){
                fileName = getKey2Value(text);
            }
            InputStream is = am.open("face/" + fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return image;
        }
    }

    //替换表情字符
    public static SpannableString replaceFace(String content){
        SpannableString spannableString = new SpannableString(content);
        Pattern pattern = Pattern.compile("\\[([^\\[\\]]+)\\]");
//        Pattern pattern = Pattern.compile("/s[0-9]{3,}");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            Bitmap bitmap = FileUtil.getImageFromAssetsFile(matcher.group(),false);
            if(null == bitmap){
                break;
            }
            int sp2px = DimenUtils.sp2px(BaseApplication.getInstance(), 20);
            bitmap = Bitmap.createScaledBitmap(bitmap, sp2px, sp2px, true);
            ImageSpan imageSpan = new ImageSpan(BaseApplication.getInstance(), bitmap);

            spannableString.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    private static String getKey2Value(String value){
        AssetManager assetsManager = BaseApplication.getInstance().getAssets();
        try {
            Properties pro = new Properties();
            InputStream is = assetsManager.open("emoji.properties");
            pro.load(is);
            Enumeration<String> enumeration = (Enumeration<String>) pro.propertyNames();

            while (enumeration.hasMoreElements()){
                String key = enumeration.nextElement();
                String property = pro.getProperty(key);
                if(property.equals(value)){
                    return key;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
