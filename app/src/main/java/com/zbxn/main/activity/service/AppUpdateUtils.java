package com.zbxn.main.activity.service;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.pub.utils.MyToast;
import com.zbxn.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

/**
 * Created by U on 2017/3/8.
 */

public class AppUpdateUtils {
    private static Context mContext;
    private static int mVersionCode;
    private static AppVersion mAppVersion;
    private static String mVersionName;
    private static String mFileName;
    private static ProgressDialog mProgressDialog;
    private static String mLocalFilepath;
    private static DownloadFileAsyncTask mDownloadFileAsyncTask;
    private static boolean hasCancel = false;
    public static final String TAG = "AppUpDate";
    // 如果是自动更新，那么就不跳出已经是最新版本的对话框。否则用户点击更新应用按钮，弹出已经是最新版本的提示框
    private static boolean mIsAuto = false;
    private static boolean mShowProgressDialog = true;
    private static final int NOTIFY_ID = 54;
    private static NotificationManager mNotificationManager;
    private static Notification mNotification;

    /**
     * 初始化
     *
     * @param context            执行上下文
     * @param newAv              对比版本
     * @param isauto             指示是否是自动升级，当版本号没变时，true无响应，false弹出已是最新版的对话框。
     * @param showProgressDialog true弹出下载进度对话框，false为通知消息提示进度
     */
    public static void init(Context context, AppVersion newAv, boolean isauto,
                            boolean showProgressDialog) {
        mIsAuto = isauto;
        mShowProgressDialog = showProgressDialog;
        mContext = context;
        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mVersionCode = getVerCode(mContext);
        mVersionName = getVerName(mContext);
        mAppVersion = newAv;
        mFileName = mAppVersion.getVersionName() + ".apk";
        File sdDir = null;

        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            // 获取根目录
            sdDir = Environment.getExternalStorageDirectory();
        }

        // 注意FileOutputStream不会自动创建路径，所以初始化的时候要主动创建路径。
        String dirpath;
        /*if (sdDir != null) {
            // AppName为你想保存的路径，一般为应用目录
            dirpath = sdDir.toString() + "/AppName/";
        } else {
            dirpath = "/AppName/";
        }*/

        dirpath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName();
//        dirpath = context.getFilesDir().getPath()+"/";
        File dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdir();// 如果路径不存在就先创建路径
        }
        mLocalFilepath = dirpath + "/" + mFileName;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;
    }

    /**
     * 更新应用
     */
    public static void upDate() {
//        mAppVersion.setDownloadUrl("http://www.tt8ss.com:8080/zbxbank_dev/zbxbank0.18.apk");
        if (mVersionCode < mAppVersion.getVersionCode()) {
            doNewVersionUpdate();
        } else {
            notNewVersionShow();
        }
    }

    /**
     * 不执行更新
     */
    private static void notNewVersionShow() {
        // 如果不是自动升级
        if (!mIsAuto) {
            MyToast.showToast("已是最新版本");
        }
    }

    /**
     * 更新应用提示
     */
    private static void doNewVersionUpdate() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("更新内容：\n");
        // 这里我们使用;作为分隔符，显示多条跟新内容信息。
        if (null != mAppVersion.getDescription() && mAppVersion.getDescription().contains(";")) {
            String[] up = mAppVersion.getDescription().split(";");
            for (String s : up) {
                sb.append(s).append("\n");
            }
        } else {
            sb.append(mAppVersion.getDescription()).append("\n");
        }
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();// 创建
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        // 显示对话框
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(R.layout.dialog_update);
        TextView tv_title = (TextView) window.findViewById(R.id.dialog_title);
//        Bitmap bitmap = fillet(ALL, BitmapFactory.decodeResource(BaseApp.getContext().getResources(), R.mipmap.bg_dialog_head), 55);
//        ImageView mImageView = (ImageView) window.findViewById(R.id.mImageview);
//        mImageView.setImageBitmap(bitmap);


        tv_title.setText("新版本" + mAppVersion.getVersionName());
        TextView tv_message = (TextView) window.findViewById(R.id.dialog_message);
        tv_message.setText(sb.toString());
        TextView bt_update = (TextView) window.findViewById(R.id.mBtnUpdate);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ZBXN的升级方式
                updateFile(mAppVersion.getDownloadUrl());
                dialog.dismiss();
            }
        });
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int windowWidth = dm.widthPixels;
        int windowHeight = dm.heightPixels;

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = windowWidth * 5 / 6;//定义宽度
        //lp.height = windowHeight * 1 / 3;//定义高度
        dialog.getWindow().setAttributes(lp);
    }

    // 执行应用更新
    private static void updateFile(final String path) {
        // pBar.show();

        File file = new File(mLocalFilepath);

        // 根据服务器传回的更新包sha1进行比对,判断是否下载完成，如果不匹配，则重新下载，否则直接打开。
//		if (mAppVersion.getSha1() != null) {
//			if (getFileSHA1(file).toUpperCase().equals(mAppVersion.getSha1())) {
//				openFile(file);
//			} else {
//				try {
//					// downloadFile(path, localFilepath);
//					mDownloadFileAsyncTask = new DownloadFileAsyncTask();
//					mDownloadFileAsyncTask.execute(path);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		} else {
        try {
            mDownloadFileAsyncTask = new DownloadFileAsyncTask();
            mDownloadFileAsyncTask.execute(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
//		}
    }

    /**
     * 取消更新
     */
    public void cancelUpDate() {
        if (mDownloadFileAsyncTask != null) {
            if (!mDownloadFileAsyncTask.isCancelled()) {
                mDownloadFileAsyncTask.cancel(true);
            }
        }
    }

    /**
     * 应用下载Task
     */
    private static class DownloadFileAsyncTask extends
            AsyncTask<String, Integer, String> {

        // 后台下载任务
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection connection;
                connection = url.openConnection();
                // 2.2以上默认用“gzip”，这里要取消使用，否则大小永远为-1.
                connection.setRequestProperty("Accept-Encoding", "identity");
                connection.connect();
                int length = connection.getContentLength();
                mProgressDialog.setMax(length);
                InputStream input = new BufferedInputStream(
                        connection.getInputStream());
                File file = new File(mLocalFilepath);
                FileOutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (!hasCancel) {
                        total += count;
//                        publishProgress((int) (total * 100 / length));
                        publishProgress((int) total);
                        output.write(data, 0, count);
                    } else {
                        cancel(true);
                        break;
                    }
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // 进度更新
        private int tempv;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 如果需要用到进度提示框，则执行
            if (mShowProgressDialog) {
                if (values[0] != tempv) {
                    mProgressDialog.setProgress(values[0]);
                    float all = (float) mProgressDialog.getMax() / 1024f / 1024f;
                    float percent = (float) values[0] / 1024f / 1024f;
                    mProgressDialog.setProgressNumberFormat(String.format("%.2fM/%.2fM", percent, all));
                }
            } else {
                // 防止多次重复提示，影响性能
                if (values[0] != tempv) {
                    RemoteViews contentView = mNotification.contentView;
                    contentView.setTextViewText(R.id.rate,
                            String.valueOf(values[0]) + "%");
                    contentView.setProgressBar(R.id.progress, 100, values[0],
                            false);
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    tempv = values[0];
                }
            }
        }

        // 下载完成后执行
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (mShowProgressDialog) {
                openFile(new File(mLocalFilepath));
            } else {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                // 设定intent的file与MimeType
                intent.setDataAndType(Uri.fromFile(new File(mLocalFilepath)),
                        "application/vnd.android.package-archive");
                mContext.startActivity(intent);

                // 下载完毕后变换通知形式
                /*mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotification.contentView = null;
				// Intent intent = new Intent(mContext, FileMgrActivity.class);
				// // 告知已完成
				// intent.putExtra("completed", "yes");
				// //更新参数,注意flags要使用FLAG_UPDATE_CURRENT
				PendingIntent contentIntent = PendingIntent.getActivity(
						mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				mNotification.setLatestEventInfo(mContext, "下载完成",
						"文件已下载完毕，点击进行安装。", contentIntent);
				new Notification.Builder(mContext)
						.setAutoCancel(true)
						.setDefaults(Notification.DEFAULT_LIGHTS)
						.setContentTitle("下载完成")
						.setContentText("升级包已下载完毕，点击进行安装。")
						.setContentIntent(contentIntent)
						.setSmallIcon(notifyIcon)
						.setWhen(when)
						.build();
				mNotificationManager.notify(NOTIFY_ID, mNotification);*/
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hasCancel = false;
            if (mShowProgressDialog) {
                showProgressDialog();
            } else {
                tempv = 0;
                int icon = R.mipmap.ic_launcher;
                CharSequence tickerText = "开始下载";
                long when = System.currentTimeMillis();
                mNotification = new Notification(icon, tickerText, when);

                // 在通知栏上点击此通知后自动清除此通知
                mNotification.flags = Notification.FLAG_AUTO_CANCEL;

                RemoteViews contentView = new RemoteViews(
                        mContext.getPackageName(), R.layout.notification_update);
                contentView.setTextViewText(R.id.fileName, "更新文件下载中……");
                // 指定个性化视图
                mNotification.contentView = contentView;
                Intent intent = new Intent();
                PendingIntent contentIntent = PendingIntent.getActivity(
                        mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // 指定内容意图
                mNotification.contentIntent = contentIntent;
                mNotificationManager.notify(NOTIFY_ID, mNotification);
            }
        }
    }

    // 在手机上打开文件
    private static void openFile(File f) {
        mProgressDialog.dismiss();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        // 设定intent的file与MimeType，安装文件
        intent.setDataAndType(Uri.fromFile(f),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    // 使用自带算法 获取文件的SHA1
    private static String getFileSHA1(File file) {
        if (file.exists()) {
            MessageDigest digest = null;
            byte buffer[] = new byte[1024];
            int len;
            try {
                digest = MessageDigest.getInstance("SHA-1");// ("SHA-1");
                FileInputStream in = new FileInputStream(file);
                while ((len = in.read(buffer, 0, 1024)) != -1) {
                    digest.update(buffer, 0, len);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }

            // 直接用这玩意转换成16进制，碉堡了、
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);

        } else {
            return "";
        }
    }

    /**
     * 显示下载进度对话框，可以取消下载任务。
     */
    private static void showProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mDownloadFileAsyncTask != null) {
                    if (!mDownloadFileAsyncTask.isCancelled()) {
                        mDownloadFileAsyncTask.cancel(true);
                        hasCancel = true;
                    }
                }
                mProgressDialog.cancel();
            }
        });
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("正在下载");
//        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("请稍候...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    /***
     * 图片切圆角，方向自由.
     */
    public static final int ALL = 347120;
    public static final int TOP = 547120;
    public static final int LEFT = 647120;
    public static final int RIGHT = 747120;
    public static final int BOTTOM = 847120;

    public static Bitmap fillet(int type, Bitmap bitmap, int roundPx) {
        try {
            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
            // 然后在画板上画出一个想要的形状的区域。
            // 最后把源图片帖上。
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();

            Bitmap paintingBoard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(paintingBoard);
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);

            if (TOP == type) {
                clipTop(canvas, paint, roundPx, width, height);
            } else if (LEFT == type) {
                clipLeft(canvas, paint, roundPx, width, height);
            } else if (RIGHT == type) {
                clipRight(canvas, paint, roundPx, width, height);
            } else if (BOTTOM == type) {
                clipBottom(canvas, paint, roundPx, width, height);
            } else {
                clipAll(canvas, paint, roundPx, width, height);
            }

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            //帖子图
            final Rect src = new Rect(0, 0, width, height);
            final Rect dst = src;
            canvas.drawBitmap(bitmap, src, dst, paint);
            return paintingBoard;
        } catch (Exception exp) {
            return bitmap;
        }
    }

    private static void clipLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(offset, 0, width, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, 0, offset * 2, height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, 0, width - offset, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(width - offset * 2, 0, width, height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipTop(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, offset, width, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, 0, width, offset * 2);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipBottom(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, 0, width, height - offset);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, height - offset * 2, width, height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipAll(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
}
