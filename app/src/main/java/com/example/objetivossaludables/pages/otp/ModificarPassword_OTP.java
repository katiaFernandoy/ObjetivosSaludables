package com.example.objetivossaludables.pages.otp;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsOTP;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_MODIFICAR_PWD;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.regex.Pattern;

public class ModificarPassword_OTP extends AppCompatActivity implements ApiInterface {

    private TextInputEditText txt_pwd, txt_pwdRepe;
    private PdLoading pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_password_otp);

        setColorState(this, Collections.singletonList(findViewById(R.id.bt_modificarPassWord_otp)));
        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        txt_pwd = findViewById(R.id.txt_passWordNueva_otp);
        txt_pwdRepe = findViewById(R.id.txt_passWordNuevaRepe_otp);

        findViewById(R.id.bt_modificarPassWord_otp).setOnClickListener(v -> {
            if(!verificar()){
                return;
            }

            ((TextView) findViewById(R.id.lbError)).setTextColor(Color.WHITE);
            pdLoading = new PdLoading(this);

            new ApiHandler(this,
                    URL_MODIFICAR_PWD,
                    getParamsOTP(new UserPreferences(this).getUserEmail(), getTexto(txt_pwd))).start();
        });
    }

    private boolean verificar() {
        if (txt_pwd.getText() == null || getTexto(txt_pwd).equals("")) {
            Toast.makeText(this, getResources().getText(R.string.campoObligatorio), Toast.LENGTH_SHORT).show();
            return false;
        }if (!verificarPassword()) {
            ((TextView) findViewById(R.id.lbError)).setTextColor(Color.RED);
            return false;
        }
        if (!getTexto(txt_pwd).equals(getTexto(txt_pwdRepe))) {
            Toast.makeText(this, getResources().getText(R.string.passNoCoinciden), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean verificarPassword() {
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(getTexto(txt_pwd)).find();
    }

    @Override
    public void returnResponse(JSONObject json) {
        pdLoading.dismiss(); // Se cierra el PdLoading

        try {
            if(json.getBoolean("error")){
                Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }finally {
            //Importante, en este método dentro del hilo secundario,
            // si queremos modificar  alguna vista, hay que hacerlo con runOnUiThread
            runOnUiThread(() -> {
                txt_pwd.setText("");
                txt_pwdRepe.setText("");
            });
        }
    }
}