var GlobalStatApiDetailObj = {
    htmlId: "apiDetailWrap",
    init: function () {
        var strApiObj = $("#api_apiobj").html();
        var apiObj = {};
        try {
            apiObj = JSON.parse(strApiObj);
        }
        catch (ex) {
            apiObj = {};
        }
        var htmls = "";
        $.each(apiObj, function (key, val) {
            if (val == undefined) {
                val = "";
            }
            htmls += "<div style='width: 100%;padding-left: :5px; padding-right: 5px;'><span style='color: red;'>" + key + "</span>:" + val + "</div></br>";
        })
        $("#apiDetailWrap").html(htmls);
    }
};

$(function () {
    GlobalStatApiDetailObj.init();
});