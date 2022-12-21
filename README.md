# k8s-kind-inheritance-generator
用于多套环境的k8s配置生成，简化重复配置代码的编写，只关注各环境的不同配置项

## a. Example
```yaml

├── k8s                                                                   
│   └── business-service
│       ├── business-service-endpoint.yaml                 
│       └── env
│           ├── dev
│           │   └── business-service-endpoint.yaml   
│           ├── prod
│           │   └── business-service-endpoint.yaml
│           └── test
│               └── business-service-endpoint.yaml

···

├── generated
│   ├── dev
│   │   └── business-service-endpoint.yaml
│   ├── prod
│   │   └── business-service-endpoint.yaml
│   └── test
│       └── business-service-endpoint.yaml

```
模板文件`k8s/business-service/business-service-endpoint.yaml`是完整的 k8s yaml文件
```yaml
---
apiVersion: v1
kind: Endpoints
metadata:
  name: business-service-svc
subsets:
  - addresses:
      - ip: 1.2.3.4
    ports:
      - name: tcp
        port: 8080
```
环境文件`k8s/business-service/env/dev/business-service-endpoint.yaml`只关注不同配置项，但是必须逐级加入，如ip需逐级写下subsets->address->ip
```yaml
subsets:
  - addresses:
      - ip: 5.6.7.8
```

## b. Installation

>该步骤获取到的zip压缩包用于后续使用

- **手动构建**

```shell
$ gradle build distZip
```
在项目目录可执行以上命令进行打包构建，构建成功后程序包为`./build/distributions/k8s-kind-inheritance-generator.zip`

- **在线下载**

可以直接在https://github.com/tw-open/k8s-kind-inheritance-generator/releases下载

## c. Usage
```shell
unzip k8s-kind-inheritance-generator.zip
./k8s-kind-inheritance-generator/bin/k8s-kind-inheritance-generator <path_to_template> ./generated
```
