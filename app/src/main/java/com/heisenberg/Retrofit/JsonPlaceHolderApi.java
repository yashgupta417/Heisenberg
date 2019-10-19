package com.heisenberg.Retrofit;

import com.heisenberg.Retrofit.Serializers.Contest;
import com.heisenberg.Retrofit.Serializers.Participant;
import com.heisenberg.Retrofit.Serializers.Question;
import com.heisenberg.Retrofit.Serializers.SubmitResponse;
import com.heisenberg.Retrofit.Serializers.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("question_list/")
    Call<List<Question>> getQuestions();

    @GET("contest_list/")
    Call<List<Contest>> getContests( @Query("q") String q);

    @GET("contest_detail/{id}")
    Call<Contest> getContestDetail(@Path("id") String id);

    @POST("login/")
    Call<AuthToken> login(@Body User user);

    @POST("signup/")
    Call<User> signup(@Body User user);

    @GET("user_detail/{username}/")
    Call<User> getUserDetail(@Path("username") String username);

    @GET("user_list/")
    Call<List<User>> searchUsers(@Query("q") String s);

    @GET("contest/{contest_id}/participants/")
    Call<List<Participant>> getParticipantsOfContest(@Path("contest_id") String s);

    @POST("contest/{contest_id}/{user_username}/")
    Call<Participant> registerForContest(@Path("contest_id") String s,@Path("user_username") String v);

    @GET("{username}/contests/")
    Call<List<Participant>> getUserParticipation(@Path("username") String s);

    @POST("submit/{c_id}/{q_id}/{p_id}/")
    Call<SubmitResponse> submitAnswer(@Path("c_id")Integer c,@Path("q_id")Integer q, @Path("p_id")Integer p, @Query("answer")Integer answer);

    @Multipart
    @PATCH("user_detail/{username}/")
    Call<User> updateProfileImage(@Path("username") String username, @Part MultipartBody.Part image);
}
