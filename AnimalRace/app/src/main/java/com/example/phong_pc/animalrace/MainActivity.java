package com.example.phong_pc.animalrace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //define variables
    CheckBox _cbNum1, _cbNum2, _cbNum3;
    SeekBar _sbNum1, _sbNum2, _sbNum3;
    int _numWinner, _flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapCoponents();
        _cbNum1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(_flag==0) {
                        Toast.makeText(MainActivity.this, "Bạn đã chọn gã lãng tử từ địa ngục", Toast.LENGTH_LONG).show();
                        _numWinner = 1;
                        _flag =1 ;
                    }
                    else {
                        _cbNum1.toggle();
                    }
                }
                else if(_flag==1){
                    _flag=0;
                }
            }
        });

        _cbNum2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(_flag==0) {
                        Toast.makeText(MainActivity.this, "Bạn đã chọn quỷ sa tăng", Toast.LENGTH_LONG).show();
                        _numWinner = 2;
                        _flag =1 ;
                    }
                    else {
                        _cbNum2.toggle();
                    }
                }

                else if(_flag==1){
                    _flag=0;
                }
            }
        });

        _cbNum3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(_flag==0) {
                        Toast.makeText(MainActivity.this, "Bạn đã chọn sói bạc cô đơn và kêu hảnh", Toast.LENGTH_LONG).show();
                        _numWinner = 3;
                        _flag =1 ;
                    }
                    else {
                        _cbNum3.toggle();
                    }
                }

                else if(_flag==1){
                    _flag=0;
                }
            }
        });


    }

    private void mapCoponents(){
        _cbNum1 = (CheckBox) findViewById(R.id.cbNum1);
        _cbNum2 = (CheckBox) findViewById(R.id.cbNum2);
        _cbNum3 = (CheckBox) findViewById(R.id.cbNum3);

        _sbNum1 = (SeekBar) findViewById(R.id.sbAnimal1);
        _sbNum2 = (SeekBar) findViewById(R.id.sbAnimal2);
        _sbNum3 = (SeekBar) findViewById(R.id.sbAnimal3);
    }
}
