package org.temoteam.holodilnik.Main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.temoteam.holodilnik.Adapters.StepsReciclerAdapter;
import org.temoteam.holodilnik.R;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class RecipeActivity extends AppCompatActivity {

    static String id;

    TextView title;
    TextView description;
    TextView ingradients;
    TextView likes;
    TextView time;
    ImageView image;
    RecyclerView steps;



    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public static void updata(String id){
        RecipeActivity.id = id;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        setContentView(R.layout.activity_recipe);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        ingradients = (TextView) findViewById(R.id.ingradiends);
        likes = (TextView) findViewById(R.id.likes);
        time = (TextView) findViewById(R.id.time);
        steps = (RecyclerView) findViewById(R.id.recicler);
        image = (ImageView) findViewById(R.id.img);
        super.onResume();
        new GetData(this).execute();

    }

    class GetData extends AsyncTask<Void,Void,Void>{

        public GetData(RecipeActivity recipeActivity){
            this.recipeActivity = recipeActivity;
        }

        RecipeActivity recipeActivity;

        String title;
        String description;
        String ingradients;
        String likes;
        String time;

        ArrayList<String> steps;
        ArrayList<String> links;

        @Override
        protected Void doInBackground(Void... params) {
            try{
                String myURL = "http://lohness.com/hlad/recipe_get.php?id="+id;
                URL url = new URL(myURL);

                Scanner in = new Scanner(url.openConnection().getInputStream());
                in.useDelimiter("::");
                title = in.next();
                description = in.next();
                time = in.next();
                likes = in.next();
                ingradients = in.next();
                publishProgress();

                steps = new ArrayList<String>();
                links = new ArrayList<String>();

                myURL = "http://lohness.com/hlad/steps/"+id;
                url = new URL(myURL);
                in = new Scanner(url.openConnection().getInputStream());
                while (in.hasNextLine()){
                    String temp = in.nextLine();
                    int c = temp.indexOf("|");
                    if (c>0){
                    steps.add(temp.substring(0,c));
                    links.add(temp.substring(c+1));}
                }

            }
            catch (Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            recipeActivity.title.setText(title);
            recipeActivity.description.setText(description);
            recipeActivity.ingradients.setText(ingradients);
            recipeActivity.likes.setText(likes);
            recipeActivity.time.setText(time);
            Picasso.with(recipeActivity).load("http://lohness.com/hlad/photo/recipe_"+id+".png").error(R.drawable.loading).into(image);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            StepsReciclerAdapter sra = new StepsReciclerAdapter(recipeActivity);
            sra.init(steps,links);
            recipeActivity.steps.setLayoutManager(new LinearLayoutManager(recipeActivity));
            recipeActivity.steps.setAdapter(sra);

        }
    }
}
