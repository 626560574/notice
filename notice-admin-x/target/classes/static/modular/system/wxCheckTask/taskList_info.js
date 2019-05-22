/**
 * 初始化应用列表详情对话框
 */
var TaskListInfoDlg = {
    taskListInfoData : {},
    staffnoList: null
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
    this.set('name').set('taskno').set('staffno').set("status").set("id");
}


var checkInput = function(taskListInfoData) {

    var name = taskListInfoData.name;
    var taskno = taskListInfoData.taskno;
    var servicemac = taskListInfoData.servicemac;

    if(name.trim() == "") {
        alert("任务名称不能为空");
        return false;
    }
    if(taskno.trim() == "") {
        alert("任务编码不能为空");
        return false;
    }

    return true;

};

/**
 * 提交添加
 */
TaskListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    // this.check();
    // this.checkAdd();
    var isSubmit = checkInput(this.taskListInfoData);
    if(!isSubmit) {
        return false;
    }
    UserList.checkAdd();
    this.taskListInfoData.staffno=this.staffnoList;
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
TaskListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    var isSubmit = checkInput(this.taskListInfoData);
    UserList.checkAdd();
    this.taskListInfoData.staffno=this.staffnoList;
    if(!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkTask/update", function(data){
        Feng.success("修改成功!");
        window.parent.TaskList.table.refresh();
        TaskListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.taskListInfoData);
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

/**
 * 检查是否选中
 */
UserList.checkAdd = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    else{
        var userids="";
        for ( var i = 0; i <selected.length; i++){
            userids=selected[i].userid+"_"+userids;
        }
        TaskListInfoDlg.staffnoList = userids;
        return true;
    }
};
// $(function() {
//     var userListtColunms = UserList.initColumn();
//     var usertable = new BSTable(UserList.id, "/checkTask/userList", userListtColunms);
//     usertable.setPaginationType("client");
//     UserList.table = usertable.initChildren();
// });

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
                    if (recObjs[rec].recId == allRows[row].id) {
                        $('#' + UserList.id).bootstrapTable('check', row);
                        break;
                    }
                }
            }
        }
    });
    UserList.table = usertable;
});