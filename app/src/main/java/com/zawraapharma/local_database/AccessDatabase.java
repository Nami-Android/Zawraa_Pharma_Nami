package com.zawraapharma.local_database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.CartModel;
import com.zawraapharma.models.CartModelBillModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.InvoiceCompanyPharmacyModel;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.RetrieveModelBillModel;
import com.zawraapharma.models.UserModel;

import java.util.List;

public class AccessDatabase {
    private LocalDatabase localDatabase;
    private DAOInterface daoInterface;

    public AccessDatabase(Context context) {
        localDatabase = LocalDatabase.newInstance(context);
        daoInterface = localDatabase.daoInterface();
    }

    public void insertUsers(List<UserModel.User> userList, DataBaseInterfaces.UserInterface userInterface) {
        new InsertUserTask(userInterface).execute(userList);
    }

    public void getUserByCode(DataBaseInterfaces.UserByCodeInterface userByCodeInterface,String access_code) {
        new UserByCodeTask(userByCodeInterface).execute(access_code);
    }


    public void getPharmacies(DataBaseInterfaces.PharmacyInterface pharmacyInterface) {
        new PharmacyTask(pharmacyInterface).execute();
    }

    public void insertPharmacies(List<PharmacyModel> pharmacyModelList, DataBaseInterfaces.PharmacyInsertInterface pharmacyInsertInterface) {
        new InsertPharmacyTask(pharmacyInsertInterface).execute(pharmacyModelList);
    }

    public void search(DataBaseInterfaces.PharmacySearchInterface pharmacyInterface, String query) {
        new PharmacySearchTask(pharmacyInterface).execute(query);
    }

    public void insertCompany(List<CompanyModel> companyModelList, DataBaseInterfaces.CompanyInsertedInterface companyInsertedInterface) {
        new InsertCompanyTask(companyInsertedInterface).execute(companyModelList);
    }


    public void getCompanies(DataBaseInterfaces.CompanyInterface companyInterface) {
        new CompanyTask(companyInterface).execute();
    }

    public void insertInvoice(List<InvoiceModel> invoiceModelList, DataBaseInterfaces.InvoiceInsertedInterface invoiceInsertedInterface) {
        new InsertInvoiceTask(invoiceInsertedInterface).execute(invoiceModelList);
    }

    public void getInvoices(DataBaseInterfaces.InvoiceInterface invoiceInterface) {
        new InvoiceTask(invoiceInterface).execute();
    }

    public void getInvoicesByPharmacyId(DataBaseInterfaces.InvoiceInterface invoiceInterface, int pharmacy_id) {
        new InvoiceByPharmacyIdTask(invoiceInterface).execute(pharmacy_id);
    }

    public void getInvoicesByCode(DataBaseInterfaces.SingleInvoiceInterface invoiceInterface, String code) {
        new InvoiceByCodeTask(invoiceInterface).execute(code);
    }

    public void getAllInvoice(DataBaseInterfaces.SingleInvoiceInterface invoiceInterface) {
        new InvoicesTask(invoiceInterface).execute();
    }

    public void insertCompanyProducts(List<CompanyProductModel> companyProductModelList, DataBaseInterfaces.CompanyProductInsertedInterface companyProductInsertedInterface) {
        new InsertProductsTask(companyProductInsertedInterface).execute(companyProductModelList);
    }

    public void getProductsByCompanyId(DataBaseInterfaces.ProductsInterface productsInterface, String company_id) {
        new ProductsTask(productsInterface).execute(company_id);
    }


    public void insertRetrieve(RetrieveModel retrieveModel, DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface) {
        new InsertRetrieveTask(retrieveInsertInterface).execute(retrieveModel);
    }

    public void insertBill(List<Product_Bill_Model> list, DataBaseInterfaces.BillInsertInterface billInsertInterface) {
        new InsertProductBillTask(billInsertInterface).execute(list);
    }

    public void getRetrieve(DataBaseInterfaces.RetrieveInterface retrieveInterface) {
        new RetrieveTask(retrieveInterface).execute();
    }

    public void getBillByRetrieveId(DataBaseInterfaces.BillInterface billInterface, String retrieve_id) {
        new BillTask(billInterface).execute(retrieve_id);
    }

    public void insertCart(CartModel cartModel, DataBaseInterfaces.CartInsertInterface cartInsertInterface) {
        new InsertCartTask(cartInsertInterface).execute(cartModel);
    }

    public void insertBills(List<BillModel> list, DataBaseInterfaces.BillInsertInterface billInsertInterface) {
        new InsertBillsTask(billInsertInterface).execute(list);
    }


    public void getCart(DataBaseInterfaces.CartInterface cartInterface) {
        new CartTask(cartInterface).execute();
    }

    public void deleteRetrieve() {
        new DeleteRetrieve().execute();

    }


    public void deletePharmacies() {
        new DeletePharmacy().execute();
    }

    public void deleteCompanies() {
        new DeleteCompany().execute();

    }

    public void deleteInvoices() {
        new DeleteInvoices().execute();

    }

    public void deleteProducts() {
        new DeleteProduct().execute();

    }

    public void deleteCart() {
        new DeleteCart().execute();

    }

    public void deleteCartBill() {
        new DeleteCartBill().execute();

    }

    public class InsertUserTask extends AsyncTask<List<UserModel.User>, Void, Boolean> {
        private DataBaseInterfaces.UserInterface userInterface;

        public InsertUserTask(DataBaseInterfaces.UserInterface userInterface) {
            this.userInterface = userInterface;
        }

        @Override
        protected Boolean doInBackground(List<UserModel.User>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertUsers(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                userInterface.onUserDataSuccess();
            }
        }
    }

    public class UserByCodeTask extends AsyncTask<String, Void, UserModel.User> {
        private DataBaseInterfaces.UserByCodeInterface userInterface;

        public UserByCodeTask(DataBaseInterfaces.UserByCodeInterface userInterface) {
            this.userInterface = userInterface;
        }

        @Override
        protected UserModel.User doInBackground(String... strings) {
            boolean isInserted = false;
            UserModel.User user = daoInterface.getUserByCode(strings[0]);

            return user;
        }

        @Override
        protected void onPostExecute(UserModel.User user) {
            userInterface.onUserDataSuccess(user);
        }
    }


    public class InsertPharmacyTask extends AsyncTask<List<PharmacyModel>, Void, Boolean> {
        private DataBaseInterfaces.PharmacyInsertInterface pharmacyInsertInterface;

        public InsertPharmacyTask(DataBaseInterfaces.PharmacyInsertInterface pharmacyInsertInterface) {
            this.pharmacyInsertInterface = pharmacyInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<PharmacyModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertPharmacies(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                pharmacyInsertInterface.onPharmacyDataInsertedSuccess();
            }
        }
    }


    public class PharmacyTask extends AsyncTask<Void, Void, List<PharmacyModel>> {
        private DataBaseInterfaces.PharmacyInterface pharmacyInterface;

        public PharmacyTask(DataBaseInterfaces.PharmacyInterface pharmacyInterface) {
            this.pharmacyInterface = pharmacyInterface;
        }

        @Override
        protected List<PharmacyModel> doInBackground(Void... voids) {
            return daoInterface.getPharmacies();
        }

        @Override
        protected void onPostExecute(List<PharmacyModel> pharmacyModelList) {
            pharmacyInterface.onPharmacyDataSuccess(pharmacyModelList);
        }
    }


    public class PharmacySearchTask extends AsyncTask<String, Void, List<PharmacyModel>> {
        private DataBaseInterfaces.PharmacySearchInterface pharmacyInterface;

        public PharmacySearchTask(DataBaseInterfaces.PharmacySearchInterface pharmacyInterface) {
            this.pharmacyInterface = pharmacyInterface;
        }

        @Override
        protected List<PharmacyModel> doInBackground(String... strings) {
            return daoInterface.search('%' + strings[0] + '%');
        }

        @Override
        protected void onPostExecute(List<PharmacyModel> pharmacyModelList) {
            pharmacyInterface.onPharmacySearchDataSuccess(pharmacyModelList);
        }
    }

    public class InsertCompanyTask extends AsyncTask<List<CompanyModel>, Void, Boolean> {
        DataBaseInterfaces.CompanyInsertedInterface companyInsertedInterface;

        public InsertCompanyTask(DataBaseInterfaces.CompanyInsertedInterface companyInsertedInterface) {
            this.companyInsertedInterface = companyInsertedInterface;
        }

        @Override
        protected Boolean doInBackground(List<CompanyModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertCompanies(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                companyInsertedInterface.onCompanyDataInsertedSuccess();
            }
        }
    }

    public class CompanyTask extends AsyncTask<Void, Void, List<CompanyModel>> {
        private DataBaseInterfaces.CompanyInterface companyInterface;

        public CompanyTask(DataBaseInterfaces.CompanyInterface companyInterface) {
            this.companyInterface = companyInterface;
        }

        @Override
        protected List<CompanyModel> doInBackground(Void... voids) {
            return daoInterface.getCompanies();
        }

        @Override
        protected void onPostExecute(List<CompanyModel> companyModelList) {
            companyInterface.onCompanyDataSuccess(companyModelList);
        }
    }

    public class InsertInvoiceTask extends AsyncTask<List<InvoiceModel>, Void, Boolean> {
        private DataBaseInterfaces.InvoiceInsertedInterface invoiceInsertedInterface;

        public InsertInvoiceTask(DataBaseInterfaces.InvoiceInsertedInterface invoiceInsertedInterface) {
            this.invoiceInsertedInterface = invoiceInsertedInterface;
        }

        @Override
        protected Boolean doInBackground(List<InvoiceModel>... lists) {
            boolean isInserted = false;
            long[] result = daoInterface.insertInvoice(lists[0]);
            if (result.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                invoiceInsertedInterface.onInvoiceDataInsertedSuccess();
            }

        }
    }

    public class InvoiceTask extends AsyncTask<Void, Void, List<InvoiceCompanyPharmacyModel>> {
        private DataBaseInterfaces.InvoiceInterface invoiceInterface;

        public InvoiceTask(DataBaseInterfaces.InvoiceInterface invoiceInterface) {
            this.invoiceInterface = invoiceInterface;
        }

        @Override
        protected List<InvoiceCompanyPharmacyModel> doInBackground(Void... voids) {
            return daoInterface.getInvoices();
        }

        @Override
        protected void onPostExecute(List<InvoiceCompanyPharmacyModel> invoiceModelList) {
            invoiceInterface.onInvoiceDataSuccess(invoiceModelList);
        }
    }

    public class InvoiceByPharmacyIdTask extends AsyncTask<Integer, Void, List<InvoiceCompanyPharmacyModel>> {
        private DataBaseInterfaces.InvoiceInterface invoiceInterface;

        public InvoiceByPharmacyIdTask(DataBaseInterfaces.InvoiceInterface invoiceInterface) {
            this.invoiceInterface = invoiceInterface;
        }

        @Override
        protected List<InvoiceCompanyPharmacyModel> doInBackground(Integer... integers) {
            return daoInterface.getInvoicesByPharmacyId(integers[0]);
        }

        @Override
        protected void onPostExecute(List<InvoiceCompanyPharmacyModel> invoiceModelList) {
            invoiceInterface.onInvoiceDataSuccess(invoiceModelList);
        }
    }

    public class InvoiceByCodeTask extends AsyncTask<String, Void, List<InvoiceCompanyPharmacyModel>> {
        private DataBaseInterfaces.SingleInvoiceInterface invoiceInterface;

        public InvoiceByCodeTask(DataBaseInterfaces.SingleInvoiceInterface invoiceInterface) {
            this.invoiceInterface = invoiceInterface;
        }

        @Override
        protected List<InvoiceCompanyPharmacyModel> doInBackground(String... strings) {
            return daoInterface.getInvoicesByCode(strings[0]);
        }

        @Override
        protected void onPostExecute(List<InvoiceCompanyPharmacyModel> invoiceModelList) {
            invoiceInterface.onInvoiceDataSuccess(invoiceModelList);
        }
    }

    public class InvoicesTask extends AsyncTask<String, Void, List<InvoiceCompanyPharmacyModel>> {
        private DataBaseInterfaces.SingleInvoiceInterface invoiceInterface;

        public InvoicesTask(DataBaseInterfaces.SingleInvoiceInterface invoiceInterface) {
            this.invoiceInterface = invoiceInterface;
        }

        @Override
        protected List<InvoiceCompanyPharmacyModel> doInBackground(String... strings) {
            return daoInterface.getInvoices();
        }

        @Override
        protected void onPostExecute(List<InvoiceCompanyPharmacyModel> invoiceModelList) {
            invoiceInterface.onInvoiceDataSuccess(invoiceModelList);
        }
    }


    public class InsertProductsTask extends AsyncTask<List<CompanyProductModel>, Void, Boolean> {
        private DataBaseInterfaces.CompanyProductInsertedInterface companyProductInsertedInterface;

        public InsertProductsTask(DataBaseInterfaces.CompanyProductInsertedInterface companyProductInsertedInterface) {
            this.companyProductInsertedInterface = companyProductInsertedInterface;
        }

        @Override
        protected Boolean doInBackground(List<CompanyProductModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertCompanyProducts(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                companyProductInsertedInterface.onCompanyProductDataInsertedSuccess();
            }
        }
    }


    public class ProductsTask extends AsyncTask<String, Void, List<CompanyProductModel>> {
        private DataBaseInterfaces.ProductsInterface productsInterface;

        public ProductsTask(DataBaseInterfaces.ProductsInterface productsInterface) {
            this.productsInterface = productsInterface;
        }

        @Override
        protected List<CompanyProductModel> doInBackground(String... strings) {
            return daoInterface.getProductsByCompanyId(strings[0]);
        }

        @Override
        protected void onPostExecute(List<CompanyProductModel> invoiceModelList) {
            productsInterface.onProductsDataSuccess(invoiceModelList);
        }
    }


    public class InsertRetrieveTask extends AsyncTask<RetrieveModel, Void, Long> {
        private DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface;

        public InsertRetrieveTask(DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(RetrieveModel... retrieveModels) {
            long data = daoInterface.insertRetrieveData(retrieveModels[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id > 0) {
                retrieveInsertInterface.onRetrieveDataSuccess(id);
            }
        }
    }

    public class RetrieveTask extends AsyncTask<Void, Void, List<RetrieveModelBillModel>> {
        private DataBaseInterfaces.RetrieveInterface retrieveInterface;

        public RetrieveTask(DataBaseInterfaces.RetrieveInterface retrieveInterface) {
            this.retrieveInterface = retrieveInterface;
        }

        @Override
        protected List<RetrieveModelBillModel> doInBackground(Void... voids) {
            return daoInterface.getRetrieve();
        }

        @Override
        protected void onPostExecute(List<RetrieveModelBillModel> retrieveModelList) {
            retrieveInterface.onRetrieveDataSuccess(retrieveModelList);
        }
    }


    public class InsertProductBillTask extends AsyncTask<List<Product_Bill_Model>, Void, Boolean> {
        private DataBaseInterfaces.BillInsertInterface billInsertInterface;

        public InsertProductBillTask(DataBaseInterfaces.BillInsertInterface billInsertInterface) {
            this.billInsertInterface = billInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<Product_Bill_Model>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertBillData(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            super.onPostExecute(bol);
            if (bol) {
                billInsertInterface.onBillDataSuccess();
            }

        }
    }

    public class BillTask extends AsyncTask<String, Void, List<Product_Bill_Model>> {
        private DataBaseInterfaces.BillInterface billInterface;

        public BillTask(DataBaseInterfaces.BillInterface billInterface) {
            this.billInterface = billInterface;
        }

        @Override
        protected List<Product_Bill_Model> doInBackground(String... strings) {
            return daoInterface.getProductBill(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Product_Bill_Model> productBillModelList) {
            billInterface.onBillDataSuccess(productBillModelList);
        }
    }

    public class InsertCartTask extends AsyncTask<CartModel, Void, Long> {
        private DataBaseInterfaces.CartInsertInterface cartInsertInterface;

        public InsertCartTask(DataBaseInterfaces.CartInsertInterface cartInsertInterface) {
            this.cartInsertInterface = cartInsertInterface;
        }

        @Override
        protected Long doInBackground(CartModel... cartModels) {
            long data = daoInterface.insertCartData(cartModels[0]);
            Log.e("data", data + "_");
            return data;
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);
            if (id > 0) {
                cartInsertInterface.onCartDataSuccess(id);
            }
        }
    }

    public class InsertBillsTask extends AsyncTask<List<BillModel>, Void, Boolean> {
        private DataBaseInterfaces.BillInsertInterface billInsertInterface;

        public InsertBillsTask(DataBaseInterfaces.BillInsertInterface billInsertInterface) {
            this.billInsertInterface = billInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<BillModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertBillsData(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            super.onPostExecute(bol);
            if (bol) {
                billInsertInterface.onBillDataSuccess();
            }

        }
    }

    public class CartTask extends AsyncTask<Void, Void, List<CartModelBillModel>> {
        private DataBaseInterfaces.CartInterface cartInterface;

        public CartTask(DataBaseInterfaces.CartInterface cartInterface) {
            this.cartInterface = cartInterface;
        }

        @Override
        protected List<CartModelBillModel> doInBackground(Void... voids) {
            return daoInterface.getCartData();
        }

        @Override
        protected void onPostExecute(List<CartModelBillModel> retrieveModelList) {
            cartInterface.onCartDataSuccess(retrieveModelList);
        }
    }

    public class DeletePharmacy extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deletePharmacies();
            return null;
        }


    }

    public class DeleteCompany extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deleteCompany();
            return null;
        }


    }

    public class DeleteRetrieve extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deleteAllBill();
            return null;
        }


    }

    public class DeleteCart extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deleteAllCart();
            return null;
        }


    }

    public class DeleteCartBill extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deleteCartBill();
            return null;
        }


    }

    public class DeleteInvoices extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deleteInvoice();
            return null;
        }


    }

    public class DeleteProduct extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daoInterface.deleteProducts();
            return null;
        }


    }


}
