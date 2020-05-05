package cn.sunline.myrpc.provider.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "my.rpc.zookeeper")
@Component
public class RegistryConfig {
    private String host;
    private String port;
    private int sessionTimeOut;
    private int connTimeout;
    private Boolean registry=false;
    private String basePackages;
    private String registryHost;
    private int registryPort;
    private String path;
    private boolean isNio;

    public Boolean getIsNio() {
        return isNio;
    }

    public void setIsNio(Boolean isNio) {
        this.isNio = isNio;
    }

    public String getRegistryHost() {
        return registryHost;
    }

    public void setRegistryHost(String registryHost) {
        this.registryHost = registryHost;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public void setRegistryPort(int registryPort) {
        this.registryPort = registryPort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }

    public Boolean getRegistry() {
        return registry;
    }

    public void setRegistry(Boolean registry) {
        this.registry = registry;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
