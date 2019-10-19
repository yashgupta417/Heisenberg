package com.heisenberg.Retrofit.Serializers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("problem_name")
    @Expose
    private String problemName;
    @SerializedName("problem")
    @Expose
    private String problem;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("is_available_for_practice")
    @Expose
    private Boolean is_available_for_practice;
    @SerializedName("solved_by")
    @Expose
    private List<User> solvedBy;
    @SerializedName("solved_by_count")
    @Expose
    private Integer solvedByCount;


    /**
     * No args constructor for use in serialization
     *
     */
    public Question() {
    }
    /**
     *
     * @param id
     * @param answer
     * @param problem
     * @param solution
     * @param problemName
     * @param is_available_for_practice
     * @param solvedBy
     * @param solvedByCount
     */
    public Question(Integer id, String problemName, String problem, String answer, String solution, String difficulty, Integer points, Boolean is_available_for_practice, List<User> solvedBy, Integer solvedByCount) {
        this.id = id;
        this.problemName = problemName;
        this.problem = problem;
        this.answer = answer;
        this.solution = solution;
        this.difficulty = difficulty;
        this.points = points;
        this.is_available_for_practice = is_available_for_practice;
        this.solvedBy = solvedBy;
        this.solvedByCount = solvedByCount;
    }






    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Boolean getIs_available_for_practice() {
        return is_available_for_practice;
    }

    public void setIs_available_for_practice(Boolean is_available_for_practice) {
        this.is_available_for_practice = is_available_for_practice;
    }

    public List<User> getSolvedBy() {
        return solvedBy;
    }

    public void setSolvedBy(List<User> solvedBy) {
        this.solvedBy = solvedBy;
    }

    public Integer getSolvedByCount() {
        return solvedByCount;
    }

    public void setSolvedByCount(Integer solvedByCount) {
        this.solvedByCount = solvedByCount;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
