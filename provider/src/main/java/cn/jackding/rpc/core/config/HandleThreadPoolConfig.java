package cn.jackding.rpc.core.config;

public class HandleThreadPoolConfig {
    private int coreSize=Runtime.getRuntime().availableProcessors();
    private int maxSize=200;
    private int queueSize=1000;


    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}
