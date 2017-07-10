package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */
public class AttachmentGetEntity {

    /**
     * LoanApplyId : 10c9e9f5-2099-4bee-acce-60ddf3d49e21
     * TypeName : BusinessLicense
     * BindingId : a5673afb-a6e3-48af-8a0b-0ca5f966bf84
     * isUpload : true
     * Status : 1
     * StatusName : 已上传
     * Attachment : [{"RealName":"1492140006645_21-40-37-image.jpg","FilePath":"http://proxy.dev.zbzbx.com/Resources/image/20170414112009_3031.jpg","FileSize":140,"FileType":"image","RelativePath":"image/20170414112009_3031.jpg"}]
     */

    @Expose
    private String LoanApplyId;
    @Expose
    private String TypeName;
    @Expose
    private String BindingId;
    @Expose
    private boolean isUpload;
    @Expose
    private int Status;
    @Expose
    private String StatusName;
    @Expose
    private List<AttachmentBean> Attachment;

    public String getLoanApplyId() {
        return LoanApplyId;
    }

    public void setLoanApplyId(String LoanApplyId) {
        this.LoanApplyId = LoanApplyId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getBindingId() {
        return BindingId;
    }

    public void setBindingId(String BindingId) {
        this.BindingId = BindingId;
    }

    public boolean isIsUpload() {
        return isUpload;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }

    public List<AttachmentBean> getAttachment() {
        return Attachment;
    }

    public void setAttachment(List<AttachmentBean> Attachment) {
        this.Attachment = Attachment;
    }

    public static class AttachmentBean {
        /**
         * RealName : 1492140006645_21-40-37-image.jpg
         * FilePath : http://proxy.dev.zbzbx.com/Resources/image/20170414112009_3031.jpg
         * FileSize : 140
         * FileType : image
         * RelativePath : image/20170414112009_3031.jpg
         */

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
        @Expose
        private String DownloadPath;

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

        public String getDownloadPath() {
            return DownloadPath;
        }

        public void setDownloadPath(String downloadPath) {
            DownloadPath = downloadPath;
        }
    }
}
