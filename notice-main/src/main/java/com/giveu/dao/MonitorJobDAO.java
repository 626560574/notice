package com.giveu.dao;

import com.giveu.entity.MonitorJob;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fox on 2018/10/23.
 */
@Mapper
@Component
public interface MonitorJobDAO {

	@Delete("delete from wx_monitor_job where id = #{id}")
	int del(Integer id);

	@Insert("insert into wx_monitor_job(job_id, job_code, rec_obj, monitor_status) value(#{jobId}, #{jobCode}, #{recObj}, #{monitorStatus})")
	int add(MonitorJob monitorJob);

	@Select("select * from wx_monitor_job where job_id = #{jobId}")
	MonitorJob getByJobId(String jobId);

	@Select("select * from wx_monitor_job where id = #{id}")
	MonitorJob getById(Integer id);

	@Update("<script> " +
			"UPDATE wx_monitor_job " +
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
	int upd(MonitorJob monitorJob);

	@Update("<script> " +
			"UPDATE wx_monitor_job " +
			"<set>" +
				"<if test = 'recObj != null and recObj.length() > 0'>" +
				"rec_obj = #{recObj}, " +
				"</if>" +
				"<if test = 'monitorStatus != 0'>" +
				"monitor_status = #{monitorStatus}, " +
				"</if>" +
			"</set>" +
			"WHERE job_id = #{jobId} " +
			"</script>")
	int updByJobId(MonitorJob monitorJob);

	@Select("<script> " +
			"SELECT * FROM wx_monitor_job " +
			"WHERE 1 = 1 " +
			"<if test = 'jobId != null and jobId.length() > 0'>" +
			"and job_id like CONCAT('%', #{jobId}, '%') " +
			"</if>" +
			"<if test = 'jobCode != null and jobCode.length() > 0'>" +
			"and job_code like CONCAT('%', #{jobCode}, '%') " +
			"</if>" +
			"<if test = 'monitorStatus != null and monitorStatus != 0 '>" +
			"and monitor_status = #{monitorStatus} " +
			"</if>" +
			"order by create_time desc " +
			"</script>")
	List<MonitorJob> getList(MonitorJob monitorJob);

	@Select("select * from wx_monitor_job where monitor_status != 2")
	List<MonitorJob> getListExcludeDisable();

	@Update("update wx_monitor_job set monitor_status = 1 where id = #{id}")
	int enableById(@Param("id") Integer id);

	@Update("update wx_monitor_job set monitor_status = 2 where id = #{id}")
	int disableById(@Param("id") Integer id);

}
