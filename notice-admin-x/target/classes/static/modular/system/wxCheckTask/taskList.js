/**
 * 应用列表管理初始化
 */
var TaskList = {
    id: "TaskListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TaskList.initColumn = function () {
    return [
        {field: 'selectItem',radio:true},
        {field: 'monitorStatus', visible: false},
        {field: 'id', visible: false},
        {field: 'jobId', visible: false},
        {title: '名称', field: 'jobName', align: 'center', valign: 'middle', sortable: true,width:'17%'},
        {title: '标识', field: 'jobCode', align: 'center', valign: 'middle', sortable: true,width:'12%'},
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
        // {title: '预警人员', field: 'recObj', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTimeDesc', align: 'center', valign: 'middle', sortable: true},
        {title: '更新时间', field: 'updateTimeDesc', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'monitorStatusDesc', align: 'center', valign: 'middle', sortable: true}
    ];
};



/**
 * 检查是否选中
 */
TaskList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TaskList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加应用列表
 */
TaskList.openAddTaskList = function () {
    var index = layer.open({
        type: 2,
        title: '添加应用列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/checkTask/taskList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看应用列表详情
 */
TaskList.openTaskListDetail = function () {
    var flag = this.check();
    var key = "recObj" + TaskList.seItem.id;
    var value = TaskList.seItem.recObj;
    sessionStorage.setItem(key, value);
    if (flag) {
        var index = layer.open({
            type: 2,
            title: '修改任务监控配置',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/checkTask/taskList_update?id='+TaskList.seItem.id+'&jobId='+ TaskList.seItem.jobId+'&jobName='+TaskList.seItem.jobName+'&jobCode='+TaskList.seItem.jobCode+'&monitorStatus='+(isNaN(parseInt(TaskList.seItem.monitorStatus))?"1":TaskList.seItem.monitorStatus)
        });
        this.layerIndex = index;
    }
};

/**
 * 删除应用列表
 */
TaskList.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/checkTask/delete", function (data) {
                Feng.success("删除成功!");
                TaskList.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",TaskList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该应用?", operation);
    }
};
/**
 * 禁用
 */
TaskList.disable = function () {

    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/checkTask/disable", function (data) {
                Feng.success("成功!");
                TaskList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",TaskList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否禁用该应用?", operation);
    }
};
/**
 * 启用
 */
TaskList.enable = function () {

    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/checkTask/enable", function (data) {
                Feng.success("成功!");
                TaskList.table.refresh();
            }, function (data) {
                Feng.error("失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",TaskList.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否启用该应用?", operation);
    }
};

/**
 * 查询应用列表列表
 */
TaskList.search = function () {
    var queryData = {};
    queryData['jobName'] = $("#jobName").val();
    queryData['jobCode'] = $("#jobCode").val();
    TaskList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TaskList.initColumn();
    var table = new BSTable(TaskList.id, "/checkTask/list", defaultColunms);
    table.setPaginationType("client");
    TaskList.table = table.init();
});
