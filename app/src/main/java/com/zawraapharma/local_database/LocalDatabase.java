package com.zawraapharma.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.CartModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.tags.Tags;

@Database(version = 1,entities = {PharmacyModel.class, InvoiceModel.class, CompanyModel.class, CompanyProductModel.class, RetrieveModel.class, Product_Bill_Model.class, CartModel.class, BillModel.class, UserModel.User.class},exportSchema = true)
public abstract class LocalDatabase extends RoomDatabase {

    public static volatile LocalDatabase instance = null;

    public abstract DAOInterface daoInterface();


    public static LocalDatabase newInstance(Context context){
        if (instance==null){
            synchronized (LocalDatabase.class){
                instance = Room.databaseBuilder(context.getApplicationContext(),LocalDatabase.class, Tags.DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }
}
