/**
 * 初始化应用列表详情对话框
 */
var DataListInfoDlg = {
    dataListInfoData : {},
    staffnoList: null
};

/**
 * 清除数据
 */
DataListInfoDlg.clearData = function() {
    this.dataListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataListInfoDlg.set = function(key, val) {
    this.dataListInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DataListInfoDlg.close = function() {
    parent.layer.close(window.parent.DataList.layerIndex);
}

/**
 * 收集数据
 */
DataListInfoDlg.collectData = function() {
    this.set('objName').set('objCode').set('objId').set("recObj").set("monitorStatus").set("id");
}


/**
 * 提交修改
 */
DataListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    var isSubmit = UserList.checkAdd();

    this.dataListInfoData.recObj = this.recObjListJson;

    if(!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkData/add", function(data){
        Feng.success("修改成功!");
        window.parent.DataList.table.refresh();
        DataListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataListInfoData);
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
        {field: 'selectItem', radio: false, value: 'recWxNick'},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'recName', align: 'center', valign: 'middle', sortable: true, width: '17%'},
        {title: '手机', field: 'recPhone', align: 'center', valign: 'middle', sortable: true, width: '12%'},
        {title: '邮箱', field: 'recEmail', align: 'center', valign: 'middle', sortable: true}
    ];
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
        var recObjArray = [];
        for (var i = 0; i < rows.length; i++) {
            recObjArray[i] = {id: rows[i].id, recNo: rows[i].recNo, recWxNick: rows[i].recWxNick, recWxId: rows[i].recWxId, recName: rows[i].recName};
        }
        DataListInfoDlg.recObjListJson = JSON.stringify(recObjArray);
        return true;
    }
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
            DataListInfoDlg.id = DataListInfoDlg.get("id");
            //如果是编辑操作则在列表加载完成后选中相关人员
            if (DataListInfoDlg.id == undefined || DataListInfoDlg.id == "" || DataListInfoDlg.id == "0")
                return;
            var key = "recObjData" + DataListInfoDlg.id;
            var strRecObjs = sessionStorage.getItem(key);
            if (strRecObjs == "")
                return;
            var recObjs = {};
            try {
                recObjs = JSON.parse(strRecObjs);
            }catch(e) {
                console.error(e);
            }
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