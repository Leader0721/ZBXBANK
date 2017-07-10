/**
 * Created by edwinZhang on 17/3/14.
 */

var nCreateType = 0;//用户：0；银行：1
var vectorObject = new Object();//原生代码传值到js的对象
var blIsCurrentStep = true;//是否当前步骤,如果是当前步骤则用户点击保存提交数据之后不做处理,如果不是当前步骤则用户点击保存提交之后跳转到下一步
var nStatusCode = 0;//审核状态
var arrId;//设置锚点定位数组

//需要处理财务数据%的字段
var arrTableRatioKey = ["CurrentRatio", "AssetLiiabilityRatio", "IncomeRatio", "AssetRation", "QuickRatio", "TotalAssetReturnRatio", "NetAssetReturnRation"];

//获取当前年份
function getNowYear() {
    var date = new Date();
    return date.getFullYear();
}

//获取当前日期example:2017-04-01
function getNowDate() {
    var date = new Date();
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

//获取当前月份
function getLastMonth() {
    var date = new Date();
    return date.getMonth()
}

//根据日期格式生成时间戳
function dateToTimestamp(strDate) {
    if (strDate) {
        var timestamp2 = Date.parse(new Date(strDate));
        timestamp2 = timestamp2 / 1000;
        return timestamp2;
    }
}

//根据当前年份,计算往年年份
//duration:需要减去的年份数
function computeYear(duration) {
    return getNowYear() - duration;
}

//| 日期时间有效性检查
//| 格式为：YYYY-MM-DD HH:MM:SS
function CheckDateTime(str) {
    if (typeof str != "string") return false;
    var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    var r = str.match(reg);
    if (r == null) return false;
    r[2] = r[2] - 1;
    var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
    if (d.getFullYear() != r[1]) return false;
    if (d.getMonth() != r[2]) return false;
    if (d.getDate() != r[3]) return false;
    if (d.getHours() != r[4]) return false;
    if (d.getMinutes() != r[5]) return false;
    if (d.getSeconds() != r[6]) return false;

    return true;
}

//判断是否是身份证的字段用
function getRegIDcard() {
    return "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
}

//验证身份证号全局方法
function validateIdCard(idCard) {
    //15位和18位身份证号码的正则表达式
    var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

    //如果通过该验证，说明身份证格式正确，但准确性还需计算
    if (regIdCard.test(idCard)) {
        if (idCard.length == 18) {
            var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
            var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
            var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (var i = 0; i < 17; i++) {
                idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
            }

            var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            var idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                if (idCardLast == "X" || idCardLast == "x") {
                    return true;
                } else {
                    return false;
                }
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast == idCardY[idCardMod]) {
                    return true
                } else {
                    return false;
                }
            }
        } else {
            return true;
        }
    } else {
        return false
    }
}

//检测是否是财务数据的百分比字段
function checkTableRatio(key) {
    if (arrTableRatioKey.indexOf(key) > -1) {
        return true;
    } else {
        return false;
    }
}

//获取input数组
function input(name) {
    return "input[name='" + name + "[]']";
}

//获取area数组
function area(name) {
    return "textarea[name='" + name + "[]']";
}

//获取select数组
function select(name) {
    return "select[name='" + name + "[]']";
}

//获取所有匹配name的数组
function allName(name) {
    return "*[name='" + name + "[]']";
}

//是否存在样式名称
function hasClass(obj, cls) {
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

//向布局对象中添加样式名称
function addClass(obj, cls) {
    if (!this.hasClass(obj, cls)) obj.className += " " + cls;
}

//从布局对象中移除对象名称
function removeClass(obj, cls) {
    if (hasClass(obj, cls)) {
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
        obj.className = obj.className.replace(reg, ' ');
    }
}

//objectName:json数组结构中的键值名称
//arguments:json数组结构中需要获取的键值名称数组
function createArrayObject(objectName) {
    var args = arguments;
    var objOffice = new Object();
    objOffice[objectName] = args;
    return objOffice;
}

//页面头部位置滚动到某个控件
function scrollOffset(elementItem) {
    if (elementItem) {
        $(window).scrollTop($(elementItem).offset());
        $(elementItem).focus();
    }
}

//检测input的name是否为数组
function isArryByForm(elementItem) {
    var elementName = new String($(elementItem).attr("name"));
    var index = elementName.indexOf("[]");
    if (index != -1) {
        return true;
    } else {
        return false;
    }
}

//获取距离屏幕最左边的绝对布局
function getAbsoluteLeft(obj) {
    var left = obj.offsetLeft;
    while (obj != null && obj.offsetParent != null && obj.offsetParent.tagName != 'BODY') {
        left += obj.offsetParent.offsetLeft;
        obj = obj.offsetParent;
    }
    return left;
}

//获取距离平布最顶部的绝对坐标
function getAbsoluteTop(obj) {
    var top = obj.offsetTop;
    while (obj != null && obj.offsetParent != null && obj.offsetParent.tagName != 'BODY') {
        top += obj.offsetParent.offsetTop;
        obj = obj.offsetParent;
    }
    return top;
}

//检测表格内容是否为空
function checkTableIsEmpty(elementItem, index) {
    var isArrayName = false;
    if (elementItem) {
        if (isArryByForm(elementItem)) {
            isArrayName = true;
            var eleItem = $(elementItem);
            if (eleItem.val() != "" && matchPattern(elementItem)) {
                eleItem.removeClass("tableEmptyBorder");
            } else {
                eleItem.addClass("tableEmptyBorder");
            }
        }
    }

    return isArrayName;
}

//判断输入框输入的值是否符合规则
function matchPattern(elementItem) {
    var isMatch = true;
    var pattern = $(elementItem).attr("pattern");
    if (pattern) {
        var isMatch = $(elementItem).val().match(pattern);
        if (!isMatch) {
            isMatch = false;
        } else {
            if (pattern == getRegIDcard()) {
                if (!validateIdCard($(elementItem).val())) {
                    isMatch = false;
                }
            }
        }
    } else if ($(elementItem).attr("title") == "rangeMaxDate") {
        var bRange = rangeMax(elementItem);
        if (bRange) {
            isMatch = false;
        }
    } else if ((elementItem.tagName == "SELECT")) {
        if ($(elementItem).val() == "0") {
            isMatch = false;
        }
    }
    return isMatch;
}

//判断表格中输入的值是否符合规则
function matchValidity(elementItem, index) {
    var isValidity = true;
    if (elementItem) {
        var eleItem = $(elementItem);
        if (!elementItem.validity.valid) {
            eleItem.addClass("tableEmptyBorder");
            isValidity = false;
        } else {
            eleItem.removeClass("tableEmptyBorder");
        }
    }

    return isValidity;
}

//检测文本内容是否为空
function checkIsEmpty(elementItem, index) {
    if (elementItem) {
        if (!checkTableIsEmpty(elementItem, index)) {
            if ($(elementItem).val() != "") {
                if (!matchPattern(elementItem)) {
                    $(elementItem).addClass("inputEmptyBorder");
                } else {
                    $(elementItem).removeClass("inputEmptyBorder");
                }
            } else {
                $(elementItem).addClass("inputEmptyBorder");
            }
        }
    }
}

//判断所有必填的值是否已填写
function checkEmptyOfAll(submitType) {
    var isCheck = false;
    if (!submitType) {
        return isCheck;
    }
    var isNone = financialBlock();//判断表格是否隐藏,隐藏的话需要显示出来才能获取offset
    var idAll = form.elements;
    for (var i = 0; i < idAll.length; i++) {
        var item = idAll[i];
        if (item) {
            if ($(item).attr("required") == "required") {
                if ($(item).val() == "") {
                    isCheck = true;
                } else if (!matchPattern(item)) {
                    isCheck = true;
                }
                checkIsEmpty(item, i);
            } else {
                if (!matchValidity(item, i)) {
                    isCheck = true;
                }
            }
        }
    }
    if (isNone) {
        financialNone();//如果表格手动显示了,在此处设置隐藏
    }
    return isCheck;
}

//判断所有必填的值是否已填写
function checkRegOfBank() {
    var isCheck = false;
    var idAll = form.elements;
    for (var i = 0; i < idAll.length; i++) {
        var item = idAll[i];
        if (item) {
            if ($(item).attr("required") == "required") {
                if ($(item).val() != "") {
                    if (!matchPattern(item)) {
                        isCheck = true;
                        $(item).addClass("inputEmptyBorder");
                    } else {
                        $(item).removeClass("inputEmptyBorder");
                    }
                } else {
                    $(item).removeClass("inputEmptyBorder");
                }
            } else {
                if (!matchValidity(item, i)) {
                    isCheck = true;
                }
            }
        }
    }

    return isCheck;
}

//对表单输入框进行焦点的初始化
function initFormFocus() {
    initInputByFixed();
    var idAll = form.elements;
    for (var i = 0; i < idAll.length; i++) {
        var item = idAll[i];
        if (item) {
            if (!isBankType()) {
                if (nStatusCode != 1 && nStatusCode != 2) {
                    $(item).focus(function () {
                        checkOneFocus(this);
                    });
                }
            } else {
                $(item).focus(function () {
                    checkOneFocus(this);
                });
            }
        }
    }
}

//输入框获取焦点后检测输入框的值是否符合规则
function checkOneFocus(element) {
    if (element) {
        var isNone = financialBlock();//判断表格是否隐藏,隐藏的话需要显示出来才能获取offset
        var idAll = form.elements;
        for (var i = 0; i < idAll.length; i++) {
            var item = idAll[i];
            if (item) {
                if (item == element) {
                    if (isNone) {
                        financialNone();//如果表格手动显示了,在此处设置隐藏
                    }
                    return;
                } else if ($(item).attr("required") == "required") {
                    checkIsEmpty(item, i);
                } else {
                    matchValidity(item, i);
                }
            }
        }
        if (isNone) {
            financialNone();//如果表格手动显示了,在此处设置隐藏
        }
    }
}

//json序列化
function jsonString(obj) {
    return JSON.stringify(obj);
}

//json转为object对象
function jsonParse(obj) {
    return JSON.parse(obj);
}

//如果输入框类型为number,则统一规范number的类型
function realValue(item) {
    if (item) {
        var vType = $(item).attr("type");
        if (vType == "number" && item.value != "") {
            return Number(item.value);
        } else {
            return item.value;
        }
    }
}

//替换表格中存在的%
function replacePercent(item) {
    if (item) {
        var vText = item.value.replace('%', '');
        var nRt = Number(vText);
        return nRt;
    }
}

//添加表格中带有%的比率
function addPercent(fValue) {
    if (fValue) {
        return Number(fValue) + "%";
    }
    return fValue;
}

//keyArray:组建json数据结构的数组
function getJsonByForm() {
    var keyArray = arguments;
    var obj = new Object();
    var eleAll = form.elements;

    //读取单项表单
    for (var i = 0; i < eleAll.length; i++) {
        var item = eleAll[i];
        if (item) {
            var idName = $(item).attr("id");
            if (idName) {
                if ($(item).attr("type") == "checkbox") {
                    var check = $(item).is(':checked');
                    obj[idName] = check ? 1 : 0;
                } else if (item.tagName == "SELECT") {
                    obj[idName] = parseInt($(item).val());
                } else {
                    obj[idName] = realValue(item);
                }
            } else {
                if ($(item).attr("type") == "radio") {
                    var check = $(item).is(':checked');
                    if (check) {
                        obj[item.name] = $(item).val();
                    }
                }
            }
        }
    }

    if (keyArray && keyArray.length > 0) {
        //读取数组表单
        for (var i = 0; i < keyArray.length; i++) {
            if (keyArray[i] != null) {
                if (typeof keyArray[i] == "object") {
                    for (var key in keyArray[i]) {
                        var itemArray = keyArray[i][key];
                        for (var idx = 0; idx < itemArray.length; idx++) {
                            $(itemArray[idx]).each(function (index, item) {
                                    var keyName = item.name.replace('[]', '');
                                    if (!obj[key]) {
                                        obj[key] = new Array();
                                    }
                                    if (!obj[key][index]) {
                                        obj[key][index] = new Object();
                                    }

                                    if (keyName == "Date") {
                                        var strDate = item.value;
                                        if (strDate) {
                                            if (index == 3 && strDate.indexOf("-") > 0 && $(item).attr("title") == "rangeMaxDate") {
                                                strDate = strDate.replace("-", "年");
                                                strDate += "月";
                                            }
                                        }
                                        obj[key][index][keyName] = strDate;

                                        //添加的DateTime
                                        var strDateTime = item.value;
                                        if (strDateTime) {
                                            if (index == 3 && strDateTime.indexOf("-") > 0) {
                                                strDateTime = strDateTime + "-01";
                                            } else if (index == 4) {
                                                strDateTime = "2014-04-01";
                                            } else {
                                                strDateTime = strDateTime.replace("年", "-");
                                                strDateTime = strDateTime + "01-01";
                                            }
                                        }
                                        obj[key][index]["DateTime"] = strDateTime;
                                    } else if (checkTableRatio(keyName)) {
                                        obj[key][index][keyName] = replacePercent(item);
                                    } else {
                                        obj[key][index][keyName] = realValue(item);
                                    }
                                }
                            );
                        }
                    }
                }
            }
        }
    }

    return obj;
}

//判断是保存还是提交
function initSubmitStatus() {
    if (isBankType()) {

        $("#saveStatus").css('display', "block");
    } else {
        $("#submitStatus").css('display', "block");

        $('#saveForm').click(function () {
            if (blIsCurrentStep) {
                saveData();
            } else {
                saveData(1);
            }
        });

        $('#submitForm').click(function () {
            saveData(1);
        });

        $('#submitStatus').click(function () {
            $('#submitStatus').popModal({

                html: $('#content'),

                placement: 'bottomRight',

                showCloseBut: false,

                onDocumentClickClose: false,

                onOkBut: function () {
                },

                onCancelBut: function () {
                },

                onLoad: function () {
                },

                onClose: function () {
                }

            });

        });

    }
}

//设置tab样式
function initTab() {
    $(".baseData").click(function () {

        $(".baseData").css({"color": "#31A4E5"});

        $(".financial").css({"color": "#555555"});


        $(".blueLine").animate({left: '50%'});

        $("#borrowerInfo").css({"display": "block"});
        $("#financialData").css({"display": "none"});
        $(".location").css({"display": "block"});
        $(document.body).css({"overflow": "auto", "overflow-y": "auto"});//非财务报表界面启用整页滚动
    });
    $(".financial").click(function () {

        $(".baseData").css({"color": "#555555"});
        $(".financial").css({"color": "#31A4E5"});

        $(".blueLine").animate({left: '0'});

        $("#financialData").css({"display": "block"});
        $("#borrowerInfo").css({"display": "none"});
        $(".location").css({"display": "none"});
        $(document.body).css({"overflow": "scroll", "overflow-y": "hidden"});//财务报表界面禁止整页滚动
    });


    $("#financialData").css({"display": "block"});
    $("#borrowerInfo").css({"display": "none"});
    $(".location").css({"display": "none"});
    $(document.body).css({"overflow": "scroll", "overflow-y": "hidden"});//财务报表界面禁止整页滚动
}

//处理ios设备中点击输入框,屏幕样式错乱问题(固定的导航条随滚动条上下移动)
function initInputByFixed() {
    if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) {
        $(document).on('focus', 'input, textarea', function () {
            $('header').css({'position': 'static'});
            $('.location').css({'position': 'absolute'});
            $('.content_multi').css({'padding-top': '0px'});
            $('.content').css({'padding-top': '0px'});
            $("#baseInfo p").css({'position': 'absolute'});

        });
        $(document).on('blur', 'input, textarea', function () {
            $('header').css({'position': 'fixed'});
            $('.location').css({'position': 'fixed'});
            $('.content_multi').css({'padding-top': '88px'});
            $('.content').css({'padding-top': '44px'});
        });
    }
}

//设置财务数据为显示状态
function financialBlock() {
    if ($("#financialData")) {
        if ($("#financialData").css("display") == "none") {
            $("#financialData").css({"display": "block"});
            return true;
        }
    }

    return false;
}

//设置财务数据为不可显示状态
function financialNone() {
    if ($("#financialData")) {
        $("#financialData").css({"display": "none"});
    }
}

//改变索引的背景色
function changeLocation(index) {
    $(".basic").each(function (i) {
        if (index == i) {
            $(this).css("background", "#86ceff");
        } else {
            $(this).css("background", "#fff");
        }
    });
}

//end 统计过去或今年的日期
function initFormDate(end) {
    $(allName("Date")).each(function (index, item) {
        if (end == 5) { //等于5是财务数据
            if (index < 3) {
                for (var i = 0; i < 10; i++) {
                    var ctYear = computeYear(i);
                    var strSelect = "";
                    if (3 - index == i) {
                        strSelect = "selected";
                    }
                    $(item).append("<option value='" + ctYear + "年' " + strSelect + ">" + ctYear + "年</option>");
                }
            } else {
                var value = "";
                if (index != end - 1) {
                    value = computeYear(end - 2 - index) + "-" + getLastMonth();
                } else {
                    value = "上年同期";
                }
                $(item).val(value);
            }
        } else { //其他表格的年份倒计
            var value = computeYear(end - index) + "年";
            $(item).val(value);
        }
    });

    //给填写日期赋值
    var inputDate = $("#InputDate");
    if (inputDate) {
        inputDate.val(getNowDate());
    }
}

//获取用户类型
function getCreateType() {
    return nCreateType;
}

//判断是否是银行方面
function isBankType() {
    return getCreateType() == 1;
}

//初始化索引锚点的id
function initPositionById() {
    arrId = arguments;
}

//根据当前页面位置,定位索引
function positionByScroll() {
    if (arrId.length > 0) {
        for (var k = 0; k < arrId.length; k++) {
            var keyItem = $("#" + arrId[k]);
            if (keyItem) {
                if ($(window).scrollTop() + 100 > keyItem.offset().top) {
                    changeLocation(k);
                }
            }
        }
    }
}

//页面顶部滚动到某个位置
//height:为滚动到顶部后的差值
function scrollToPlace(elemId, height) {
    var p = elemId || "body";
    var scHeight = height ? height : 44;
    var offset = $(p).offset();
    $("body,html").animate({
        scrollTop: offset.top - scHeight
    }, 300);
};

//如果是审核状态和已通过状态则显示预览其他步骤的菜单,屏蔽可修改的输入框
function shieldFormEdit() {
    if (!isBankType()) {
        if (nStatusCode == 1 || nStatusCode == 2) {
            $("#saveForm").css("display", "none");
            $("#submitForm").css("display", "none");
            $("#showFirst").css("display", "block");
            $("#showSecond").css("display", "block");
            $("#showThird").css("display", "block");
            $("#showFour").css("display", "block");

            var idAll = form.elements;
            for (var i = 0; i < idAll.length; i++) {
                var item = idAll[i];
                if (item) {
                    $(item).unbind();

                    if ($(item).attr("type") == "checkbox") {
                        item.onclick = function () {
                            return false;
                        };
                    } else if (item.tagName == "SELECT") {
                        $(item).attr("disabled", "disabled");
                    } else {
                        $(item).attr("readonly", "readonly")
                    }

                }
            }
        }
    }
}

//判断日期控件的最大值
function rangeMax(element) {
    var strDate = $(element).val();
    if (strDate) {
        var arrDate = strDate.split("-");
        if (arrDate.length > 1) {
            var nYear = getNowYear();
            var nMonth = getLastMonth() + 1;
            var inYear = parseInt(arrDate[0]);
            var inMonth = parseInt(arrDate[1]);

            if (inYear > nYear || (inYear >= nYear && inMonth > nMonth)) {
                alertMsg("财务数据中选择的时间不能大于当前月份");
                return true;
            }
        }
    }

    return false;
}

//固定财务报表表格的表头和首列
function fixedHeaderTable() {
    var right_div2 = document.getElementById("right_div2");
    right_div2.onscroll = function () {
        var right_div2_top = this.scrollTop;
        var right_div2_left = this.scrollLeft;
        document.getElementById("left_div2").scrollTop = right_div2_top;
        document.getElementById("right_div1").scrollLeft = right_div2_left;
    }
    //设置右边div宽度
    document.getElementById("right_div").style.width = "" + document.documentElement.clientWidth - 130 + "px";
    setInterval(function () {
        document.getElementById("right_div").style.width = "" + document.documentElement.clientWidth - 130 + "px";
    }, 0);

    document.getElementById("left_div2").style.height = "" + document.documentElement.clientHeight - 140 + "px";
    document.getElementById("right_div2").style.height = "" + document.documentElement.clientHeight - 140 + "px";
}

/**************原生态调用方法********************/
//设置用户还是银行
function setCreateType(type) {
    if (type) {
        nCreateType = type;
    }
}

//原生态代码传参数到js中
function setVector(vectorInfo) {
    if (vectorInfo) {
        vectorObject = jsonParse(vectorInfo);
        blIsCurrentStep = vectorObject.IsCurrent;
        if (vectorObject.StatusCode) {
            nStatusCode = vectorObject.StatusCode;
        }
        setCreateType(vectorObject.CreateType);
        shieldFormEdit();
    }
}

//设置审核状态
function setReviewStatus(nCode) {
    if (nCode) {
        nStatusCode = nCode;
    }
}

//解析json数据结构并进行form的赋值
function matchObjectValue(str) {
    if (str) {
        var formInfo = jsonParse(str);
        if (formInfo) {
            for (var key in formInfo) {
                if (typeof formInfo[key] == "object") {
                    //按顺序显示
                    if (key == "BranchOffice") {
                        formInfo[key].sort(compare("Order"));
                    }
                    if (key == "PaymentPlan") {
                        formInfo[key].sort(compare("Order"));
                    }
                    if (key == "RepaymentPlan") {
                        formInfo[key].sort(compare("Order1"));
                    }
                    for (var childkey in formInfo[key]) {
                        var itemObject = formInfo[key][childkey];
                        for (var skey in itemObject) {
                            if (skey == "Date") {
                                var strDate = itemObject[skey];
                                if (strDate) {
                                    if (childkey == formInfo[key].length - 2 && strDate.indexOf("年") > 0 && strDate.indexOf("月") > 0) {
                                        strDate = strDate.replace("年", "-");
                                        strDate = strDate.replace("月", "");
                                    } else {
                                        strDate = strDate.substr(0, 10);
                                    }
                                }
                                $(allName(skey))[childkey].value = strDate;
                            } else if ($(input(skey))[childkey]) {
                                if (CheckDateTime(itemObject[skey])) {
                                    $(input(skey))[childkey].value = itemObject[skey].substr(0, 10);
                                } else if (checkTableRatio(skey)) {
                                    $(input(skey))[childkey].value = addPercent(itemObject[skey]);
                                } else {
                                    $(input(skey))[childkey].value = itemObject[skey];
                                }
                            } else if ($(select(skey))[childkey]) {
                                $(select(skey))[childkey].value = itemObject[skey];
                            }
                        }
                    }
                } else {
                    if ($("#" + key)) {
                        if ($("#" + key).attr("type") == "checkbox") {
                            if (formInfo[key] == 1) {
                                $("#" + key).attr("checked", true);
                            }
                        }
                        else {
                            if (CheckDateTime(formInfo[key])) {
                                $("#" + key).val(formInfo[key].substr(0, 10));
                            } else {
                                $("#" + key).val(formInfo[key]);
                            }
                        }
                    }
                }
            }
        }


        vectorObject.FormInfo = jsonString(getJsonData());
    }
}
//定义一个比较器   结果升序 0，1，2，3，4
function compare(propertyName) {
    return function (object1, object2) {
        var value1 = object1[propertyName];
        var value2 = object2[propertyName];
        if (value2 > value1) {
            return -1;
        }
        else if (value2 < value1) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
//根据是否是当前步骤保存或提交表单数据
function save() {
    if (blIsCurrentStep) {
        saveData();
    } else {
        saveData(1);
    }
}

//返回上一步
function cancel() {
    goBackNative();
}

//submitType:(数据请求后的处理)0为不做处理,1为跳转下一步,2为返回上一步,3为审核
function saveBack(submitType) {
    if (submitType == 1) { //下一步
        gotoNextStep();
    } else if (submitType == 2) { //返回
        goBackNative();
    } else if (submitType == 3) { //审核
        nStatusCode = 0;
    }
}

//企业信息的赋值
function initBaseInfo(strBase) {
    if (strBase) {
        var baseInfo = jsonParse(strBase);
        if (baseInfo) {
            if (baseInfo.companyName && $("#companyName")) {
                $("#companyName").html(baseInfo.companyName);
            }
            if ($("#callPhone")) {
                if (baseInfo.companyTel) {
                    $("#callPhone").attr("href", "tel:" + baseInfo.companyTel);
                } else {
                    $("#callPhone").attr("href", "javascript:alertMsg('此企业没有电话')");
                }
            }
        }
    }
}

/**************调用原生态方法********************/
function goBackNative() {
    if (window.objectModel.goBack) {
        window.objectModel.goBack();
    }
}

function goBack() {
    var jsonData = jsonString(getJsonData());
    if (typeof vectorObject.FormInfo == "object") {
        if (jsonString(vectorObject.FormInfo) != jsonData) {
            showAlert();
            return;
        }
    } else if (typeof vectorObject.FormInfo == "string") {
        if (vectorObject.FormInfo != jsonData) {
            showAlert();
            return;
        }
    }

    goBackNative();
}

//提交数据时候,forminfo为对象的处理
//submitType:(数据请求后的处理)0为不做处理,1为跳转下一步,2为返回上一步,3为审核
//isShowMsg:是否需要显示后端的提示
function submitObject(object, url, submitType, isShowMsg) {
    if (window.objectModel) {
        vectorObject.FormInfo = object
        window.objectModel.submitObject(jsonString(vectorObject), url, submitType, isShowMsg ? isShowMsg : 0);
    }
}

//保存数据时候,forminfo为字符串的处理
function submitString(object, url, submitType) {
    if (window.objectModel) {
        vectorObject.FormInfo = jsonString(object);
        window.objectModel.submitObject(jsonString(vectorObject), url, submitType, 0);
    }
}

//提交数据时,直接提交键值对的处理
function submitFormUrlEncode(object, url, submitType) {
    if (window.objectModel) {
        var objectSubmit = vectorObject;
        for (var key in object) {
            objectSubmit[key] = object[key];
        }
        window.objectModel.submitFormUrlEncode(jsonString(objectSubmit), url, submitType);
    }
}

//选中企业详情中某一个cell的触发
function selectItem(itemName) {
    if (window.objectModel) {
        window.objectModel.selectItem(itemName);
    }
}

//原生的提示信息框
function alertMsg(msg) {
    if (window.objectModel) {
        window.objectModel.alertMsg(msg);
    }
}

//原生的选择框
function showAlert() {
    if (window.objectModel) {
        window.objectModel.showAlert();
    }
}

//原生跳转下一步
function gotoNextStep() {
    if (window.objectModel) {
        window.objectModel.gotoNextStep();
    }
}

//原生跳转到第几步
function pushStep(step) {
    if (window.objectModel) {
        window.objectModel.pushStep(step);
    }
}