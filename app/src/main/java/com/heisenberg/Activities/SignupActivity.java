package com.heisenberg.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.heisenberg.LoadingDialog;
import com.heisenberg.R;
import com.heisenberg.Retrofit.AuthToken;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void signUp(View view){
        User user=new User();
        EditText firstName=findViewById(R.id.s_firstName);
        EditText lastName=findViewById(R.id.s_last_name);
        EditText email=findViewById(R.id.s_email);
        EditText username=findViewById(R.id.s_username);
        EditText password=findViewById(R.id.s_password);
        EditText password2=findViewById(R.id.s_confirmPassword);
        String fName=firstName.getText().toString();
        String lName=lastName.getText().toString();
        String _email=email.getText().toString();
        String _username=username.getText().toString();
        String _password=password.getText().toString();
        String _password2=password2.getText().toString();
        if(fName.isEmpty() || lName.isEmpty() || _email.isEmpty() || _username.isEmpty() || _password.isEmpty() || _password2.isEmpty()){
            Toast.makeText(this, "Enter All Details ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!_password.equals(_password2)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        user.setFirstName(fName);
        user.setLastName(lName);
        user.setUsername(_username);
        user.setEmail(_email);
        user.setPassword(_password);
        signUp(user);
    }
    LoadingDialog dialog;
    public void signUp(final User user){
        dialog=new LoadingDialog(this);
        dialog.showDialog();
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<User> call=jsonPlaceHolderApi.signup(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    login(user);
                }else {
                    Toast.makeText(SignupActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void login(final User user){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<AuthToken> call=jsonPlaceHolderApi.login(user);
        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if(response.body()!=null) {
                    AuthToken authToken = response.body();
                    sharedPreferences=getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("token", authToken.getToken()).apply();
                    sharedPreferences.edit().putString("username",user.getUsername()).apply();
                    dialog.hideDialog();
                    moveToHome();
                }else {
                    Toast.makeText(SignupActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    dialog.hideDialog();
                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                dialog.hideDialog();
            }
        });
    }
    public void moveToHome(){
        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
