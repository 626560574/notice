/**
 * 应用列表管理初始化
 */
var ReceiverList = {
    id: "receiverListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ReceiverList.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '工号', field: 'recNo', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'recName', align: 'center', valign: 'middle', sortable: true},
        {title: '微信昵称', field: 'recWxNick', align: 'center', valign: 'middle', sortable: true},
        {title: '电话', field: 'recPhone', align: 'center', valign: 'middle', sortable: true},
        {title: '邮箱', field: 'recEmail', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'recStatusDesc', align: 'center', valign: 'middle', sortable: true},
        {title: '更新时间', field: 'updateTimeDesc', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
ReceiverList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ReceiverList.seItem = selected[0];
        return true;
    }
};

/**
 * 添加项目
 */
ReceiverList.openCreate = function () {
    var index = layer.open({
        type: 2,
        title: '添加预警对象',
        area: ['650px', '550px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/receiver/receiver_add'
    });
    this.layerIndex = index;
};

/**
 * 编辑项目
 */
ReceiverList.openDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑接口监控项目',
            area: ['650px', '550px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/receiver/receiver_update/' + ReceiverList.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
ReceiverList.delete = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/receiver/delete", function (data) {
                Feng.success("删除成功!");
                ReceiverList.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", ReceiverList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该应用?", operation);
    }
};
/**
 * 禁用
 */
ReceiverList.disable = function () {

    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/receiver/disable", function (data) {
                Feng.success("成功!");
                ReceiverList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", ReceiverList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否禁用该应用?", operation);
    }
};
/**
 * 启用
 */
ReceiverList.enable = function () {

    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/receiver/enable", function (data) {
                Feng.success("成功!");
                ReceiverList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", ReceiverList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否启用该应用?", operation);
    }
};

/**
 * 同步微信人员信息
 */
ReceiverList.syncwechat = function () {

    var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/receiver/syncwechat", function (data) {
            Feng.success("成功!");
            ReceiverList.table.refresh();
        }, function (data) {
            Feng.error("失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    }
    Feng.confirm("确定同步微信人员信息？", operation);
};

/**
 * 查询应用列表列表
 */
ReceiverList.search = function () {
    var queryData = {};
    queryData['searchKey'] = $.trim($("#searchKey").val());
    ReceiverList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ReceiverList.initColumn();
    var table = new BSTable(ReceiverList.id, "/receiver/list", defaultColunms);
    table.setPaginationType("client");
    ReceiverList.table = table.init();
});
