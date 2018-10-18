package yunnuo.baseframe.function.gsonconverter;

/**
 * Created by hxb on 2018/4/26.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
    }
//    @Override public T convert(ResponseBody value) throws IOException {
//        try {
//            String response = value.string();
//            JSONObject jsonObject = null;
//            JSONObject result = null;
//            JSONObject addtion = null;
//            try {
//                jsonObject = new JSONObject(response);
//                result = jsonObject.getJSONObject("result");
//                addtion = jsonObject.getJSONObject("addtion");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            JsonReader jsonReader = null;
//            MediaType mediaType = value.contentType();
//            Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
//            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
//            jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
//
//            if (addtion!=null){
//                String nativeId = addtion.optString("nativeId");
//                if ("NAT_LOGIN".equals(nativeId)){
//                    Intent loginIntent = new Intent(MyApp.getInstance(), LoginActivity.class);
//                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    MyApp.getInstance().startActivity(loginIntent);
//                }else if("NAT_CARD".equals(nativeId)){
//                    Intent cardIntent = new Intent(MyApp.getInstance(), BankCardActivity.class);
//                    cardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    MyApp.getInstance().startActivity(cardIntent);
//                }else if ("NAT_AUTH".equals(nativeId)){
//                    Intent authIntent = new Intent(MyApp.getInstance(), IdentificationActivity.class);
//                    authIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    MyApp.getInstance().startActivity(authIntent);
//                }
//            }
//
//            String resultCode = result.optString("resultCode");
//            if (!"00".equals(resultCode)){
//                value.close();
//                throw new CustomException(result.optString("resultMsg"));
//            }
//            String msgcde = jsonObject.optString("msgcde",null);
//            if (msgcde==null||"00".equals(msgcde)) {
//                return adapter.read(jsonReader);
//            }else {
//                value.close();
//                throw new SystemException(jsonObject.optString("rtnmsg"));
//            }
//
//        } finally {
//            value.close();
//        }
//    }
}