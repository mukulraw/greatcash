package com.tbx.gc.greatcash.QuestionPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataQuestion
{

    @SerializedName("questionId")
    @Expose
    private String questionID;

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    @SerializedName("question")
    @Expose
    private String Question;

}
