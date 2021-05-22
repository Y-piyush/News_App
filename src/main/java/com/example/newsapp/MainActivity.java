package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements newsItemClicked, headingclicked
{
    String url="https://newsdata.io/api/1/news?apikey=pub_193eeaa1214e6b03c0c7aff73a5a6234aa7&language=en&country=in";

    private  RecyclerNewsAdapter mAdapter;
    private OuterRecycler oAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycle2=findViewById(R.id.recyclerView2);
        recycle2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));

        ArrayList<String> dat=new ArrayList<>(Arrays.asList("ALL","TECHNOLOGY","SPORTS","HEALTH","SCIENCE","ENTERTAINMENT"));
        oAdapter=new OuterRecycler(dat,this);
        recycle2.setAdapter(oAdapter);
        // oAdapter.updatedata(dat);


        RecyclerView recycle=findViewById(R.id.recyclerView);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        fill_with_data(url);



        mAdapter= new RecyclerNewsAdapter( this);
        recycle.setAdapter(mAdapter);


    }
   private void fill_with_data(String url)
    {  //RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
       // url="https://newsdata.io/api/1/news?apikey=pub_193eeaa1214e6b03c0c7aff73a5a6234aa7&language=en&country=in";

        JsonObjectRequest jsonobjectrequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray newsarray = response.getJSONArray("results");
                     ArrayList<News> newsarr =  new ArrayList<>();
                    for (int i = 0; i < newsarray.length(); i++) {
                        JSONObject newsobject = newsarray.getJSONObject(i);
                        News news= new News(newsobject.getString("title"), newsobject.getString("link"), newsobject.getString("image_url"));
                        newsarr.add(news);
                    }
                    mAdapter.newsupdate(newsarr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
           Toast.makeText(MainActivity.this,"Failed to open!",Toast.LENGTH_LONG).show();
            }
        }


        );
                MySingleton.getInstance(this).addToRequestQueue(jsonobjectrequest);
    }

    @Override
    public void itemonclick( News pos) {
        /* Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(pos.url));
         startActivity(intent);*/
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(pos.url));

    }


 /*  public void clicktext(View view) {

        url="https://newsdata.io/api/1/news?apikey=pub_193eeaa1214e6b03c0c7aff73a5a6234aa7&language=en&country=in&category=technology";
        fill_with_data(url);
    }

    public void clicknext(View view) {
        url="https://newsdata.io/api/1/news?apikey=pub_193eeaa1214e6b03c0c7aff73a5a6234aa7&language=en&country=in&category=sports";
        fill_with_data(url);
    }

    public void clickon(View view) {
        url="https://newsdata.io/api/1/news?apikey=pub_193eeaa1214e6b03c0c7aff73a5a6234aa7&language=en&country=in";
        fill_with_data(url);
    }*/

    @Override
    public void headingisclicked(int pos) {
        ArrayList<String> urllist=new ArrayList<>(Arrays.asList("","&category=technology","&category=sports","&category=health","&category=science","&category=entertainment"));
        url= "https://newsdata.io/api/1/news?apikey=pub_193eeaa1214e6b03c0c7aff73a5a6234aa7&language=en&country=in";
        fill_with_data(url+urllist.get(pos));
    }
}