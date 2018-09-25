package duc.ugcm.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private final Map<String, Class> classes;

    public Model() {
        this.classes = new HashMap<>();
    }

    public boolean containClass(String name) {
        return classes.containsKey(name);
    }

    public Class getClass(String name) {
        return classes.get(name);
    }

    public void addClass(Class toAdd) {
        classes.put(toAdd.getFqn(), toAdd);
    }

    public List<Class> getClasses() {
        List<Class> res = new ArrayList<>(classes.size());

        for(String key: classes.keySet()) {
            res.add(classes.get(key));
        }

        return res;
    }
}
