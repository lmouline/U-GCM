package duc.ugcm.ast;

import java.util.*;

public class Class {

    private String name;
    private String packName;
    private List<Class> parents;
    private Map<String, Property> properties;
    private int index;

    public Class(String packName, String name, int index) {
       this.name = name;
       this.packName = packName;
       properties = new HashMap<>();
       this.parents = new ArrayList<>();
       this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getFqn() {
        if(packName != null) {
            return packName + '.' + name;
        }

        return name;
    }

    public void addParent(Class parent) {
        this.parents.add(parent);
    }

    public List<Class> getParents() {
        return parents;
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

        Set<String> visitedClass = new HashSet<>();
        Map<String, Property> allProperties = new HashMap<>();
        deepCollectProperties(allProperties, visitedClass);

        for(String keyProp: allProperties.keySet()) {
            res.add(allProperties.get(keyProp));
        }

        return res;
    }

    private void deepCollectProperties(Map<String, Property> props, Set<String> visited) {
        if(visited.contains(this.name)) {
            return;
        }

        for(String keyProp: this.properties.keySet()) {
            if(!props.containsKey(keyProp)) {
                props.put(keyProp, this.properties.get(keyProp));
            }
        }
        visited.add(this.name);
        for(Class parent: parents) {
            parent.deepCollectProperties(props, visited);
        }
    }

    public int getIndex() {
        return this.index;
    }


}
