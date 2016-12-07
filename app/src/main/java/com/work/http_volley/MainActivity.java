package com.work.http_volley;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.work.http_volley.GsonPack.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    TextView textView;
    TextView jsonTV;
    TextView fromJsonTV;
    Gson gson;
    Student student;

    String jsonArray;

    /**
     * 包装成json格式
     **/
    private void init() {
        student = new Student();
        /**创建gson**/
        gson = new Gson();
        student.age = 18;
        student.sex = 0;
        student.name = "JETYIN";
        student.stuNo = "2012";
        /**书籍集合**/
        List<String> list = new ArrayList<>();
        list.add("苏菲的世界");
        list.add("人间食粮");
        list.add("质数的孤独");
        student.bookList = list;
        /**成绩**/
        Map<String, Integer> map = new HashMap<>();
        map.put("语文", 98);
        map.put("数学", 118);
        map.put("物理", 87);
        student.scoreMap = map;
        /**将student包装成json格式**/
        jsonArray = gson.toJson(student);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        init();
        textView = (TextView) findViewById(R.id.hello_tv);
        jsonTV = (TextView) findViewById(R.id.json_tv);
        fromJsonTV = (TextView) findViewById(R.id.fromjson_tv);
        /**包装成json格式**/
        jsonTV.setText(jsonArray);

        /**将json格式翻译成class**/
        Student studentGson = gson.fromJson(jsonArray, Student.class);
        List<String> listGson = studentGson.bookList;
        Map<String, Integer> mapGson = studentGson.scoreMap;

        //遍历map集合
        Iterator it = mapGson.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Integer value = (Integer) entry.getValue();
            Log.e(key, String.valueOf(value));
        }

        //

        for (String list : listGson) {
            Log.e("LIST_STRING", list);
        }

        fromJsonTV.setText(new StringBuilder()
                .append(studentGson.name).append("-")
                .append(studentGson.age).append("-")
                .append(studentGson.stuNo).append("-")
                .append(studentGson.sex));


        //获取请求队列对象
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //创建一个string请求，或是创建一个json请求,需要传入参数，url，请求成功回调，请求失败回调
        /**JsonArrayRequest，JsonRequest，ImageRequest均是继承自response**/
       /* new JsonArrayRequest()
        new JsonRequest<>()
        new ImageRequest();*/
        /**volley中没有GsonRequest，如需和Gson配合使用，需要手动的和继承Response**/
        StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            /**Response回调**/
            @Override
            public void onResponse(String response) {
                textView.setText(response);
                Log.e("SUCESSCE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("FAILED", volleyError.getMessage());
            }
        });

        /**post请求需要对参数进行封装，需要重写Request的 getparams方法**/

        StringRequest mRequest = new StringRequest(Request.Method.POST, "http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            /**重写request的getparams方法将参数进行封装**/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }
        };

        //将创建的请求加入请求队列
        requestQueue.add(stringRequest);
    }

}
