package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by U on 2017/3/20.
 */

public class DynamicMessageEntity {

    /**
     * IsRead : false
     * Id : 9
     * User : {"UserId":"c2c1e556-8f25-4970-ac9b-1e1a575d82ee","TokenId":null,"RealName":"秘","HeadImgUrl":"","Address":null,"NickName":null,"BirthDay":null,"CaptialChar":null}
     * ToUser : [{"UserId":"9d152551-1e1c-418b-b83d-dca26b0de5f5","TokenId":null,"RealName":"孔明杰","HeadImgUrl":"","Address":null,"NickName":null,"BirthDay":null,"CaptialChar":null}]
     * Message : 用户秘已同意您的好友请求!
     * Type : 0
     * Titile :
     * CreationDate : /Date(1490150638400)/
     */
    @Expose
    private boolean IsRead;
    @Expose
    private int Id;
    @Expose
    private UserBean User;
    @Expose
    private String Message;
    @Expose
    private int Type;
    @Expose
    private String Titile;
    @Expose
    private String CreationDate;
    @Expose
    private String CreationDateStr;
    @Expose
    private List<ToUserBean> ToUser;
    @Expose
    private int ReadCount;
    @Expose
    private boolean IsRoot;
    @Expose
    private int Pid;
    @Expose
    private String ReadDate;

    public int getReadCount() {
        return ReadCount;
    }

    public void setReadCount(int readCount) {
        ReadCount = readCount;
    }

    public boolean isRoot() {
        return IsRoot;
    }

    public void setRoot(boolean root) {
        IsRoot = root;
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public String getReadDate() {
        return ReadDate;
    }

    public void setReadDate(String readDate) {
        ReadDate = readDate;
    }

    public boolean isIsRead() {
        return IsRead;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getTitile() {
        return Titile;
    }

    public void setTitile(String Titile) {
        this.Titile = Titile;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public List<ToUserBean> getToUser() {
        return ToUser;
    }

    public void setToUser(List<ToUserBean> ToUser) {
        this.ToUser = ToUser;
    }

    public String getCreationDateStr() {
        return CreationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        CreationDateStr = creationDateStr;
    }

    public static class UserBean {
        /**
         * UserId : c2c1e556-8f25-4970-ac9b-1e1a575d82ee
         * TokenId : null
         * RealName : 秘
         * HeadImgUrl :
         * Address : null
         * NickName : null
         * BirthDay : null
         * CaptialChar : null
         */

        @Expose
        private String UserId;
        @Expose
        private String RealName;
        @Expose
        private String HeadImgUrl;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getHeadImgUrl() {
            return HeadImgUrl;
        }

        public void setHeadImgUrl(String HeadImgUrl) {
            this.HeadImgUrl = HeadImgUrl;
        }
    }

    public static class ToUserBean {
        /**
         * UserId : 9d152551-1e1c-418b-b83d-dca26b0de5f5
         * TokenId : null
         * RealName : 孔明杰
         * HeadImgUrl :
         * Address : null
         * NickName : null
         * BirthDay : null
         * CaptialChar : null
         */

        @Expose
        private String UserId;
        @Expose
        private String RealName;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }
    }

    @Override
    public String toString() {
        return "DynamicMessageEntity{" +
                "IsRead=" + IsRead +
                ", Id=" + Id +
                ", User=" + User +
                ", Message='" + Message + '\'' +
                ", Type=" + Type +
                ", Titile='" + Titile + '\'' +
                ", CreationDate='" + CreationDate + '\'' +
                ", CreationDateStr='" + CreationDateStr + '\'' +
                ", ToUser=" + ToUser +
                ", ReadCount=" + ReadCount +
                ", IsRoot=" + IsRoot +
                ", Pid=" + Pid +
                ", ReadDate='" + ReadDate + '\'' +
                '}';
    }
}
