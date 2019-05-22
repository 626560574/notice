var GlobalStatApiObj = {
    htmlId: "info-list",
    getSourceData: function () {
        var strListJson = $("#list-json").text();
        var listJsonObj = [];
        if (strListJson == undefined || strListJson == "") {
            return listJsonObj;
        }
        try {
            listJsonObj = JSON.parse(strListJson);
        }
        catch (ex) {
            listJsonObj = []
        }
        return listJsonObj;
    },
    formatPros: function () {
        var htmls = "";
        var prosJsonObj = this.getSourceData();
        for (var p in prosJsonObj) {
            htmls += "<div style=\"width: 100%; padding: 0px; margin: 0px;\">";

            htmls += "  <div id=\"apiproject_" + prosJsonObj[p].id + "\" style=\"width: 100%; font-weight: bold; text-align: left;padding-left: 10px; vertical-align: middle; line-height: 30px; \">";
            htmls += prosJsonObj[p].proName;
            htmls += "</div>"

            htmls += "<div style=\"width: 100%;background-color: beige; border: 1px solid black; padding-left: 5px; padding-right: 5px; \">";
            htmls += "<a href=\"javascript:void(0);\" onclick=\"GlobalStatApiObj.proClick(" + prosJsonObj[p].id + ");\" style=\"line-height: 50px; margin-left: 20px;\" >点击加载统计详情</a>";
            htmls += "</div>";

            htmls += "</div>"
        }

        if (htmls == "") {
            htmls = "请在后台配置监控项目，配置后此处才能看到相关项目的监控统计哦";
        }
        $("#" + this.htmlId).html(htmls);
    },
    proClick: function (proId) {
        $.post("/mobile/stat/apiData", {proId: proId}, function (res) {
            if (res == undefined || res.apiPathStats == undefined || res.apiStats == undefined) {
                alert("获取数据出现异常，请联系开发人员排查问题");
                return;
            }
            var htmls = "异常在服务中分布如下：</br>";
            var exCount = 0;
            var todayCount = 0;
            for (var stat in res.apiStats) {
                exCount += res.apiStats[stat].exCount;
                todayCount += res.apiStats[stat].todayExCount;
                htmls += res.apiStats[stat].address + ":" + res.apiStats[stat].port + " 站点异常 " + res.apiStats[stat].exCount + " 次,今日 " + res.apiStats[stat].todayExCount + " 次</br>"
            }
            htmls = "近30日接口异常 " + exCount + " 次 今日接口异常 " + todayCount + " 次</br>" + htmls;

            htmls += "异常接口TOP10如下：</br>";
            for (var path in res.apiPathStats) {
                htmls += res.apiPathStats[path].path + " 共 " + res.apiPathStats[path].exCount + " 次,今日 " + res.apiPathStats[path].todayExCount + " 次</br>";
            }

            $("#apiproject_" + proId).next("div").html(htmls);
            $("#apiproject_" + proId).next("div").bind("click", function () {
                GlobalStatApiObj.wrapClick(proId);
            });

        }, "json");
    },
    wrapClick: function (proId) {
        location.href = "/mobile/stat/apiList?proId=" + proId + "&apiPath=";
    },
    init: function () {
        this.formatPros();
    }
};

$(function () {
    GlobalStatApiObj.init();
});