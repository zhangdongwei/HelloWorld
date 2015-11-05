package org.elasticsearch.plugin.cars;

import java.util.ArrayList;
import java.util.Collection;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

public class CarsRestPlugin extends AbstractPlugin{

	@Override
	public String name() {
		return "cars-rest-plugin";
	}

	@Override
	public String description() {
		 return "view cars ";
	}

	@Override
	public Collection<Class<? extends Module>> modules() {
		Collection<Class<? extends Module>> modules = Lists.newArrayList();
		modules.add(CarsRestModule.class);
		return modules;
	}

	
}
