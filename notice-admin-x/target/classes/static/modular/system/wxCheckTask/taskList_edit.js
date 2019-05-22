/**
 * 初始化应用列表详情对话框
 */
var TaskListInfoDlg = {
    taskListInfoData : {},
    recObjListJson: null
};

/**
 * 清除数据
 */
TaskListInfoDlg.clearData = function() {
    this.taskListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TaskListInfoDlg.set = function(key, val) {
    this.taskListInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TaskListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TaskListInfoDlg.close = function() {
    parent.layer.close(window.parent.TaskList.layerIndex);
}

/**
 * 收集数据
 */
TaskListInfoDlg.collectData = function() {
    this.set('jobName').set('jobCode').set('jobId').set('recObj').set("monitorStatus").set("id");
}


/**
 * 提交添加
 */
TaskListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    var isSubmit = UserList.checkAdd();
    if(!isSubmit) {
        return false;
    }

    this.taskListInfoData.recObj = this.recObjListJson;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkTask/add", function(data){
        Feng.success("添加成功!");
        window.parent.TaskList.table.refresh();
        TaskListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.taskListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */

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
        TaskListInfoDlg.recObjListJson = JSON.stringify(recObjArray);
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
            TaskListInfoDlg.id = TaskListInfoDlg.get("id");
            //如果是编辑操作则在列表加载完成后选中相关人员
            if (TaskListInfoDlg.id == undefined || TaskListInfoDlg.id == "" || TaskListInfoDlg.id == "0")
                return;
            var key = "recObj" + TaskListInfoDlg.id;
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

