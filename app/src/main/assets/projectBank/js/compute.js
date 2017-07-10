/**
 * Created by edwinZhang on 17/4/20.
 */
function ctIsMatch(jqitem){
    var isMatch = true;
    if (jqitem && jqitem.val() != "") {
        var pattern = jqitem.attr("pattern");
        if(pattern) {
            var isMatch = jqitem.val().match(pattern);
            if (!isMatch) {
                isMatch = false;
            }
        }
    }

    return isMatch
}

function ctByFixed(ctValue){
    var ctResult = ctValue;
    if (ctResult) {
        ctResult = ctResult.toFixed(2);
    }

    return ctResult;
}

function ctByPercent(ctValue){
    var ctResult = ctValue;
    if (ctResult) {
        ctResult = Math.round(ctValue.toFixed(2) * 100) + "%";
    }
    return ctResult;
}

function ctIsNotEmpty(jqitem){
    var isNotEmpty = true;
    if (jqitem.val() == "") {
        isNotEmpty = false;
    }
    return true;
}
//财务数据的计算
function ctCurrentRatio(index){
    var item1 = $($(input("CirculatingAssets"))[index]);//流动资产
    var item2 = $($(input("CashLiabilities"))[index]);//流动负债
    var item3 = $($(input("GrossLiabilities"))[index]);//总负债
    var item4 = $($(input("GeneralAssets"))[index]);//资产总额
    var item5 = $($(input("TotalInCome"))[index]);//利润总额
    var item6 = $($(input("MainTakeIn"))[index]);//主营业务收入
    var item7 = $($(input("MainCost"))[index]);//主营业务成本
    var item8 = $($(input("AccountsReceivable"))[index]);//应收帐款
    var item9 = $($(input("Stock"))[index]);//存货
    var item10 = $($(input("NetMargin"))[index]);//净利润
    var item11 = $($(input("OwnersEquity"))[index]);//所有者权益
    var item12 = $($(input("OperatingNetCashFlow"))[index]);//经营性净现金流量
    var item13 = $($(input("InvestNetCashFlow"))[index]);//投资活动净现金流量
    var item14 = $($(input("FinanceNetCashFlow"))[index]);//筹资活动净现金流量
    var item15 = $($(input("AffectofExchangeRate"))[index]);//现金汇率变动影响
    var item16 = $($(input("NetCashFlow"))[index]);//净现金流量
    var item17 = $($(input("FixedAssets"))[index]);//固定资产合计
    var item18 = $($(input("MonetaryResources"))[index]);//货币资金
    var item19 = $($(input("LongInvest"))[index]);//长期投资


    //流动比率 流动比率=流动资产/流动负债 百分比，取整
    if (ctIsMatch(item1) && ctIsMatch(item2)) {
        var rt1 = Number(item1.val());
        var rt2 = Number(item2.val());
        var ctResult = rt2 != 0 ? rt1 / rt2 : 0;

        $(input("CurrentRatio"))[index].value = ctByPercent(ctResult);
    }

    //资产负债率=总负债/资产总额 百分比，取整
    if (ctIsMatch(item3) && ctIsMatch(item4)) {
        var rt1 = Number(item3.val());
        var rt2 = Number(item4.val());
        var ctResult = rt2 != 0 ? rt1 / rt2 : 0;

        $(input("AssetLiiabilityRatio"))[index].value = ctByPercent(ctResult);
    }

    //销售利润率=利润总额/主营业务收入	百分比，取整
    if (ctIsMatch(item5) && ctIsMatch(item6)) {
        var rt1 = Number(item5.val());
        var rt2 = Number(item6.val());
        var ctResult = rt2 != 0 ? rt1 / rt2 : 0;

        $(input("IncomeRatio"))[index].value = ctByPercent(ctResult);
    }

    /*"第一年：资产利润率=利润总额/资产总额
    之后每年：资产利润率=当年利润总额*2/（当年资产总额+上一年资产总额）
    上年同期：资产利润率=上年同期利润总额*2/（上年同期资产总额+第二年资产总额）"	百分比，取整*/
    if (ctIsMatch(item5) && ctIsMatch(item4)) {
        var rt1 = Number(item5.val());
        var rt2 = Number(item4.val());
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("GeneralAssets"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            var rCount = rt2 + rt3;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;
        } else if (index == 4) {
            var lastItem = $($(input("GeneralAssets"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;

        } else {
            ctResult = rt2 != 0 ? rt1 / rt2 : 0;
        }
        $(input("AssetRation"))[index].value = ctByPercent(ctResult);
    }

    /*"第一年：应收帐款周转率=主营业务成本/应收帐款
    之后每年：应收账款周转率=当年主营业务收入*2/（当年应收账款+上一年应收账款）
    上年同期：应收账款周转率=上年同期主营业务收入*2/（上年同期应收账款+第二年应收账款）"	保留到小数点后两位*/
    if (ctIsMatch(item6) && ctIsMatch(item7) && ctIsMatch(item8)) {
        var rt0 = Number(item6.val());
        var rt1 = Number(item7.val());
        var rt2 = Number(item8.val());
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("AccountsReceivable"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt0 * 2) / (rt2 + rt3) : 0;
        } else if (index == 4) {
            var lastItem = $($(input("AccountsReceivable"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt0 * 2) / (rt2 + rt3) : 0;

        } else {
            ctResult = rt2 != 0 ? rt1 / rt2 : 0;
        }

        $(input("ReceivableTurnoverRate"))[index].value = ctByFixed(ctResult);
    }

    /*"第一年：存货周转率=主营业务成本/存货
    之后每年：存货周转率=当年主营业务成本*2/（当年存货+上一年存货）
    上年同期：存货周转率=上年同期主营业务成本*2/（上年同期存货+第二年存货）"	保留到小数点后两位*/
    if (ctIsMatch(item7) && ctIsMatch(item9)) {
        var rt1 = Number(item7.val());
        var rt2 = Number(item9.val());
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("Stock"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;
        } else if (index == 4) {
            var lastItem = $($(input("Stock"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;

        } else {
            ctResult = rt2 != 0 ? rt1 / rt2 : 0;
        }

        $(input("StockTurnoverRate"))[index].value = ctByFixed(ctResult);
    }

    /* 速动比率=(流动资产-存货）/流动负债	百分比，取整*/
    if (ctIsMatch(item1) && ctIsMatch(item2) && ctIsMatch(item9)) {
        var rt1 = Number(item1.val());
        var rt2 = Number(item2.val());
        var rt3 = Number(item9.val());
        var ctResult = rt2 != 0 ? (rt1 - rt3) / rt2 : 0;

        $(input("QuickRatio"))[index].value = ctByPercent(ctResult);
    }

    /* "总资产报酬率=利润总额/资产总额
    第二年到本年当月：总资产报酬率=当年利润总额*2/（当年资产总额+上年资产总额）
    上年同期：总资产报酬率=上年同期利润总额*2/（上年同期资产总额+第二年资产总额）"	百分比，取整*/
    if (ctIsMatch(item5) && ctIsMatch(item4)) {
        var rt1 = Number(item5.val());
        var rt2 = Number(item4.val());
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("GeneralAssets"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;
        } else if (index == 4) {
            var lastItem = $($(input("GeneralAssets"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;

        } else {
            ctResult = rt2 != 0 ? rt1 / rt2 : 0;
        }

        $(input("TotalAssetReturnRatio"))[index].value = ctByPercent(ctResult);
    }

    /*"净资产收益率=净利润/所有者权益
    第二年到本年当月：净资产收益率=当年净利润*2/（当年所有者权益+上年所有者权益）
    上年同期：净资产收益率=上年同期净利润*2/（上年同期所有者权益+第二年所有者权益）"	百分比，取整*/
    if (ctIsMatch(item10) && ctIsMatch(item11)) {
        var rt1 = Number(item10.val());
        var rt2 = Number(item11.val());
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("OwnersEquity"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;
        } else if (index == 4) {
            var lastItem = $($(input("OwnersEquity"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = (rt2 + rt3) != 0 ? (rt1 * 2) / (rt2 + rt3) : 0;

        } else {
            ctResult = rt2 != 0 ? rt1 / rt2 : 0;
        }

        $(input("NetAssetReturnRation"))[index].value = ctByPercent(ctResult);
    }

    /*现金流量正确为0=经营性净现金流量+投资活动净现金流量+筹资活动净现金流量+现金汇率变动影响-净现金流量	取整*/
    if (ctIsMatch(item12) || ctIsMatch(item13) || ctIsMatch(item14) || ctIsMatch(item15) || ctIsMatch(item16)) {
        var rt1 = item12.val() ? Number(item12.val()) : 0;
        var rt2 = item13.val() ? Number(item13.val()) : 0;
        var rt3 = item14.val() ? Number(item14.val()) : 0;
        var rt4 = item15.val() ? Number(item15.val()) : 0;
        var rt5 = item16.val() ? Number(item16.val()) : 0;
        var ctResult = rt1 + rt2 + rt3 + rt4 - rt5;

        $(input("CashFlowRight1"))[index].value = parseInt(ctResult);
    }

    /*"第二年开始到当年当月：现金流量正确为0=当年货币资金-上一年货币资金-当年净现金流量
    上年同期：现金流量正确为0=上年同期货币资金-第二年货币资金-上年同期净现金流量	取整*/
    if (ctIsMatch(item18) || ctIsMatch(item16)) {
        var rt1 = item18.val() ? Number(item18.val()) : 0;
        var rt2 = item16.val() ? Number(item16.val()) : 0;
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("MonetaryResources"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = rt1 - rt3 - rt2;
        } else if (index == 4) {
            var lastItem = $($(input("MonetaryResources"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;;
            ctResult = rt1 - rt3 - rt2;
        }

        $(input("CashFlowRight2"))[index].value = parseInt(ctResult);
    }

    /* 验证填写是否正确=资产总额-总负债-所有者权益	取整*/
    if (ctIsMatch(item4) || ctIsMatch(item3) || ctIsMatch(item11)) {
        var rt1 = item4.val() ? Number(item4.val()) : 0;
        var rt2 = item3.val() ? Number(item3.val()) : 0;
        var rt3 = item11.val() ? Number(item11.val()) : 0;
        var ctResult = rt1 - rt2 - rt3;

        $(input("InputRight"))[index].value = parseInt(ctResult);
    }

    /* 全填完为0则正确=资产总额-固定资产合计-流-长期投资动资产	取整*/
    if (ctIsMatch(item4) || ctIsMatch(item17) || ctIsMatch(item1) || ctIsMatch(item19)) {
        var rt1 = item4.val() ? Number(item4.val()) : 0;
        var rt2 = item17.val() ? Number(item17.val()) : 0;
        var rt3 = item1.val() ? Number(item1.val()) : 0;
        var rt4 = item19.val() ? Number(item19.val()) : 0;
        var ctResult = rt1 - rt2 - rt3 - rt4;

        $(input("AllRight"))[index].value = parseInt(ctResult);
    }


    /* "第二年开始到当年当月：所有则权益正确0=当年所有者权益-上一年所有者权益-当年净利润
    上年同期：所有则权益正确0=上年同期所有者权益-第二年所有者权益-上年同期净利润"	取整*/
    if (ctIsMatch(item11) || ctIsMatch(item10)) {
        var rt1 = item11.val() ? Number(item11.val()) : 0;
        var rt2 = item10.val() ? Number(item10.val()) : 0;
        var ctResult = 0;
        if (index > 0 && index < 4) {
            var lastItem = $($(input("OwnersEquity"))[index-1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = rt1 - rt3 - rt2;
        } else if (index == 4) {
            var lastItem = $($(input("OwnersEquity"))[1]);
            var rt3 = lastItem.val() ? Number(lastItem.val()) : 0;
            ctResult = rt1 - rt3 - rt2;
        }

        $(input("OwnerShipRight"))[index].value = parseInt(ctResult);
    }

}

function ctOurLoan(){
    var item1 = $("#OurLoan");
    var item2 = $("#NormalOfFive");
    if (item1 && item2) {
        item2.val(item1.val());
    }
}

function ctOtherBankPromise(){
    var item1 = $($(input("PayableBill"))[3]);//应付票据
    var item2 = $("#OurPromise");
    var item3 = $("#OtherBankPromise");
    if (item1 && item2 && item3) {
        //本年当月应付票据-我社承兑
        item3.val(item1.val() - item2.val());
    }
}

//需求测算表的计算
function ctDemandMeasure(){
    var item1 = $("#MainTakeIn1");//销售收入
    var item2 = $("#MainTakeIn2");
    var item3 = $("#MainTakeIn3");

    var item4 = $("#MainCost1");//销售成本
    var item5 = $("#MainCost2");
    var item6 = $("#MainCost3");

    var item7 = $("#MainIncome1");//销售利润
    var item8 = $("#MainIncome2");
    var item9 = $("#MainIncome3");

    var item10 = $("#AccountsReceivable1");//应收账款
    var item11 = $("#AccountsReceivable2");
    var item12 = $("#AccountsReceivable3");

    var item13 = $("#DepositReceived1");//预收账款
    var item14 = $("#DepositReceived2");
    var item15 = $("#DepositReceived3");

    var item16 = $("#Stock1");//存货
    var item17 = $("#Stock2");
    var item18 = $("#Stock3");

    var item19 = $("#PrepaidAccount1");//预付账款
    var item20 = $("#PrepaidAccount2");
    var item21 = $("#PrepaidAccount3");

    var item22 = $("#PayableAccount1");//应付账款
    var item23 = $("#PayableAccount2");
    var item24 = $("#PayableAccount3");

    //（本年度销售收入+上年度销售收入）/2
    if (ctIsNotEmpty(item1) || ctIsNotEmpty(item2)) {
        var ct1 = Number(item1.val());
        var ct2 = Number(item2.val());
        var ctRet = (ct1 + ct2)/2;
        item3.val(parseInt(ctRet));
    }

    //（本年度销售成本+上年度销售成本）/2
    if (ctIsNotEmpty(item4) || ctIsNotEmpty(item5)) {
        var ct1 = Number(item4.val());
        var ct2 = Number(item5.val());
        var ctRet = (ct1 + ct2)/2;
        item6.val(parseInt(ctRet));
    }

    //（本年度销售利润+上年度销售利润）/2
    if (ctIsNotEmpty(item7) || ctIsNotEmpty(item8)) {
        var ct1 = Number(item7.val());
        var ct2 = Number(item8.val());
        var ctRet = (ct1 + ct2)/2;
        item9.val(parseInt(ctRet));
    }

    //（期初余额+期末余额）/2
    if (ctIsNotEmpty(item10) || ctIsNotEmpty(item11)) {
        var ct1 = Number(item10.val());
        var ct2 = Number(item11.val());
        var ctRet = (ct1 + ct2)/2;
        item12.val(parseInt(ctRet));
    }

    //（期初余额+期末余额）/2
    if (ctIsNotEmpty(item13) || ctIsNotEmpty(item14)) {
        var ct1 = Number(item13.val());
        var ct2 = Number(item14.val());
        var ctRet = (ct1 + ct2)/2;
        item15.val(parseInt(ctRet));
    }

    //（期初余额+期末余额）/2
    if (ctIsNotEmpty(item16) || ctIsNotEmpty(item17)) {
        var ct1 = Number(item16.val());
        var ct2 = Number(item17.val());
        var ctRet = (ct1 + ct2)/2;
        item18.val(parseInt(ctRet));
    }

    //（期初余额+期末余额）/2
    if (ctIsNotEmpty(item19) || ctIsNotEmpty(item20)) {
        var ct1 = Number(item19.val());
        var ct2 = Number(item20.val());
        var ctRet = (ct1 + ct2)/2;
        item21.val(parseInt(ctRet));
    }

    //（期初余额+期末余额）/2
    if (ctIsNotEmpty(item22) || ctIsNotEmpty(item23)) {
        var ct1 = Number(item22.val());
        var ct2 = Number(item23.val());
        var ctRet = (ct1 + ct2)/2;
        item24.val(parseInt(ctRet));
    }

    //存货周转天数
    var item25 = $("#StockTurnoverDays1");
    //若存货平均值为0，则存货周转天数为0；
    //若存货平均值不为0，则存货周转天数=360/（本年度销售成本/存货平均值）
    if (ctIsNotEmpty(item4) && ctIsNotEmpty(item18)) {
        var ctRet = 0;
        var t1 = Number(item18.val());
        var t2 = Number(item4.val());
        if (t1 != 0 && t2 != 0) {
            ctRet = 360 / (t2 / t1);
        }
        item25.val(parseInt(ctRet));
    }

    //应收账款周转天数
    var item26 = $("#AccountsReceivableTurnoverDays1");
    //若应收账款平均值为0，则应收账款周转天数为0；
    //若应收账款平均值不为0，则应收账款周转天数=360/（本年度销售收入/应收账款平均值）
    if (ctIsNotEmpty(item1) && ctIsNotEmpty(item12)) {
        var ctRet = 0;
        var t1 = Number(item12.val());
        var t2 = Number(item1.val());
        if (t1 != 0 && t2 != 0) {
            ctRet = 360 / (t2 / t1);
        }

        item26.val(parseInt(ctRet));
    }

    //应付账款周转天数
    var item27 = $("#PayableAccountTurnoverDays1");
    //若应付账款平均值为0，则应付账款周转天数为0；
    //若应付账款平均值不为0，则应付账款周转天数=360/（本年度销售成本/应付账款平均值）
    if (ctIsNotEmpty(item4) && ctIsNotEmpty(item24)) {
        var ctRet = 0;
        var t1 = Number(item24.val());
        var t2 = Number(item1.val());
        if (t1 != 0 && t2 != 0) {
            ctRet = 360 / (t2 / t1);
        }

        item27.val(parseInt(ctRet));
    }


    //预付账款周转天数
    var item28 = $("#PrepaidAccountTurnoverDays1");
    var item29 = $("#PrepaidAccountTurnoverDays2");
    //若预付账款平均值为0，则预付账款周转天数为0；
    //若预付账款平均值不为0，则预付账款周转天数=360/（本年度销售成本/预付账款平均值）
    if (ctIsNotEmpty(item4) && ctIsNotEmpty(item21)) {
        var ctRet = 0;
        var t1 = Number(item21.val());
        var t2 = Number(item4.val());
        if (t1 != 0 && t2 != 0) {
            ctRet = 360 / (t2 / t1);
        }

        item28.val(parseInt(ctRet));
        item29.val(parseInt(ctRet));
    }


    //预收账款周转天数
    var item30 = $("#DepositReceivedTurnoverDays1");
    var item31 = $("#DepositReceivedTurnoverDays2");
    //若预收账款平均值为0，则预收账款周转天数为0；
    //若预收账款平均值不为0，则预收账款周转天数=360/（本年度销售收入/预收账款平均值）
    if (ctIsNotEmpty(item1) && ctIsNotEmpty(item15)) {
        var ctRet = 0;
        var t1 = Number(item15.val());
        var t2 = Number(item4.val());
        if (t1 != 0 && t2 != 0) {
            ctRet = 360 / (t2 / t1);
        }

        item30.val(parseInt(ctRet));
        item31.val(parseInt(ctRet));
    }

    //营运资金周转次数--调整前
    var item32 = $("#WorkingCapitalTurnoverDays1");
    //营运资金周转次数=360/（存货周转天数+应收账款周转天数-应付账款周转天数+预付账款周转天数-预收账款周转天数）
    var nWorkingCap1 = 0
    if (item25 && item26 && item27 && item28 && item30) {
        var ctRet = 0;
        var t1 = Number(item25.val()) + Number(item26.val()) - Number(item27.val()) + Number(item28.val()) - Number(item30.val());
        if (t1 != 0) {
            ctRet = 360 / t1;
        }
        nWorkingCap1 = ctRet;
        item32.val(parseInt(ctRet));
    }

    //营运资金周转次数--调整后
    var item33 = $("#WorkingCapitalTurnoverDays2");
    var item__sock = $("#StockTurnoverDays2");
    var item__accounts = $("#AccountsReceivableTurnoverDays2");
    var item__pay = $("#PayableAccountTurnoverDays2");
    var item__prepaid = $("#PrepaidAccountTurnoverDays2");
    var item__deposit = $("#DepositReceivedTurnoverDays2");

    //营运资金周转次数=360/（存货周转天数+应收账款周转天数-应付账款周转天数+预付账款周转天数-预收账款周转天数）
    var nWorkingCap2 = 0
    if (item__sock && item__accounts && item__pay) {
        var ctRet = 0;
        var t1 = Number(item__sock.val()) + Number(item__accounts.val()) - Number(item__pay.val()) + Number(item__prepaid.val()) - Number(item__deposit.val());
        if (t1 != 0) {
            ctRet = 360 / t1;
        }
        nWorkingCap2 = ctRet;
        item33.val(ctByFixed(ctRet));
    }

    //上年度销售利润率
    var nLastIncom = 0;
    var item34 = $("#LastYearMainIncomRate1");
    var item35 = $("#LastYearMainIncomRate2");
    //上年度销售利润率=上年度销售利润/上年度销售成本
    if (item8 && item5) {
        var ctRet = 0;
        var t1 = Number(item8.val());
        var t2 = Number(item5.val());
        if (t2 != 0) {
            ctRet = t1 / t2;
        }
        nLastIncom = ctRet;
        item34.val(ctByPercent(ctRet));
        item35.val(ctByPercent(ctRet));
    }

    //预计销售收入年增长率
    var item36 = $("#ForcastIncreaseRate1");
    var item37 = $("#ForcastIncreaseRate2");
    if (item36 && item37) {
        if (item36.val() != "") {
            item37.val(item36.val());
        }
    }

    //营运资金量--调整前
    var item38 = $("#WorkingCapital1");
    //营运资金量=上年度销售收入*（1-上年度销售利润率）*（1+预计销售收入年增长率）/营运资金周转次数
    if (item2 && item36 && nWorkingCap1 != 0) {
        var ctRet = 0;
        var t1 = Number(item2.val());
        ctRet = t1 * (1-nLastIncom)*(1+Number(item36.val())/100)/nWorkingCap1;
        item38.val(parseInt(ctRet));
    }

    //营运资金量--调整后
    var item39 = $("#WorkingCapital2");
    //营运资金量=上年度销售收入*（1-上年度销售利润率）*（1+预计销售收入年增长率）/营运资金周转次数
    if (item2 && item37 && nWorkingCap2 != 0) {
        var ctRet = 0;
        var t1 = Number(item2.val());
        ctRet = t1 * (1-nLastIncom)*(1+Number(item37.val())/100)/nWorkingCap2;
        item39.val(ctByFixed(ctRet));
    }

    //调整前测算的流贷需求
    var item40 = $("#InternalFund");
    var item41 = $("#FlowFundLoan");
    var item42 = $("#OtherWorkingCapital");
    var item43 = $("#LoanDemandBefore");
    //调整前测算的流贷需求=营运资金量-借款人自有资金-现有流动资金贷款-其他渠道提供的营运资金；
    if (item38) {
        var ctRet = 0;
        var t1 = Number(item38.val());
        var t2 = Number(item40.val());
        var t3 = Number(item41.val());
        var t4 = Number(item42.val());
        ctRet =  t1 - t2 - t3 - t4;
        item43.val(parseInt(ctRet));
    }

    //调整后测算的流贷需求
    var item44 = $("#LoanDemandAfter");
    //调整后测算的流贷需求=调整后营运资金量-借款人自有资金-现有流动资金贷款-其他渠道提供的营运资金。
    if (item39) {
        var ctRet = 0;
        var t1 = Number(item39.val());
        var t2 = Number(item40.val());
        var t3 = Number(item41.val());
        var t4 = Number(item42.val());
        ctRet =  t1 - t2 - t3 - t4;
        item44.val(ctByFixed(ctRet));
    }

}

//基本情况调查表的计算
function ctBasic() {
    var ctResult = [[0,0],[0,0],[0,0],[0,0]];
    var ctArr = ["CuRmb","CuForeign","OfRmb","OfForeign","TfRmb","TfForeign"];
    for (var ctIndex in ctArr) {
        var cuRmbComponets = $(input(ctArr[ctIndex]));
        var nCuRmb = 0;

        $(cuRmbComponets).each(function (index, item) {
            if (index == 4) {
                item.value = nCuRmb;
            } else {
                if (ctIndex < 4) {
                    ctResult[index][ctIndex % 2] += Number(item.value);
                } else {
                    item.value = ctResult[index][parseInt(ctIndex - 4)];
                }
                nCuRmb += Number(item.value);
            }
        });
    }
}