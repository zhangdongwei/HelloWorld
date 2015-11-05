package org.elasticsearch.plugin.cars;

import org.elasticsearch.common.inject.AbstractModule;

public class CarsRestModule extends AbstractModule{

	@Override
	protected void configure() {

		bind(CarsRestHandler.class).asEagerSingleton();
		
	}

}
