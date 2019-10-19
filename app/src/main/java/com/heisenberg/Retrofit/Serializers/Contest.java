package com.heisenberg.Retrofit.Serializers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contest {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("starting_time")
    @Expose
    private String startingTime;
    @SerializedName("starting_date")
    @Expose
    private String startingDate;
    @SerializedName("is_finished")
    @Expose
    private Boolean is_finished;
    @SerializedName("no_of_questions")
    @Expose
    private Integer noOfQuestions;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Contest() {
    }



    public Contest(Integer id){
        this.id=id;
    }
    /**
     *
     * @param id
     * @param startingTime
     * @param startingDate
     * @param name
     * @param endTime
     * @param endDate
     * @param questions
     * @param is_finished
     * @param noOfQuestions
     */

    public Contest(Integer id, String name, String endTime, String endDate, String startingTime, String startingDate, Boolean is_finished, Integer noOfQuestions, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.endTime = endTime;
        this.endDate = endDate;
        this.startingTime = startingTime;
        this.startingDate = startingDate;
        this.is_finished = is_finished;
        this.noOfQuestions = noOfQuestions;
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }



    public Integer getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Boolean getIs_finished() {
        return is_finished;
    }

    public void setIs_finished(Boolean is_finished) {
        this.is_finished = is_finished;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}