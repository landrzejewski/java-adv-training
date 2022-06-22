package pl.training.processor.adv.search.commons;

import java.util.ArrayList;
import java.util.List;

public class ListCommons {

    public static <E> List<E> concat(List<E> firstList, List<E> secondList) {
        var result = new ArrayList<E>(firstList.size() + secondList.size());
        result.addAll(firstList);
        result.addAll(secondList);
        return result;
    }

}
