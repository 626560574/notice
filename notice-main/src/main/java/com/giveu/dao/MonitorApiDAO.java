package com.giveu.dao;

import com.giveu.entity.MonitorApi;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监控接口配置DAO
 * Created by fox on 2018/10/23.
 */
@Mapper
@Component
public interface MonitorApiDAO {

	/**
	 * 新增
	 *
	 * @param monitorApi
	 * @return
	 */
	@Insert("insert into wx_monitor_api(pro_name, pro_address, rec_obj, monitor_status) value(#{proName}, #{proAddress}, #{recObj}, #{monitorStatus})")
	Integer add(MonitorApi monitorApi);

	/**
	 * 更新接口配置
	 *
	 * @param monitorApi
	 * @return
	 */
	@Update("<script> " +
			"update wx_monitor_api " +
			"<set>" +
				"<if test = 'proName != null'>" +
				" pro_name = #{proName}, " +
				"</if>" +
				"<if test = 'proAddress != null'>" +
				" pro_address = #{proAddress}, " +
				"</if>" +
				"<if test = 'recObj != null'>" +
				" rec_obj = #{recObj}, " +
				"</if>" +
				"<if test = 'monitorStatus != null'>" +
				" monitor_status = #{monitorStatus}, " +
				"</if>" +
			"</set>" +
			"WHERE id = #{id} " +
			"</script>")
//	@Select("UPDATE wx_monitor_api set pro_name = #{proName},pro_address = #{proAddress},rec_obj = #{recObj},monitor_status = #{monitorStatus} WHERE id = #{id}")
	Integer upd(MonitorApi monitorApi);

	/**
	 * 根据条件查询接口信息
	 *
	 * @param proName
	 * @param monitorStatus
	 * @return
	 */
	@Select("<script> " +
			"SELECT * FROM wx_monitor_api " +
			"WHERE 1 = 1 " +
			"<if test = 'proName != null and proName.length() > 0'>" +
			"and pro_name like CONCAT('%', #{proName}, '%') " +
			"</if>" +
			"<if test = 'monitorStatus != 0 '>" +
			"and monitor_status = #{monitorStatus} " +
			"</if>" +
			"order by create_time desc " +
			"</script>")
	List<MonitorApi> getAll(@Param("proName") String proName, @Param("monitorStatus") Integer monitorStatus);

	/**
	 * 启用
	 *
	 * @param id
	 * @return
	 */
	@Update("update wx_monitor_api set monitor_status = 1 where id = #{id}")
	Integer enableById(Integer id);

	/**
	 * 禁用
	 *
	 * @param id
	 * @return
	 */
	@Update("update wx_monitor_api set monitor_status = 2 where id = #{id}")
	Integer disableById(Integer id);

	/**
	 * 根据ID 获取监控接口配置信息
	 *
	 * @param id
	 * @return
	 */
	@Select("select * from wx_monitor_api where id = #{id}")
	MonitorApi getById(Integer id);

	/**
	 * 根据地址查询监控接口配置信息
	 *
	 * @param proAddress
	 * @return
	 */
	@Select("select * from wx_monitor_api where pro_address = #{proAddress}")
	List<MonitorApi> getByAddress(String proAddress);

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@Delete("delete from wx_monitor_api where id = #{id}")
	Integer del(Integer id);

	/**
	 * 获取启用的数据
	 * @return
	 */
	@Select("select * from wx_monitor_api where monitor_status != 2")
	List<MonitorApi> getListExcludeDisable();
}
