package com.xmd.response;

/**
 * @Description 响应结构
 * @Author lixiao
 * @Date 2021/6/29 下午2:50
 */
public class ServerResponse<T> {


    private boolean result;

    private String msg;

    private T data;

    private Integer errorCode;

    public ServerResponse() {

    }

    private ServerResponse(boolean result){
        this.result = result;
    }

    private ServerResponse(boolean result, String msg, Integer errorCode){
        this.result = result;
        this.msg = msg;
        this.errorCode = errorCode;
    }

    private ServerResponse(boolean result, T data){
        this.result = result;
        this.data = data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(true);
    }

    public static <T> ServerResponse<T> createByFail(){
        return new ServerResponse<T>(false);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data){
        return new ServerResponse<T>(true,data);
    }

    public static <T> ServerResponse createByErrorCodeData(Integer code,T t,String msg){
        ServerResponse serverResponse = new ServerResponse(false, msg, code);
        serverResponse.setData(t);
        return serverResponse;
    }

    public static <T> ServerResponse<T> createByFailMsg(String msg, Integer errorCode){
        return new ServerResponse<T>(false,msg,errorCode);
    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(String msg,Integer code){
        return new ServerResponse<T>(false,msg,code);
    }

    public static <T> ServerResponse createByFailData(T t){
        return new ServerResponse<T>(false,t);
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
