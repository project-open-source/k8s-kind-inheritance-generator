package com.script

import com.script.k8s.K8sKindFactory
import com.script.k8s.K8sKinds
import com.script.k8s.Project
import groovy.io.FileType

class K8sKindEnvironmentLoader {

    static K8sKinds load(Project project) {
        def mergedK8sKinds = []
        new File(project.relativePath).traverse(type: FileType.FILES, nameFilter: ~/.*\.yaml$/) {
            def parentFileName = it.getParentFile().getName()
            if (parentFileName != project.name) {
                def k8sKind = K8sKindFactory.create(it, parentFileName, project.name)
                mergedK8sKinds.add(k8sKind)
            }
        }
        return new K8sKinds(mergedK8sKinds)
    }
}
