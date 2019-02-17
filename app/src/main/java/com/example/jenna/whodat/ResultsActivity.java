package com.example.jenna.whodat;
//import com.mashape.unirest.http.Unirest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.services.rekognition.model.Celebrity;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private List<Celebrity> results;
    private TextView textView;
    private TextView editText;
    private ImageView imageView;
    private Button toIMDB;
    private byte[] imgBytes;
    private int size;
    List<String> url;
    //Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        toIMDB = findViewById(R.id.button);

        imageView = findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        results = new ArrayList<>();
        size = bundle.getInt("size");
        //imgBytes = bundle.getByteArray("bytes");

        for (int i = 0; i < size; i++) {
            results.add((Celebrity)bundle.get("results" + i));
        }

        if (size == 0 || results == null) {
            editText.setText("Sorry, try again.");
        } else {
            String names = "";
            for (int i = 0; i < results.size(); i++) {
                names += results.get(i).getName() + "\n";
            }

            url = results.get(0).getUrls();
            editText.setText(names);
        }
        //Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        //imageView.setImageBitmap(bitmap);
        //toIMDB.setText(url.get(0));

    }

    /**
     * go to url
     * @param view
     */
    public void toIMDB (View view) {
        if (size != 0) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + url.get(0)));
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com"));
            startActivity(intent);
        }

    }
}
