$(function() {
    var listJson = $("#list-json").text();
    console.log(listJson);
    var list = JSON.parse(listJson);
    console.log(list);

    var str = "";
    for (var j = 0; j < list.length; j++) {
        str +=
            "            <div>\n" +
            "                <b><span style=\"display:inline-block; max-width: 8em;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;\">"+list[j].objName+"</span></b>\n" +
            "                <span style=\"float: right;\">"+list[j].logCreateTime+"</span>\n" +
            "            </div>\n" +
            "            <div>\n" +
            "                <span style='color: red;'>"+list[j].logContext+"</span>\n" +
            "            </div>\n" +
            "            <hr style=\"margin: 8px\"/>\n" +
            "        </div>"
    }

    console.log(str);
    $("#info-list").html(str);
});