var GlobalStatApiListObj = {
    htmlId: "apiList",
    proId: 0,
    proName: "",
    apiPath: "",
    currentPage: 0,
    setinitdate: function () {
        this.proId = parseInt($("#api_proId").html());
        this.proName = $("#api_proName").html();
        this.apiPath = $("#api_apiPath").html();
        $("#tool_proname").html(this.proName);
    },
    bindevent: function () {
        $("#tool_backprev").bind("click", function () {
            window.history.back();
        });
        $("#tool_pager").bind("click", function () {
            GlobalStatApiListObj.getPageData();
        });
    },
    init: function () {
        this.setinitdate();
        this.bindevent();
        this.getPageData();
    },
    gotodetail: function (apiId) {
        location.href = "/mobile/stat/apiDetail?apiId=" + apiId;
    },
    getPageData: function () {
        $.post("/mobile/stat/apiListData", {proId: this.proId, apiPath: this.apiPath, pageindex: this.currentPage}, function (res) {
            if (res == undefined) {
                alert("获取数据出现异常，请联系开发人员排查问题");
                return;
            }
            if (res.length == 0) {
                alert("只显示最近30天的数据,没有更多数据显示了");
                $("#tool_pager").hide();
                return;
            }
            var htmls = "";
            for (var api in res) {
                htmls += "<div style=\"width: 100%; line-height: 20px; padding-left: 5px; padding-right: 5px; border-bottom: 1px solid cornflowerblue;\">"
                htmls += "<a href=\"javascript:void(0);\" onclick='GlobalStatApiListObj.gotodetail(\"" + res[api].id + "\")' style='text-decoration:none; out-line: none;color: black;' >";
                htmls += res[api].requestTimeDesc + "</br>";
                htmls += "接口:" + res[api].path + "</br>";
                htmls += "服务器:" + res[api].localIpAddress + ":" + res[api].localPort + "</br>";
                htmls += "异常描述:" + res[api].exceptionMessage + "</br>";
                htmls += "</a>";
                htmls += "</div>";
            }
            $("#" + GlobalStatApiListObj.htmlId).append(htmls);
            GlobalStatApiListObj.currentPage++;
        }, "json");
    }
};

$(function () {
    GlobalStatApiListObj.init();
});