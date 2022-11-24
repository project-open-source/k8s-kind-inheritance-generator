package com.script

import spock.lang.Specification

class K8sScriptMain extends Specification {
    def "should generate k8s Kind by env"() {
        when:
        def projects = K8sScriptGenerator.getProjects("./src/main/resources/k8s")
        projects.forEach(project -> {
            K8sScriptGenerator.generateEnvFiles(project.relativePath, project.name)
        })
        then:
        noExceptionThrown()
    }


}
