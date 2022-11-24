package com.script.k8s

class Project {
    final String name;
    final String relativePath

    Project(String name,String relativePath) {
        this.name = name
        this.relativePath = relativePath
    }

     Project createBY(String relativePath){
        def project = new File(relativePath)
        return new Project(project.getName(),project.getPath())
    }
}
