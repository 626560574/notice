/**
 * 初始化任务列表详情对话框
 */
var JobListInfoDlg = {
    jobListInfoData : {}
};

/**
 * 清除数据
 */
JobListInfoDlg.clearData = function() {
    this.jobListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobListInfoDlg.set = function(key, val) {
    this.jobListInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
JobListInfoDlg.close = function() {
    parent.layer.close(window.parent.JobList.layerIndex);
}

/**
 * 收集数据
 */
JobListInfoDlg.collectData = function() {
    this.set('jobName').set('jobDesc').set('jobCode').set('appKey').set('cronExpression').set('jobCreateTime').set('callbackUrl').set('appKey');
}


var checkInput = function(jobListInfoData) {
    var jobName = jobListInfoData.jobName;
    var jobDesc = jobListInfoData.jobDesc;
    var jobCode = jobListInfoData.jobCode;
    var cronExpression = jobListInfoData.cronExpression;
    var callbackUrl = jobListInfoData.callbackUrl;
    var appKey = jobListInfoData.appKey;

    if(jobName.trim() == "") {
        alert("任务名称不能为空");
        return false;
    }
    if(jobDesc.trim() == "") {
        alert("任务描述不能为空");
        return false;
    }
    if(jobCode.trim() == "") {
        alert("任务编号不能为空");
        return false;
    }
    if(cronExpression.trim() == "") {
        alert("任务CRON表达式不能为空");
        return false;
    }
    if(callbackUrl.trim() == "") {
        alert("任务回调地址不能为空");
        return false;
    }
    if(appKey.trim() == "") {
        alert("请选择应用");
        return false;
    }
    return true;
};

/**
 * 提交添加
 */
JobListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    var isSubmit = checkInput(this.jobListInfoData);
    if(!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobList/add", function(data){
        Feng.success("添加成功!");
        window.parent.JobList.table.refresh();
        JobListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
JobListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    var isSubmit = checkInput(this.jobListInfoData);
    if(!isSubmit) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobList/update", function(data){
        Feng.success("修改成功!");
        window.parent.JobList.table.refresh();
        JobListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobListInfoData);
    ajax.start();
}

$(function() {

});
