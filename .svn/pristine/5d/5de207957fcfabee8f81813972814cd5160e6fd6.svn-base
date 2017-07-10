package com.pub.widget.fileselector.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.pub.widget.fileselector.FileSelector;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * 文件的各种相关的操作
 * <p>
 * Created by zzhoujay on 2014/12/30 0030.
 */
public class FileUtils {

    private static FileCompare fileCompare = new FileCompare();

    //获取文件列表
    public static ArrayList<HashMap<String, Object>> getFileList(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {

                File[] files = file.listFiles();

                ArrayList<HashMap<String, Object>> ds = new ArrayList<>();
                ArrayList<HashMap<String, Object>> fs = new ArrayList<>();


                if (!path.equals(FileSelector.rootPath)) {

                    HashMap<String, Object> up = new HashMap<>(3);
                    up.put(FileSelector.NAME, "...返回上一级");
                    up.put(FileSelector.ICON, FileSelector.theme.upfolder);
                    up.put(FileSelector.PATH, path);
                    up.put(FileSelector.TYPE, FileType.UPTO);
                    up.put(FileSelector.SELECT, FileType.forbid);

                    ds.add(up);
                }


                for (File e : files) {

                    HashMap<String, Object> value = new HashMap<>(3);
                    String p = e.getAbsolutePath();
                    String name = getFileName(p);

                    /**
                     * 隐藏文件处理
                     */
                    if (isHitFiles(name)) {
                        if (!FileSelector.fileConfig.showHiddenFiles) {
                            continue;
                        }
                    }

                    /**
                     * 文件夹处理
                     */
                    if (e.isDirectory()) {

                        value.put(FileSelector.NAME, name);
                        value.put(FileSelector.PATH, p);
                        value.put(FileSelector.ICON, FileSelector.theme.folder);
                        value.put(FileSelector.TYPE, FileType.FOLDER);
                        value.put(FileSelector.SELECT, FileSelector.selectType == FileType.FILE ? FileType.forbid : FileType.unselect);

                        ds.add(value);

                    } else {//文件处理


                        String lastName = getFileLastName(name);

                        //如果需要过滤
                        if (FileSelector.filterModel != FileFilter.FILTER_NONE) {
                            //如果是positive过滤
                            if (FileSelector.positiveFilter) {
                                //如果匹配不成功,则跳过此次循环
                                if (!FileFilter.doFilter(lastName)) {
                                    continue;
                                }

                            } else {//native 过滤
                                //如果匹配成功则跳过此次循环
                                if (FileFilter.doFilter(lastName)) {
                                    continue;
                                }
                            }

                        }


                        int type = getFileType(name);

                        int icon;
                        switch (type) {
                            case FileType.AUDIO:
                                icon = FileSelector.theme.audioIcon;
                                break;
                            case FileType.VIDEO:
                                icon = FileSelector.theme.videoIcon;
                                break;
                            case FileType.TEX:
                                icon = FileSelector.theme.txtIcon;
                                break;
                            case FileType.IMAGE:
                                icon = FileSelector.theme.imageIcon;
                                break;
                            default:
                                icon = FileSelector.theme.otherIcon;


                        }

                        value.put(FileSelector.NAME, name);
                        value.put(FileSelector.PATH, p);
                        value.put(FileSelector.ICON, icon);
                        value.put(FileSelector.TYPE, FileType.FILE);
                        value.put(FileSelector.SELECT, FileSelector.selectType == FileType.FOLDER ? FileType.forbid : FileType.unselect);

                        fs.add(value);
                    }
                }

                Collections.sort(ds, fileCompare);
                Collections.sort(fs, fileCompare);

                ds.addAll(fs);
                return ds;
            }
        }


        return null;
    }


    //获取文件的种类
    public static int getFileType(String name) {
        int type = FileType.OTHER;

        String last = getFileLastName(name);
        if (last == null)
            return type;

        if (last.equals("jpg") | last.equals("png") | last.equals("bmp")) {
            type = FileType.IMAGE;
        } else if (last.equals("avi") | last.equals("mp4") | last.equals("mkv") | last.equals("flv")) {
            type = FileType.VIDEO;
        } else if (last.equals("txt")) {
            type = FileType.TEX;
        } else if (last.equals("mp3")) {
            type = FileType.AUDIO;
        }
        return type;
    }


    //获取文件的后缀名
    public static String getFileLastName(String name) {
        if (!name.contains(".")) {
            return null;
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    //通过文件路径获取文件的名字
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());
    }

    //获取文件父目录
    public static String getParentPath(String path) {
        File file = new File(path);

        if (file.exists()) {
            return file.getParent();
        }

        return null;
    }

    //判断文件是否是隐藏文件
    public static boolean isHitFiles(String name) {
        return name.substring(0, 1).equals(".");
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(Context context, File file) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        context.startActivity(intent);

    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.d("获取文件大小", "文件不存在!");
        }
        return size;
    }

    //建立一个MIME类型与文件后缀名的匹配表
    public static final String[][] MIME_MapTable = {
            //{后缀名，    MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/msword"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".prop", "text/plain"},
            {".rar", "application/x-rar-compressed"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/zip"},
            {"", "*/*"}
    };
}
