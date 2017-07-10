package com.pub.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



/**
 * Created by Leader on 2017/5/22.
 * 为图片添加水印
 *
 */

public class WaterMarkUtils {

    /**
     * 水印格式:
     * <p>
     * 如一个2*2倍水印文件大小的图片，A1和B2添加水印文件
     * ----------------------
     * |       A      B      |
     * |   1  水印            |
     * |   2         水印     |
     * ----------------------
     *
     * @param src
     * @param watermark
     * @return
     */
    public static String createBitmap(Bitmap src, Bitmap watermark, String fileName) {
        Bitmap newb = null;//创建一个保存水印的位图
        if (src == null) {
            return null;
        }

        int w = src.getWidth();//原图片的宽
        int h = src.getHeight();//原图片的高
        Bitmap newMark = zoomImg(watermark, w * 2 / 3, watermark.getHeight() * (w * 2 / 3) / watermark.getWidth());
        int ww = newMark.getWidth();//水印图片的宽
        int wh = newMark.getHeight();//水印图片的高
        Log.v("wz", w + "," + h + "," + ww + "," + wh);//日志文件中查看位图大小
        newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//创建一个新的和src一样大小的位图
        Canvas cv = new Canvas(newb);//创建一个同等 大小的画布

        cv.drawBitmap(src, 0, 0, null);//从坐标0,0开始把src画入画布
        //循环在src中画入水印
        for (int i = 0; i < (w + ww) / ww; i++) {
            for (int j = 0; j < (h + wh) / wh; j++) {
                if (i % 2 == j % 2) {
                    cv.drawBitmap(newMark, i * ww, j * wh, null);
                    Log.d("nwz", i * ww + "--" + j * wh);
                }
            }
        }

        cv.save();//保存
        cv.restore();//存储

        //转file保存到Pictures中，返回有水印的url
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        //图片压缩比，100为原图
        int quality = 100;
        OutputStream stream = null;
        //公共图片路径
        File filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String newFilePath = filePath.getPath() + "/" + fileName;
        try {
            stream = new FileOutputStream(newFilePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        newb.compress(format, quality, stream);
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newb.recycle();
        newMark.recycle();
        return newFilePath;//返回带水印的图片url
    }

    /**
     * 缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
