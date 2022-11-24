package com.script.k8s

class MultipleYamlScriptException extends RuntimeException {
    MultipleYamlScriptException(String fileName, Throwable cause) {
        super(fileName,cause)
    }
}
