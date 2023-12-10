package com.example.listapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private List<Tarea> listaTareas;
    private Context context;
    private OnItemClickListener listener;

    public TareasAdapter(List<Tarea> listaTareas, Context context) {
        this.listaTareas = listaTareas;
        this.context = context;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);
        holder.checkBoxTarea.setOnCheckedChangeListener(null);

        holder.checkBoxTarea.setChecked(tarea.isCompletada());
        holder.textViewTarea.setText(tarea.getTitulo());

        // Definiendo tachado de tareas
        holder.checkBoxTarea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tarea.setCompletada(isChecked);
                holder.textViewTarea.setPaintFlags(isChecked ? holder.textViewTarea.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : 0);
            }
        });

        // Eligiendo tarea de la lista
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

        // creacion del boton eliminar tarea
        holder.btnEliminarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEliminarClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTarea;
        TextView textViewTarea;
        Button btnEliminarTarea;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTarea = itemView.findViewById(R.id.checkBoxTarea);
            textViewTarea = itemView.findViewById(R.id.textViewTarea);
            btnEliminarTarea = itemView.findViewById(R.id.btnEliminarTarea);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEliminarClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}