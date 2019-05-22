/**
 * 应用列表管理初始化
 */
var InterfaceList = {
    id: "InterfaceListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InterfaceList.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'proName', align: 'center', valign: 'middle', sortable: true},
        {
            title: '服务器地址', field: 'proAddress', align: 'center', valign: 'middle', sortable: true
            , formatter: function (value, row, index) {
            if (value == "")
                return value;
            var ipObjs = eval("(" + value + ")");
            var cellHtmls = "";
            for (var ip in ipObjs) {
                if (cellHtmls != "")
                    cellHtmls += "</br>";
                cellHtmls += ipObjs[ip];
            }
            return cellHtmls;
        }
        },
        {
            title: '预警人员', field: 'recObj', align: 'center', valign: 'middle', sortable: true
            , formatter: function (value, row, index) {
            if (value == "")
                return value;
            var recObjs = eval("(" + value + ")");
            var cellHtmls = "";
            for (var rec in recObjs) {
                if (cellHtmls != "")
                    cellHtmls += "、";
                cellHtmls += recObjs[rec].recName;
            }
            return cellHtmls;
        }
        },
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '更新时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'monitorStatusDesc', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
InterfaceList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        InterfaceList.seItem = selected[0];
        return true;
    }
};

/**
 * 添加项目
 */
InterfaceList.openAddInterfaceList = function () {
    var index = layer.open({
        type: 2,
        title: '添加接口监控项目',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/checkInterface/interfaceList_add'
    });
    this.layerIndex = index;
};

/**
 * 编辑项目
 */
InterfaceList.openInterfaceListDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑接口监控项目',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/checkInterface/interfaceList_update/' + InterfaceList.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
InterfaceList.delete = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkInterface/delete", function (data) {
                Feng.success("删除成功!");
                InterfaceList.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", InterfaceList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该应用?", operation);
    }
};
/**
 * 禁用
 */
InterfaceList.disable = function () {

    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkInterface/disable", function (data) {
                Feng.success("成功!");
                InterfaceList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", InterfaceList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否禁用该应用?", operation);
    }
};
/**
 * 启用
 */
InterfaceList.enable = function () {

    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkInterface/enable", function (data) {
                Feng.success("成功!");
                InterfaceList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", InterfaceList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否启用该应用?", operation);
    }
};

/**
 * 查询应用列表列表
 */
InterfaceList.search = function () {
    var queryData = {};
    queryData['proName'] = $("#proName").val();
    queryData['monitorStatus'] = $("#monitorStatus").val();
    InterfaceList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = InterfaceList.initColumn();
    var table = new BSTable(InterfaceList.id, "/checkInterface/list", defaultColunms);
    table.setPaginationType("client");
    InterfaceList.table = table.init();
});
