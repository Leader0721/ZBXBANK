package com.zbxn.main.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ysj
 * @date: 2017-03-16 15:48
 */
public class AdjunctEntity implements Parcelable {

    private String fileType;//File类型
    private boolean isUpload;//此处是否有图片或文件
    private boolean isMust;//是否必需
    private String fileName;//说明
    private String fileNameStr;
    private String guid;
    private String fileId;
    private List<Attachment> fileList;
    private boolean isMore;//是否多选

    public AdjunctEntity() {//实例化用
    }

    public AdjunctEntity(List<Attachment> fileList, String fileType, boolean isUpload, boolean isMust, String fileName, String fileNameStr, String guid, boolean isMore) {//初始化数据
        this.fileList = fileList;
        this.fileType = fileType;
        this.isUpload = isUpload;
        this.isMust = isMust;
        this.fileName = fileName;
        this.fileNameStr = fileNameStr;
        this.guid = guid;
        this.isMore = isMore;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameStr() {
        return fileNameStr;
    }

    public void setFileNameStr(String fileNameStr) {
        this.fileNameStr = fileNameStr;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<Attachment> getFileList() {
        return fileList;
    }

    public void setFileList(List<Attachment> fileList) {
        this.fileList = fileList;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public static class Attachment implements Parcelable {
        private String fileUrl;
        private String fileId;
        private String fileName;
        private String downloadPath;

        public String getDownloadPath() {
            return downloadPath;
        }

        public void setDownloadPath(String downloadPath) {
            this.downloadPath = downloadPath;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public Attachment() {
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.fileUrl);
            dest.writeString(this.fileId);
            dest.writeString(this.fileName);
            dest.writeString(this.downloadPath);
        }

        protected Attachment(Parcel in) {
            this.fileUrl = in.readString();
            this.fileId = in.readString();
            this.fileName = in.readString();
            this.downloadPath = in.readString();
        }

        public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
            @Override
            public Attachment createFromParcel(Parcel source) {
                return new Attachment(source);
            }

            @Override
            public Attachment[] newArray(int size) {
                return new Attachment[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileType);
        dest.writeByte(this.isUpload ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMust ? (byte) 1 : (byte) 0);
        dest.writeString(this.fileName);
        dest.writeString(this.fileNameStr);
        dest.writeString(this.guid);
        dest.writeString(this.fileId);
        dest.writeList(this.fileList);
    }

    protected AdjunctEntity(Parcel in) {
        this.fileType = in.readString();
        this.isUpload = in.readByte() != 0;
        this.isMust = in.readByte() != 0;
        this.fileName = in.readString();
        this.fileNameStr = in.readString();
        this.guid = in.readString();
        this.fileId = in.readString();
        this.fileList = new ArrayList<Attachment>();
        in.readList(this.fileList, Attachment.class.getClassLoader());
    }

    public static final Parcelable.Creator<AdjunctEntity> CREATOR = new Parcelable.Creator<AdjunctEntity>() {
        @Override
        public AdjunctEntity createFromParcel(Parcel source) {
            return new AdjunctEntity(source);
        }

        @Override
        public AdjunctEntity[] newArray(int size) {
            return new AdjunctEntity[size];
        }
    };
}
