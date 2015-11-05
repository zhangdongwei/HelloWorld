package org.elasticsearch.plugin.onModuleExample;

import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;

/**
 * <p>
 * 	 作为一个ES的插件，继承自{@linkplain org.elasticsearch.plugins.AbstractPlugin}类，
 *  需要实现其onModule()或modules()方法，将本插件相关的Module类注入到ES的Guice体系中
 * </p>
 * 
 * @author zhang-dw
 *
 */
public class OnModuleExamplePlugin extends AbstractPlugin {
	
	/**
	 * 插件名称
	 */
    @Override 
    public String name() {
        return "onModule-example-plugin";
    }

    /**
     * 插件描述
     */
    @Override 
    public String description() {
        return "onModule-Example Plugin Description";
    }
    
    /**
     * 所有实现了onModule方法的Plugin类都会被ES的插件体系自动执行该方法。
     * 该方法只有一个参数，即ES Injector中已经包含的某个Module实例。
     * 通过这种方式，可以在当前新插件中扩展ES已有的模块（如Rest模块）的功能。
     * 本例中，是为rest模块添加了一个新的_endpoint（这个新的_endpoint的处理在OnModuleHelloRestHandler中）。
     * 
     * @param restModule
     */
    public void onModule(RestModule restModule){
    	restModule.addRestAction(OnModuleHelloRestHandler.class);
    }
    
}