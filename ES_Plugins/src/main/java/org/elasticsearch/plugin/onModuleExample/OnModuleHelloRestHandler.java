package org.elasticsearch.plugin.onModuleExample;

import static org.elasticsearch.rest.RestStatus.OK;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

/**
 * <p>
 *   实现{@linkplain org.elasticsearch.rest.RestHandler}接口，可以处理rest请求，该类目前处理<b>/_hello</b>端点的endpoint。
 * </p>
 * 
 * @author zhang-dw
 *
 */
public class OnModuleHelloRestHandler extends BaseRestHandler {
	
	/**
	 * 这个构造函数是从基类继承下来必须实现的
	 * @param settings
	 * @param controller
	 * @param client
	 */
	@Inject
	protected OnModuleHelloRestHandler(Settings settings,RestController controller, Client client) {
		super(settings, controller, client);
		controller.registerHandler(RestRequest.Method.GET,"_test",this);
	}


	@Override
	protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
		String content = request.param("test");
		channel.sendResponse(new BytesRestResponse(OK,"Hello,"+content));
		
	}
}