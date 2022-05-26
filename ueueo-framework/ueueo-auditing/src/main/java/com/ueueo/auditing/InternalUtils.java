package com.ueueo.auditing;

/**
 * @author Lee
 * @date 2022-05-26 11:24
 */
class InternalUtils {

    static String addCounter(String str) {
        if (str.contains("__")) {
            String[] splitted = str.split("__");
            if (splitted.length == 2) {
                int num = 0;
                try {
                    num = Integer.parseInt(splitted[1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return splitted[0] + "__" + (++num);
            }
        }
        return str + "__2";
    }
}
