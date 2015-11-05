package org.elasticsearch.plugin.example;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.rest.RestRequest;

/**
 * <p>
 *   实现{@linkplain org.elasticsearch.rest.RestHandler}接口，可以处理rest请求，该类目前处理<b>/_hello</b>端点的endpoint。
 * </p>
 * 
 * @author zhang-dw
 *
 */
public class HelloRestHandler implements RestHandler {
	
	// 注意，这里注入了RestController
    @Inject
    public HelloRestHandler(RestController restController) {
        restController.registerHandler(GET, "/_hello", this); //对/_hello这个rest端点进行注册
    }

    // 处理/_hello端点的rest请求，处理其who参数
    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        String who = request.param("who");
        String whoSafe = (who!=null) ? who : "world";
        channel.sendResponse(new BytesRestResponse(OK, "Hello, " + whoSafe + "!"));
    }
}