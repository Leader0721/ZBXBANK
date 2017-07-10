package com.pub.anyversion;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Yoojia.Chen
 * yoojia.chen@gmail.com
 * 2015-01-04
 */
class Installations {

    private final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (!Downloads.KEEPS.contains(reference)) return;

            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(reference);
            DownloadManager download = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = download.query(query);
            try {
                if (cursor.moveToFirst()) {
                    int fileNameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                    String fileName = cursor.getString(fileNameIdx);
                    if (fileName.endsWith(".apk")){
//                        Intent install = new Intent(Intent.ACTION_VIEW);
//                        install.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
//                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(install);

                        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
                            File file= new File(fileName);
                            Uri apkUri = FileProvider.getUriForFile(context, "com.zbxn.main.activity.service.MyFileProvider", file);//在AndroidManifest中的android:authorities值
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            context.startActivity(install);
                        } else{
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(install);
                        }
                    }
                }
            } finally {
                cursor.close();
            }

        }
    };

    public void register(Context context) {
        Preconditions.requiredMainUIThread();
        context.getApplicationContext().registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void unregister(Context context) {
        Preconditions.requiredMainUIThread();
        context.getApplicationContext().unregisterReceiver(downloadReceiver);
    }
}
