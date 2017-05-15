package com.example.enzo.chatbasedonlocation;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Enzo on 14.5.2017..
 */

public class CommunicationController<T> {
    private static String loginUrl;
    private static String signupUrl;
    private int responseCode;
    private JSONObject jsonResponse;
    private Context context;
    private String Result;

    public CommunicationController(Context context) {
        this.context = context;
        AppConfig appConfig = new AppConfig();
        loginUrl = appConfig.URL_LOGIN;
        signupUrl = appConfig.URL_REGISTER;
    }

    public String ConvertObjectToJson(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(t);
        return jsonInString;
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
