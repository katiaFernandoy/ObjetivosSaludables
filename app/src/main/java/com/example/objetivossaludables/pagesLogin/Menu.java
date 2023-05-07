package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.objetivossaludables.databinding.ActivityMenuBinding;
import com.example.objetivossaludables.fragmenthomepage.HomeFragment;
import com.example.objetivossaludables.fragmenthomepage.NoticiasFrangment;
import com.example.objetivossaludables.R;
import com.example.objetivossaludables.fragmenthomepage.SettingsFragemt;
import com.example.objetivossaludables.fragmenthomepage.TrofeosFragemt;

public class Menu extends AppCompatActivity {

    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_iniciarSesion);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new NoticiasFrangment());

        binding.navigationBar.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.homeNav:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.newsNav:
                    replaceFragment(new NoticiasFrangment());
                    break;
                case R.id.trophyNav:
                    replaceFragment(new TrofeosFragemt());
                    break;
                case R.id.profileNav:
                    replaceFragment(new SettingsFragemt());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}