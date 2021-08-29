package com.shivamingawale.background.Adapters;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySinglton {
    private final RequestQueue requestQueue;
    private static VolleySinglton volleySinglton;

    private VolleySinglton(Context context) {
        requestQueue= Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized VolleySinglton getvolleySinglton(Context context){
        if(volleySinglton==null){
            volleySinglton=new VolleySinglton(context);
        }
        return volleySinglton;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
