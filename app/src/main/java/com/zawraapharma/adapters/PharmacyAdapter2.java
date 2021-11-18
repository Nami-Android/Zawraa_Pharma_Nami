package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.PharmacyRow2Binding;
import com.zawraapharma.databinding.PharmacyRowBinding;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.ui.activity_calender.CalenderActivity;
import com.zawraapharma.ui.activity_find_pharmacy.FindPharmacyActivity;
import com.zawraapharma.ui.activity_pay_pill.PayPillActivity;

import java.util.List;

public class PharmacyAdapter2 extends RecyclerView.Adapter<PharmacyAdapter2.MyHolder> {

    private List<PharmacyModel> list;
    private Context context;
    private CalenderActivity activity;

    public PharmacyAdapter2(List<PharmacyModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (CalenderActivity) context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            PharmacyRow2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pharmacy_row2, parent, false);
            return new MyHolder(binding);
        }catch (Exception e){
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        PharmacyModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.imageDelete.setOnClickListener(view -> {
            PharmacyModel model2 = list.get(holder.getAdapterPosition());
            activity.deleteAppointment(holder.getAdapterPosition(),model2);
        });

        holder.binding.imageEdit.setOnClickListener(view -> {
            PharmacyModel model2 = list.get(holder.getAdapterPosition());
            activity.editAppointment(holder.getAdapterPosition(),model2);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private PharmacyRow2Binding binding;

        public MyHolder(PharmacyRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
