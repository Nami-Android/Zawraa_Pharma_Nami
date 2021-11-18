package com.zawraapharma.ui.activity_bill_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.zawraapharma.R;
import com.zawraapharma.adapters.InvoiceAdapter2;
import com.zawraapharma.databinding.ActivityBillDetailsBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.LoginModel;
import com.zawraapharma.models.PrinterModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.zawraapharma.preferences.Preferences;

import com.zawraapharma.printer_utils.BluetoothUtil;
import com.zawraapharma.printer_utils.ESCUtil;
import com.zawraapharma.printer_utils.SunmiPrintHelper;
import com.zawraapharma.ui.activity_printer.PrinterActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class BillDetailsActivity extends AppCompatActivity {
    private ActivityBillDetailsBinding binding;
    private BillResponse.Data model;
    private String lang = "";
    private InvoiceAdapter2 adapter;
    private List<BillResponse.Data.Bill> billList;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private Bitmap bitmap;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_details);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BillResponse.Data) intent.getSerializableExtra("data");
    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);


        billList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(model);
        binding.setUserModel(userModel);
        binding.tvTitle.setPaintFlags(binding.tvTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        billList.addAll(model.getBills());
        adapter = new InvoiceAdapter2(billList, this);
        binding.recView.setAdapter(adapter);
        binding.btnShow.setOnClickListener(view -> {
            checkWritePermission();

        });

        binding.btnBack.setOnClickListener(v -> finish());
        binding.llBack.setOnClickListener(view -> finish());


    }

    private void checkWritePermission(){
        if (ContextCompat.checkSelfPermission(this,write_permission)== PackageManager.PERMISSION_GRANTED){
            bitmap = getBitmapFromView(binding.llBill);
            navigateToPrintActivity();
        }else {
            String [] permission = {write_permission};
            ActivityCompat.requestPermissions(this,permission,200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
           int type = data.getIntExtra("type",0);
           int orientation = data.getIntExtra("orientation", 0);
           int align = data.getIntExtra("align", 0);
           boolean width = data.getBooleanExtra("width", false);
           boolean height = data.getBooleanExtra("height", false);
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==200&&grantResults.length>0){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                 bitmap = getBitmapFromView(binding.llBill);
                 navigateToPrintActivity();



            }else {
                Toast.makeText(this, "Access storage in your device denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpImageToPrint(int type, int orientation, int align, boolean width, boolean height) {

        if(!BluetoothUtil.isBlueToothPrinter){
            SunmiPrintHelper.getInstance().setAlign(align);
        }else{
            byte[] send;
            if(align == 0){
                send = ESCUtil.alignLeft();
            }else if(align == 1){
                send = ESCUtil.alignCenter();
            }else {
                send = ESCUtil.alignRight();
            }
            BluetoothUtil.sendData(send);
        }



        if (!BluetoothUtil.isBlueToothPrinter) {
            SunmiPrintHelper.getInstance().printBitmap(bitmap, orientation);
            SunmiPrintHelper.getInstance().feedPaper();

        } else {
            if(type == 0){
                if(width && height){
                    BluetoothUtil.sendData(ESCUtil.printBitmap(bitmap, 3));
                }else if(width){
                    BluetoothUtil.sendData(ESCUtil.printBitmap(bitmap, 1));
                }else if(height){
                    BluetoothUtil.sendData(ESCUtil.printBitmap(bitmap, 2));
                }else{
                    BluetoothUtil.sendData(ESCUtil.printBitmap(bitmap, 0));
                }
            }else if(type == 1){
                BluetoothUtil.sendData(ESCUtil.selectBitmap(bitmap, 0));
            }else if(type == 2){
                BluetoothUtil.sendData(ESCUtil.selectBitmap(bitmap, 1));
            }else if(type == 3){
                BluetoothUtil.sendData(ESCUtil.selectBitmap(bitmap, 32));
            }else if(type == 4){
                BluetoothUtil.sendData(ESCUtil.selectBitmap(bitmap, 33));
            }
            BluetoothUtil.sendData(ESCUtil.nextLine(3));
        }

    }

    private void navigateToPrintActivity() {
        setUpImageToPrint(4,0,0,false,false);

       /* Intent intent = new Intent(this, PrinterActivity.class);
        startActivityForResult(intent,100);*/
    }






    public static Bitmap getBitmapFromView(View view) {
        long now = System.currentTimeMillis();
        Bitmap returnBitmap = null;

        try {

           /* BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 160;
            options.inDensity = 160;*/

            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile,false);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            if (imageFile.exists()) {
                returnBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        Bitmap b = Bitmap.createScaledBitmap(returnBitmap,384,576,true);
        return b;    }

}