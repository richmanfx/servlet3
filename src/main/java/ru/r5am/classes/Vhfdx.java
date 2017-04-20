package ru.r5am.classes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandr Jashhuk (R5AM) on 18.04.17.
 *
 */

public class Vhfdx {

    public Map<String, Object> vhfdxGrab() {

        Map<String, Object> pageContext = new HashMap<>();
        pageContext.put("call1", "RD3BA");
        pageContext.put("call2", "R5AM");

        return pageContext;
    }
}
