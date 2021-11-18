package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.zawraapharma.R;
import com.zawraapharma.databinding.PharmacyRowBinding;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.ui.activity_find_pharmacy.FindPharmacyActivity;
import com.zawraapharma.ui.activity_pay_pill.PayPillActivity;

import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyHolder> {

    private List<PharmacyModel> list;
    private Context context;
    private AppCompatActivity activity;

    public PharmacyAdapter(List<PharmacyModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (AppCompatActivity) context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            PharmacyRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pharmacy_row, parent, false);
            return new MyHolder(binding);
        }catch (Exception e){
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        PharmacyModel model = list.get(position);
        holder.binding.setModel(model);
        holder.itemView.setOnClickListener(view -> {
            PharmacyModel model2 = list.get(holder.getAdapterPosition());
            if (activity instanceof PayPillActivity){
                PayPillActivity payPillActivity = (PayPillActivity) activity;
                payPillActivity.setItemData(model2);
            }else if (activity instanceof FindPharmacyActivity){
                FindPharmacyActivity findPharmacyActivity = (FindPharmacyActivity) activity;
                findPharmacyActivity.setItemData(model2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private PharmacyRowBinding binding;

        public MyHolder(PharmacyRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
