package cn.huanjv.base_module.base;

import java.util.List;

public class BaseGson<T> {
    private String code;
    private String msg;
    private List<T> data;
    private long time;
    private boolean status;
    private T singleData;



    public T getSingleData() {
        return singleData;
    }

    public void setSingleData(T singleData) {
        this.singleData = singleData;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getCode() {
        return code.equals("200")?true:false;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseGson{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
