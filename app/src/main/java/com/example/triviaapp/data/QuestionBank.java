package com.example.triviaapp.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<Question> arrayQuestion = new ArrayList<>();

    public ArrayList<Question> getArrayQuestion(final AsyncFinishedInteface callBack) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            for(int i = 0 ; i < response.length() ; i++){
                Question question = new Question();
                try {
                    question.setQuestion(response.getJSONArray(i).get(0).toString());
                    question.setTrue(response.getJSONArray(i).getBoolean(1));
                    arrayQuestion.add(question);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
                if (null != callBack) callBack.processFinished(arrayQuestion);}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getmInstance().getRequestQueue().add(jsonArrayRequest);
        return arrayQuestion;
    }
}
