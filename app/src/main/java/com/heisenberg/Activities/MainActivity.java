package com.heisenberg.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.heisenberg.R;
import com.heisenberg.Retrofit.AuthToken;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token",null);
        loginButton=findViewById(R.id.login_button);
        if(token!=null){
            moveToHome();
        }

    }
    public void goToSignUpActivity(View view){
        Intent intent=new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
    public void moveToHome(){
        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void login(View view){
        EditText username=findViewById(R.id.username);
        EditText password=findViewById(R.id.password);
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter details first", Toast.LENGTH_SHORT).show();
            return;
        }
        User user=new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        login(user);
    }
    public void login(final User user){
        loginButton.setEnabled(false);
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<AuthToken> call=jsonPlaceHolderApi.login(user);
        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if(response.body()!=null) {
                    AuthToken authToken = response.body();
                    sharedPreferences.edit().putString("token", authToken.getToken()).apply();
                    sharedPreferences.edit().putString("username",user.getUsername()).apply();
                    loginButton.setEnabled(true);
                    moveToHome();
                }else {
                    loginButton.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                loginButton.setEnabled(true);
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
