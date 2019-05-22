/**
 * 初始化应用列表详情对话框
 */
var InterfaceListInfoDlg = {
    interfaceListInfoData: {},
    recObjListJson: null
};

/**
 * 清除数据
 */
InterfaceListInfoDlg.clearData = function () {
    this.interfaceListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InterfaceListInfoDlg.set = function (key, val) {
    this.interfaceListInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InterfaceListInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
InterfaceListInfoDlg.close = function () {
    parent.layer.close(window.parent.InterfaceList.layerIndex);
}

/**
 * 收集数据
 */
InterfaceListInfoDlg.collectData = function () {
    this.set('proName').set('proAddress').set('recObj').set("monitorStatus").set("id");
}

/**
 * 检查用户输入数据
 * @param interfaceListInfoData
 * @returns {boolean}
 */
var checkInput = function (interfaceListInfoData) {

    var proName = interfaceListInfoData.proName;
    var proAddress = interfaceListInfoData.proAddress;

    if (proName.trim() == "") {
        Feng.error("项目名称不能为空");
        return false;
    }
    if (proAddress.trim() == "") {
        Feng.error("接口服务地址不能为空");
        return false;
    }

    return true;
};

/**
 * 初始化数据
 * @constructor
 */
InterfaceListInfoDlg.Init = function () {
    //判断是否是编辑操作
    InterfaceListInfoDlg.id = InterfaceListInfoDlg.get("id");
    if (InterfaceListInfoDlg.id == undefined || InterfaceListInfoDlg.id == "") {
        return;
    }
    //若是编辑操作则格式化服务器地址、用户等数据
    var strProAddress = $.trim($("#proAddressText").html());
    if (strProAddress != undefined && strProAddress != "") {
        var ipObjs = eval("(" + strProAddress + ")");
        var proAddressText = "";
        for (var ip in ipObjs) {
            if (proAddressText != "")
                proAddressText += "\n";
            proAddressText += ipObjs[ip];
        }
        $("#proAddress").val(proAddressText);
    }
    var strMonitorStatusValue = InterfaceListInfoDlg.get("monitorStatusValue");
    if (strMonitorStatusValue != undefined && strMonitorStatusValue != "") {
        $("#monitorStatus").val(strMonitorStatusValue);
    }

};

/**
 * 提交添加
 */
InterfaceListInfoDlg.addSubmit = function () {

    if (InterfaceListInfoDlg.id != "") {
        InterfaceListInfoDlg.editSubmit();
        return;
    }

    this.clearData();
    this.collectData();
    var isSubmit = checkInput(this.interfaceListInfoData);
    if (!isSubmit) {
        return false;
    }
    UserList.checkAdd();
    this.interfaceListInfoData.recObj = JSON.stringify(this.recObjListJson);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkInterface/add", function (data) {
        Feng.success("添加成功!");
        window.parent.InterfaceList.table.refresh();
        InterfaceListInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.interfaceListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
InterfaceListInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    var isSubmit = checkInput(this.interfaceListInfoData);
    UserList.checkAdd();
    this.interfaceListInfoData.recObj = JSON.stringify(this.recObjListJson);
    if (!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkInterface/update", function (data) {
        Feng.success("修改成功!");
        window.parent.InterfaceList.table.refresh();
        InterfaceListInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.interfaceListInfoData);
    ajax.start();
}

/**
 * 应用列表管理初始化
 */
var UserList = {
    id: "UserListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true, value: 'id'},
        {title: '工号', field: 'recNo', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'recName', align: 'center', valign: 'middle', sortable: true},
        {title: '手机号码', field: 'recPhone', align: 'center', valign: 'middle', sortable: true},
        {title: '邮箱', field: 'recEmail', align: 'center', valign: 'middle', sortable: true},
        {title: '微信名', field: 'recWxNick', align: 'center', valign: 'middle', sortable: true}
    ];
};

//获取选中行对象集合
UserList.GetSelections = function () {
    var selectRows = [];
    var rows = $('#' + this.id).bootstrapTable('getSelections');
    if (rows.length == 0) {
        return selectRows;
    }
    selectRows = rows;
    return selectRows;
};
/**
 * 检查是否选中
 */
UserList.checkAdd = function () {
    var rows = this.GetSelections();
    if (rows.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    else {
        var users = [];
        for (var i = 0; i < rows.length; i++) {
            users[i] = {id: rows[i].id, recNo: rows[i].recNo, recName: rows[i].recName};
        }
        InterfaceListInfoDlg.recObjListJson = users;
        return true;
    }
};

//获取选中的人员名称
UserList.GetSelectionsToNames = function () {
    var txtNames = '';
    var rows = this.GetSelections();
    if (rows.length == 0) {
        return txtNames;
    }
    for (var i = 0; i < rows.length; i++) {
        if (i > 0) {
            txtNames += ',';
        }
        txtNames += rows[i].recName;
    }
    return txtNames;
};

$(function () {
    InterfaceListInfoDlg.Init();
    var userListtColunms = UserList.initColumn();
    var usertable = new BSTable(UserList.id, "/checkInterface/userList", userListtColunms);
    usertable.setPaginationType("client");
    usertable.initChildren({
        showRefresh: false, showColumns: false
        , onCheck: function (row) {
            $("#receivers").val(UserList.GetSelectionsToNames());
        }
        , onUncheck: function (row) {
            $("#receivers").val(UserList.GetSelectionsToNames());
        }
        , checkboxHeader: false
        , height: 200
        , onPostBody: function () {
            //如果是编辑操作则在列表加载完成后选中相关人员
            if (InterfaceListInfoDlg.id == undefined || InterfaceListInfoDlg.id == "")
                return;
            var strRecObjs = $.trim($("#recObjText").text());
            if (strRecObjs == "")
                return;

            var recObjs = eval("(" + strRecObjs + ")");
            if (recObjs.length == 0)
                return;

            var allRows = $('#' + UserList.id).bootstrapTable('getData');
            if (allRows.length == 0) {
                return;
            }

            for (var row in allRows) {
                for (var rec in recObjs) {
                    if (recObjs[rec].id == allRows[row].id) {
                        $('#' + UserList.id).bootstrapTable('check', row);
                        break;
                    }
                }
            }
        }
    });
    UserList.table = usertable;
});
