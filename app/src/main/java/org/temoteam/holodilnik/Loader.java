package org.temoteam.holodilnik;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {

    private RecyclerView recyclerView;
    private Activity activity;




    public Loader(RecyclerView recyclerView, Activity activity){
        this.recyclerView = recyclerView;
        this.activity = activity;

    }

    public void load(String q){
        new GetPrediction().execute(q);
    }

    public static void loadIMG(String id,String title, ImageView to,Activity activity){

        class Getter implements Callback {

            ImageView to;
            Activity activity;
            String id;
            String title;

            public Getter (String id,String title, ImageView to,Activity activity){
                this.to = to;
                this.id = id;
                this.activity = activity;
                this.title = title;
            }


            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                 new GetImgURL(to,activity,id,title).execute();
            }
        }

        Picasso.with(activity).load("http://lohness.com/hlad/photo/food_"+id+".png").error(R.drawable.loading).into(to, new Getter(id,title,to,activity));


    }

    private class GetPrediction extends AsyncTask<String,Void,Boolean>{

        //lol github kek

        ArrayList<String> titles;
        ArrayList<String> ids;

        public void getPredicrion (String q) throws Exception{
            titles = new ArrayList<String>();
            ids = new ArrayList<String>();


            URLConnection connection = new URL("http://amam.ru/ajax/getTipsHolod.php?txt="+ URLEncoder.encode(q, "UTF-8")).openConnection();
            Log.i("Connection started",connection.toString());
            InputStream is = connection.getInputStream();
            Scanner in2 = new Scanner(is,"windows-1251");
            String ans = in2.nextLine();
            Scanner in = new Scanner(ans.replaceAll("\"", "").replaceAll("</div", "").replaceAll("<div", "")+">");
            in = in.useDelimiter("txt_");
            in.next();
            while(in.hasNext()){
                String temp = in.next();
                int first = temp.indexOf(">");
                String temp1 = temp.substring(first+1,temp.length()-1);
                ids.add(temp.substring(0,first));
                titles.add(temp1.substring(0,temp1.indexOf(">")));
            }

        }

        @Override
        protected Boolean doInBackground(String... params) {


            try{
                if (params[0].length()>1){
                    getPredicrion(params[0]);
                return true;}
            }
            catch (Exception e){
                e.printStackTrace();

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean a) {
            super.onPostExecute(a);
            if (a){
                SearchRecrclerAdapter searchRecrclerAdapter = new SearchRecrclerAdapter(activity);
                searchRecrclerAdapter.init(titles,ids);
                recyclerView.removeAllViewsInLayout();
                if (recyclerView.getLayoutManager()==null)
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(searchRecrclerAdapter);
            }
            else {

            }
        }
    }

    private static class GetImgURL extends AsyncTask<Void,Void,String>{
        private ImageView to;
        private Activity activity;
        private String id;
        private String title;

        public GetImgURL(ImageView to,Activity activity,String id,String title){this.to=to;this.activity = activity;this.id = id;this.title = title;}

        public String getImgURL(String q) throws Exception{
            URL url = new URL("http://go.mail.ru/search_images?q="+URLEncoder.encode(q, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Scanner in2 = new Scanner(conn.getInputStream(),"utf-8");
            String answer = "";
            while (in2.hasNextLine())
                answer += in2.nextLine();
            if (answer.contains("&amp;frm=captcha_error"))
                return getImgURL(q);
            String end = "";
            System.out.println(answer);
            int first = answer.indexOf("\"PNG\",\"imUrl\": \"")+16;
            end = answer.substring(first,answer.substring(first,answer.length()-1).indexOf("\"")+first);
            return end;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return getImgURL(title);
            }
            catch (Exception e){e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            class Getter implements Callback {


                String id;
                String url;
                String title;

                public Getter (String id,String url,String title){

                    this.id = id;
                    this.url = url;
                    this.title = title;

                }


                @Override
                public void onSuccess() {
                    new ServerSender(id,url,title).execute();
                }

                @Override
                public void onError() {

                }
            }
            Picasso.with(activity).load(s).error(R.drawable.loading).into(to, new Getter(id,s,title));
        }
    }

    static class ServerSender extends AsyncTask<Void,Void,Void>{

        String id;
        String url;
        String title;

        public ServerSender(String id, String url,String title){
            this.id = id;
            this.url = url;
            this.title = title;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL conn = new URL("http://lohness.com/hlad/data_add.php?id="+id+"&title="+URLEncoder.encode(title, "UTF-8")+"&url="+url);
                System.out.println(conn.toString());
                conn.openConnection().getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
