package com.script

import com.script.k8s.Project

import static com.script.k8s.converter.YamlConverter.toYaml
import static groovy.io.FileType.DIRECTORIES
import static org.apache.tools.ant.util.FileUtils.fileUtils

class K8sScriptGenerator {

    static getProjects(String ProjectsPath) {
        List<Project> projects = []
        new File(ProjectsPath).traverse(type: DIRECTORIES, nameFilter: ~/env$/) {project ->
            projects.add(new Project(project.getParentFile().getName(),project.getParentFile().getPath()));
        }
        return projects
    }


    static List<File> generateEnvFiles(String projectPath, String projectName) {
        def generatedFiles = []
        def templateK8sKinds = K8sKindTemplateLoader.load(projectPath, projectName)
        def envK8sKinds = K8sKindEnvironmentLoader.load(projectPath, projectName)
        templateK8sKinds.compose(envK8sKinds).each {
            def generatedFile = new File("./generated/" + it.env + "/" + it.projectName + "/" + it.fileName)
            getFileUtils().createNewFile(generatedFile, true)
            String fileYamlContent = toYaml(it)
            generatedFile.write(fileYamlContent)
            generatedFiles.add(generatedFile)
        }
        return generatedFiles
    }
}
