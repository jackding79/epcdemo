package cn.sunline.myrpc.Exception;

public class NoConfigException extends RuntimeException {
    public NoConfigException(){};
    public NoConfigException(String message) {
        super(message);
    }

}
