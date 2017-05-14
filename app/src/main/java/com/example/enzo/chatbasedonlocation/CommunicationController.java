package com.example.enzo.chatbasedonlocation;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Enzo on 14.5.2017..
 */

public class CommunicationController<T> {
    private static String loginUrl;
    private static String signupUrl;
    private int responseCode;
    private JSONObject responseString;
    private Context context;

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

    // Treba prepravit!
    public int PostLogin(String jsonData) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String mRequestBody = jsonData;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.statusCode);
                    responseCode = response.statusCode;
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
        return responseCode;
    }
}
