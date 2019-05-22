package com.stylefeng.guns.modular.system.controller;


import com.giveu.notice.common.dto.ObjMonitorDTO;
import com.giveu.notice.common.vo.CheckDataVo;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.modular.system.service.ICheckDataService;
import com.stylefeng.guns.modular.system.service.IWxUserService;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: ScheduleCenter
 * @description: 数据预警
 * @author: qin
 * @create: 2018-08-18 15:00
 **/
@Controller
@RequestMapping("/checkData")
public class CheckDataController extends BaseController {
    private String PREFIX = "/system/wxCheckData/";

    @Autowired
    ICheckDataService checkDataService;
    @Autowired
    IWxUserService userService;
    /**
     * 跳转到列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dataList.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/dataList_add")
    public String appListAdd() throws IOException{
        return PREFIX + "dataList_add.html";
    }

    /**
     * 获取用户列表
     */
    @RequestMapping(value = "/userList")
    @ResponseBody
    public Object userList(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        userService.list(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 编辑时获取用户列表，用来绑定已选择用户
     */
    @RequestMapping(value = "/editUserList")
    @ResponseBody
    public Object editUserList(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        userService.editList(request, list);
        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }


    /**
     * 跳转到修改应用列表
     */
    @RequestMapping("/dataList_update")
    public String appListUpdate(HttpServletRequest request, Model model) throws IOException {
        String id = request.getParameter("id");
        String objId = request.getParameter("objId");
        String objCode = request.getParameter("objCode");
        String objName = request.getParameter("objName");
        String monitorStatus = request.getParameter("monitorStatus");
        ObjMonitorDTO objMonitorDTO = new ObjMonitorDTO();
        objMonitorDTO.setId(NumberUtils.toInt(id, 0));
        objMonitorDTO.setObjId(objId);
        objMonitorDTO.setObjCode(objCode);
        objMonitorDTO.setObjName(objName);
        objMonitorDTO.setMonitorStatus(NumberUtils.toInt(monitorStatus,1));
        model.addAttribute("objMonitorDTO", objMonitorDTO);
        return PREFIX + "dataList_edit.html";
    }

    /**
     * 获取应用列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        checkDataService.list(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增应用列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid ObjMonitorDTO objMonitorDTO, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Integer code = checkDataService.add(objMonitorDTO);

//        if (code == 519 || code.equals(519)) {
//            throw new Exception(" APPKEY 已存在");
//
//        }

        return super.SUCCESS_TIP;
    }

    /**
     * 删除列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "id") Integer id) {
        checkDataService.delete(id);
        return SUCCESS_TIP;
    }
    /**
     * 启用任务
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Object enable(@RequestParam(value = "id") Integer id) {
        checkDataService.enableById(id);
        return SUCCESS_TIP;
    }
    /**
     * 禁用任务
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Object disable(@RequestParam(value = "id") Integer id) {
        checkDataService.disableById(id);
        return SUCCESS_TIP;
    }


    /**
     * 修改任务
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid CheckDataVo checkDataVo, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        checkDataService.update(checkDataVo);
        return super.SUCCESS_TIP;
    }

    /**
     * 任务列表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
