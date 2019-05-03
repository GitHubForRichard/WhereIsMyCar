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

public class DialogPassword extends AppCompatDialogFragment {
    private EditText editTextPassword;
    private DialogPasswordListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogpassword, null);

        builder.setView(view)
                .setTitle("Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = editTextPassword.getText().toString();

                        listener.updatepassword(password);
                    }
                });

        editTextPassword = view.findViewById(R.id.editText_password);


        return builder.create();
    }

    @Override


    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogPasswordListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogPasswordListener");
        }
    }

    public interface DialogPasswordListener {
        void updatepassword(String password);
    }
}