package org.elasticsearch.plugin.example;

import java.util.ArrayList;
import java.util.Collection;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

/**
 * <p>
 * 	 作为一个ES的插件，继承自{@linkplain org.elasticsearch.plugins.AbstractPlugin}类，
 *  需要实现其onModule()或modules()方法，将本插件相关的Module类注入到ES的Guice体系中
 * </p>
 * 
 * @author zhang-dw
 *
 */
public class ExamplePlugin extends AbstractPlugin {
	
	/**
	 * 插件名称
	 */
    @Override 
    public String name() {
        return "example-plugin";
    }

    /**
     * 插件描述
     */
    @Override 
    public String description() {
        return "Example Plugin Description";
    }
    
    /**
     * 实现该方法，将该插件的Module类注入到ES的Guice体系中
     */
    @Override
    public Collection<Class<? extends Module>> modules() {
//        Collection<Class<? extends Module>> modules = Lists.newArrayList();
        ArrayList<Class<? extends Module>> modules = new ArrayList<Class<? extends Module>>();
        modules.add(ExampleRestModule.class);
        return modules;
    }   
    
}