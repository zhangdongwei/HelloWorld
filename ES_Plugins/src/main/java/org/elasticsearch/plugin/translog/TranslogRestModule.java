package org.elasticsearch.plugin.translog;

import org.elasticsearch.common.inject.AbstractModule;

public class TranslogRestModule extends AbstractModule{

	@Override
	protected void configure() {

		bind(TranslogRestHandler.class).asEagerSingleton();
		
	}

}
