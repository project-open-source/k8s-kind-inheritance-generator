package com.script

import com.google.common.collect.Lists
import com.script.k8s.K8sKindFactory
import com.script.k8s.K8sKinds
import groovy.io.FileType

class K8sKindEnvironmentLoader {

    private static final List<String> ENV = Lists.newArrayList("prod","prod-idc","prod-verify", "test")


    static K8sKinds load(String dir, String projectName) {
        def mergedK8sKinds = []
        new File(dir).traverse(type: FileType.FILES, nameFilter: ~/.*\.yaml$/) {
            def parentFileName = it.getParentFile().getName()
            if (ENV.contains(parentFileName)) {
                def k8sKind = K8sKindFactory.create(it, parentFileName, projectName)
                mergedK8sKinds.add(k8sKind)
            }
        }
        return new K8sKinds(mergedK8sKinds)
    }
}
