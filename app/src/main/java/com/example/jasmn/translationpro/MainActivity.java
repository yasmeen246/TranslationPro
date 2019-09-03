package com.example.jasmn.translationpro;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
EditText wordtextt;
TextView wordtrans;
Spinner fromlan,tolan;
TextToSpeech speech;
    databaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordtextt=findViewById(R.id.wordtext);
        wordtrans=findViewById(R.id.showtt);
        fromlan=findViewById(R.id.spinnerlan1);
        tolan=findViewById(R.id.spinnerlan2);
        speech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
           speech.setPitch(0.994f);
           speech.setSpeechRate(0.8f);
            }
        });
        }
    String tex;
    String l1;String l2;

    public void translate(View view) {
        tex = wordtextt.getText().toString();

        l1 = fromlan.getSelectedItem().toString();

        l2 = tolan.getSelectedItem().toString();
        RequestQueue queue=Volley.newRequestQueue(this);
        String url="http://api.mymemory.translated.net/get?q="+tex+"!&langpair="+l1+"|"+l2;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,this, this);
        queue.add(request);
     //   databaseHelper = new databaseHelper(this);

      //  boolean insert = databaseHelper.insert(tex,l1,translations,l2);

    }

    public void find(View view) {
        databaseHelper = new databaseHelper(this);

        tex = wordtextt.getText().toString();
        ArrayList<String>strings=new ArrayList<>();
        strings=databaseHelper.get_translat(tex);
        wordtrans.setText(strings.get(0));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }
    String translations;
    @Override
    public void onResponse(JSONObject response) {

        try {
            translations = response.getJSONObject("responseData").getString("translatedText");
        } catch (JSONException e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        wordtrans.setText(translations);
        speech.speak(translations,TextToSpeech.QUEUE_FLUSH,null);

    }
}
