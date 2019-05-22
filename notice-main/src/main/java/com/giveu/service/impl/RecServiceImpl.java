package com.giveu.service.impl;

import com.giveu.core.Wechat.UserUtil;
import com.giveu.dao.MonitorRecDAO;
import com.giveu.entity.MonitorRec;
import com.giveu.entity.User;
import com.giveu.notice.common.info.CommonMessage;
import com.giveu.notice.common.vo.ResultModel;
import com.giveu.service.RecService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 预警对象服务类实现
 * Created by fox on 2018/10/23.
 */
@Service
public class RecServiceImpl implements RecService {
	private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

	@Autowired
	MonitorRecDAO monitorRecDAO;

	@Autowired
	UserUtil userUtil;

	/**
	 * 获取所有预警对象
	 *
	 * @param request
	 * @param resultModel
	 */
	@Override
	public void list(HttpServletRequest request, ResultModel resultModel) {
		List<MonitorRec> list = monitorRecDAO.getAll(null, null, null);
		resultModel.setCode(CommonMessage.OK_CODE);
		resultModel.setMessage(CommonMessage.OK_DESC);
		resultModel.setData(list);
	}

	/**
	 * 获取所有预警对象
	 *
	 * @param request
	 * @param resultModel
	 */
	@Override
	public void Search(HttpServletRequest request, ResultModel resultModel) {
		String recNo = request.getParameter("recNo");
		String recName = request.getParameter("recName");
		String recPhone = request.getParameter("recPhone");
		List<MonitorRec> list = monitorRecDAO.Search(recNo, recName, recPhone);
		resultModel.setCode(CommonMessage.OK_CODE);
		resultModel.setMessage(CommonMessage.OK_DESC);
		resultModel.setData(list);
	}

	@Override
	public Integer add(HttpServletRequest request, ResultModel resultModel) {
		String recNo = request.getParameter("recNo");
		String recName = request.getParameter("recName");
		String recWxId = request.getParameter("recWxId");
		String recWxNick = request.getParameter("recWxNick");
		String recPhone = request.getParameter("recPhone");
		String recEmail = request.getParameter("recEmail");
		String recDeptNo = request.getParameter("recDeptNo");
		Integer recStatus = NumberUtils.toInt(request.getParameter("recStatus"), 1);

		if (recWxId == null)
			recWxId = "";
		if (recWxNick == null)
			recWxNick = "";
		if (recDeptNo == null)
			recDeptNo = "";
		MonitorRec monitorRec = new MonitorRec();
		monitorRec.setRecNo(recNo);
		monitorRec.setRecName(recName);
		monitorRec.setRecWxId(recWxId);
		monitorRec.setRecWxNick(recWxNick);
		monitorRec.setRecPhone(recPhone);
		monitorRec.setRecEmail(recEmail);
		monitorRec.setRecDeptNo(recDeptNo);
		monitorRec.setRecStatus(recStatus);
		int affect = monitorRecDAO.add(monitorRec);
		return affect;
	}

	@Override
	public MonitorRec getById(Integer id) {
		if (id <= 0) {
			return null;
		}
		return monitorRecDAO.getById(id);
	}

	@Override
	public Integer update(HttpServletRequest request, ResultModel resultModel) {
		Integer recId = NumberUtils.toInt(request.getParameter("id"), 0);
		if (recId <= 0) {
			isSuccess(0, resultModel);
			return 0;
		}
		String recNo = request.getParameter("recNo");
		String recName = request.getParameter("recName");
		String recWxId = request.getParameter("recWxId");
		String recWxNick = request.getParameter("recWxNick");
		String recPhone = request.getParameter("recPhone");
		String recEmail = request.getParameter("recEmail");
		String recDeptNo = request.getParameter("recDeptNo");
		Integer recStatus = NumberUtils.toInt(request.getParameter("recStatus"), 1);

		if (recWxId == null)
			recWxId = "";
		if (recWxNick == null)
			recWxNick = "";
		if (recDeptNo == null)
			recDeptNo = "";
		MonitorRec monitorRec = new MonitorRec();
		monitorRec.setId(recId);
		monitorRec.setRecNo(recNo);
		monitorRec.setRecName(recName);
		monitorRec.setRecWxId(recWxId);
		monitorRec.setRecWxNick(recWxNick);
		monitorRec.setRecPhone(recPhone);
		monitorRec.setRecEmail(recEmail);
		monitorRec.setRecDeptNo(recDeptNo);
		monitorRec.setRecStatus(recStatus);
		int affect = monitorRecDAO.update(monitorRec);
		isSuccess(affect, resultModel);
		return affect;
	}

	@Override
	public Integer delete(Integer id) {
		if (id <= 0) {
			return 0;
		}
		return monitorRecDAO.del(id);
	}

	@Override
	public Integer enableById(Integer id) {
		if (id <= 0) {
			return 0;
		}
		MonitorRec monitorRec = new MonitorRec();
		monitorRec.setId(id);
		monitorRec.setRecStatus(1);
		return monitorRecDAO.update(monitorRec);
	}

	@Override
	public Integer disableById(Integer id) {
		if (id <= 0) {
			return 0;
		}
		MonitorRec monitorRec = new MonitorRec();
		monitorRec.setId(id);
		monitorRec.setRecStatus(2);
		return monitorRecDAO.update(monitorRec);
	}

	@Override
	public Integer syncwechat() {
		//获取所有的微信用户
		List<User> users = userUtil.getUser();
		//获取所有的数据库用户
		List<MonitorRec> list = monitorRecDAO.getAll(null, null, null);
		//记录影响行数
		Integer affectRows = 0;

		if (users.size() <= 0)
			return affectRows;
		for (User user : users) {
			MonitorRec monitorRec = null;
			for (MonitorRec rec : list) {
				if (rec.getRecWxId().isEmpty())
					continue;
				if (!rec.getRecWxId().equals(user.getUserid()))
					continue;
				rec.setRecWxNick(user.getName());
				if (user.getMobile() != null && !user.getMobile().isEmpty())
					rec.setRecPhone(user.getMobile());
				monitorRec = rec;
				break;
			}
			if (monitorRec != null)
				affectRows += monitorRecDAO.update(monitorRec);
			else {
				monitorRec = new MonitorRec();
				monitorRec.setRecNo("");
				monitorRec.setRecName(user.getName());
				monitorRec.setRecWxId(user.getUserid());
				monitorRec.setRecWxNick(user.getName());
				monitorRec.setRecPhone(user.getMobile());
				monitorRec.setRecEmail(user.getEmail());
				monitorRec.setRecDeptNo("");
				monitorRec.setRecStatus(1);
				affectRows += monitorRecDAO.add(monitorRec);
			}
		}

		return affectRows;
	}

	void isSuccess(int affectRows, ResultModel resultModel) {
		if (affectRows == 1) {
			resultModel.setCode(CommonMessage.OK_CODE);
			resultModel.setMessage(CommonMessage.OK_DESC);
		} else {
			resultModel.setCode(CommonMessage.SER_UNKNOW_ERROR_CODE);
			resultModel.setMessage(CommonMessage.SER_UNKNOW_ERROR_DESC);
		}
	}
}
