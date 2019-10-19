package com.heisenberg.Retrofit.Serializers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Participant {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("intital_rating")
    @Expose
    private Integer intitalRating;
    @SerializedName("rating_change")
    @Expose
    private Integer ratingChange;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("final_rating")
    @Expose
    private Integer finalRating;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("contest")
    @Expose
    private Contest contest;
    @SerializedName("questions_solved_count")
    @Expose
    private Integer questionSolvedCount;
    /**
     * No args constructor for use in serialization
     *
     */
    public Participant() {
    }

    /**
     *
     * @param ratingChange
     * @param id
     * @param rank
     * @param finalRating
     * @param score
     * @param contest
     * @param intitalRating
     * @param user
     * @param questionSolvedCount
     */
    public Participant(Integer id, Integer intitalRating, Integer ratingChange, Integer score, Integer rank, Integer finalRating, User user, Contest contest,Integer questionSolvedCount) {
        super();
        this.id = id;
        this.intitalRating = intitalRating;
        this.ratingChange = ratingChange;
        this.score = score;
        this.rank = rank;
        this.finalRating = finalRating;
        this.user = user;
        this.contest = contest;
        this.questionSolvedCount=questionSolvedCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntitalRating() {
        return intitalRating;
    }

    public void setIntitalRating(Integer intitalRating) {
        this.intitalRating = intitalRating;
    }

    public Integer getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(Integer ratingChange) {
        this.ratingChange = ratingChange;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(Integer finalRating) {
        this.finalRating = finalRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Integer getQuestionSolvedCount() {
        return questionSolvedCount;
    }

    public void setQuestionSolvedCount(Integer questionSolvedCount) {
        this.questionSolvedCount = questionSolvedCount;
    }
}
