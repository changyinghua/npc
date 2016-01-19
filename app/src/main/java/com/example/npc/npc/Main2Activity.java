package com.example.npc.npc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    private Map<String,DataValue> map;

    private Map<String,DataValue> mock;

    private Button verifyButton;

    private TextView frontTextView;

    private TextView rearTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("NPC Barcode Reader");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.map=new HashMap<>();
        this.mock=new HashMap<>();

        DataValue d =new DataValue();
        d.setFront("20");
        d.setRear("20");


        this.mock.put("J0420160115160000", d);
        this.mock.put("J0420160115170000",d);
        this.mock.put("J0420160115180000",d);
        this.mock.put("J0420160115190000",d);
        this.mock.put("J0420160115200000",d);
        this.mock.put("J0420160115210000",d);



        this.verifyButton=(Button)findViewById(R.id.verifyButton);
        this.verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "SCAN_MODE");

                try {
                    if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() == 0) {
                        Toast.makeText(Main2Activity.this, "Not Install", Toast.LENGTH_LONG).show();

                    } else {

                        startActivityForResult(intent, 1);
                    }
                } catch (ActivityNotFoundException ex) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.zxing.client.android"));
                    startActivity(i);
                }
            }
        });

       /* this.frontTextView = (TextView)findViewById(R.id.frontEditText);
        this.frontTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    Toast.makeText(Main2Activity.this, "Please Enter value", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.rearTextView = (TextView)findViewById(R.id.rearEditText);
        this.rearTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    Toast.makeText(Main2Activity.this, "Please Enter value", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            this.map.clear();
           /* if( 0==requestCode && null!=data && data.getExtras()!=null ) {
                //掃描結果存放在 key 為 la.droid.qr.result 的值中
                String result = data.getExtras().getString("la.droid.qr.result");

                TextView mScanResult = (TextView)findViewById(R.id.mScanResult);
                mScanResult.setText(result);  //將結果顯示在 TextVeiw 中
            }*/

            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "掃描成功", Toast.LENGTH_LONG).show();
                    // ZXing回傳的內容
                    String contents = data.getStringExtra("SCAN_RESULT");
                    //ZXing回傳的內容
                    // String contents = data.getStringExtra("SCAN_RESULT_FORMAT");
                    TextView r = (TextView) findViewById(R.id.barcodeResult);
                    r.setText(contents);

                    String machine = "";
                    String year = "";
                    String month = "";
                    String day = "";
                    String hour = "";
                    String min = "";
                    String sec = "";
                    //假如長度小於17 //barcode錯誤

                    // TextView result = (TextView)findViewById(R.id.resultTextView);
                    //  result.setText("此產品"+ contents.trim()+"經驗證後，生產來源不明，請洽經銷商");


                    try{
                        machine = contents.substring(0, 3);
                        year = contents.substring(3, 7);
                        month = contents.substring(7, 9);
                        day = contents.substring(9, 11);
                        hour = contents.substring(11, 13);
                        min = contents.substring(13, 15);
                        sec = contents.substring(15, 17);
                    }catch (Exception ex){
                        Toast.makeText(this, "掃描的barcode長度錯誤!", Toast.LENGTH_LONG).show();
                        TextView result = (TextView) findViewById(R.id.resultTextView);
                        result.setText("掃描的barcode長度錯誤!");
                        return;
                    }




                  /*  try
                    {
                        //取得SD卡儲存路徑
                        File mSDFile = Environment.getExternalStorageDirectory();
                        Toast.makeText(this, mSDFile.toString(), Toast.LENGTH_LONG).show();
                        //讀取文件檔路徑
                       // FileReader mFileReader = new FileReader(mSDFile+"/config.txt");
                        //讀取文件檔路徑
                        FileReader mFileReader = new FileReader(mSDFile.getParent() + "/" + mSDFile.getName() + "/config.txt");

                        BufferedReader mBufferedReader = new BufferedReader(mFileReader);

                        String mTextLine = mBufferedReader.readLine();

                        //一行一行取出文字字串裝入String裡，直到沒有下一行文字停止跳出
                        while (mTextLine!=null)
                        {
                            Toast.makeText(this, mTextLine, Toast.LENGTH_LONG).show();
                            ArrayList<DataValue> dataList = new ArrayList<DataValue>();
                            String s = mTextLine.trim();
                            String[] cArray = s.split("，");

                            String barcode = cArray[0].trim();
                            String front = cArray[1].trim();
                            String rear =cArray[2].trim();
                            DataValue d = new DataValue();
                            d.setFront(front);
                            d.setRear(rear);
                            dataList.add(d);

                         //   this.map.put(barcode,dataList);
                            //mReadText += mTextLine+"\n";
                            mTextLine = mBufferedReader.readLine();
                        }*/
                    try {
                        //把掃描的barcode 跟 txt檔的barcode做比較
                        DataValue valueList = this.mock.get(contents.trim());
                        if (valueList != null) {
                            String txtFront = valueList.getFront();
                            String txtRear = valueList.getRear();

                            TextView t1 = (TextView)findViewById(R.id.frontEditText);
                            TextView t2 = (TextView)findViewById(R.id.rearEditText);

                            if (txtFront.equals(t1.getText().toString().trim()) && txtRear.equals(t2.getText().toString().trim())) {
                                TextView result = (TextView) findViewById(R.id.resultTextView);
                                result.setText("此產品" + contents.trim() + "經驗證後為南亞" + year + "年" + month + "月" + day + "日" + hour + "時" + min + "分" + sec + "秒，由" + machine + "機台製作的PVC硬管");
                            } else {
                                TextView result = (TextView) findViewById(R.id.resultTextView);
                                result.setText("此產品" + contents.trim() + "經驗證後，生產來源不明，請洽經銷商");
                            }

                        } else {
                            TextView result = (TextView) findViewById(R.id.resultTextView);
                            result.setText("此產品" + contents.trim() + "經驗證後，生產來源不明，請洽經銷商");
                            // Key might be present...
                          /*  if (map.containsKey(contents.trim())) {
                                // Okay, there's a key but the value is null
                            } else {
                                // Definitely no such key
                            }*/

                        }

                    } catch (Exception ex) {
                        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "取消掃描", Toast.LENGTH_LONG).show();
                    }
                }

         /*   int r=  (int)(Math.random()*15 +10);

            int r2=  (int)(Math.random()*20 +880);

            TextView t1 = (TextView)findViewById(R.id.distance);
            t1.setText(Integer.toString(r));

            TextView t2 = (TextView)findViewById(R.id.weight);
            t2.setText(Integer.toString(r2));*/

            }
        }
    }
}
