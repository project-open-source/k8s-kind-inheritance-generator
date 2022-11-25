package com.script

class K8sMain {
    static void main(String[] args) {
        def projects = K8sScriptGenerator.getProjects(args[0])
        projects.forEach(project -> {
            K8sScriptGenerator.generateEnvFiles(project, args[1])
        })
    }
}
