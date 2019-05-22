/**
 * 初始化应用列表详情对话框
 */
var receiverInfoDlg = {
    receiverInfoDlg: {},
    recObjListJson: null
};

/**
 * 清除数据
 */
receiverInfoDlg.clearData = function () {
    this.receiverInfoDlg = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
receiverInfoDlg.set = function (key, val) {
    this.receiverInfoDlg[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
receiverInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
receiverInfoDlg.close = function () {
    parent.layer.close(window.parent.ReceiverList.layerIndex);
}

/**
 * 收集数据
 */
receiverInfoDlg.collectData = function () {
    this.set('recNo').set('recName').set('recPhone').set("recEmail").set("id").set("recStatus");
}

/**
 * 检查用户输入数据
 * @param receiverInfoDlg
 * @returns {boolean}
 */
var checkInput = function (receiverInfoDlg) {

    var recNo = receiverInfoDlg.recNo;
    var recName = receiverInfoDlg.recName;
    var recPhone = receiverInfoDlg.recPhone;
    var recEmail = receiverInfoDlg.recEmail;

    if (recNo.trim() == "") {
        Feng.error("人员工号不能为空");
        return false;
    }
    if (recName.trim() == "") {
        Feng.error("人员名称不能为空");
        return false;
    }
    if (recPhone.trim() == "") {
        Feng.error("人员电话不能为空");
        return false;
    }
    if (recEmail.trim() == "") {
        Feng.error("人员邮箱不能为空");
        return false;
    }

    return true;
};

/**
 * 初始化数据
 * @constructor
 */
receiverInfoDlg.Init = function () {
    //判断是否是编辑操作
    receiverInfoDlg.id = receiverInfoDlg.get("id");
    if (receiverInfoDlg.id == undefined)
        receiverInfoDlg.id = "";
    if (receiverInfoDlg.id == "") {
        return;
    }
    var strRecStatusValue = receiverInfoDlg.get("recStatusValue");
    if (strRecStatusValue != undefined && strRecStatusValue != "") {
        $("#recStatus").val(strRecStatusValue);
    }

};

/**
 * 提交添加
 */
receiverInfoDlg.addSubmit = function () {

    if (receiverInfoDlg.id != "") {
        receiverInfoDlg.editSubmit();
        return;
    }

    this.clearData();
    this.collectData();
    var isSubmit = checkInput(this.receiverInfoDlg);
    if (!isSubmit) {
        return false;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/receiver/add", function (data) {
        Feng.success("添加成功!");
        window.parent.ReceiverList.table.refresh();
        receiverInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.receiverInfoDlg);
    ajax.start();
}

/**
 * 提交修改
 */
receiverInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    var isSubmit = checkInput(this.receiverInfoDlg);
    if (!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/receiver/update", function (data) {
        Feng.success("修改成功!");
        window.parent.ReceiverList.table.refresh();
        receiverInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.receiverInfoDlg);
    ajax.start();
};

/**
 * 页面加载完成事件
 */
$(function () {
    receiverInfoDlg.Init();
});
