package org.elasticsearch.plugin.translog;

import java.util.ArrayList;
import java.util.Collection;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

public class TranslogRestPlugin extends AbstractPlugin{

	@Override
	public String name() {
		return "translog-rest-plugin";
	}

	@Override
	public String description() {
		 return "view elasticsearch translog";
	}

	@Override
	public Collection<Class<? extends Module>> modules() {
		Collection<Class<? extends Module>> modules = Lists.newArrayList();
		modules.add(TranslogRestModule.class);
		return modules;
	}

	
}
