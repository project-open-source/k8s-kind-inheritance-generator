package k8s

import com.script.K8sKindEnvironmentLoader
import com.script.K8sScriptGenerator
import com.script.k8s.Project
import spock.lang.Specification

import static com.script.k8s.converter.YamlConverter.toYaml

class K8sScriptGeneratorTest extends Specification {
    Project project = new Project("","")
    def "should return files by resource directory"() {
        when:
        def projects = K8sScriptGenerator.getProjects("./src/test/resources/k8s")
        then:
        projects.stream().collect{it.getName()}.contains("based-on-template-with-env-override")
    }

    def "should return override k8sKinds by env files of based-on-template-with-env-override"() {
        when:
        def k8sKindsForEnv = K8sKindEnvironmentLoader.load(project.createBY("./src/test/resources/k8s/based-on-template-with-env-override"))
        def elements = k8sKindsForEnv.elements()
        then:
        elements.size() == 2

        String prodYaml = toYaml(elements.get(0))
        String testYaml = toYaml(elements.get(1))
        prodYaml == """subsets:
- addresses:
  - ip: 192.168.prod.ip
"""
        testYaml == """subsets:
- addresses:
  - ip: 192.168.test.ip
"""
    }

    def "should generated files by env of based-on-template-with-env-override"() {
        when:
        def k8sKindsFiles = K8sScriptGenerator.generateEnvFiles(project.createBY("./src/test/resources/k8s/based-on-template-with-env-override"), "./test")
        then:
        k8sKindsFiles.size() == 2

        def prodK8sKindFile = k8sKindsFiles.get(0)
        prodK8sKindFile.getParentFile().getName() == "based-on-template-with-env-override"
        prodK8sKindFile.getParentFile().getParentFile().getName() == "prod"
        prodK8sKindFile.getParentFile().getParentFile().getParent() == "./test"

        def testK8sKindFile = k8sKindsFiles.get(1)
        testK8sKindFile.getParentFile().getName() == "based-on-template-with-env-override"
        testK8sKindFile.getParentFile().getParentFile().getName() == "test"
        testK8sKindFile.getParentFile().getParentFile().getParent() == "./test"
    }

    def "should generated file content by env of based-on-template-with-env-override"() {
        when:
        def k8sKindsFiles = K8sScriptGenerator.generateEnvFiles(project.createBY("./src/test/resources/k8s/based-on-template-with-env-override"), "./test")
        then:
        k8sKindsFiles.size() == 2
        def prodYaml = new String(k8sKindsFiles.get(0).readBytes())
        prodYaml == """apiVersion: v1
kind: Endpoints
metadata:
  name: mysql-business-svc
subsets:
- addresses:
  - ip: 192.168.prod.ip
  ports:
  - name: tcp
    port: 3306
"""
    }

    def "should generated file content by env of based-on-template-with-env-override-by-multiple-dot"() {
        when:
        def k8sKindsFiles = K8sScriptGenerator.generateEnvFiles(project.createBY("./src/test/resources/k8s/based-on-template-with-env-override-by-multiple-dot"), "./test")
        then:
        k8sKindsFiles.size() == 1
        def prodYaml = new String(k8sKindsFiles.get(0).readBytes())
        prodYaml == """apiVersion: v1
kind: Secret
type: Opaque
metadata:
  labels:
    app: business-service-app
  name: business-service-app-secret
stringData:
  spring.datasource.username: prod-username
  spring.datasource.password: password
  jwt.user-secret: user-secret
"""
    }

    def "should generated file  without env override"() {
        when:
        def k8sKindsFiles = K8sScriptGenerator.generateEnvFiles(project.createBY("./src/test/resources/k8s/based-on-template-without-env-override"), "./test")
        then:
        k8sKindsFiles.size() == 2

        def prodK8sKindFile = k8sKindsFiles.get(0)
        prodK8sKindFile.getParentFile().getName() == "based-on-template-without-env-override"
        prodK8sKindFile.getParentFile().getParentFile().getName() == "prod"
        prodK8sKindFile.getParentFile().getParentFile().getParent() == "./test"

    }


}
