package com.script.k8s

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.composer.ComposerException

class K8sKindFactory {

    static K8sKind create(File resourceFile, String env, String projectName) throws IOException {
        Yaml yaml = new Yaml();
        try {
            LinkedHashMap<Object, Object> load = yaml.load(new FileInputStream(resourceFile));
            return new K8sKind((JSONObject) JSON.toJSON(load), env, resourceFile.getName(), projectName);
        } catch (ComposerException e) {
            throw new MultipleYamlScriptException(resourceFile.getName(), e);
        }
    }
}
