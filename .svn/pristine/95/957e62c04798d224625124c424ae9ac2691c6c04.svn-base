package com.pub.http;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by zbx on 16/1/11.
 */
public interface IResource {

    /**
     * action 拼接的url地址   @Url动态的url地址请求;半静态的url地址请求 @GET("users/{user}/repos") @Path("id") String id
     * map 传入的参数
     */
    @FormUrlEncoded
    @POST
    Call<JsonObject> GetRequest(@Url String action, @FieldMap Map<String, String> map);


    /**
     * Body体
     *
     * @param action
     * @param body
     * @return
     */
    @POST
    Call<JsonObject> GetRequestBody(@Url String action, @Body RequestBody body);

    /**
     * 自动升级
     *
     * @param app
     * @return
     */
    @FormUrlEncoded
    @POST("App/Index")
    Call<JsonObject> getNewVersion(@Field("App") String app);


    /**
     * 找回密码
     *
     * @param tokenid
     * @param phoneNumber
     * @return
     */
    @FormUrlEncoded
    @POST("User/ResetUserPws")
    Call<JsonObject> GetLogin(@Field("tokenid") String tokenid, @Field("phoneNumber") String phoneNumber);

    /**
     * 获取验证码
     *
     * @param Phone
     * @param State:国籍:China(中国), Thailand(泰国)
     * @return
     */
    @FormUrlEncoded
    @POST("User/GetSmsCode")
    Call<JsonObject> GetSmsCode(@Field("Phone") String Phone, @Field("State") String State);

    /**
     * 检查用户密码
     *
     * @param pws:输入的原密码
     * @return
     */
    @FormUrlEncoded
    @POST("User/CheckUserPws")
    Call<JsonObject> CheckUserPws(@Field("pws") String pws);

    /**
     * 修改用户手机之前发送验证码
     *
     * @param Phone
     * @param State:国籍:China(中国), Thailand(泰国)
     * @param resetPws:是否修改密码
     * @return
     */
    @FormUrlEncoded
    @POST("User/GetSmsCode")
    Call<JsonObject> GetSmsCode(@Field("Phone") String Phone, @Field("State") String State, @Field("resetPws") boolean resetPws);

    /**
     * 修改手机号之前发送验证码
     *
     * @param Phone
     * @param State:国籍:China(中国), Thailand(泰国)
     * @return
     */
    @FormUrlEncoded
    @POST("User/GetChangePhoneSmsCode")
    Call<JsonObject> GetChangePhoneSmsCode(@Field("Phone") String Phone, @Field("State") String State);

    /**
     * 忘记密码修改密码
     *
     * @param {"SmsCode":"375050"}
     * @param {"MissionId":"c725fedb-62da-4f78-908d-4f7e3f822ff8"}
     * @param NewPws
     * @return
     */
    @FormUrlEncoded
    @POST("User/ResetUserPws")
    Call<JsonObject> ResetUserPws(@Field("SmsCode") String SmsCode, @Field("MissionId") String MissionId, @Field("NewPws") String NewPws);

    /**
     * 安全中心修改密码
     *
     * @param {"SmsCode":"375050"}
     * @param {"MissionId":"c725fedb-62da-4f78-908d-4f7e3f822ff8"}
     * @return
     */
    @FormUrlEncoded
    @POST("User/ChangePws")
    Call<JsonObject> ChangePws(@Field("opw") String opw, @Field("npw") String npw);

    /**
     * 确认修改手机
     *
     * @param MissionId
     * @param //state:国籍:China(中国), Thailand(泰国)
     * @return
     */
    @FormUrlEncoded
    @POST("User/ChangePhone")
    Call<JsonObject> ChangePhone(@Field("MissionId") String MissionId, @Field("smscode") String smscode);

    /**
     * 注销登录
     *
     * @param {"TokenId":"ae767ace-c1e2-4166-8755-b1d19820ab2c"}
     */
    @FormUrlEncoded
    @POST("User/LogOut")
    Call<JsonObject> LogOut(@Field("TokenId") String TokenId);

    /**
     * 登陆
     *
     * @param LogingType   0=Web,1=App(会踢人,注销Token)
     *                     Phone:用户手机号码
     * @param Phone:用户手机号码
     * @return Pws:密码
     */
    @FormUrlEncoded
    @POST("User/Login")
    Call<JsonObject> GetLogin(@Field("LoginType") String LogingType, @Field("Phone") String Phone, @Field("Pws") String Pws);

    /**
     * 注册接口
     *
     * @param LogingType                   0=Web,1=App(会踢人,注销Token)
     *                                     Phone:用户手机号码
     * @param Phone:用户手机号码
     * @param Pws:密码
     * @param MissionId::获取验证码获得的MissionId
     * @param RealName:用户姓名
     * @param SmsCode:验证码
     */
    @FormUrlEncoded
    @POST("User/Regedit")
    Call<JsonObject> GetRegedit(@Field("LoginType") String LogingType, @Field("Phone") String Phone, @Field("Pws") String Pws,
                                @Field("MissionId") String MissionId, @Field("RealName") String RealName, @Field("SmsCode") String SmsCode);

    /**
     * 获取当前用户信息
     *
     * @param TokenId
     */
    @FormUrlEncoded
    @POST("User/GetUserLoginUserInfo")
    Call<JsonObject> GetUserLoginUserInfo(@Field("TokenId") String TokenId);

    /**
     * 修改当前用户信息
     *
     * @param HeadImgUrl
     * @param RealName
     * @param Address
     * @param NickName
     * @param BirthDay
     */
    @FormUrlEncoded
    @POST("User/ChangeUserInfo")
    Call<JsonObject> ChangeUserInfo(@Field("HeadImgUrl") String HeadImgUrl, @Field("RealName") String RealName,
                                    @Field("Address") String Address, @Field("NickName") String NickName,
                                    @Field("BirthDay") String BirthDay, @Field("Email") String Email);

    /**
     * 获取公司列表
     */
    @POST("Organization/GetCompanyListByUserId")
    Call<JsonObject> GetCompanyListByUserId();

    /**
     * 获取公司列表
     */
    @FormUrlEncoded
    @POST("Organization/AddCompany")
    Call<JsonObject> AddCompany(@Field("CompanyName") String CompanyName);

    /**
     * 获取公司详情
     */
    @FormUrlEncoded
    @POST("Organization/GetCompanyInfoById")
    Call<JsonObject> GetCompanyInfo(@Field("CompanyId") String CompanyId);

    /**
     * 退出企业
     */
    @FormUrlEncoded
    @POST("Organization/ExitCompany")
    Call<JsonObject> ExitCompany(@Field("CompanyId") String CompanyId);


    /**
     * 请求添加好友
     *
     * @param phone       用户手机号
     * @param RequestType 请求模式0:企业关系 1:手机直接添加(企业会验证是否是同事关系,需要目标用户与用户 任意一公司的同事关系)
     * @return
     */
    @FormUrlEncoded
    @POST("User/AddFriendRequest")
    Call<JsonObject> AddFriendRequest(@Field("phone") String phone, @Field("RequestType") String RequestType);

    /**
     * 删除好友
     *
     * @param ToUserId 删除好友的ID
     * @return
     */
    @FormUrlEncoded
    @POST("User/DeleteFriendRel")
    Call<JsonObject> DeleteFriendRel(@Field("ToUserId") String ToUserId);

    /**
     * 获取消息列表
     *
     * @param listType  0所有 1未读 2已读
     * @param PageIndex
     * @param PageSize  个人发布消息=0,版本更新=1,系统维护=2,使用技巧=3,营销推广=4,企业邀请=5,添加好友=6,
     *                  贷款申请批复=7,贷款额度变更=8,提交报表提醒=9,新的贷款申请=10,报表收取提醒=11,企业发布消息=12,
     *                  企业动态=13,部门=14,职位=15,群组=16,成员申请企业回复=17,成员申请个人通知=18,修改职位企业通知=19,
     *                  修改职位个人通知=20,修改部门企业通知=21,修改部门个人通知=22,修改群组企业通知=23,修改群组个人通知=24,停用成员企业通知=25,
     *                  停用成员个人通知=26,启用成员企业通知=27,启用成员个人通知=28,同意加入企业企业通知=29,同意加入企业个人通知=30，拒绝加入企业个人通知=31
     * @return400c51a698834dd5a191795d17ee7d71
     */
    @FormUrlEncoded
    @POST("User/GetUserMessageList")
    Call<JsonObject> getUserMessageList(@Field("listType") String listType, @Field("PageSize") String PageSize,
                                        @Field("PageIndex") String PageIndex);

    /**
     * 获取消息列表
     */
    @FormUrlEncoded
    @POST("UserCenter/GetMessageListByUId")
    Call<JsonObject> GetMessageListById(@Field("MessageType") int MessageType,@Field("PageIndex") int PageIndex,
                                        @Field("PageSize") int PageSize,@Field("CreateTime") String CreateTime);

    /**
     * 获取邀请成员详细信息
     *
     * @param InviteId 企业邀请ID
     */
    @FormUrlEncoded
    @POST("Organization/GetInviteInfoByID")
    Call<JsonObject> GetInviteInfoByID(@Field("InviteId") String InviteId);

    /**
     * 同意企业邀请
     *
     * @param nviteId 企业邀请ID
     */
    @FormUrlEncoded
    @POST("Organization/AgreeInvite")
    Call<JsonObject> AgreeInvite(@Field("InviteId") String nviteId, @Field("MessageId") String MessageId);

    /**
     * 拒绝企业邀请
     *
     * @param nviteId 企业邀请ID
     */
    @FormUrlEncoded
    @POST("Organization/RefuseInvite")
    Call<JsonObject> RefuseInvite(@Field("InviteId") String nviteId, @Field("MessageId") String MessageId);

    /**
     * 个人加好友请求
     *
     * @param {"Id":"好友消息Id"} 企业邀请ID
     *                        {"Respond":"是否同意(Bool)"}
     */
    @FormUrlEncoded
    @POST("User/RespondAddFriend")
    Call<JsonObject> RespondAddFriend(@Field("Id") String Id, @Field("Respond") String Respond);


    /**
     * 消息动态界面的发布消息
     *
     * @param UserIdStr
     * @param MessageStr
     * @param TitleStr
     * @param Type
     * @return
     */
    @FormUrlEncoded
    @POST("User/SendUserMessage")
    Call<JsonObject> SendUserMessage(@Field("UserIdStr") String UserIdStr, @Field("MessageStr") String MessageStr,
                                     @Field("TitleStr") String TitleStr, @Field("Type") String Type);


    /**
     * 消息动态界面获取消息详情
     *
     * @param RootId
     * @return
     */
    @FormUrlEncoded
    @POST("User/GetUserMessageInfo")
    Call<JsonObject> GetUserMessageInfo(@Field("RootId") String RootId);


    /**
     * 回复
     *
     * @return
     */
    @FormUrlEncoded
    @POST("User/ReplyMessage")
    Call<JsonObject> ReplyMessage(@Field("Title") String Title, @Field("Message") String Message, @Field("Pid") int Pid);

    /**
     * 获取好有列表
     */
    @POST("User/GetUserFriendList")
    Call<JsonObject> GetUserFriendList();

    /**
     * 根据企业ID获取成员列表
     */
    @FormUrlEncoded
    @POST("Organization/GetAllUsersByCompanyId")
    Call<JsonObject> GetAllUsersByCompanyId(@Field("CompanyId") String CompanyId);

    /**
     * raw提交方式
     *
     * @param parmas
     * @return
     */
    @POST("Organization/GetAllUsersByCompanyId")
    Call<JsonObject> GetAllUsersByCompanyIdd(@Body JsonObject parmas);

    /**
     * 获取公司部门列表
     */
    @FormUrlEncoded
    @POST("Organization/GetDepartmentListByCompanyID")
    Call<JsonObject> GetDepartmentListByCompanyID(@Field("companyid") String companyid);

    /**
     * 获取同事/好友的信息
     */
    @FormUrlEncoded
    @POST("User/GetUserInfo")
    Call<JsonObject> GetUserInfo(@Field("UserId") String UserId);

    /**
     * 获取贷款企业列表
     *
     * @return
     */
    @POST("ChinaRCB/GetLoanCompanyListByUid")
    Call<JsonObject> GetLoanCompanyListByUid();

    /**
     * 获取选择银行列表
     *
     * @return
     */
    @POST("ChinaRCB/getBankList")
    Call<JsonObject> getBankList();

    /**
     * 提交选择的银行，业务员
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/addApply")
    Call<JsonObject> postBankSelect(@Field("ApplyCompanyId") String ApplyCompanyId, @Field("BankId") String BankId,
                                    @Field("AccountManagerId") String AccountManagerId, @Field("CompanyName") String companyName);

    /**
     * 农信贷中获取已经通过的报表列表
     * SearchContent
     * BankId
     * PageIndex
     * PageSize
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/getApplyListForReport")
    Call<JsonObject> getApplyListForReport(@Field("SearchContent") String SearchContent, @Field("BankId") String BankId
            , @Field("PageIndex") String PageIndex, @Field("PageSize") String PageSize
    );


    /**
     * 获取草稿箱列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetDraftList")
    Call<JsonObject> getDraftList(@Field("PageIndex") String PageIndex, @Field("PageSize") String PageSize,
                                  @Field("SearchContent") String SearchContent, @Field("BankId") String BankId);


    /**
     * 获取我的申请记录
     *
     * @param BankId        银行id，全部时传null
     * @param Status        申请状态，全部时传null，未审核：1；审核通过：2；审核未通过：3
     * @param SearchContent 搜索内容
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/getMyApplyList")
    Call<JsonObject> getMyApplyList(@Field("BankId") String BankId, @Field("Status") String Status,
                                    @Field("SearchContent") String SearchContent, @Field("PageIndex") String PageIndex, @Field("PageSize") String PageSize);


    /**
     * 提交附件
     *
     * @param LoanApplyId
     * @param VersionId
     * @param CreateType
     * @param FormInfo
     * @return
     */
    @POST("ChinaRCB/addAttachment")
    Call<JsonObject> AddAttachment(@Body RequestBody body);
//            @Field("LoanApplyId") String LoanApplyId, @Field("VersionId") String VersionId,
//            @Field("CreateType") String CreateType, @Field("FormInfo") String FormInfo);

    /**
     * 贷管家获取附件列表
     *
     * @param LoanApplyId
     * @param VersionId
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetAttachment")
    Call<JsonObject> getAttachment(@Field("LoanApplyId") String LoanApplyId, @Field("VersionId") String VersionId);

    /**
     * 农信贷获取客户经理
     *
     * @param BankId
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/getAccountManagerList")
    Call<JsonObject> getAccountManagerList(@Field("BankId") String BankId);

    /**
     * 添加报表接口
     *
     * @param LoanApplyId
     * @param BindingId
     * @param Date
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/AddReport")
    Call<JsonObject> AddReport(@Field("LoanApplyId") String LoanApplyId, @Field("BindingId") String BindingId, @Field("Date") String Date);


    /**
     * 贷管家获取报表(到浏览器进行一个下载)
     *
     * @param LoanApplyId
     * @param Date
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetReport")
    Call<JsonObject> GetReport(@Field("LoanApplyId") String LoanApplyId, @Field("Date") String Date);


    /**
     * 企业端获取报表列表
     *
     * @param LoanApplyId
     * @param Year
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetReportList")
    Call<JsonObject> GetReportList(@Field("LoanApplyId") String LoanApplyId, @Field("Year") String Year);

    /**
     * 银行端获取申请单财务报表
     *
     * @param LoanApplyId
     * @param Year
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetReportListByAccountManagerId")
    Call<JsonObject> GetReportListByAccountManagerId(@Field("LoanApplyId") String LoanApplyId, @Field("Year") String Year);



    /**
     * 保存草稿
     *
     * @param LoanApplyId
     * @param VersionId
     * @param Step
     * @param FormInfo
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/addDraft")
    Call<JsonObject> AddDraft(@Field("LoanApplyId") String LoanApplyId, @Field("VersionId") String VersionId, @Field("Step") int Step,
                              @Field("FormInfo") String FormInfo);

    /**
     * 审核申请单
     *
     * @param LoanApplyId
     * @param Status
     * @param VerifyMessage
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/AuditApply")
    Call<JsonObject> AuditApply(@Field("LoanApplyId") String LoanApplyId, @Field("Status") boolean Status,
                                @Field("VerifyMessage") String VerifyMessage);

    /**
     * 贷管家获取企业列表
     *
     * @param Status        申请状态，全部时传null，未审核：1；审核通过：2；审核未通过：3
     * @param SearchContent 搜索内容
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetApplyByAccountManagerId")
    Call<JsonObject> getApplyByAccountManagerId(@Field("Status") String Status, @Field("SearchContent") String SearchContent,
                                                @Field("PageIndex") String PageIndex, @Field("PageSize") String PageSize);

    /**
     * 提交申请单
     *
     * @param LoanApplyId
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/SubmitApply")
    Call<JsonObject> SubmitApply(@Field("ApplyId") String LoanApplyId);

//    /**
//     * 提交附件
//     *
//     * @param LoanApplyId
//     * @param VersionId
//     * @param CreateType
//     * @param FormInfo
//     * @return
//     */
//    @POST("ChinaRCB/getApply")
//    Call<JsonObject> getApply(@Body RequestBody body);




    /**
     * 获取指定申请单信息接口
     *
     * @param body
     * @return
     */
    @POST("ChinaRCB/getApply")
    Call<JsonObject> getApply(@Body RequestBody body);


    /**
     * 获取附件上传BindingId
     *
     * @param body
     * @return
     */
    @POST("ChinaRCB/GetAttachmentBindingId")
    Call<JsonObject> GetAttachmentBindingId(@Body RequestBody body);

    /**
     * 获取报表上传BindingId
     *
     * @param body
     * @return
     */
    @POST("ChinaRCB/GetReportBindingId")
    Call<JsonObject> GetReportBindingId(@Body RequestBody body);

    /**
     * 文件删除
     *
     * @param AttachmentId
     * @return
     */
    @FormUrlEncoded
    @POST("Chinarcb/DeleteFile")
    Call<JsonObject> deleteFile(@Field("AttachmentId") String AttachmentId);

    /**
     * 获取贷款申请消息列表
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetMessageList")
    Call<JsonObject> getMessageList(@Field("PageIndex") int PageIndex, @Field("PageSize") int PageSize);

    /**
     * 获取信贷管理消息列表
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/GetMessageListByAccountManagerId")
    Call<JsonObject> getMessageListByAccountManagerId(@Field("PageIndex") int PageIndex, @Field("PageSize") int PageSize);


    /**
     * 提醒上传报表
     *
     * @param LoanApplyId
     * @param VersionId
     * @param UploadDate
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/RemindUploadReport")
    Call<JsonObject> remindUploadReport(@Field("LoanApplyId") String LoanApplyId, @Field("VersionId") String VersionId,
                                        @Field("UploadDate") String UploadDate);

    /**
     * 申请单详情中的返回退出时调用
     *
     * @param LoanApplyId
     * @return
     */
    @FormUrlEncoded
    @POST("ChinaRCB/AddLoanApplyByAccountManager")
    Call<JsonObject> addLoanApplyByAccountManager(@Field("LoanApplyId") String LoanApplyId);







}
