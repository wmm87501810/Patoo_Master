package com.shishiTec.HiMaster.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.*;

/**
 * 图片处理的工具类
 *
 * @author Lih
 */
public class PictureManageUtil {

    /**
     * 设置图片大小
     *
     * @param bm        ：原图
     * @param newWidth  ：最终希望得到的宽
     * @param newHeight ：最终希望得到的高
     */
    public static Bitmap resizeBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float scale = (scaleWidth <= scaleHeight) ? scaleWidth : scaleHeight;
        matrix.postScale(scale, scale);
        // 设置大小
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
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

    /**
     * 等比绽放
     *
     * @param image
     * @return
     */
    public static Bitmap geometricZoomBitmap(Bitmap image, int newWidth) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f

        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > newWidth) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / newWidth);
        } else if (w < h && h > newWidth) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / newWidth);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;
    }

    /**
     * 带旋转的，缩放图片，会按照 宽高缩放比 较小的值进行缩放
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @param rotate    旋转参数
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bm, int newWidth, int newHeight,
                                      int rotate) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float scale = (scaleWidth <= scaleHeight) ? scaleWidth : scaleHeight;
        matrix.postScale(scale, scale);
        matrix.postRotate(rotate);
        // 设置大小
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 获取旋转参数
     *
     * @param imagePath
     * @return
     */
    public static int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            // Log.v(TAG, "Exif orientation: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    /**
     * 将图片按照宽高中比较小的裁剪成正方形
     *
     * @param bm
     * @return
     */
    public static Bitmap cropBitmap(Bitmap bm) {
        int height = bm.getHeight();
        int width = bm.getWidth();
        if (height > width) {
            return Bitmap.createBitmap(bm, 0, (height - width) / 4, width,
                    width);
        } else {
            return Bitmap.createBitmap(bm, (width - height) / 2, 0, height,
                    height);
        }
    }


    /**
     * 将指定路径的图片根据指定宽度缩放(支持大图片)
     *
     * @param filePath
     * @param width
     * @return
     */
    public static Bitmap getMicroImage(String filePath, int width) {
        // 获取文件输入流
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            File f = new File(filePath);
            is = new FileInputStream(f);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            // 获取调整后的options
            BitmapFactory.decodeFile(filePath, options);
            int height = options.outHeight * width / options.outWidth;
            if (is != null) {
                int isSize = is.available();
                // 大于1M=1048576字节，则视为大图
                int base = 309600;
                if (isSize > base) {
                    options.inSampleSize = 10; // width，hight设为原来的十分一
                } else if (isSize <= 409600 && isSize > 104800) {
                    options.inSampleSize = 4;
                } else if (isSize <= 104800 && isSize > 60) {
                    options.inSampleSize = 2;
                } else {
                    options.inSampleSize = 1;
                }
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            int rotate = PictureManageUtil.getCameraPhotoOrientation(filePath);
            bitmap = PictureManageUtil.resizeBitmap(bitmap, width, height,
                    rotate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 根据路径，不失真压缩
     *
     * @param filePath
     * @return
     */
    public static Bitmap getCompressBm(String filePath) {
        Bitmap bm = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 450, 350);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(filePath, options);
        return bm;
    }

    /**
     * 计算压缩比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param rotate
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

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
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
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

    public static Bitmap drawable2Bitmap(Drawable drawable){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        if(null == bd ){
            return null;
        }
        return bd.getBitmap();
    }

}
