package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaapp.data.AsyncFinishedInteface;
import com.example.triviaapp.data.QuestionBank;
import com.example.triviaapp.model.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button trueBtn, falseBtn;
    ImageButton nextBtn, backBtn;
    ImageView icon;
    CardView cardView;
    TextView questionTv;
    int currentIndex = 0;
    ArrayList<Question> questionArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueBtn = findViewById(R.id.true_button);
        falseBtn = findViewById(R.id.false_button);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);
        questionTv = findViewById(R.id.questionTv);

        questionArrayList = new QuestionBank().getArrayQuestion(new AsyncFinishedInteface() {
            @Override
            public void processFinished(ArrayList<Question> arrayList) {
                questionTv.setText(questionArrayList.get(currentIndex).getQuestion());

            }
        });

        trueBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.false_button:
                checkAnswers(false);
                break;
            case R.id.true_button:
                checkAnswers(true);
                break;
            case R.id.nextBtn:
                currentIndex = (currentIndex + 1) % questionArrayList.size();
                updateQuestion();
                break;

            case R.id.backBtn:
                if (currentIndex > 0) {
                    currentIndex = (currentIndex - 1) % questionArrayList.size();
                    updateQuestion(); }
                break;
        }

    }

    public void updateQuestion() {
            questionTv.setText(questionArrayList.get(currentIndex).getQuestion());
        }
    public void checkAnswers(boolean userChoosen){
        int toastMessage = 0;
        boolean correctAnswer = questionArrayList.get(currentIndex).isTrue();
        if(userChoosen == correctAnswer){
            toastMessage = R.string.correct_answer;
        }else{
            toastMessage = R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this, toastMessage , Toast.LENGTH_SHORT).show();
    }

}