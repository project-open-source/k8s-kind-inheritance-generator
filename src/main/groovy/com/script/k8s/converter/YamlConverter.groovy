package com.script.k8s.converter

import com.script.k8s.K8sKind
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

class YamlConverter {

    static String toYaml(K8sKind k8sKind) {
        Yaml yaml = new Yaml(new Constructor());
        return yaml.dumpAsMap(k8sKind.toMap());
    }
}