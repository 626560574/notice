$(function() {
    var listJson = $("#list-json").text();
    var list = JSON.parse(listJson);
    var str = "";
    for (var i = 0; i < list.length; i++) {

        str += "<div><div><h4><b>应用：" + list[i].appName +"</b></h4></div><hr style='margin: 8px'/>";
        var infoList = list[i].list;
        for (var j = 0; j < infoList.length; j++) {
            var count = parseInt(infoList[j].successCount) + parseInt(infoList[j].failCount);
            var todayCount = parseInt(infoList[j].todayFailCount) + parseInt(infoList[j].todaySuccessCount);
            str += "<a style='text-decoration:none;out-line: none;color: inherit; nonecolor: inherit;' href='/mobile/log/list?jobId="+infoList[j].jobId+"'><div>\n" +
                "            <div>\n" +
                "                <span style=\"display:inline-block; max-width: 8em;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;\">"+infoList[j].jobName+"</span>\n" +
                "                <span style=\"float: right;\">"+infoList[j].jobCreateTime+"</span>\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <span>累计执行"+count+"次，失败"+infoList[j].failCount+"次，比例"+infoList[j].failureRate+"%</span>\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <span>今日执行"+todayCount+"次，今日"+infoList[j].todayFailCount+"次，比例"+infoList[j].todayFailureRate+"%</span>\n" +
                "            </div>\n" +
                "        </div></a>" +
                "<hr style=\"margin: 8px\"/>\n"

        }
        str += "</div>"
    }

    $("#info-list").html(str);
});