package ru.r5am.classes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aleksandr Jashhuk (R5AM) on 18.04.17.
 *
 */

public class Vhfdx {

    public Map<String, Object> vhfdxGrab() {

        Map<String, Object> pageContext = new HashMap<>();
        List<String> calls = new LinkedList<>();
        calls.add("RD3BA");
        calls.add("R5AM");
        calls.add("RA3ASD");

        pageContext.put("calls", calls);

        return pageContext;
    }
}
