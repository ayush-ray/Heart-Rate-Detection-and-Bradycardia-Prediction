package com.example.bradycardia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



public class DisplayActivity extends AppCompatActivity {
    Boolean nameFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final RadioGroup activityType = (RadioGroup) findViewById(R.id.radioGroup);
        Button selectFile=(Button)findViewById(R.id.select_btn);
        valid_permissions();
        Button detect = (Button) findViewById(R.id.detect_btn);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checked = activityType.getCheckedRadioButtonId();

                RadioButton radio_selected = (RadioButton) findViewById(checked);
                String file_name = "";
                if(radio_selected != null){
                    file_name = radio_selected.getText().toString().trim();
                }

                ImageView imageView = findViewById(R.id.heartRate_Image);
                ImageView imageView2= findViewById(R.id.ECG_Image);

                switch(file_name){
                    case "Amar":
                        imageView.setImageResource(R.drawable.amar_ecg);
                        imageView.setVisibility(View.VISIBLE);
                        imageView2.setImageResource(R.drawable.amar_hrt);
                        imageView2.setVisibility(View.VISIBLE);
                        //checkedValue="Amar";
                        break;
                    case "Ayush":
                        imageView.setImageResource(R.drawable.ayush_ecg);
                        imageView.setVisibility(View.VISIBLE);
                        imageView2.setImageResource(R.drawable.ayush_hrt);
                        imageView2.setVisibility(View.VISIBLE);
                        //checkedValue="Ayush";

                        break;
                    case "Neeha":
                        imageView.setImageResource(R.drawable.neeharika_ecg);
                        imageView.setVisibility(View.VISIBLE);
                        imageView2.setImageResource(R.drawable.neeharika_hrt);
                        imageView2.setVisibility(View.VISIBLE);
                        //checkedValue="Neeha";
                        break;
                    case "Abhika":
                        imageView.setImageResource(R.drawable.abika_ecg);
                        imageView.setVisibility(View.VISIBLE);
                        imageView2.setImageResource(R.drawable.ayush_hrt);
                        imageView2.setVisibility(View.VISIBLE);
                        //checkedValue="Abhika";
                        break;
                    case "AP":
                        imageView.setImageResource(R.drawable.aravind_ecg);
                        imageView.setVisibility(View.VISIBLE);
                        imageView2.setImageResource(R.drawable.aravind_hrt);
                        imageView2.setVisibility(View.VISIBLE);
                        //checkedValue="AP";
                        break;

                }

            }
        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, DetectionActivity.class);
                int checked = activityType.getCheckedRadioButtonId();
                RadioButton radio_selected = (RadioButton) findViewById(checked);
                String file_name = "";
                if(radio_selected != null){
                    file_name = radio_selected.getText().toString().trim();
                    if("AP".compareTo(file_name)==0){
                        file_name="Ayush";
                    }
                }

                Log.d("Radio Button", "Count:" +file_name );
                intent.putExtra("intVariableName",file_name );

                DisplayActivity.this.startActivity(intent);
            }
        });

        Button predict = (Button) findViewById(R.id.predict_btn);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, PredictionActivity.class);
                int checked = activityType.getCheckedRadioButtonId();
                RadioButton radio_selected = (RadioButton) findViewById(checked);
                Log.d("Radio Button", "Count:" + checked);
                String name = radio_selected.getText().toString();
                 if("Neeha".compareTo(name)==0){
                     name="Ayush";
                 }
                 if("Abhika".compareTo(name)==0){
                     name="AP";
                 }


                Log.d("Radio Button", "Count:" + checked);
                intent.putExtra("intVariableName", name);


                DisplayActivity.this.startActivity(intent);
            }
        });
    }


    private void valid_permissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);

            }
        }
    }


}

