package com.script.k8s

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.script.k8s.converter.JsonConverter

class K8sKind {
    final String env
    final String fileName
    final JSONObject properties
    final String projectName

    K8sKind(JSONObject properties, String env, String fileName, String projectName) {
        this.projectName = projectName
        this.properties = properties
        this.env = env
        this.fileName = fileName
    }

    Map<Object, Object> toMap() {
        return JsonConverter.toMap(properties)
    }

    K8sKind mergeBy(K8sKind source) {
        JSONObject mergedJson = JsonConverter.merge(source.properties, properties)
        LinkedHashMap object = JsonConverter.toMap(mergedJson)
        return new K8sKind((JSONObject) JSON.toJSON(object), source.env, source.fileName, projectName)
    }


    K8sKind cloneWith(String env) {
        return new K8sKind(properties, env, fileName, projectName)
    }

}
