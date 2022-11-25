package com.script

class K8sMain {
    static void main(String[] args) {
//        def projects = K8sScriptGenerator.getProjects("/Users/yuanxin.mao/TW/ftms/code/k8s-kind-inheritance-generator/src/test/resources/k8s")
        def projects = K8sScriptGenerator.getProjects(args[0])
        projects.forEach(project -> {
            K8sScriptGenerator.generateEnvFiles(project, args[1])
        })
    }
}
