package com.example.tarea22grupo3pm1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallePostActivity extends AppCompatActivity {

    private TextView tvTitulo, tvCuerpo, tvUserId, tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_post);

        tvTitulo = findViewById(R.id.textViewDetalleTitulo);
        tvCuerpo = findViewById(R.id.textViewDetalleCuerpo);
        tvUserId = findViewById(R.id.textViewDetalleUserId);
        tvId = findViewById(R.id.textViewDetalleId);

        int postId = getIntent().getIntExtra("POST_ID", -1);
        if (postId != -1) {
            obtenerDetallePost(postId);
        } else {
            Toast.makeText(this, "ID de post no válido.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void obtenerDetallePost(int postId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonPlaceholderApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderApi api = retrofit.create(JsonPlaceholderApi.class);

        Call<Post> call = api.getPostById(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetallePostActivity.this, "Error al obtener detalle: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Post post = response.body();
                if (post != null) {
                    tvTitulo.setText(post.getTitle());
                    tvCuerpo.setText(post.getBody());
                    tvUserId.setText("ID de Usuario: " + post.getUserId());
                    tvId.setText("ID del Post: " + post.getId());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(DetallePostActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_CALL", "Error al obtener detalle del post: " + t.getMessage(), t);
            }
        });
    }
}