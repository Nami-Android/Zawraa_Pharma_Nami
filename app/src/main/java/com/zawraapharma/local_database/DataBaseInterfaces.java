package com.zawraapharma.local_database;

import com.zawraapharma.models.CartModelBillModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.InvoiceCompanyPharmacyModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.RetrieveModelBillModel;
import com.zawraapharma.models.UserModel;

import java.util.List;

public class DataBaseInterfaces {

    public interface UserInterface {
        void onUserDataSuccess();
    }
    public interface UserByCodeInterface {
        void onUserDataSuccess(UserModel.User user);
    }
    public interface PharmacyInterface {
        void onPharmacyDataSuccess(List<PharmacyModel> pharmacyModelList);
    }

    public interface PharmacyInsertInterface {
        void onPharmacyDataInsertedSuccess();
    }

    public interface PharmacySearchInterface {
        void onPharmacySearchDataSuccess(List<PharmacyModel> pharmacyModelList);
    }

    public interface CompanyInterface {
        void onCompanyDataSuccess(List<CompanyModel> companyModelList);
    }

    public interface CompanyInsertedInterface {
        void onCompanyDataInsertedSuccess();
    }

    public interface CompanyProductInsertedInterface {
        void onCompanyProductDataInsertedSuccess();
    }

    public interface InvoiceInterface {
        void onInvoiceDataSuccess(List<InvoiceCompanyPharmacyModel> invoiceModelList);
    }

    public interface InvoiceInsertedInterface {
        void onInvoiceDataInsertedSuccess();
    }

    public interface SingleInvoiceInterface {
        void onInvoiceDataSuccess(List<InvoiceCompanyPharmacyModel> invoiceCompanyPharmacyModels);
    }

    public interface ProductsInterface {
        void onProductsDataSuccess(List<CompanyProductModel> companyProductModelList);
    }

    public interface RetrieveInterface {
        void onRetrieveDataSuccess(List<RetrieveModelBillModel> retrieveModelList);
    }

    public interface BillInterface {
        void onBillDataSuccess(List<Product_Bill_Model> productBillModelList);
    }

    public interface RetrieveInsertInterface {
        void onRetrieveDataSuccess(long id);
    }

    public interface BillInsertInterface {
        void onBillDataSuccess();
    }

    public interface CartInsertInterface {
        void onCartDataSuccess(long id);
    }

    public interface CartInterface {
        void onCartDataSuccess(List<CartModelBillModel> cartModelBillModelList);
    }
}
