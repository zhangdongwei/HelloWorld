package org.elasticsearch.plugin.translog;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

import java.io.FileInputStream;
import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.io.stream.InputStreamStreamInput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.translog.Translog;
import org.elasticsearch.index.translog.TranslogStreams;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.rest.RestRequest;


public class TranslogRestHandler implements RestHandler{

	private XContentBuilder builder; // 基于该builder可以进行json序列化
	
	@Inject
	public TranslogRestHandler(RestController restController,Client client) {
		restController.registerHandler(GET,"/_translog",this);
	}

	public void buildSave(Translog.Index op){
        try {
            builder.startObject()
            .field("opType").value("save")
            .field("id").value(op.id())
            .field("type").value(op.type())
            .field("version").value(op.version())
            .field("routing").value(op.routing())
            .field("parent").value(op.parent())
            .field("timestamp").value(op.timestamp())
            .field("ttl").value(op.ttl())
            .field("version").value(op.version())
            .field("source").value(op.source().toUtf8())
            .endObject();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buildCreate(Translog.Create op){
        try {
            builder.startObject()
            .field("opType").value("create")
            .field("id").value(op.id())
            .field("type").value(op.type())
            .field("version").value(op.version())
            .field("routing").value(op.routing())
            .field("parent").value(op.parent())
            .field("timestamp").value(op.timestamp())
            .field("ttl").value(op.ttl())
            .field("version").value(op.version())
            .field("source").value(op.source().toUtf8())
            .endObject();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buildDelete(Translog.Delete op){
        try {
            builder.startObject()
            .field("opType").value("delete")
            .field("id").value(op.uid().text())
            .field("version").value(op.version())
            .endObject();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void buildDeleteByQuery(Translog.DeleteByQuery op){
        try {
            builder.startObject()
            .field("opType").value("deleteByQuery")
            .field("source").value(op.source().toUtf8())
            .endObject();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public XContentBuilder buildTranslog(String filePath,int size){
         FileInputStream fs = null;
         int count = 0;
            try {
                fs = new FileInputStream(filePath);
                InputStreamStreamInput si = new InputStreamStreamInput(fs);
                while (true) {
                    if(count>=size){
                        break;
                    }
                    Translog.Operation operation;
                    try {
                        int opSize = si.readInt();
//                      System.out.println("opSize = "+opSize);
                        operation = TranslogStreams.readTranslogOperation(si);
                        switch (operation.opType()) {
                        case CREATE:
                            Translog.Create create = (Translog.Create) operation;
                            buildCreate(create);
                            break;
                        case SAVE:
                            Translog.Index index = (Translog.Index) operation;
                            buildSave(index);
                            break;
                        case DELETE:
                            Translog.Delete delete = (Translog.Delete) operation;
                            buildDelete(delete);
                            break;
                        case DELETE_BY_QUERY:
                            Translog.DeleteByQuery dbq = (Translog.DeleteByQuery) operation;
                            buildDeleteByQuery(dbq);
                            break;
                        default:
                            System.out.println("Invaid Operation Type");
                            break;
                        }
                         
                        count++;
                         
                    }catch(Exception e){
                        break;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(null!=fs){
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return builder;
     
    }
    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
//        String file = request.param("file");
//        String sizeStr = request.param("size");
//        int size = 10;
//        if(null!=sizeStr){
//            size = Integer.parseInt(sizeStr);
//        }
//        try {
//            builder = RestXContentBuilder.restContentBuilder(request);
//            if(null!=file){
//                builder.startArray();
//                buildTranslog(file,size);
//                builder.endArray();
//            }else{
//                builder.startObject()
//                        .field("success").value(false)
//                        .field("error").value("缺少参数:file")
//                        .endObject();
//            }
//            channel.sendResponse(new XContentRestResponse(request,OK,builder));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
         
    }


}
