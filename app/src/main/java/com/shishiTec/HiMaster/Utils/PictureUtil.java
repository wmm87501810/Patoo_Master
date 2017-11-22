package com.shishiTec.HiMaster.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pursue on 16/4/28.
 */
public class PictureUtil {
    /**
     * @param imgPath
     * @param bitmap  //     * @param imgFormat 图片格式
     * @return
     */
    public static String imgToBase64(String imgPath, Bitmap bitmap) {
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        if (bitmap == null) {
            //bitmap not found!!
        }else{
            if (bitmap.getWidth() > 640) {
                bitmap = resizeBitmap(bitmap, 640);
            }
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("data:image/png;base64,");
            stringBuilder.append(Base64.encodeToString(imgBytes, Base64.DEFAULT));
            return stringBuilder.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }

    /**
     * 设置图片大小
     *
     * @param bm       ：原图
     * @param newWidth ：最终希望得到的宽
     */
    public static Bitmap resizeBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float scale = (scaleWidth <= scaleWidth) ? scaleWidth : scaleWidth;
        matrix.postScale(scale, scale);
        // 设置大小
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

}
