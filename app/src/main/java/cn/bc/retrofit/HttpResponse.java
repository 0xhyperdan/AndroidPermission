package cn.bc.retrofit;

/**
 * Created by iscod.
 * Time:2016/6/21-14:46.
 */
public class HttpResponse<T> {

    public String msg;
    public BaseRes<T> res;
    public String state;

    public static class BaseRes<T> {
        public int code;
        public String msg;
        public T data;

        @Override
        public String toString() {
            return "\n" + "   [code: " + code + "\n" + "     msg: " + msg + "\n" + "    data: " + data;
        }
    }

    @Override
    public String toString() {
        return "[服务器返回信息:]" + "\n" + "msg:" + msg + "\n" + "res:" + res.toString() + "\n" + "state:" + state;
    }
}
