package com.example.tarea22grupo3pm1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context mContext;
    private int mResource;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String titulo = getItem(position).getTitle();
        String cuerpo = getItem(position).getBody();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitulo = convertView.findViewById(R.id.textViewTitulo);
        TextView tvCuerpo = convertView.findViewById(R.id.textViewCuerpo);

        tvTitulo.setText(titulo);
        tvCuerpo.setText(cuerpo);

        return convertView;
    }
}
