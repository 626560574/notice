package com.stylefeng.guns.modular.system.controller;

import com.giveu.notice.common.vo.AppVo;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.modular.system.service.IAppListService;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
 * 应用列表控制器
 *
 * @author fengshuonan
 * @Date 2018-07-03 17:28:13
 */
@Controller
@RequestMapping("/appList")
public class AppListController extends BaseController {

    private String PREFIX = "/system/appList/";

    @Autowired
    IAppListService appListService;

    /**
     * 跳转到应用列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "appList.html";
    }

    /**
     * 跳转到添加应用列表
     */
    @RequestMapping("/appList_add")
    public String appListAdd() {
        return PREFIX + "appList_add.html";
    }

    /**
     * 跳转到修改应用列表
     */
    @RequestMapping("/appList_update/{appListId}")
    public String appListUpdate(@PathVariable String appListId, Model model) throws IOException {
        AppVo appList = appListService.getAppVoById(appListId);
        model.addAttribute("appList", appList);
        return PREFIX + "appList_edit.html";
    }

    /**
     * 获取应用列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        appListService.list(request, list);

        List<Map<String, Object>> menus = list;
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增应用列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid AppVo appVo, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Integer code = appListService.addApp(appVo);

        if (code == 519 || code.equals(519)) {
            throw new Exception(" APPKEY 已存在");

        }

        return super.SUCCESS_TIP;
    }

    /**
     * 删除应用列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "id") String id) {
        appListService.delAppById(id);
        return SUCCESS_TIP;
    }
    /**
     * 删除应用列表
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Object enable(@RequestParam(value = "id") String id) {
        appListService.enableById(id);
        return SUCCESS_TIP;
    }
    /**
     * 删除应用列表
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Object disable(@RequestParam(value = "id") String id) {
        appListService.disableById(id);
        return SUCCESS_TIP;
    }


    /**
     * 修改应用列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid AppVo appVo, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        appListService.updAppById(appVo);
        return super.SUCCESS_TIP;
    }

    /**
     * 应用列表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
