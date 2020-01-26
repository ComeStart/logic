package cn.comestart.trinity.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionTools {
    public static <E> List<List<E>> split(List<E> list, int groupNum) {
        int i = 0;
        List<List<E>> result = new ArrayList<>();
        List<E> eList = new ArrayList<>();
        for (E e : list) {
            eList.add(e);
            i++;
            if (i % groupNum == 0) {
                result.add(eList);
                eList = new ArrayList<>();
            }
        }
        return result;
    }
}
