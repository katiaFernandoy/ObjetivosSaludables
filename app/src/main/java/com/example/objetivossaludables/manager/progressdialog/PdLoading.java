package com.example.objetivossaludables.manager.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;

public class PdLoading {

    private ProgressDialog pdLoading;

    public PdLoading(Context c){
        pdLoading = new ProgressDialog(c);

        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

    public void dismiss(){
        pdLoading.dismiss();
    }
}
