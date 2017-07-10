package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Administrator on 2017/3/26.
 */
public class ReportListEntity {

    /**
     * FormInfo : [{"RealName":"移动端-4月份工作量汇总-任统帅.xlsx","FilePath":"http://proxy.dev.zbzbx.com/Resources/document/20170414112026_3799.xlsx","FileSize":13,"FileType":"document","RelativePath":"document/20170414112026_3799.xlsx"}]
     * LoanApplyId : 10c9e9f5-2099-4bee-acce-60ddf3d49e21
     * BindingId : 9e59ea20-8a7d-4ec1-9b28-e668ee887f1d
     * Date : 2017-04-01 00:00:00
     * IsUpLoad : true
     * CreateDate : 2017-04-14 11:19:59
     */


    @Expose
    private String LoanApplyId;
    @Expose
    private String BindingId;
    @Expose
    private String Date;
    @Expose
    private boolean IsUpLoad;
    @Expose
    private String FilePermissions;
    @Expose
    private String CreateDate;
    @Expose
    private List<FormInfoBean> FormInfo;

    public String getFilePermissions() {
        return FilePermissions;
    }

    public void setFilePermissions(String filePermissions) {
        FilePermissions = filePermissions;
    }

    public boolean isUpLoad() {
        return IsUpLoad;
    }

    public void setUpLoad(boolean upLoad) {
        IsUpLoad = upLoad;
    }

    public String getLoanApplyId() {
        return LoanApplyId;
    }

    public void setLoanApplyId(String LoanApplyId) {
        this.LoanApplyId = LoanApplyId;
    }

    public String getBindingId() {
        return BindingId;
    }

    public void setBindingId(String BindingId) {
        this.BindingId = BindingId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public boolean isIsUpLoad() {
        return IsUpLoad;
    }

    public void setIsUpLoad(boolean IsUpLoad) {
        this.IsUpLoad = IsUpLoad;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public List<FormInfoBean> getFormInfo() {
        return FormInfo;
    }

    public void setFormInfo(List<FormInfoBean> FormInfo) {
        this.FormInfo = FormInfo;
    }

    public static class FormInfoBean {
        /**
         * RealName : 移动端-4月份工作量汇总-任统帅.xlsx
         * FilePath : http://proxy.dev.zbzbx.com/Resources/document/20170414112026_3799.xlsx
         * FileSize : 13
         * FileType : document
         * RelativePath : document/20170414112026_3799.xlsx
         */
        @Expose
        private String AttachmentId;
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

        public String getDownloadPath() {
            return DownloadPath;
        }

        public void setDownloadPath(String downloadPath) {
            DownloadPath = downloadPath;
        }

        @Expose
        private String DownloadPath;

        public String getAttachmentId() {
            return AttachmentId;
        }

        public void setAttachmentId(String attachmentId) {
            AttachmentId = attachmentId;
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
    }
}
