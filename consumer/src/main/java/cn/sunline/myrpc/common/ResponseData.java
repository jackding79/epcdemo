package cn.sunline.myrpc.common;

public class ResponseData {
    private String status;
    private String resCode;
    private String message;
    private Object object;
    public ResponseData(){
        this.status="OK";
        this.resCode="200";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
}
