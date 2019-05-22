package com.giveu.dao.impl;

import com.giveu.dao.MonitorApiMongoDAO;
import com.giveu.entity.ApiFullModel;
import com.giveu.entity.ApiSimpleModel;
import com.giveu.entity.GiveUMonitorMongoDB;
import com.giveu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.*;

/**
 * Created by fox on 2018/11/1.
 */
@Component
public class MonitorApiMongoDAOImpl implements MonitorApiMongoDAO {

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * 目前没有基础统计表，直接统计过慢，所以这里的所有数据只查近30日
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<ApiProStatModel> getApiStat(List<ApiServerAddressParam> params) {
		List<ApiProStatModel> list = new ArrayList<>();
		if (params == null || params.size() == 0)
			return list;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date startDate = calendar.getTime();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -30);
		Date normalStartDate = calendar.getTime();

		List<String> addressArry = new ArrayList<>();
		List<Integer> portArry = new ArrayList<>();
		for (ApiServerAddressParam p : params) {
			if (!addressArry.contains(p.getAddress())) {
				addressArry.add(p.getAddress());
			}
			if (!portArry.contains(p.getPort())) {
				portArry.add(p.getPort());
			}
		}
		Query query = new Query();
		Criteria criteria = Criteria.where("RequestTime").gte(normalStartDate)
				.and("StatuCode").is("500")
				.and("LocalIpAddress").in(addressArry)
				.and("LocalPort").in(portArry);
		List<AggregationOperation> aggs = new ArrayList<>();
		aggs.add(Aggregation.match(criteria));
		aggs.add(Aggregation.group("LocalIpAddress", "LocalPort", "Path").count().as("Count"));
		aggs.add(Aggregation.project("LocalIpAddress", "LocalPort", "Path", "Count"));

		Aggregation agg = Aggregation.newAggregation(aggs);
		AggregationResults<GiveUMonitorMongoDB> results = mongoTemplate.aggregate(agg, "GiveUMonitor", GiveUMonitorMongoDB.class);

		Query todayQuery = new Query();
		Criteria todayCriteria = Criteria.where("RequestTime").gte(startDate)
				.and("StatuCode").is("500")
				.and("LocalIpAddress").in(addressArry)
				.and("LocalPort").in(portArry);

		Aggregation todayAgg = Aggregation.newAggregation(
				Aggregation.match(todayCriteria),
				Aggregation.group("LocalIpAddress", "LocalPort", "Path").count().as("Count"),
				Aggregation.project("LocalIpAddress", "LocalPort", "Path", "Count")
		);
		AggregationResults<GiveUMonitorMongoDB> todayResults = mongoTemplate.aggregate(todayAgg, "GiveUMonitor", GiveUMonitorMongoDB.class);


		ApiProStatModel proStat = new ApiProStatModel();
		Map<String, Long> mapPathStat = new HashMap<>();
		Map<String, Long> mapTodayPathStat = new HashMap<>();
		for (ApiServerAddressParam p : params) {
			ApiStatModel stat = new ApiStatModel();
			stat.setAddress(p.getAddress());
			stat.setPort(p.getPort());
			stat.setExCount(0L);
			stat.setTodayExCount(0L);
			for (GiveUMonitorMongoDB mongo : results.getMappedResults()) {
				if (mongo.getLocalIpAddress() == null || mongo.getLocalIpAddress().isEmpty())
					continue;
				if (mongo.getLocalPort() == null)
					continue;
				if (mongo.getCount() == null)
					mongo.setCount(0L);
				if (mongo.getLocalIpAddress().equals(stat.getAddress()) && mongo.getLocalPort().equals(stat.getPort())) {
					stat.setExCount(stat.getExCount() + mongo.getCount());

					if (!mapPathStat.containsKey(mongo.getPath())) {
						mapPathStat.put(mongo.getPath(), 0L);
					}
					mapPathStat.put(mongo.getPath(), mongo.getCount() + mapPathStat.get(mongo.getPath()));
				}
			}

			for (GiveUMonitorMongoDB todayMongo : todayResults.getMappedResults()) {
				if (todayMongo.getLocalIpAddress() == null || todayMongo.getLocalIpAddress().isEmpty())
					continue;
				if (todayMongo.getLocalPort() == null)
					continue;
				if (todayMongo.getCount() == null)
					todayMongo.setCount(0L);
				if (todayMongo.getLocalIpAddress().equals(stat.getAddress()) && todayMongo.getLocalPort().equals(stat.getPort())) {
					stat.setTodayExCount(stat.getTodayExCount() + todayMongo.getCount());

					if (!mapTodayPathStat.containsKey(todayMongo.getPath())) {
						mapTodayPathStat.put(todayMongo.getPath(), 0L);
					}
					mapTodayPathStat.put(todayMongo.getPath(), todayMongo.getCount() + mapTodayPathStat.get(todayMongo.getPath()));
				}
			}
			proStat.getApiStats().add(stat);
		}

		for (Map.Entry<String, Long> entry : mapPathStat.entrySet()) {
			ApiPathStatModel apiPathStat = new ApiPathStatModel();
			apiPathStat.setPath(entry.getKey());
			apiPathStat.setExCount(entry.getValue());
			apiPathStat.setTodayExCount(0L);
			if (mapTodayPathStat.containsKey(apiPathStat.getPath())) {
				apiPathStat.setTodayExCount(mapTodayPathStat.get(apiPathStat.getPath()));
			}
			proStat.getApiPathStats().add(apiPathStat);
		}
		List<ApiPathStatModel> sortApiPathStats = proStat.getApiPathStats();
		Collections.sort(
				sortApiPathStats, new Comparator<ApiPathStatModel>() {
					@Override
					public int compare(ApiPathStatModel o1, ApiPathStatModel o2) {
						if (o2.getExCount() > o1.getExCount())
							return 1;
						return -1;
					}
				});
		if (sortApiPathStats.size() > 10) {
			proStat.setApiPathStats(sortApiPathStats.subList(0, 10));
		} else {
			proStat.setApiPathStats(sortApiPathStats);
		}
		list.add(proStat);
		return list;
	}

	/**
	 * 获取指定系统的接口异常信息列表
	 *
	 * @param params    指定系统的接口地址集合
	 * @param path      action接口名称
	 * @param pageIndex 分页索引 0开始
	 * @param rows      条数
	 * @return
	 */
	@Override
	public List<ApiSimpleModel> getApiList(List<ApiServerAddressParam> params, String path, Integer pageIndex, Integer rows) {
		List<ApiSimpleModel> list = new ArrayList<>();
		if (params == null || params.size() == 0) {
			return list;
		}
		Query query = new Query();
		Criteria criteria = Criteria.where("StatuCode").is("500");
		if (path == null) {
			path = "";
		}
		path = path.trim();
		if (!path.isEmpty()) {
			criteria.and("Path").is(path);
		}

		Criteria hostCriteria = new Criteria();
		List<Criteria> hostOrCriterias = new ArrayList<>();
		for (ApiServerAddressParam p : params) {
			hostOrCriterias.add(Criteria.where("LocalIpAddress").is(p.getAddress()).and("LocalPort").is(p.getPort()));
		}
		hostCriteria = hostCriteria.orOperator(hostOrCriterias.toArray(new Criteria[0]));
		criteria.andOperator(hostCriteria);
		query.addCriteria(criteria);
		query.with(new Sort(Sort.Direction.DESC, "RequestTime"));
		query.with(new PageRequest(pageIndex, rows));

		list = mongoTemplate.find(query, ApiSimpleModel.class, "GiveUMonitor");

		return list;
	}

	/**
	 * 根据api日志ID获取API详情
	 *
	 * @param apiId APIid
	 * @return
	 */
	@Override
	public ApiFullModel getById(String apiId) {
		if (apiId == null)
			return null;
		Criteria criteria = Criteria.where("_id").is(apiId);
		Query query = new Query();
		query.addCriteria(criteria);
		ApiFullModel apiFullModel = mongoTemplate.findOne(query, ApiFullModel.class, "GiveUMonitor");
		return apiFullModel;
	}

	/**
	 * 获取近30日 系统流量统计数据
	 *
	 * @param params
	 * @return KEY:"IP:PORT: VALUE:count
	 */
	@Override
	public Map<String, Integer> getHostExCount(List<ApiServerAddressParam> params) {
		Map<String, Integer> result = new HashMap<>();

		if (params == null || params.size() == 0) {
			return result;
		}
		Date startDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, -30);
		startDate = calendar.getTime();
		Criteria criteria = Criteria.where("RequestTime").gte(startDate).and("StatuCode").is("500");
		Aggregation todayAgg = Aggregation.newAggregation(
				Aggregation.match(criteria),
				Aggregation.group("LocalIpAddress", "LocalPort").count().as("Count"),
				Aggregation.project("LocalIpAddress", "LocalPort", "Count")
		);
		AggregationResults<GiveUMonitorMongoDB> totalResults = mongoTemplate.aggregate(todayAgg, "GiveUMonitor", GiveUMonitorMongoDB.class);
		for (GiveUMonitorMongoDB g : totalResults.getMappedResults()) {
			Boolean isHas = false;
			for (ApiServerAddressParam p : params) {
				if (p.getAddress().equals(g.getLocalIpAddress()) && p.getPort().equals(g.getLocalPort())) {
					isHas = true;
					break;
				}
			}
			if (!isHas) {
				continue;
			}
			String key = g.getLocalIpAddress() + ":" + g.getLocalPort().toString();
			if (!result.containsKey(key)) {
				result.put(key, 0);
			}
			result.put(key, result.get(key) + g.getCount().intValue());
		}
		return result;
	}
}
