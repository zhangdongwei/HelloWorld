package org.elasticsearch.plugin.cars;

import static org.elasticsearch.rest.RestRequest.Method.GET;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;


public class CarsRestHandler implements RestHandler{

	private Client client;
	
	@Inject
	public CarsRestHandler(RestController restController,Client client) {
		this.client = client;
		restController.registerHandler(GET,"/_cars",this);
	}

	@Override
	public void handleRequest(RestRequest request, RestChannel channel)	throws Exception {
		String sizeParam = request.param("size");
		int size = sizeParam == null ? 10 : Integer.parseInt(sizeParam);
		
		String index = "cars";
		String type = "transactions";
		
		List<Map<String, Object>> cars = getAllDatas(index,type);
		
		

	}
	
	private List<Map<String,Object>> getAllDatas(String index,String type){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch()
														  .setIndices(index)
														  .setTypes(type)
														  .setSearchType(SearchType.SCAN) //加上这个据说可以提高性能，但第一次却不返回结果
														  .setScroll(TimeValue.timeValueMinutes(8)) // 这个游标维持多长时间
														  .setSize(5);                     //实际返回的数量为5*index的主分片格式，如果index是默认配置的话
		// 第一次查询，只返回数量和一个scrollId
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet(); 
		String scrollId = searchResponse.getScrollId();
		while(true){
			SearchScrollRequestBuilder requestBuilder = client.prepareSearchScroll(scrollId).setScroll(TimeValue.timeValueMinutes(8));
			SearchResponse response = requestBuilder.execute().actionGet();
			scrollId = response.getScrollId();
			SearchHits hits = response.getHits();
			//输出结果
			for (SearchHit hit : hits) {
				result.add(hit.getSource());
			}
			//出口，插入的数据够了
			if(result.size() == hits.getTotalHits()){
				break;
			}
		}
		return result;
	}

	

}
