package com.example.objetivossaludables.pages.HomePages;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.databinding.ActivityMenuBinding;
import com.example.objetivossaludables.pages.fragmenthomepage.HomeFragment;
import com.example.objetivossaludables.pages.fragmenthomepage.NoticiasFrangment;
import com.example.objetivossaludables.pages.fragmenthomepage.SettingsFragment;
import com.example.objetivossaludables.pages.fragmenthomepage.MenusFragment;

public class Menu extends AppCompatActivity {

    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    replaceFragment(new MenusFragment());
                    break;
                case R.id.profileNav:
                    replaceFragment(new SettingsFragment());
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