package k8s

import com.script.k8s.K8sKind
import com.script.k8s.K8sKindFactory
import com.script.k8s.MultipleYamlScriptException
import com.script.k8s.converter.YamlConverter
import spock.lang.Specification

class K8SKindFactoryTest extends Specification {
    def "should override yaml config by key"() {
        setup:
        K8sKind k8sKind = K8sKindFactory.create(new File("./src/test/resources/mysql-business-svc.yaml"), "test", "projectName")
        K8sKind k8sKindOverride = K8sKindFactory.create(new File("./src/test/resources/mysql-business-svc-override.yaml"), "test", "projectName")

        when:
        K8sKind k8sKindMerged = k8sKind.mergeBy(k8sKindOverride)
        String yaml = YamlConverter.toYaml(k8sKindMerged)

        then:
        yaml == """apiVersion: v1
kind: Endpoints
metadata:
  name: mysql-business-svc
subsets:
- addresses:
  - ip: 1.1.1.1
  ports:
  - name: tcp
    port: 3306
"""
    }

    def "should return error when yaml file contains multiple yaml script"() {
        when:
        K8sKindFactory.create(new File("./src/test/resources/mysql-business-svc-mutiple-k8s-kind.yaml"), "test", "projectName")

        then:
        thrown(MultipleYamlScriptException)
    }

    def "should parse yaml success when yaml file contains multiple dot key "() {
        given:
        def k8sKind = K8sKindFactory.create(new File("./src/test/resources/mutiple-dot-key-secret.yaml"), "test", "projectName")
        when:
        String yamlstr = YamlConverter.toYaml(k8sKind);
        then:
        yamlstr == """apiVersion: v1
kind: Secret
type: Opaque
metadata:
  labels:
    app: business-service-app
  name: business-service-app-secret
stringData:
  spring.datasource.username: username
  spring.datasource.password: password
  jwt.user-secret: user-secret
"""
    }
}



