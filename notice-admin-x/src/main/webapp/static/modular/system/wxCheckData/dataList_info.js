/**
 * 初始化应用列表详情对话框
 */
var DataListInfoDlg = {
    dataListInfoData : {},
    staffnoList:null
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
    this.set('name').set('comment').set('identification').set("status").set("id");
}


var checkInput = function(dataListInfoData) {

    var name = dataListInfoData.name;
    var identification = dataListInfoData.identification;

    if(name.trim() == "") {
        alert("名称不能为空");
        return false;
    }
    if(identification.trim() == "") {
        alert("标示能为空");
        return false;
    }

    return true;

};

/**
 * 提交添加
 */
DataListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    // this.check();
    // this.checkAdd();
    var isSubmit = checkInput(this.dataListInfoData);
    if(!isSubmit) {
        return false;
    }
    UserList.checkAdd();
    this.dataListInfoData.staffno=this.staffnoList;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkData/add", function(data){
        Feng.success("添加成功!");
        window.parent.DataList.table.refresh();
        DataListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
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
        {field: 'selectItem', radio: false},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true,width:'17%'},
        {title: '手机号码', field: 'phone', align: 'center', valign: 'middle', sortable: true,width:'12%'},
        {title: '邮箱', field: 'email', align: 'center', valign: 'middle', sortable: true}
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
        DataListInfoDlg.staffnoList = userids;
        return true;
    }
};
// /**
//  * 检查是否选中
//  */
// UserList.check = function () {
//     var selected = $('#' + this.id).bootstrapTable('getSelections');
//     if(selected.length == 0){
//         Feng.info("请先选中表格中的某一记录！");
//         return false;
//     }
//     else{
//         TaskListInfoDlg.set = userids;
//         return true;
//     }
// };
$(function() {
    var userListtColunms = UserList.initColumn();
    var usertable = new BSTable(UserList.id, "/checkData/userList", userListtColunms);
    usertable.setPaginationType("client");
    UserList.table = usertable.initChildren();
});
