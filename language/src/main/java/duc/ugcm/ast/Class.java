package duc.ugcm.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class {

    private String name;
    private Class parent;
    private Map<String, Property> properties;

    public Class(String name) {
       this.name = name;
       properties = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getParent() {
        return parent;
    }

    public void setParent(Class parent) {
        this.parent = parent;
    }

    public boolean containProperty(String name) {
        return properties.containsKey(name);
    }

    public void addProperty(Property property) {
        properties.put(property.getName(), property);
    }

    public Property getProperty(String name) {
        return properties.get(name);
    }

    public List<Property> getProperties() {
        List<Property> res = new ArrayList<>();

        for(String key: properties.keySet()) {
            res.add(properties.get(key));
        }

        return res;
    }

}
