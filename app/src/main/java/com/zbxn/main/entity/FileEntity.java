package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by U on 2017/3/24.
 */

public class FileEntity {


    /**
     * BindingId : 345892d7-59c9-4b82-b260-8a98b6b60a00
     * RealName : 1492124111133_1492096781531_Screenshot_2016-12-15-13-41-09.png
     * FilePath : http://proxy.dev.zbzbx.com/Resources/image/20170414065511_8137.png
     * FileSize : 89
     * FileType : image
     * RelativePath : image/20170414065511_8137.png
     */

    @Expose
    private String BindingId;
    @Expose
    private String RealName;
    @Expose
    private String FilePath;
    @Expose
    private int FileSize;
    @Expose
    private String FileType;
    @Expose
    private String RelativePath;
    @Expose
    private String AttachmentId;

    public String getBindingId() {
        return BindingId;
    }

    public void setBindingId(String BindingId) {
        this.BindingId = BindingId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public int getFileSize() {
        return FileSize;
    }

    public void setFileSize(int FileSize) {
        this.FileSize = FileSize;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String FileType) {
        this.FileType = FileType;
    }

    public String getRelativePath() {
        return RelativePath;
    }

    public void setRelativePath(String RelativePath) {
        this.RelativePath = RelativePath;
    }

    public String getAttachmentId() {
        return AttachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        AttachmentId = attachmentId;
    }
}
