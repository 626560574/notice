package com.giveu.dao;

import com.giveu.entity.MonitorRec;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fox on 2018/10/23.
 */
@Mapper
@Component
public interface MonitorRecDAO {
	/**
	 * 根据条件查询接口信息
	 *
	 * @return
	 */
	@Select("<script> " +
			"select * from wx_monitor_receiver " +
			"WHERE 1 = 1 " +
			"<if test = 'recNo != null and recNo.length() > 5'>" +
			"and rec_no = #{recNo} " +
			"</if>" +
			"<if test = 'recName != null '>" +
			"and rec_name = #{recName} " +
			"</if>" +
			"<if test = 'recPhone != null and recPhone.length() > 10'>" +
			"and rec_phone = #{recPhone} " +
			"</if>" +
			"order by update_time desc " +
			"</script>")
	List<MonitorRec> getAll(@Param("recNo") String recNo, @Param("recName") String recName, @Param("recPhone") String recPhone);

	/**
	 * 根据条件查询接口信息
	 *
	 * @return
	 */
	@Select("<script> " +
			"select * from wx_monitor_receiver " +
			"<where>" +
			"<if test = 'recNo != null and recNo.length() > 5'>" +
			"or rec_no = #{recNo} " +
			"</if>" +
			"<if test = 'recName != null '>" +
			"or rec_name = #{recName} " +
			"</if>" +
			"<if test = 'recPhone != null and recPhone.length() > 10'>" +
			"or rec_phone = #{recPhone} " +
			"</if>" +
			"</where>" +
			"order by update_time desc " +
			"</script>")
	List<MonitorRec> Search(@Param("recNo") String recNo, @Param("recName") String recName, @Param("recPhone") String recPhone);


	/**
	 * 新增
	 *
	 * @param monitorRec
	 * @return
	 */
	@Insert("insert into wx_monitor_receiver(rec_no, rec_name, rec_wx_id, rec_wx_nick, rec_phone ,rec_email ,rec_dept_no ,rec_status) value(#{recNo}, #{recName}, #{recWxId}, #{recWxNick}, #{recPhone}, #{recEmail}, #{recDeptNo}, #{recStatus})")
	Integer add(MonitorRec monitorRec);

	/**
	 * 修改微信预警对象信息
	 *
	 * @param monitorRec
	 * @return
	 */
	@Update("<script> " +
			"update wx_monitor_receiver " +
			"<set>" +
			"<if test = 'recNo != null'>" +
			" rec_no = #{recNo}, " +
			"</if>" +
			"<if test = 'recName != null '>" +
			" rec_name = #{recName}, " +
			"</if>" +
			"<if test = 'recWxId != null and recWxId != \"\"'>" +
			" rec_wx_id = #{recWxId}, " +
			"</if>" +
			"<if test = 'recWxNick != null and recWxNick != \"\"'>" +
			" rec_wx_nick = #{recWxNick}, " +
			"</if>" +
			"<if test = 'recPhone != null'>" +
			" rec_phone = #{recPhone}, " +
			"</if>" +
			"<if test = 'recEmail != null'>" +
			" rec_email = #{recEmail}, " +
			"</if>" +
			"<if test = 'recDeptNo != null'>" +
			" rec_dept_no = #{recDeptNo}, " +
			"</if>" +
			"<if test = 'recStatus != null'>" +
			" rec_status = #{recStatus}, " +
			"</if>" +
			"</set>" +
			"WHERE id = #{id} " +
			"</script>")
	Integer update(MonitorRec monitorRec);

	/**
	 * 根据ID获取预警对象详情
	 *
	 * @param id
	 * @return
	 */
	@Select("select * from wx_monitor_receiver where id = #{id} ")
	MonitorRec getById(Integer id);

	/**
	 * 删除预警对象
	 *
	 * @param id
	 * @return
	 */
	@Delete("delete from wx_monitor_receiver where id = #{id}")
	Integer del(Integer id);


	/**
	 * 获得启用的列表
	 * @return
	 */
	@Select("select * from wx_monitor_receiver where rec_status != 2")
	List<MonitorRec> getListExcludeDisable();

	/**
	 * 根据ID获取预警对象详情
	 *
	 * @param wxid
	 * @return
	 */
	@Select("select * from wx_monitor_receiver where rec_wx_id = #{wxid} limit 1 ")
	MonitorRec getByWxId(String wxid);
}
