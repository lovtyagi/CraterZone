package com.example.createrzone;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    RequestQueue requestQueue;
    CustomViewModel customViewModel;

    public Repository(RequestQueue queue, CustomViewModel viewModel) {
        this.requestQueue = queue;
        this.customViewModel = viewModel;
    }

    public void getSearchResult(String tag) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=7f6e0b808c9a1056baed32c1d161becd&text=" + tag + "&format=json&nojsoncallback=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<String> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONObject("photos").getJSONArray("photo");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        String url = "https://farm" + jsonObj.getString("farm") + ".staticflickr.com/" + jsonObj.getString("server") + "/" + jsonObj.getString("id") + "_" + jsonObj.getString("secret") + ".jpg";
                        if (list.size() < 11) {
                            list.add(url);
                        } else {
                            break;
                        }
                    }
                    //customViewModel.list.postValue(list);
                    customViewModel.getResponse(list);
                } catch (Exception ex) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s = error.getMessage();
                if (s.equals("")) {
                    //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
