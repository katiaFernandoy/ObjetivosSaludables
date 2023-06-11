package com.example.objetivossaludables.pages.fragmenthomepage;


import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsId;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_DESACTIVAR_CUENTA;
import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;
import com.example.objetivossaludables.pages.configuracion.ConfgPreferencias;
import com.example.objetivossaludables.pages.configuracion.ConfgSeguridad;
import com.example.objetivossaludables.pages.inicioapp.Portada;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SettingsFragment extends Fragment implements ApiInterface {

    private UserPreferences preferences;
    private PdLoading pdLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings_fragment, container, false);

        setColorState(getContext(),
                Arrays.asList(
                        root.findViewById(R.id.bt_cerrarSesion),
                        root.findViewById(R.id.bt_eliminarCuenta)));

        Button bt_cerrar = root.findViewById(R.id.bt_cerrarSesion);
        Button bt_eliminarCuenta = root.findViewById(R.id.bt_eliminarCuenta);
        LinearLayout linearLayoutConfPersonal = root.findViewById(R.id.linearLayoutConfPersonal);
        LinearLayout linearLayoutPreferencias = root.findViewById(R.id.linearLayoutPreferencias);
        LinearLayout linearLayoutSeguridad = root.findViewById(R.id.linearLayoutSeguridad);
        preferences = new UserPreferences(getContext());

        linearLayoutConfPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgPersonal.class);
                startActivity(intent);
            }
        });

        linearLayoutPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgPreferencias.class);
                startActivity(intent);
            }
        });

        linearLayoutSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgSeguridad.class);
                startActivity(intent);
            }
        });

        bt_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        bt_eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        return root;
    }

    public void cerrarSesion() {
        preferences.clearPreferences();
        ((Activity) getContext()).finish();
        Intent intent = new Intent(getContext(), Portada.class);
        startActivity(intent);
    }

    public void alertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alerta");
        builder.setMessage("¿Deseas desactivar su cuenta?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pdLoading = new PdLoading(getContext());

                String id = String.valueOf(preferences.getUserId());
                new ApiHandler(SettingsFragment.this, URL_DESACTIVAR_CUENTA, getParamsId(id)).start();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void returnResponse(JSONObject json) {
        pdLoading.dismiss();

        try {
            if (json.getBoolean("error")) {
                Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();

            preferences.cerrarSesion();

            Intent intent = new Intent(getContext(), Portada.class);
            startActivity(intent);
            ((Activity) getContext()).finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}