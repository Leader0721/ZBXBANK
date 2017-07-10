package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by U on 2017/3/9.
 */

public class UserEntity {

    /**
     * Key : qye9brafj69fs6wj
     * Iv : qye9brafj69fs6wj
     * TokenId : bfcc1881-37b9-43aa-9b6c-ab90676aa1a6
     * HeadImgUrl : http://proxy.dev.zbzbx.com/Resources/image/20170427160816_4554.png
     * RealName : 智乃
     * Phone : 17865915811
     * Email : null
     * Address : 山西洪洞大槐树
     * BirthDay : /Date(1492531200000)/
     * BirthDayStr : 2017-04-19
     * Permission : [{"PermissionType":2,"CompanyId":"2062c233-9837-4ad7-b9c1-87e8a22696a4","CompanyName":"估计","Permissoin":[{"NodeId":"edaf4a72-6750-4713-aeec-447fbcdd9241","ParentId":"00000000-0000-0000-0000-000000000000","Code":"06","Name":"企业权限","Checked":false,"NodeType":1,"Children":[{"NodeId":"afccafd0-ae6c-4442-a40e-626cf4317819","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0601","Name":"企业信息管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"4693645d-4f32-4093-b82d-aa64bfccfb33","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0602","Name":"企业成员管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"e2fe92c5-fca0-4b9e-a8aa-ebc086f90636","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060201","Name":"成员浏览权","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0603","Name":"部门结构管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"3f391909-2e00-4206-90b0-681da12ece09","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060301","Name":"浏览部门","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0604","Name":"群组管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"59845b07-531d-492e-b728-c0389cb5fab4","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060401","Name":"浏览群组","Checked":false,"NodeType":2,"Children":[]}]}]}]},{"PermissionType":2,"CompanyId":"bb643209-dcf9-44c7-a186-e0a398a66bbe","CompanyName":"我的企业","Permissoin":[{"NodeId":"4d4acda5-5752-43cd-aaad-885fbe70f1ad","ParentId":"00000000-0000-0000-0000-000000000000","Code":"02","Name":"贷款申请","Checked":false,"NodeType":1,"Children":[{"NodeId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","ParentId":"4d4acda5-5752-43cd-aaad-885fbe70f1ad","Code":"0201","Name":"贷款申请操作权","Checked":false,"NodeType":1,"Children":[{"NodeId":"4af84333-e777-4139-bac6-67801e4bb35d","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020101","Name":"提交在线申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"134ee43a-e088-4e69-bc74-977d572f063d","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020102","Name":"打印","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"fc04dcb1-08b9-416e-9026-176f6925b080","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020103","Name":"保存申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"a9089191-b9ab-41c2-9a60-4e9a189dcf7c","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020104","Name":"编辑在线申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"7a7cf735-91b5-4678-a8da-52ea6091cbfd","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020105","Name":"查看贷款申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"18de2f9a-f770-4028-accd-7733c1f27c47","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020106","Name":"保存草稿","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"ac50ac30-8278-4b8b-88af-7a04768678a3","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020107","Name":"查看草稿","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6efb7dd3-a7d9-43d3-8b62-10f92ea93188","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020108","Name":"根据条件检索","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"e7967190-f90e-4152-ab86-98b700c60f3f","ParentId":"4d4acda5-5752-43cd-aaad-885fbe70f1ad","Code":"0202","Name":"财务报表","Checked":false,"NodeType":1,"Children":[{"NodeId":"6f9d48ee-35da-45ab-8daa-63a1b3a7199e","ParentId":"e7967190-f90e-4152-ab86-98b700c60f3f","Code":"020201","Name":"报表上传","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"5ceed49c-b13c-42be-84d0-4b768ada731a","ParentId":"e7967190-f90e-4152-ab86-98b700c60f3f","Code":"020202","Name":"查看报表","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"330d07e9-f2dd-4580-b7e5-454f9155afc7","ParentId":"e7967190-f90e-4152-ab86-98b700c60f3f","Code":"020203","Name":"下载报表","Checked":false,"NodeType":2,"Children":[]}]}]},{"NodeId":"edaf4a72-6750-4713-aeec-447fbcdd9241","ParentId":"00000000-0000-0000-0000-000000000000","Code":"06","Name":"企业权限","Checked":false,"NodeType":1,"Children":[{"NodeId":"afccafd0-ae6c-4442-a40e-626cf4317819","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0601","Name":"企业信息管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"f89bb097-2719-4973-916d-f13d22095f8e","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060102","Name":"编辑企业信息","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"4693645d-4f32-4093-b82d-aa64bfccfb33","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0602","Name":"企业成员管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"e2fe92c5-fca0-4b9e-a8aa-ebc086f90636","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060201","Name":"成员浏览权","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"5b8cee92-2d48-478d-9feb-cd10d29a512f","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060202","Name":"添加成员","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"f65f7fcd-c385-4453-b3d3-867208e8d1a9","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060203","Name":"成员申请操作","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"a3a75f8e-f8f4-41ce-9822-3bcfee09dc03","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060204","Name":"编辑成员基础信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"abf35a84-f135-4d3a-8021-a7b02ca2dddf","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060205","Name":"编辑成员部门信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"923f5eed-cdd8-471a-815e-3dcf152f1e5a","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060206","Name":"编辑成员职位信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"19bdb659-3390-422c-985f-d9584a341c68","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060207","Name":"停用成员","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"3696a6b4-33b6-4cc5-bc3b-db5d3f419919","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060208","Name":"启用成员","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0603","Name":"部门结构管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"3f391909-2e00-4206-90b0-681da12ece09","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060301","Name":"浏览部门","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"0c3c6f95-8623-4d10-856a-27d3721022d1","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060302","Name":"创建部门","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"00eda94a-d020-420e-a988-7e9154010db5","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060303","Name":"编辑部门","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6cf3d1ea-422d-4a9c-b553-50bfc741a21c","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060304","Name":"删除部门","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0604","Name":"群组管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"59845b07-531d-492e-b728-c0389cb5fab4","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060401","Name":"浏览群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"62a92e71-0134-441a-adc4-b1674fd27f38","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060402","Name":"创建群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"184f8e5b-aa6d-4ce2-9b4b-09f1715bbd11","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060403","Name":"编辑群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6325fe4e-343f-42ec-8d04-b555cd743c02","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060404","Name":"解散群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"77dafa8b-86d5-46e8-996f-ccd0c865e00e","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060405","Name":"添加群组成员","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"7257391f-894e-4254-aed2-ad6baef77258","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060406","Name":"移除群组成员","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"358f5ad8-4939-48cf-8200-34ac03f7baad","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0605","Name":"职位管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"dbba36a2-bc63-4165-96c5-284915a57797","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060501","Name":"浏览职位","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"b6298b62-f235-46b9-96ce-d83abf65d672","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060502","Name":"创建职位","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"e3decbd3-4ae8-452b-bea8-f90a1c36fc9d","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060503","Name":"编辑职位","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"18819eab-e4d0-42bc-abbb-b21ce6bf3550","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060504","Name":"删除职位","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0606","Name":"权限管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"5d087e56-ace9-4f64-9009-46bbc384535b","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060601","Name":"浏览角色","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"3caf2769-9a49-4006-a81f-a6760fa4e9b3","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060602","Name":"创建角色","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6ba8aa41-b28d-4380-b3e2-ec1772d4b541","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060603","Name":"角色复制","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"d29471f9-7e0e-41e1-81e5-c5abdbf0bb58","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060604","Name":"编辑角色","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"1173fdde-b31b-402b-a662-364a8db16864","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060605","Name":"删除角色","Checked":false,"NodeType":2,"Children":[]}]}]}]},{"PermissionType":2,"CompanyId":"ae5c6f45-f2d9-4906-86b6-6f6dd37ccbfb","CompanyName":"测试公司4","Permissoin":[{"NodeId":"4d4acda5-5752-43cd-aaad-885fbe70f1ad","ParentId":"00000000-0000-0000-0000-000000000000","Code":"02","Name":"贷款申请","Checked":false,"NodeType":1,"Children":[{"NodeId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","ParentId":"4d4acda5-5752-43cd-aaad-885fbe70f1ad","Code":"0201","Name":"贷款申请操作权","Checked":false,"NodeType":1,"Children":[{"NodeId":"4af84333-e777-4139-bac6-67801e4bb35d","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020101","Name":"提交在线申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"134ee43a-e088-4e69-bc74-977d572f063d","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020102","Name":"打印","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"fc04dcb1-08b9-416e-9026-176f6925b080","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020103","Name":"保存申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"a9089191-b9ab-41c2-9a60-4e9a189dcf7c","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020104","Name":"编辑在线申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"7a7cf735-91b5-4678-a8da-52ea6091cbfd","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020105","Name":"查看贷款申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"18de2f9a-f770-4028-accd-7733c1f27c47","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020106","Name":"保存草稿","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"ac50ac30-8278-4b8b-88af-7a04768678a3","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020107","Name":"查看草稿","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6efb7dd3-a7d9-43d3-8b62-10f92ea93188","ParentId":"3b5c4928-369c-46f9-b7c2-2ce90e679eae","Code":"020108","Name":"根据条件检索","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"e7967190-f90e-4152-ab86-98b700c60f3f","ParentId":"4d4acda5-5752-43cd-aaad-885fbe70f1ad","Code":"0202","Name":"财务报表","Checked":false,"NodeType":1,"Children":[{"NodeId":"6f9d48ee-35da-45ab-8daa-63a1b3a7199e","ParentId":"e7967190-f90e-4152-ab86-98b700c60f3f","Code":"020201","Name":"报表上传","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"5ceed49c-b13c-42be-84d0-4b768ada731a","ParentId":"e7967190-f90e-4152-ab86-98b700c60f3f","Code":"020202","Name":"查看报表","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"330d07e9-f2dd-4580-b7e5-454f9155afc7","ParentId":"e7967190-f90e-4152-ab86-98b700c60f3f","Code":"020203","Name":"下载报表","Checked":false,"NodeType":2,"Children":[]}]}]},{"NodeId":"223d6ee3-fc9a-4d22-a3c4-67bf32160f4d","ParentId":"00000000-0000-0000-0000-000000000000","Code":"03","Name":"信贷管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"14496750-9664-48c9-b7ba-34f5bec015d1","ParentId":"223d6ee3-fc9a-4d22-a3c4-67bf32160f4d","Code":"0301","Name":"贷款申请","Checked":false,"NodeType":1,"Children":[{"NodeId":"60d604a4-e71b-42fe-8623-16914608f825","ParentId":"14496750-9664-48c9-b7ba-34f5bec015d1","Code":"030101","Name":"根据条件检索","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"71006e5c-3a34-4e48-8f60-03e6bef2e5d3","ParentId":"14496750-9664-48c9-b7ba-34f5bec015d1","Code":"030102","Name":"贷款申请-查看详情","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","ParentId":"223d6ee3-fc9a-4d22-a3c4-67bf32160f4d","Code":"0302","Name":"申请资料操作权","Checked":false,"NodeType":1,"Children":[{"NodeId":"e3c78495-7f08-43b4-8af4-6fdcd543e697","ParentId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","Code":"030201","Name":"查看历史版本","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"c54ba9d2-9ba0-4dcb-81a8-08351910f694","ParentId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","Code":"030202","Name":"编辑-贷款申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"16506dad-2b2d-425d-998a-87399e05f3a7","ParentId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","Code":"030203","Name":"保存-贷款申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"a61f48cf-28d2-4312-a095-ab6eb22ef1f8","ParentId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","Code":"030204","Name":"审核通过-贷款申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"09e18acf-f376-4b79-9909-553fe2195044","ParentId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","Code":"030205","Name":"拒绝-贷款申请","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"afee0a8f-71b6-4abe-afbc-99f8a00d3725","ParentId":"06b7ecbd-c79e-4d8b-ad86-8e0c97214787","Code":"030206","Name":"打印-资料详情","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"1f9748f6-3e05-4b5c-966d-97311ec89543","ParentId":"223d6ee3-fc9a-4d22-a3c4-67bf32160f4d","Code":"0303","Name":"企业资料","Checked":false,"NodeType":1,"Children":[{"NodeId":"3266e961-d6d8-4e3a-bf9f-338b8463e0aa","ParentId":"1f9748f6-3e05-4b5c-966d-97311ec89543","Code":"030301","Name":"查看-企业资料","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"12bf31f0-15e5-4243-bd29-e69682256269","ParentId":"1f9748f6-3e05-4b5c-966d-97311ec89543","Code":"030302","Name":"列表-预览切换查看-企业资料","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"51515f12-2a72-4bcf-b759-2de3efd7773d","ParentId":"1f9748f6-3e05-4b5c-966d-97311ec89543","Code":"030303","Name":"下载-企业资料","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"0b5e00cc-aa03-40d8-97ac-6aa00646694f","ParentId":"1f9748f6-3e05-4b5c-966d-97311ec89543","Code":"030304","Name":"打印-企业资料","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"10d960fb-8f09-4ad6-8ceb-274dc0e15d88","ParentId":"1f9748f6-3e05-4b5c-966d-97311ec89543","Code":"030305","Name":"批量下载-企业资料","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"f8b2b314-9331-4e23-8c01-1070e5a736c7","ParentId":"1f9748f6-3e05-4b5c-966d-97311ec89543","Code":"030306","Name":"批量打印-企业资料","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"2883bc9b-c859-423f-b8da-542cb8d99fc5","ParentId":"223d6ee3-fc9a-4d22-a3c4-67bf32160f4d","Code":"0304","Name":"财务报表","Checked":false,"NodeType":1,"Children":[{"NodeId":"927b96a9-5b82-4c1e-b10b-afa93069a343","ParentId":"2883bc9b-c859-423f-b8da-542cb8d99fc5","Code":"030401","Name":"查看-财务报表","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"4cc07810-fe89-41e0-b947-6bd8b749123b","ParentId":"2883bc9b-c859-423f-b8da-542cb8d99fc5","Code":"030402","Name":"下载-财务报表","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"c53cd00d-f53a-470e-b5d1-5f5201be6073","ParentId":"2883bc9b-c859-423f-b8da-542cb8d99fc5","Code":"030403","Name":"打印-财务报表","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"a7e7ec0a-1e06-46f4-b9f6-541ae130fe0f","ParentId":"2883bc9b-c859-423f-b8da-542cb8d99fc5","Code":"030404","Name":"批量下载-财务报表","Checked":false,"NodeType":2,"Children":[]}]}]},{"NodeId":"edaf4a72-6750-4713-aeec-447fbcdd9241","ParentId":"00000000-0000-0000-0000-000000000000","Code":"06","Name":"企业权限","Checked":false,"NodeType":1,"Children":[{"NodeId":"afccafd0-ae6c-4442-a40e-626cf4317819","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0601","Name":"企业信息管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"f89bb097-2719-4973-916d-f13d22095f8e","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060102","Name":"编辑企业信息","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"4693645d-4f32-4093-b82d-aa64bfccfb33","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0602","Name":"企业成员管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"e2fe92c5-fca0-4b9e-a8aa-ebc086f90636","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060201","Name":"成员浏览权","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"5b8cee92-2d48-478d-9feb-cd10d29a512f","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060202","Name":"添加成员","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"f65f7fcd-c385-4453-b3d3-867208e8d1a9","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060203","Name":"成员申请操作","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"a3a75f8e-f8f4-41ce-9822-3bcfee09dc03","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060204","Name":"编辑成员基础信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"abf35a84-f135-4d3a-8021-a7b02ca2dddf","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060205","Name":"编辑成员部门信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"923f5eed-cdd8-471a-815e-3dcf152f1e5a","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060206","Name":"编辑成员职位信息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"19bdb659-3390-422c-985f-d9584a341c68","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060207","Name":"停用成员","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"3696a6b4-33b6-4cc5-bc3b-db5d3f419919","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060208","Name":"启用成员","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0603","Name":"部门结构管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"3f391909-2e00-4206-90b0-681da12ece09","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060301","Name":"浏览部门","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"0c3c6f95-8623-4d10-856a-27d3721022d1","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060302","Name":"创建部门","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"00eda94a-d020-420e-a988-7e9154010db5","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060303","Name":"编辑部门","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6cf3d1ea-422d-4a9c-b553-50bfc741a21c","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060304","Name":"删除部门","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0604","Name":"群组管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"59845b07-531d-492e-b728-c0389cb5fab4","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060401","Name":"浏览群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"62a92e71-0134-441a-adc4-b1674fd27f38","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060402","Name":"创建群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"184f8e5b-aa6d-4ce2-9b4b-09f1715bbd11","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060403","Name":"编辑群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6325fe4e-343f-42ec-8d04-b555cd743c02","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060404","Name":"解散群组","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"77dafa8b-86d5-46e8-996f-ccd0c865e00e","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060405","Name":"添加群组成员","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"7257391f-894e-4254-aed2-ad6baef77258","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060406","Name":"移除群组成员","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"358f5ad8-4939-48cf-8200-34ac03f7baad","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0605","Name":"职位管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"dbba36a2-bc63-4165-96c5-284915a57797","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060501","Name":"浏览职位","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"b6298b62-f235-46b9-96ce-d83abf65d672","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060502","Name":"创建职位","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"e3decbd3-4ae8-452b-bea8-f90a1c36fc9d","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060503","Name":"编辑职位","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"18819eab-e4d0-42bc-abbb-b21ce6bf3550","ParentId":"358f5ad8-4939-48cf-8200-34ac03f7baad","Code":"060504","Name":"删除职位","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0606","Name":"权限管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"5d087e56-ace9-4f64-9009-46bbc384535b","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060601","Name":"浏览角色","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"3caf2769-9a49-4006-a81f-a6760fa4e9b3","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060602","Name":"创建角色","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"6ba8aa41-b28d-4380-b3e2-ec1772d4b541","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060603","Name":"角色复制","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"d29471f9-7e0e-41e1-81e5-c5abdbf0bb58","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060604","Name":"编辑角色","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"1173fdde-b31b-402b-a662-364a8db16864","ParentId":"e784a0f8-a090-4c88-8a78-8e2b528ca785","Code":"060605","Name":"删除角色","Checked":false,"NodeType":2,"Children":[]}]}]}]},{"PermissionType":2,"CompanyId":"45bd4f2e-fa26-432a-9c8e-125d033399b5","CompanyName":"测试公司4","Permissoin":[{"NodeId":"edaf4a72-6750-4713-aeec-447fbcdd9241","ParentId":"00000000-0000-0000-0000-000000000000","Code":"06","Name":"企业权限","Checked":false,"NodeType":1,"Children":[{"NodeId":"afccafd0-ae6c-4442-a40e-626cf4317819","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0601","Name":"企业信息管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"4693645d-4f32-4093-b82d-aa64bfccfb33","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0602","Name":"企业成员管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"e2fe92c5-fca0-4b9e-a8aa-ebc086f90636","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060201","Name":"成员浏览权","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0603","Name":"部门结构管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"3f391909-2e00-4206-90b0-681da12ece09","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060301","Name":"浏览部门","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0604","Name":"群组管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"59845b07-531d-492e-b728-c0389cb5fab4","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060401","Name":"浏览群组","Checked":false,"NodeType":2,"Children":[]}]}]}]},{"PermissionType":1,"CompanyId":"00000000-0000-0000-0000-000000000000","CompanyName":"平台权限","Permissoin":[{"NodeId":"88968bb2-7da9-4d6a-89dd-db0ae0c88ae0","ParentId":"00000000-0000-0000-0000-000000000000","Code":"01","Name":"个人管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"8744ffb4-89e1-4cb7-90e5-080ee36a2728","ParentId":"88968bb2-7da9-4d6a-89dd-db0ae0c88ae0","Code":"0101","Name":"个人基础信息设置","Checked":false,"NodeType":1,"Children":[{"NodeId":"c07ce115-cd36-4fff-93d0-0a845caefb20","ParentId":"8744ffb4-89e1-4cb7-90e5-080ee36a2728","Code":"010101","Name":"基础资料编辑","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"1d1c91a8-dc2e-4b7e-9b84-f8ffa416a4cf","ParentId":"88968bb2-7da9-4d6a-89dd-db0ae0c88ae0","Code":"0102","Name":"账号安全信息设置","Checked":false,"NodeType":1,"Children":[{"NodeId":"e3843810-8fc3-46cd-aa64-92379156261c","ParentId":"1d1c91a8-dc2e-4b7e-9b84-f8ffa416a4cf","Code":"010201","Name":"账号密码修改","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"3c754356-a329-4d7f-9d0b-7b766a23e4a6","ParentId":"1d1c91a8-dc2e-4b7e-9b84-f8ffa416a4cf","Code":"010202","Name":"手机号修改","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"57f48074-70b1-4519-8e11-995108895238","ParentId":"1d1c91a8-dc2e-4b7e-9b84-f8ffa416a4cf","Code":"010203","Name":"邮箱编辑","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"ba861207-e3da-4e98-a234-c72ffa988756","ParentId":"88968bb2-7da9-4d6a-89dd-db0ae0c88ae0","Code":"0103","Name":"偏好信息设置","Checked":false,"NodeType":1,"Children":[{"NodeId":"3a144b75-0a0f-42d3-821d-c083f47edadd","ParentId":"ba861207-e3da-4e98-a234-c72ffa988756","Code":"010301","Name":"消息设置","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"3b7595a9-b217-47ae-b4c8-32c2d077ec9e","ParentId":"ba861207-e3da-4e98-a234-c72ffa988756","Code":"010302","Name":"语言设置","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"9595e160-ca80-46b1-bd1b-c48ba2cea880","ParentId":"ba861207-e3da-4e98-a234-c72ffa988756","Code":"010303","Name":"隐私设置","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"5da669fe-fc7b-4935-9dc0-ad5db504eba2","ParentId":"88968bb2-7da9-4d6a-89dd-db0ae0c88ae0","Code":"0104","Name":"个人企业信息设置","Checked":false,"NodeType":1,"Children":[{"NodeId":"82091b0b-af6e-4903-b5e7-ea8da5e0c654","ParentId":"5da669fe-fc7b-4935-9dc0-ad5db504eba2","Code":"010401","Name":"创建企业","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"08fc4150-ccde-48e4-a4fa-9abbc23f9d3d","ParentId":"5da669fe-fc7b-4935-9dc0-ad5db504eba2","Code":"010402","Name":"加入企业","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"581d8777-4160-465f-950a-89afbcbda76a","ParentId":"5da669fe-fc7b-4935-9dc0-ad5db504eba2","Code":"010403","Name":"退出企业","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"e875a857-ad77-49ef-bcc5-721a0dd59376","ParentId":"88968bb2-7da9-4d6a-89dd-db0ae0c88ae0","Code":"0105","Name":"基础功能使用权","Checked":false,"NodeType":1,"Children":[{"NodeId":"bafe1c15-5c3d-4951-af1e-f9a94d90a967","ParentId":"e875a857-ad77-49ef-bcc5-721a0dd59376","Code":"010501","Name":"全局搜索","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"74fb87a2-2405-47e8-84f5-5143d9c912ce","ParentId":"e875a857-ad77-49ef-bcc5-721a0dd59376","Code":"010502","Name":"平台消息","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"663ff3a5-4e8c-4f88-9af6-e2b9feb7846c","ParentId":"e875a857-ad77-49ef-bcc5-721a0dd59376","Code":"010503","Name":"平台帮助","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"24c1f170-b6e8-493c-a5f8-a6bfccd0ba79","ParentId":"e875a857-ad77-49ef-bcc5-721a0dd59376","Code":"010504","Name":"APP下载","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"e59e0176-66a8-498b-a78d-837f5c8c8a38","ParentId":"e875a857-ad77-49ef-bcc5-721a0dd59376","Code":"010505","Name":"安全退出","Checked":false,"NodeType":2,"Children":[]}]}]},{"NodeId":"a3f91f39-5258-4767-b045-c76ce34786ec","ParentId":"00000000-0000-0000-0000-000000000000","Code":"04","Name":"动态管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","ParentId":"a3f91f39-5258-4767-b045-c76ce34786ec","Code":"0401","Name":"动态设置","Checked":false,"NodeType":1,"Children":[{"NodeId":"1a596ff3-8458-4487-b534-4b1b2c8c44d9","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040101","Name":"创建动态","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"ff637b18-120b-4c56-97d4-3d9f084e9338","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040102","Name":"删除动态","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"247e5086-f227-4179-8b70-435f8acf8cfb","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040103","Name":"添加动态评论","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"b29e6589-0b25-4664-9685-e7a30ef48173","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040104","Name":"删除动态评论","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"ca7d9499-8e89-46b3-8eb8-7babff6ad12c","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040105","Name":"添加动态点赞","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"eb17d33a-ad08-44a0-a276-6388d9998f61","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040106","Name":"删除动态点赞","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"73867b50-5179-4e9c-8746-ba7ad2203e42","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040107","Name":"添加动态收藏","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"21f854fc-b3f8-4e99-8768-652849686d06","ParentId":"53ccb4e9-fa60-40df-8d2a-faa4b0fdabb1","Code":"040108","Name":"删除动态收藏","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"bc58bf99-2c30-4b62-975e-a7544de71edc","ParentId":"a3f91f39-5258-4767-b045-c76ce34786ec","Code":"0402","Name":"标签管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"792343da-e14c-465e-a93a-24fbe6b99ad7","ParentId":"bc58bf99-2c30-4b62-975e-a7544de71edc","Code":"040201","Name":"添加动态标签","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"759287e8-95cb-4c6b-8b43-80a33c6fbe7d","ParentId":"bc58bf99-2c30-4b62-975e-a7544de71edc","Code":"040202","Name":"删除动态标签","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"d6c66957-46e3-44c1-a80e-303e9425be99","ParentId":"bc58bf99-2c30-4b62-975e-a7544de71edc","Code":"040203","Name":"编辑动态标签","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"8543d686-dc90-4248-939a-12b10b9c54df","ParentId":"bc58bf99-2c30-4b62-975e-a7544de71edc","Code":"040204","Name":"删除动态标签","Checked":false,"NodeType":2,"Children":[]}]}]},{"NodeId":"3fa0ff1f-f0d9-4680-9f5c-956246eb1add","ParentId":"00000000-0000-0000-0000-000000000000","Code":"05","Name":"人脉管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"217cca42-f822-40fb-a44c-08714c36f9d2","ParentId":"3fa0ff1f-f0d9-4680-9f5c-956246eb1add","Code":"0501","Name":"好友管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"7465c2ce-be04-415c-96a9-1c879efa64f4","ParentId":"217cca42-f822-40fb-a44c-08714c36f9d2","Code":"050101","Name":"添加个人好友","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"2839839f-0f37-4fbe-a9eb-7903429b4f30","ParentId":"217cca42-f822-40fb-a44c-08714c36f9d2","Code":"050102","Name":"删除个人好友","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"502e5102-6ef1-4d91-841a-91bd7bc6c20f","ParentId":"217cca42-f822-40fb-a44c-08714c36f9d2","Code":"050103","Name":"编辑个人好友","Checked":false,"NodeType":2,"Children":[]},{"NodeId":"d62865c4-661f-4293-ad4d-ca8f83c30102","ParentId":"217cca42-f822-40fb-a44c-08714c36f9d2","Code":"050104","Name":"企业同事转个人好友","Checked":false,"NodeType":2,"Children":[]}]}]}]}]
     * Organization : [{"UserCount":4,"DepartmentCount":0,"PositionCount":0,"UserPermission":"成员","UserPermissionFlag":0,"UserPwn":null,"ID":"2062c233-9837-4ad7-b9c1-87e8a22696a4","CorprationNO":"gj21392","CompanyName":"估计","ShortName":null,"ComopanyDesc":null,"CompanyType":"其他","Address":null,"LogoUrl":null},{"UserCount":1,"DepartmentCount":0,"PositionCount":0,"UserPermission":"管理员","UserPermissionFlag":1,"UserPwn":null,"ID":"bb643209-dcf9-44c7-a186-e0a398a66bbe","CorprationNO":"wd25434","CompanyName":"我的企业","ShortName":null,"ComopanyDesc":null,"CompanyType":"其他","Address":null,"LogoUrl":null},{"UserCount":4,"DepartmentCount":0,"PositionCount":0,"UserPermission":"管理员","UserPermissionFlag":1,"UserPwn":null,"ID":"ae5c6f45-f2d9-4906-86b6-6f6dd37ccbfb","CorprationNO":"CS43483","CompanyName":"测试公司4","ShortName":null,"ComopanyDesc":null,"CompanyType":"其他","Address":null,"LogoUrl":null},{"UserCount":13,"DepartmentCount":0,"PositionCount":0,"UserPermission":"成员","UserPermissionFlag":0,"UserPwn":null,"ID":"45bd4f2e-fa26-432a-9c8e-125d033399b5","CorprationNO":"CS57790","CompanyName":"测试公司4","ShortName":"智博星4","ComopanyDesc":null,"CompanyType":"专业服务业","Address":null,"LogoUrl":null}]
     */
    @Expose
    private String Key;
    @Expose
    private String Iv;
    @Expose
    private String TokenId;
    @Expose
    private String HeadImgUrl;
    @Expose
    private String RealName;
    @Expose
    private String Phone;
    @Expose
    private Object Email;
    @Expose
    private String Address;
    @Expose
    private String BirthDay;
    @Expose
    private String BirthDayStr;
    @Expose
    private List<PermissionBean> Permission;
    @Expose
    private List<OrganizationBean> Organization;

    public String getKey() {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public String getIv() {
        return Iv;
    }

    public void setIv(String Iv) {
        this.Iv = Iv;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String TokenId) {
        this.TokenId = TokenId;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String HeadImgUrl) {
        this.HeadImgUrl = HeadImgUrl;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public Object getEmail() {
        return Email;
    }

    public void setEmail(Object Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this.BirthDay = BirthDay;
    }

    public String getBirthDayStr() {
        return BirthDayStr;
    }

    public void setBirthDayStr(String BirthDayStr) {
        this.BirthDayStr = BirthDayStr;
    }

    public List<PermissionBean> getPermission() {
        return Permission;
    }

    public void setPermission(List<PermissionBean> Permission) {
        this.Permission = Permission;
    }

    public List<OrganizationBean> getOrganization() {
        return Organization;
    }

    public void setOrganization(List<OrganizationBean> Organization) {
        this.Organization = Organization;
    }

    public static class PermissionBean {
        /**
         * PermissionType : 2
         * CompanyId : 2062c233-9837-4ad7-b9c1-87e8a22696a4
         * CompanyName : 估计
         * Permissoin : [{"NodeId":"edaf4a72-6750-4713-aeec-447fbcdd9241","ParentId":"00000000-0000-0000-0000-000000000000","Code":"06","Name":"企业权限","Checked":false,"NodeType":1,"Children":[{"NodeId":"afccafd0-ae6c-4442-a40e-626cf4317819","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0601","Name":"企业信息管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"4693645d-4f32-4093-b82d-aa64bfccfb33","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0602","Name":"企业成员管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"e2fe92c5-fca0-4b9e-a8aa-ebc086f90636","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060201","Name":"成员浏览权","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0603","Name":"部门结构管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"3f391909-2e00-4206-90b0-681da12ece09","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060301","Name":"浏览部门","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0604","Name":"群组管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"59845b07-531d-492e-b728-c0389cb5fab4","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060401","Name":"浏览群组","Checked":false,"NodeType":2,"Children":[]}]}]}]
         */

        @Expose
        private int PermissionType;
        @Expose
        private String CompanyId;
        @Expose
        private String CompanyName;
        @Expose
        private List<PermissoinBean> Permissoin;

        public int getPermissionType() {
            return PermissionType;
        }

        public void setPermissionType(int PermissionType) {
            this.PermissionType = PermissionType;
        }

        public String getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(String CompanyId) {
            this.CompanyId = CompanyId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public List<PermissoinBean> getPermissoin() {
            return Permissoin;
        }

        public void setPermissoin(List<PermissoinBean> Permissoin) {
            this.Permissoin = Permissoin;
        }

        public static class PermissoinBean {
            /**
             * NodeId : edaf4a72-6750-4713-aeec-447fbcdd9241
             * ParentId : 00000000-0000-0000-0000-000000000000
             * Code : 06
             * Name : 企业权限
             * Checked : false
             * NodeType : 1
             * Children : [{"NodeId":"afccafd0-ae6c-4442-a40e-626cf4317819","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0601","Name":"企业信息管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"4693645d-4f32-4093-b82d-aa64bfccfb33","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0602","Name":"企业成员管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"e2fe92c5-fca0-4b9e-a8aa-ebc086f90636","ParentId":"4693645d-4f32-4093-b82d-aa64bfccfb33","Code":"060201","Name":"成员浏览权","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0603","Name":"部门结构管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"3f391909-2e00-4206-90b0-681da12ece09","ParentId":"7b6c4982-fc37-437d-a5d2-6cdb80494c19","Code":"060301","Name":"浏览部门","Checked":false,"NodeType":2,"Children":[]}]},{"NodeId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","ParentId":"edaf4a72-6750-4713-aeec-447fbcdd9241","Code":"0604","Name":"群组管理","Checked":false,"NodeType":1,"Children":[{"NodeId":"59845b07-531d-492e-b728-c0389cb5fab4","ParentId":"8681534c-fc9b-4ab2-8639-515067cb8fa7","Code":"060401","Name":"浏览群组","Checked":false,"NodeType":2,"Children":[]}]}]
             */

            @Expose
            private String NodeId;
            @Expose
            private String ParentId;
            @Expose
            private String Code;
            @Expose
            private String Name;
            @Expose
            private boolean Checked;
            @Expose
            private int NodeType;
            @Expose
            private List<ChildrenBeanX> Children;

            public String getNodeId() {
                return NodeId;
            }

            public void setNodeId(String NodeId) {
                this.NodeId = NodeId;
            }

            public String getParentId() {
                return ParentId;
            }

            public void setParentId(String ParentId) {
                this.ParentId = ParentId;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public boolean isChecked() {
                return Checked;
            }

            public void setChecked(boolean Checked) {
                this.Checked = Checked;
            }

            public int getNodeType() {
                return NodeType;
            }

            public void setNodeType(int NodeType) {
                this.NodeType = NodeType;
            }

            public List<ChildrenBeanX> getChildren() {
                return Children;
            }

            public void setChildren(List<ChildrenBeanX> Children) {
                this.Children = Children;
            }

            public static class ChildrenBeanX {
                /**
                 * NodeId : afccafd0-ae6c-4442-a40e-626cf4317819
                 * ParentId : edaf4a72-6750-4713-aeec-447fbcdd9241
                 * Code : 0601
                 * Name : 企业信息管理
                 * Checked : false
                 * NodeType : 1
                 * Children : [{"NodeId":"1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2","ParentId":"afccafd0-ae6c-4442-a40e-626cf4317819","Code":"060101","Name":"浏览企业信息","Checked":false,"NodeType":2,"Children":[]}]
                 */

                @Expose
                private String NodeId;
                @Expose
                private String ParentId;
                @Expose
                private String Code;
                @Expose
                private String Name;
                @Expose
                private boolean Checked;
                @Expose
                private int NodeType;
                @Expose
                private List<ChildrenBean> Children;

                public String getNodeId() {
                    return NodeId;
                }

                public void setNodeId(String NodeId) {
                    this.NodeId = NodeId;
                }

                public String getParentId() {
                    return ParentId;
                }

                public void setParentId(String ParentId) {
                    this.ParentId = ParentId;
                }

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public boolean isChecked() {
                    return Checked;
                }

                public void setChecked(boolean Checked) {
                    this.Checked = Checked;
                }

                public int getNodeType() {
                    return NodeType;
                }

                public void setNodeType(int NodeType) {
                    this.NodeType = NodeType;
                }

                public List<ChildrenBean> getChildren() {
                    return Children;
                }

                public void setChildren(List<ChildrenBean> Children) {
                    this.Children = Children;
                }

                public static class ChildrenBean {
                    /**
                     * NodeId : 1bd2faa2-c7a2-41c5-9b10-bf74addb4fe2
                     * ParentId : afccafd0-ae6c-4442-a40e-626cf4317819
                     * Code : 060101
                     * Name : 浏览企业信息
                     * Checked : false
                     * NodeType : 2
                     * Children : []
                     */

                    @Expose
                    private String NodeId;
                    @Expose
                    private String ParentId;
                    @Expose
                    private String Code;
                    @Expose
                    private String Name;
                    @Expose
                    private boolean Checked;
                    @Expose
                    private int NodeType;
                    @Expose
                    private List<?> Children;

                    public String getNodeId() {
                        return NodeId;
                    }

                    public void setNodeId(String NodeId) {
                        this.NodeId = NodeId;
                    }

                    public String getParentId() {
                        return ParentId;
                    }

                    public void setParentId(String ParentId) {
                        this.ParentId = ParentId;
                    }

                    public String getCode() {
                        return Code;
                    }

                    public void setCode(String Code) {
                        this.Code = Code;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }

                    public boolean isChecked() {
                        return Checked;
                    }

                    public void setChecked(boolean Checked) {
                        this.Checked = Checked;
                    }

                    public int getNodeType() {
                        return NodeType;
                    }

                    public void setNodeType(int NodeType) {
                        this.NodeType = NodeType;
                    }

                    public List<?> getChildren() {
                        return Children;
                    }

                    public void setChildren(List<?> Children) {
                        this.Children = Children;
                    }
                }
            }
        }
    }

    public static class OrganizationBean {
        /**
         * UserCount : 4
         * DepartmentCount : 0
         * PositionCount : 0
         * UserPermission : 成员
         * UserPermissionFlag : 0
         * UserPwn : null
         * ID : 2062c233-9837-4ad7-b9c1-87e8a22696a4
         * CorprationNO : gj21392
         * CompanyName : 估计
         * ShortName : null
         * ComopanyDesc : null
         * CompanyType : 其他
         * Address : null
         * LogoUrl : null
         */

        @Expose
        private int UserCount;
        @Expose
        private int DepartmentCount;
        @Expose
        private int PositionCount;
        @Expose
        private String UserPermission;
        @Expose
        private int UserPermissionFlag;
        @Expose
        private Object UserPwn;
        @Expose
        private String ID;
        @Expose
        private String CorprationNO;
        @Expose
        private String CompanyName;
        @Expose
        private Object ShortName;
        @Expose
        private Object ComopanyDesc;
        @Expose
        private String CompanyType;
        @Expose
        private Object Address;
        @Expose
        private Object LogoUrl;

        public int getUserCount() {
            return UserCount;
        }

        public void setUserCount(int UserCount) {
            this.UserCount = UserCount;
        }

        public int getDepartmentCount() {
            return DepartmentCount;
        }

        public void setDepartmentCount(int DepartmentCount) {
            this.DepartmentCount = DepartmentCount;
        }

        public int getPositionCount() {
            return PositionCount;
        }

        public void setPositionCount(int PositionCount) {
            this.PositionCount = PositionCount;
        }

        public String getUserPermission() {
            return UserPermission;
        }

        public void setUserPermission(String UserPermission) {
            this.UserPermission = UserPermission;
        }

        public int getUserPermissionFlag() {
            return UserPermissionFlag;
        }

        public void setUserPermissionFlag(int UserPermissionFlag) {
            this.UserPermissionFlag = UserPermissionFlag;
        }

        public Object getUserPwn() {
            return UserPwn;
        }

        public void setUserPwn(Object UserPwn) {
            this.UserPwn = UserPwn;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCorprationNO() {
            return CorprationNO;
        }

        public void setCorprationNO(String CorprationNO) {
            this.CorprationNO = CorprationNO;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public Object getShortName() {
            return ShortName;
        }

        public void setShortName(Object ShortName) {
            this.ShortName = ShortName;
        }

        public Object getComopanyDesc() {
            return ComopanyDesc;
        }

        public void setComopanyDesc(Object ComopanyDesc) {
            this.ComopanyDesc = ComopanyDesc;
        }

        public String getCompanyType() {
            return CompanyType;
        }

        public void setCompanyType(String CompanyType) {
            this.CompanyType = CompanyType;
        }

        public Object getAddress() {
            return Address;
        }

        public void setAddress(Object Address) {
            this.Address = Address;
        }

        public Object getLogoUrl() {
            return LogoUrl;
        }

        public void setLogoUrl(Object LogoUrl) {
            this.LogoUrl = LogoUrl;
        }
    }
}
