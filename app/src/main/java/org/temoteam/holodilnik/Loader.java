package org.temoteam.holodilnik;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    public static void loadIMG(String q, ImageView to,Activity activity){
        new GetImgURL(to,activity).execute(q);

    }

    private class GetPrediction extends AsyncTask<String,Void,Boolean>{

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

    private static class GetImgURL extends AsyncTask<String,Void,String>{
        private ImageView to;
        private Activity activity;

        public GetImgURL(ImageView to,Activity activity){this.to=to; this.activity = activity;}

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
        protected String doInBackground(String... params) {
            try {
                return getImgURL(params[0]);
            }
            catch (Exception e){e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("imgURL",s);
            Picasso.with(activity).load(s).error(R.drawable.loading).into(to);
        }
    }
}
