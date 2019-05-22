package com.giveu.dao;

import com.giveu.entity.MonitorObj;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fox on 2018/10/23.
 */
@Mapper
@Component
public interface MonitorObjDAO {

	@Insert("insert into wx_monitor_obj(obj_id, obj_code, rec_obj, monitor_status) value(#{objId}, #{objCode}, #{recObj}, #{monitorStatus})")
	int add(MonitorObj monitorObj);

	@Select("select * from wx_monitor_obj where id = #{id}")
	MonitorObj getById(Integer id);

	@Select("select * from wx_monitor_obj where obj_id = #{objId}")
	MonitorObj getByObjId(String objId);

	@Update("<script> " +
			"UPDATE wx_monitor_obj " +
			"<set>" +
			"<if test = 'recObj != null and recObj.length() > 0'>" +
			"rec_obj = #{recObj}, " +
			"</if>" +
			"<if test = 'monitorStatus != 0'>" +
			"monitor_status = #{monitorStatus}, " +
			"</if>" +
			"</set>" +
			"WHERE id = #{id} " +
			"</script>")
	int upd(MonitorObj monitorObj);

	@Select("<script> " +
			"SELECT * FROM wx_monitor_obj " +
			"WHERE 1 = 1 " +
			"<if test = 'objId != null and objId.length() > 0'>" +
			"and obj_id like CONCAT('%', #{objId}, '%') " +
			"</if>" +
			"<if test = 'objCode != null and objCode.length() > 0'>" +
			"and obj_code like CONCAT('%', #{objCode}, '%') " +
			"</if>" +
			"<if test = 'monitorStatus != null and monitorStatus != 0 '>" +
			"and monitor_status = #{monitorStatus} " +
			"</if>" +
			"order by create_time desc " +
			"</script>")
	List<MonitorObj> getList(MonitorObj monitorObj);

	@Update("update wx_monitor_obj set monitor_status = 1 where id = #{id}")
	int enableById(@Param("id") Integer id);

	@Update("update wx_monitor_obj set monitor_status = 2 where id = #{id}")
	int disableById(@Param("id") Integer id);

	@Select("select * from wx_monitor_obj where monitor_status != 2")
	List<MonitorObj> getListExcludeDisable();

}
