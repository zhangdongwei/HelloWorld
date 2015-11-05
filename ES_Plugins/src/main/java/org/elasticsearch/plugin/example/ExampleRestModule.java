package org.elasticsearch.plugin.example;

import org.elasticsearch.common.inject.AbstractModule;

/**
 * <p>
 * 	ExampleRestModule类，负责装配到Guice中
 * </p>
 * 
 * <p>
 * 	 <li>1.继承自{@linkplain org.elasticsearch.common.inject.AbstractModule}，实现的是configure()方法，
 * 		         而非Guice Module原生的configure(Binder binder)方法;
 * 		 2.注入{@linkplain HelloRestHandler}到Guice中
 *   </li>
 * </p>
 * 
 * @author zhang-dw
 *
 */
public class ExampleRestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloRestHandler.class).asEagerSingleton(); //向Guice中进行注入
    }
}