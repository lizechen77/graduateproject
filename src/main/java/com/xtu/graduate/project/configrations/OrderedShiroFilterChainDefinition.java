package com.xtu.graduate.project.configrations;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;

public class OrderedShiroFilterChainDefinition implements ShiroFilterChainDefinition {

  final private Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

  public void addPathDefinition(String antPath, String definition) {
    this.filterChainDefinitionMap.put(antPath, definition);
  }

  public void addPathDefinitions(Map<String, String> pathDefinitions) {
    this.filterChainDefinitionMap.putAll(pathDefinitions);
  }

  @Override
  public Map<String, String> getFilterChainMap() {
    return this.filterChainDefinitionMap;
  }

}
