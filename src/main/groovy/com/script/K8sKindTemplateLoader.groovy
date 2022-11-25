package com.script


import com.script.k8s.K8sKindFactory
import com.script.k8s.K8sKinds
import com.script.k8s.Project
import groovy.io.FileType

class K8sKindTemplateLoader {


    static K8sKinds load(Project project) {
        def templateKinds = []
        new File(project.relativePath).traverse(type: FileType.FILES, nameFilter: ~/.*\.yaml$/, maxDepth: 0) {
            if (it.getParentFile().getName() == project.name) {
                def k8sKind = K8sKindFactory.create(it, "template", project.name)
                templateKinds.add(k8sKind)
            }
        }
        return new K8sKinds(templateKinds)
    }
}
