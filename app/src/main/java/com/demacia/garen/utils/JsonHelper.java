package com.demacia.garen.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import java.util.ArrayList;
import java.util.Set;

public class JsonHelper {
    public static final int JsonTypeArr = 2;
    public static final int JsonTypeObj = 1;
    public static final int JsonTypeValue = 0;

    public static JSONArray getJSONArray(JSONArray jSONArray, int i) {
        try {
            JSONArray jSONArray2 = jSONArray.getJSONArray(i);
            if (jSONArray2 != null) {
                return jSONArray2;
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    public static JSONArray getJSONArray(String str) {
        try {
            Object parse = JSONArray.parse(str, Feature.OrderedField);
            if (parse instanceof JSONArray) {
                return (JSONArray) parse;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONArray jSONArray, int i) {
        try {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject != null) {
                return jSONObject;
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    public static JSONObject getJSONObject(String str) {
        try {
            JSONObject parseObject = JSONObject.parseObject(str, Feature.OrderedField);
            if (parseObject != null) {
                return parseObject;
            }
            return null;
        } catch (Exception e) {
        }
        return null;
    }

    public static int getJsonType(String str) {
        if (getJSONObject(str) != null) {
            return 1;
        }
        return getJSONArray(str) != null ? 2 : 0;
    }

    public static ArrayList<String> getKeys(JSONObject jSONObject) {
        ArrayList<String> arrayList = new ArrayList<>();
        Set<String> keySet = jSONObject.keySet();
        if (keySet != null) {
            for (String add : keySet) {
                arrayList.add(add);
            }
        }
        return arrayList;
    }
}
