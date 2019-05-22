/**
 * 初始化应用列表详情对话框
 */
var InterfaceListInfoDlg = {
    interfaceListInfoData : {},
    staffnoList: null
};

/**
 * 清除数据
 */
InterfaceListInfoDlg.clearData = function() {
    this.interfaceListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InterfaceListInfoDlg.set = function(key, val) {
    this.interfaceListInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InterfaceListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
InterfaceListInfoDlg.close = function() {
    parent.layer.close(window.parent.InterfaceList.layerIndex);
}

/**
 * 收集数据
 */
InterfaceListInfoDlg.collectData = function() {
    this.set('name').set('servicemac').set('staffno').set("status").set("id");
}


var checkInput = function(interfaceListInfoData) {

    var name = interfaceListInfoData.name;
    var servicemac = interfaceListInfoData.servicemac;

    if(name.trim() == "") {
        alert("名称不能为空");
        return false;
    }
    if(servicemac.trim() == "") {
        alert("服务器地址不能为空");
        return false;
    }
    return true;

};

/**
 * 提交添加
 */
InterfaceListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    // this.check();
    // this.checkAdd();
    var isSubmit = checkInput(this.interfaceListInfoData);
    if(!isSubmit) {
        return false;
    }
    UserList.checkAdd();
    this.interfaceListInfoData.staffno=this.staffnoList;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkInterface/add", function(data){
        Feng.success("添加成功!");
        window.parent.InterfaceList.table.refresh();
        InterfaceListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.interfaceListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
InterfaceListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    var isSubmit = checkInput(this.interfaceListInfoData);
    UserList.checkAdd();
    this.interfaceListInfoData.staffno=this.staffnoList;
    if(!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkInterface/update", function(data){
        Feng.success("修改成功!");
        window.parent.InterfaceList.table.refresh();
        InterfaceListInfoDlg.close();
    },function(data){
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
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '手机号码', field: 'phone', align: 'center', valign: 'middle', sortable: true},
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
        InterfaceListInfoDlg.staffnoList = userids;
        return true;
    }
};


$(function() {
    var userListtColunms = UserList.initColumn();
    var usertable = new BSTable(UserList.id, "/checkInterface/userList", userListtColunms);
    usertable.striped = true;
    usertable.showRefresh = false;
    usertable.showColumns = false;
    usertable.setPaginationType("client");
    UserList.table = usertable.initChildren();
});
