package com.zawraapharma.ui.activity_printer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityPharmacyDetailsBinding;
import com.zawraapharma.databinding.ActivityPrinterBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.printer_utils.BluetoothUtil;
import com.zawraapharma.printer_utils.ESCUtil;
import com.zawraapharma.printer_utils.SunmiPrintHelper;
import com.zawraapharma.ui.BaseActivity;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;
import com.zawraapharma.ui.activity_payment_date.PaymentDateActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import io.paperdb.Paper;
import sunmi.sunmiui.dialog.DialogCreater;
import sunmi.sunmiui.dialog.ListDialog;

public class PrinterActivity extends BaseActivity {
    private ActivityPrinterBinding binding;
    private String lang;
    int mytype=0;
    int myorientation=0;
    int mAlign = 0;
    boolean width = false;
    boolean height = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_printer);
        initView();
    }



    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        if(!BluetoothUtil.isBlueToothPrinter){
            binding.picStyle.setVisibility(View.GONE);
            binding.picType.setVisibility(View.GONE);
            binding.picOrientation.setVisibility(View.VISIBLE);
        }else{
            binding.picStyle.setVisibility(View.VISIBLE);
            binding.picType.setVisibility(View.VISIBLE);
            binding.picOrientation.setVisibility(View.GONE);
        }

        binding.picAlign.setOnClickListener(v -> {
            final String[] pos = new String[]{getResources().getString(R.string.align_left), getResources().getString(R.string.align_mid), getResources().getString(R.string.align_right)};
            final ListDialog listDialog =  DialogCreater.createListDialog(PrinterActivity.this, getResources().getString(R.string.align_form), getResources().getString(R.string.cancel), pos);
            listDialog.setItemClickListener(position -> {
                binding.picAlignInfo.setText(pos[position]);

                listDialog.cancel();
            });
            listDialog.show();
        });
        binding.picType.setOnClickListener(v -> {
            final String[] type = new String[]{getResources().getString(R.string.pic_mode1),getResources().getString(R.string.pic_mode2), getResources().getString(R.string.pic_mode3), getResources().getString(R.string.pic_mode4), getResources().getString(R.string.pic_mode5)};
            final ListDialog listDialog = DialogCreater.createListDialog(PrinterActivity.this, getResources().getString(R.string.pic_mode), getResources().getString(R.string.cancel), type);
            listDialog.setItemClickListener(position -> {
                binding.picTypeInfo.setText(type[position]);
                mytype = position;
                if(position == 0){
                    binding.picStyle.setVisibility(View.VISIBLE);
                }else{
                    binding.picStyle.setVisibility(View.INVISIBLE);
                }
                listDialog.cancel();
            });
            listDialog.show();

        });
        binding.picOrientation.setOnClickListener(v -> {
            final String[] orientation = new String[]{getResources().getString(R.string.pic_hor),getResources().getString(R.string.pic_ver)};
            final ListDialog listDialog = DialogCreater.createListDialog(PrinterActivity.this, getResources().getString(R.string.pic_pos), getResources().getString(R.string.cancel), orientation);
            listDialog.setItemClickListener(new ListDialog.ItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    binding.picOrientationInfo.setText(orientation[position]);
                    myorientation = position;
                    listDialog.cancel();
                }
            });
            listDialog.show();
        });

        binding.picWidth.setOnClickListener(v -> {
            if (binding.picWidth.isChecked()){
                width = true;
            }else {
                width = false;
            }
        });

        binding.picHeight.setOnClickListener(v -> {
            if (binding.picHeight.isChecked()){
                height = true;
            }else {
                height = false;
            }
        });


        binding.print.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("type", mytype);
            intent.putExtra("orientation",myorientation);
            intent.putExtra("align",mAlign);
            intent.putExtra("width",width);
            intent.putExtra("height",height);
            setResult(RESULT_OK,intent);
            finish();
        });

        binding.llBack.setOnClickListener(v -> finish());

    }
}