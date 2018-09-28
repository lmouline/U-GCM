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

        for(String keyProp: properties.keySet()) {
            res.add(properties.get(keyProp));
        }

        return res;
    }

    public void deleteAllProp() {
        properties.clear();
    }

    public int getIndex() {
        return this.index;
    }


}
