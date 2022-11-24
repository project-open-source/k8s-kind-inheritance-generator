package com.script.k8s.converter

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject

import java.util.stream.IntStream

class JsonConverter {
    static JSONObject merge(JSONObject source, JSONObject target) {
        if (target == null) {
            return source;
        }

        for (String key : source.keySet()) {
            Object value = source.get(key);
            if (!target.containsKey(key)) {
                target.put(key, value);
            } else {
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    JSONObject targetValue = merge(valueJson, target.getJSONObject(key));
                    target.put(key, targetValue);
                } else if (value instanceof JSONArray) {
                    JSONArray valueArray = (JSONArray) value;
                    IntStream.range(0, valueArray.size()).forEach(i -> {
                        JSONObject obj = (JSONObject) valueArray.get(i);
                        JSONObject targetValue = merge(obj, (JSONObject) target.getJSONArray(key).get(i));
                        target.getJSONArray(key).set(i, targetValue);
                    });
                } else {
                    target.put(key, value);
                }
            }
        }
        return target;
    }

    static LinkedHashMap<Object,Object> toMap(JSONObject json) {
        return JSON.parseObject(json.toString(), LinkedHashMap.class)
    }
}
