package com.example.tarea22grupo3pm1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int postId);
}
