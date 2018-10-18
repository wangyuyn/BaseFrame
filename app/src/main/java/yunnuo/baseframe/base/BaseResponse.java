package yunnuo.baseframe.base;

/**
 * Created by hxb on 2018/4/23.
 */

import java.util.HashMap;

/**
 * 前期框架使用 后期不需要
 */
public class BaseResponse {

    public static class ResultBean {
        public String resultCode;
        public String resultMsg;
    }

    public ResultBean result;

    public Addtion addtion;

    public String msgcde;

    public String rtnmsg;

    public static class Addtion{
        public String nativeId;
        public HashMap<String, String> params;
    }
}
