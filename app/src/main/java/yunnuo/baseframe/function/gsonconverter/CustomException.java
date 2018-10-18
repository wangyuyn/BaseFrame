package yunnuo.baseframe.function.gsonconverter;

/**
 * Created by hxb on 2018/4/26.
 */

public class CustomException extends RuntimeException  {
    public CustomException(String errormsg) {
        super(errormsg);
    }
    public CustomException(int code, String errormsg) {
        super(errormsg);
    }

    public CustomException(int code, int i, String errormsg) {
    }
}
