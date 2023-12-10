package com.example.listapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AgregarTareaDialog.AgregarTareaListener {

    private RecyclerView recyclerViewTareas;
    private TareasAdapter tareasAdapter;
    private List<Tarea> listaTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTareas = findViewById(R.id.recycler_view_tareas);
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(this));

        listaTareas = obtenerListaTareas();
        tareasAdapter = new TareasAdapter(listaTareas, this);

        // Configurando onItemclick, despues de iniciar adaptador
        tareasAdapter.setOnItemClickListener(new TareasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mostrarEditarTareaDialog(position);
            }

            @Override
            public void onEliminarClick(int position) {
                eliminarTarea(position);
            }
        });

        recyclerViewTareas.setAdapter(tareasAdapter);

        Button btnAgregar = findViewById(R.id.btn_agregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAgregarTareaDialog();
            }
        });
    }

    private void eliminarTarea(int position) {
        // Eliminar la tarea de la lista permanentemente
        listaTareas.remove(position);

        tareasAdapter.notifyItemRemoved(position);
    }


    private void mostrarAgregarTareaDialog() {
        AgregarTareaDialog agregarTareaDialog = new AgregarTareaDialog();
        agregarTareaDialog.setAgregarTareaListener(new AgregarTareaDialog.AgregarTareaListener() {
            @Override
            public void onGuardarClick(String titulo, String descripcion, String tipo) {
                // creacion de nueva tarea
                Tarea nuevaTarea = new Tarea(titulo, descripcion, tipo, false);

                listaTareas.add(nuevaTarea);

                tareasAdapter.notifyItemInserted(listaTareas.size() - 1);

                this.onTareaGuardada(nuevaTarea);
            }

            @Override
            public void onTareaGuardada(Tarea tarea) {

            }
        });
        agregarTareaDialog.show(getSupportFragmentManager(), "agregar_tarea_dialog");
    }
//definiendo metodo editar tarea
    private void mostrarEditarTareaDialog(int posicion) {
        Tarea tareaSeleccionada = listaTareas.get(posicion);

        AgregarTareaDialog editarTareaDialog = AgregarTareaDialog.newInstance(
                tareaSeleccionada.getTitulo(),
                tareaSeleccionada.getDescripcion(),
                tareaSeleccionada.getTipo()
        );

        editarTareaDialog.setAgregarTareaListener(new AgregarTareaDialog.AgregarTareaListener() {
            @Override
            public void onGuardarClick(String titulo, String descripcion, String tipo) {
                tareaSeleccionada.setTitulo(titulo);
                tareaSeleccionada.setDescripcion(descripcion);
                tareaSeleccionada.setTipo(tipo);
                tareasAdapter.notifyItemChanged(posicion);
            }

            @Override
            public void onTareaGuardada(Tarea tarea) {
            }
        });

        editarTareaDialog.show(getSupportFragmentManager(), "editar_tarea_dialog");
    }


    @Override
    public void onGuardarClick(String titulo, String descripcion, String tipo) {
        Tarea nuevaTarea = new Tarea(titulo, descripcion, tipo, false);

        listaTareas.add(nuevaTarea);
        tareasAdapter.notifyDataSetChanged();

        tareasAdapter.notifyDataSetChanged();

        this.onTareaGuardada(nuevaTarea);
    }

    @Override
    public void onTareaGuardada(Tarea tarea) {

    }

    //mostrando tarea de ejemplo, pudiendo eliminar la que aparece de ejemplo
    private List<Tarea> obtenerListaTareas() {
        List<Tarea> listaTareas = new ArrayList<>();
        listaTareas.add(new Tarea("Tarea 1", "Descripción 1", "Tipo 1", false));
        return listaTareas;
    }

//Boton cerrar app
    public void cerrarAplicacion(View view){
        finishAffinity();
    }
}