package com.script.k8s.converter;

import com.script.k8s.K8sKind;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlConverter {

    public static String toYaml(K8sKind k8sKind) {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml(new Constructor());
        return yaml.dumpAsMap(k8sKind.toMap());
    }
}