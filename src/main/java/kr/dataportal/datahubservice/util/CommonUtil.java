package kr.dataportal.datahubservice.util;

import java.util.*;

public class CommonUtil<T> {
    public List<T> convertObjectToList(Object obj) {
        List<T> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = (List<T>) Collections.singletonList(obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<T>)obj);
        }
        return list;
    }
}
