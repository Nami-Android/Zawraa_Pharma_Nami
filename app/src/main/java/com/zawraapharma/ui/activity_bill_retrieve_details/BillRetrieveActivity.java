package com.zawraapharma.ui.activity_bill_retrieve_details;

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
import android.view.View;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.adapters.InvoiceAdapter2;
import com.zawraapharma.adapters.InvoiceAdapter3;
import com.zawraapharma.databinding.ActivityBillDetailsBinding;
import com.zawraapharma.databinding.ActivityBillRetrieveBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.RetrieveResponseModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.printer_utils.BluetoothUtil;
import com.zawraapharma.printer_utils.ESCUtil;
import com.zawraapharma.printer_utils.SunmiPrintHelper;
import com.zawraapharma.ui.BaseActivity;
import com.zawraapharma.ui.activity_printer.PrinterActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import sunmi.sunmiui.dialog.DialogCreater;
import sunmi.sunmiui.dialog.ListDialog;

public class BillRetrieveActivity extends BaseActivity {
    private ActivityBillRetrieveBinding binding;
    private RetrieveResponseModel model;
    private String lang = "";
    private InvoiceAdapter3 adapter;
    private List<RetrieveResponseModel.BackItemsFk> billList;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_retrieve);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (RetrieveResponseModel) intent.getSerializableExtra("data");
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
        billList.addAll(model.getBack_items_fk());
        adapter = new InvoiceAdapter3(billList, model.getCompany(),this);
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


    private void navigateToPrintActivity() {

       /* final String[] orientation = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        final ListDialog listDialog = DialogCreater.createListDialog(this, getResources().getString(R.string.pic_pos), getResources().getString(R.string.cancel), orientation);
        listDialog.setItemClickListener(new ListDialog.ItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                String item = orientation[position];
                switch (item){
                    case "1":
                        setUpImageToPrint(0,1,1,true,true,1);

                        break;
                    case "2":
                        setUpImageToPrint(0,1,1,true,false,1);

                        break;
                    case "3":
                        setUpImageToPrint(0,1,1,false,true,1);

                        break;
                    case "4":
                        setUpImageToPrint(0,1,1,false,false,1);

                        break;
                    case "5":
                        setUpImageToPrint(1,1,1,false,false,1);

                        break;
                    case "6":
                        setUpImageToPrint(2,1,1,false,false,1);

                        break;
                    case "7":
                        setUpImageToPrint(3,1,1,false,false,1);

                        break;
                    case "8":
                        setUpImageToPrint(4,1,1,false,false,1);

                        break;

                    case "9":
                        setUpImageToPrint(0,1,1,true,true,0);

                        break;
                    case "10":
                        setUpImageToPrint(0,1,1,true,false,0);

                        break;
                    case "11":
                        setUpImageToPrint(0,1,1,false,true,0);

                        break;
                    case "12":
                        setUpImageToPrint(0,1,1,false,false,0);

                        break;
                    case "13":
                        setUpImageToPrint(1,1,1,false,false,0);

                        break;
                    case "14":
                        setUpImageToPrint(2,1,1,false,false,0);

                        break;
                    case "15":
                        setUpImageToPrint(3,1,1,false,false,0);

                        break;
                    case "16":
                        setUpImageToPrint(4,1,1,false,false,0);

                        break;



                }
                listDialog.cancel();
            }
        });
        listDialog.show();*/
        setUpImageToPrint(4,0,0,false,false,1);

       /* Intent intent = new Intent(this, PrinterActivity.class);
        startActivityForResult(intent,100);*/
    }

    private void setUpImageToPrint(int type, int orientation, int align, boolean width, boolean height,int action) {

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
        return b;
    }
}