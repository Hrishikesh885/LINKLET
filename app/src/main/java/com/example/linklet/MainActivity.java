package com.example.linklet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String FolderName,file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitBtn= findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    // for permission allowed
                    FolderName="test";
                    createDirectory("Linklet","Personal").getPath();
                    Intent intent=new Intent(MainActivity.this, file_activity.class);
                    String path=Environment.getExternalStorageDirectory()+"/Android/"+"data/"+"com.example.linklet/"+"files/";
                    Uri uri = Uri.parse(path);
                    intent.setDataAndType(uri,"*/*");

                            intent.putExtra("path",path);
                            startActivity(intent);
                }
                else{
                    //for permission not allowed
                    requestPermission();
                }
            }
        });
    }

    private File createDirectory(String dirName, String subDir) {
        File file;
        if(subDir!=null){
            file = new File(getExternalFilesDir(null)+"/"+dirName+"/"+subDir);
        }
        else{
            file=new File(getExternalFilesDir(null)+"/"+dirName);
        }
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }


    private boolean checkPermission(){
        int result=ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else
            return false;
    }
    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "STORAGE PERMISSION IS REQUIRED", Toast.LENGTH_SHORT).show();
        }
        else
        ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }

}