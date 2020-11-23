package FileInteraction.Tools;

import Handling.SolarMap;

import java.io.Serializable;
import java.util.*;

public class FileObject implements Serializable {

    private final Map<String, Object> object = new HashMap<>();

    public FileObject(Map<Date, List<Integer>> data) {
        this.object.put("data",data);
    }

    public FileObject(SolarMap data) {
        this.object.put("data",data.getAsMap());
    }

    public Map<Date, List<Integer>> getData()
    {
        Object o = object.get("data");
        Map<Date, List<Integer>> map = new HashMap<>();
        if(o instanceof Map) {
            map = (HashMap<Date, List<Integer>>) o;
        }
        return (map);
    }

    public SolarMap getDataAsSolarMap() {
        return new SolarMap(getData());
    }

    public void putInformation(String key, Object info) {
        object.put(key,info);
    }

    public Object getInformation(String key) {
        return object.get(key);
    }

    public static Map<Date, List<Integer>> data(FileObject fileObject) {
        return fileObject.getData();
    }


}
