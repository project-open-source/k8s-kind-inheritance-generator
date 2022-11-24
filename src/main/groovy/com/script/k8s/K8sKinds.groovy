package com.script.k8s

import java.util.stream.Collectors

import static java.util.stream.Collectors.toList

class K8sKinds {
    private List<K8sKind> k8sKinds;

    K8sKinds(List<K8sKind> k8sKinds) {
        this.k8sKinds = k8sKinds;
    }

    List<K8sKind> compose(K8sKinds sourceK8sKinds) {
        return elements().stream()
                .map { sourceK8sKinds.groupingEnvironmentBy(it) }
                .flatMap { it.stream() }.collect(toList())
    }

    List<K8sKind> elements() {
        return this.k8sKinds
    }

    private List<K8sKind> groupingEnvironmentBy(K8sKind k8sKind) {
        return this.k8sKinds
                .stream()
                .collect(Collectors.groupingBy { K8sKind kind -> return kind.env })
                .keySet()
                .stream()
                .map { env -> k8sKind.cloneWith(env) }
                .map { merge(it) }
                .collect(toList())
    }

    private merge(K8sKind k8sKind) {
        def optional =
                this.k8sKinds
                        .stream()
                        .filter { it.fileName == k8sKind.fileName }
                        .filter { it.env == k8sKind.env }
                        .findFirst()
        if (optional.isPresent()) {
            return k8sKind.mergeBy(optional.get())
        }
        return k8sKind
    }
}
