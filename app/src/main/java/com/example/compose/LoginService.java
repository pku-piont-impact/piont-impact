package com.example.compose;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface LoginService {
    @GET("/default/login")
    Call<LoginResponse> login();
}

