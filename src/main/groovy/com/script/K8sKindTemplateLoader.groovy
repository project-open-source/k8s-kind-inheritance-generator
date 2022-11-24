package com.script


import com.script.k8s.K8sKindFactory
import com.script.k8s.K8sKinds
import groovy.io.FileType

class K8sKindTemplateLoader {


    static K8sKinds load(String dir, String projectName) {
        def templateKinds = []
        new File(dir).traverse(type: FileType.FILES, nameFilter: ~/.*\.yaml$/, maxDepth: 0) {
            if (it.getParentFile().getName() == projectName) {
                def k8sKind = K8sKindFactory.create(it, "template", projectName)
                templateKinds.add(k8sKind)
            }
        }
        return new K8sKinds(templateKinds)
    }
}
