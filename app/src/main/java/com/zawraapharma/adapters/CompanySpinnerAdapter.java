package com.zawraapharma.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.zawraapharma.R;
import com.zawraapharma.databinding.SpinnerRowBinding;
import com.zawraapharma.models.CompanyModel;

import java.util.List;

import retrofit2.http.POST;

public class CompanySpinnerAdapter extends BaseAdapter {
    private List<CompanyModel> list;
    private Context context;
    private LayoutInflater inflater;

    public CompanySpinnerAdapter(List<CompanyModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_row,null,false);
        CompanyModel model = list.get(i);
        binding.setTitle(model.getTitle());
        return binding.getRoot();
    }
}
