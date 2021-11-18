package com.zawraapharma.local_database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

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

@Dao
public interface DAOInterface {

    @Insert(entity = UserModel.User.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertUsers(List<UserModel.User> userList);

    @Query("SELECT * FROM users WHERE access_code = :access_code LIMIT 1")
    UserModel.User getUserByCode(String access_code);

    @Insert(entity = PharmacyModel.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertPharmacies(List<PharmacyModel> pharmacyModelList);

    @Query("SELECT * FROM pharmacies")
    List<PharmacyModel> getPharmacies();


    @Query("SELECT * FROM pharmacies WHERE title LIKE :query")
    List<PharmacyModel> search(String query);

    @Insert(entity = CompanyModel.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertCompanies(List<CompanyModel> companyModelList);

    @Query("SELECT * FROM companies")
    List<CompanyModel> getCompanies();


    @Insert(entity = InvoiceModel.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertInvoice(List<InvoiceModel> invoiceModelList);

    @Query("SELECT * FROM invoices")
    List<InvoiceCompanyPharmacyModel> getInvoices();


    @Query("SELECT * FROM invoices WHERE client_id = :pharmacy_id")
    List<InvoiceCompanyPharmacyModel> getInvoicesByPharmacyId(int pharmacy_id);

    @Query("SELECT * FROM invoices WHERE code = :code")
    List<InvoiceCompanyPharmacyModel> getInvoicesByCode(String code);



    @Insert(entity = CompanyProductModel.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertCompanyProducts(List<CompanyProductModel> companyProductModelList);


    @Query("SELECT * FROM products")
    List<CompanyProductModel> getProducts();

    @Query("SELECT * FROM products WHERE company_id = :company_id")
    List<CompanyProductModel> getProductsByCompanyId(String company_id);

    @Insert(entity = RetrieveModel.class, onConflict = OnConflictStrategy.IGNORE)
    long insertRetrieveData(RetrieveModel retrieveModel);

    @Query("SELECT * FROM retrieve")
    List<RetrieveModelBillModel> getRetrieve();

    @Insert(entity = Product_Bill_Model.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertBillData(List<Product_Bill_Model> productBillModelList);

    @Query("SELECT * FROM product_bill WHERE retrieve_local_id = :retrieve_id")
    List<Product_Bill_Model> getProductBill(String retrieve_id);


    @Insert(entity = CartModel.class, onConflict = OnConflictStrategy.REPLACE)
    long insertCartData(CartModel cartModel);

    @Query("SELECT * FROM cart")
    List<CartModelBillModel> getCartData();


    @Insert(entity = BillModel.class, onConflict = OnConflictStrategy.REPLACE)
    long[] insertBillsData(List<BillModel> billModel);

    @Query("SELECT * FROM bill WHERE cart_id =:cart_id")
    List<BillModel> getBillDataByCartId(String cart_id);

    @Query("DELETE from pharmacies ")
    void deletePharmacies();

    @Query("DELETE from retrieve ")
    void deleteAllBill();

    @Query("DELETE from cart")
    void deleteAllCart();

    @Query("DELETE from bill ")
    void deleteCartBill();

    @Query("DELETE from invoices ")
    void deleteInvoice();

    @Query("DELETE from companies ")
    void deleteCompany();

    @Query("DELETE from products ")
    void deleteProducts();


}
