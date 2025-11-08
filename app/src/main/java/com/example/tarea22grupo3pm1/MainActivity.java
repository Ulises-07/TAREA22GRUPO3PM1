package com.example.tarea22grupo3pm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listViewPosts;
    private PostAdapter postAdapter;
    private List<Post> listaPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPosts = findViewById(R.id.listViewPosts);
        postAdapter = new PostAdapter(this, R.layout.item_post, listaPosts);
        listViewPosts.setAdapter(postAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonPlaceholderApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderApi api = retrofit.create(JsonPlaceholderApi.class);

        Call<List<Post>> call = api.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Post> posts = response.body();
                if (posts != null) {
                    listaPosts.clear();
                    listaPosts.addAll(posts);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error al obtener posts: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_CALL", "Error: " + t.getMessage(), t);
            }
        });

        listViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post postSeleccionado = listaPosts.get(position);
                Intent intent = new Intent(MainActivity.this, DetallePostActivity.class);
                intent.putExtra("POST_ID", postSeleccionado.getId());
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonSalvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Funcionalidad de Salvar (no implementada completamente)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}