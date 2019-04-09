package com.example.richa.buttonclickapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogName extends AppCompatDialogFragment {
    private EditText editTextLastname;
    private EditText editTextFirstname;
    private DialogNameListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Name")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String lastname = editTextLastname.getText().toString();
                        String firstname = editTextFirstname.getText().toString();
                        listener.applyTexts(lastname, firstname);
                    }
                });

        editTextLastname = view.findViewById(R.id.editText_lastname);
        editTextFirstname = view.findViewById(R.id.editText_firstname);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogNameListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogNameListener");
        }
    }

    public interface DialogNameListener {
        void applyTexts(String lastname, String firstname);
    }
}