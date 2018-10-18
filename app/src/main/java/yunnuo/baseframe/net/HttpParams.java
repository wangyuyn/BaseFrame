package yunnuo.baseframe.net;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by wangyu on 2017/7/23.
 */

public class HttpParams {
    private Map<String, String> mParams = new HashMap<>();
    private Map<String, String> mHeader = new HashMap<>();

    private RequestBody body;
    private String functionId;

    public HttpParams setBody(Object obj) {
//        body = RequestBody.create(MediaType.parse("text/html;charset=UTF-8"), new Gson().toJson(new CommonRequest(functionId, obj)));
        return this;
    }

    public RequestBody getBody() {
//        if (body==null){
//            return RequestBody.create(MediaType.parse("text/html;charset=UTF-8"), new Gson().toJson(new CommonRequest(functionId, mParams)));
//        }else{
            return body;
//        }

    }

    public HttpParams(String functionId) {
        this.functionId = functionId;
        mHeader.put("ClientOs", "ANDROID");
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public HttpParams put(String key, String value) {
        mParams.put(key, value);
        return this;
    }


    public Map<String, String> getHeader() {
        return mHeader;
    }


    public HttpParams putHeader(String key, String value) {
        mHeader.put(key, value);
        return this;
    }

    public HttpParams putHeader(Map<String, String> headers) {
        mHeader.putAll(headers);
        return this;
    }

    public String toGetParams() {

        StringBuilder buffer = new StringBuilder();
        if (!mParams.isEmpty()) {
            buffer.append("?");
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                try {
                    buffer.append(URLEncoder.encode(key, "UTF-8"));
                    buffer.append("=");
                    buffer.append(URLEncoder.encode(value, "UTF-8"));
                    buffer.append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        String str = buffer.toString();
        //去掉最后的&
        if (str.length() > 1 && str.endsWith("&")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
