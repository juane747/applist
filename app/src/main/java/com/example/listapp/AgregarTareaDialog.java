package com.example.listapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.app.Dialog;
import android.widget.Spinner;

public class AgregarTareaDialog extends DialogFragment {

    private EditText editTextTitulo;
    private EditText editTextDescripcion;

    private Spinner spinnerTipo;
//definiendo constructores
    public interface AgregarTareaListener {
        void onGuardarClick(String titulo, String descripcion, String tipo);
        void onTareaGuardada(Tarea tarea);
    }

    private AgregarTareaListener listener;
//uso de dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_agregar_tarea, null);

        builder.setView(view)
                .setTitle("Agregar Tarea")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String titulo = editTextTitulo.getText().toString();
                        String descripcion = editTextDescripcion.getText().toString();
                        String tipo = spinnerTipo.getSelectedItem().toString();
                        listener.onGuardarClick(titulo, descripcion, tipo);
                        dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AgregarTareaDialog.this.getDialog().cancel();
                    }
                });

        editTextTitulo = view.findViewById(R.id.editTextTitulo);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        spinnerTipo = view.findViewById(R.id.spinnerTipo);

      //definiendo los tipos aceptados de cada tarea
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.opciones_tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        Bundle args = getArguments();
        if (args != null) {
            String titulo = args.getString("titulo", "");
            String descripcion = args.getString("descripcion", "");
            String tipo = args.getString("tipo", "");

            editTextTitulo.setText(titulo);
            editTextDescripcion.setText(descripcion);

            int position = adapter.getPosition(tipo);
            spinnerTipo.setSelection(position);
        }

        return builder.create();
    }


    public void setAgregarTareaListener(AgregarTareaListener listener) {
        this.listener = listener;
    }

    public static AgregarTareaDialog newInstance(String titulo, String descripcion, String tipo) {
        AgregarTareaDialog fragment = new AgregarTareaDialog();
        Bundle args = new Bundle();
        args.putString("titulo", titulo);
        args.putString("descripcion", descripcion);
        args.putString("tipo", tipo);
        fragment.setArguments(args);
        return fragment;
    }

}
