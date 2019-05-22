/**
 * 应用列表管理初始化
 */
var DataList = {
    id: "DataListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DataList.initColumn = function () {
    return [
        {field: 'selectItem',radio:true},
        {field: 'id', visible: false},
        {field: 'objId', visible: false},
        {title: '名称', field: 'objName', align: 'center', valign: 'middle', sortable: true,width:'17%'},
        {title: '标识', field: 'objCode', align: 'center', valign: 'middle', sortable: true},
        {
            title: '预警人员', field: 'recObj', align: 'center', valign: 'middle', sortable: true
            , formatter: function (value, row, index) {
            if (value == "")
                return value;
            var recObjs = {};
            try {
                recObjs = JSON.parse(value);
            }catch(e) {
                recObjs = {};
                console.error("Parse Error..." + value + " is not a JSON String");
            }
            var cellHtmls = "";
            for (var rec in recObjs) {
                if (cellHtmls != "")
                    cellHtmls += "、";
                cellHtmls += recObjs[rec].recName;
            }
            return cellHtmls;
        }
        },
        // {title: '预警人员', field: 'recObj', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTimeDesc', align: 'center', valign: 'middle', sortable: true},
        {title: '更新时间', field: 'updateTimeDesc', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'monitorStatusDesc', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
DataList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DataList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加应用列表
 */
DataList.openAddDataList = function () {
    var index = layer.open({
        type: 2,
        title: '添加应用列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/checkData/dataList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看数据列表详情
 */
DataList.openDataListDetail = function () {
    var flag = this.check();
    var key = "recObjData" + DataList.seItem.id;
    var value = DataList.seItem.recObj;
    sessionStorage.setItem(key, value);
    if (flag) {
        var index = layer.open({
            type: 2,
            title: '修改数据监控配置',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/checkData/dataList_update/?id=' + DataList.seItem.id+'&objId='+DataList.seItem.objId+'&objName='+DataList.seItem.objName+'&objCode='+DataList.seItem.objCode+'&monitorStatus='+(isNaN(parseInt(DataList.seItem.monitorStatus))?"1":DataList.seItem.monitorStatus)
        });
        this.layerIndex = index;
    }
};

/**
 * 删除应用列表
 */
DataList.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/checkData/delete", function (data) {
                Feng.success("删除成功!");
                DataList.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",DataList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该应用?", operation);
    }
};
/**
 * 禁用
 */
DataList.disable = function () {

    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/checkData/disable", function (data) {
                Feng.success("成功!");
                DataList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",DataList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否禁用该应用?", operation);
    }
};
/**
 * 启用
 */
DataList.enable = function () {

    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/checkData/enable", function (data) {
                Feng.success("成功!");
                DataList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",DataList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否启用该应用?", operation);
    }
};

/**
 * 查询应用列表列表
 */
DataList.search = function () {
    var queryData = {};
    queryData['objName'] = $("#objName").val();
    DataList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = DataList.initColumn();
    var table = new BSTable(DataList.id, "/checkData/list", defaultColunms);
    table.setPaginationType("client");
    DataList.table = table.init();
});
