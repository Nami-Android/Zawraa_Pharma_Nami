package com.zawraapharma.services;


import com.zawraapharma.models.AddSalesModel;
import com.zawraapharma.models.AllUsersModel;
import com.zawraapharma.models.AppointmentDataModel;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.CartModel;
import com.zawraapharma.models.CompanyDataModel;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.DebtsDataModel;
import com.zawraapharma.models.InvoiceDataModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.PlaceGeocodeData;
import com.zawraapharma.models.PlaceMapDetailsData;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.RetrieveDataModel;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.SalesDetailsDataModel;
import com.zawraapharma.models.UploadPaidModel;
import com.zawraapharma.models.UploadRetrieveModel;
import com.zawraapharma.models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {



    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("access_code") String access_code);


    @FormUrlEncoded
    @POST("api/update-location")
    Call<UserModel> updateLocation(@Header("Authorization") String user_token,
                                   @Field("user_id") String user_id,
                                   @Field("latitude") double latitude,
                                   @Field("longitude") double longitude

    );

    @FormUrlEncoded
    @POST("api/update-firebase")
    Call<LogoutModel> updateFirebaseToken(@Header("Authorization") String token,
                                          @Field("user_id") String user_id,
                                          @Field("phone_token") String phone_token,
                                          @Field("software_type") String software_type

    );

    @FormUrlEncoded
    @POST("api/logout")
    Call<LogoutModel> logout(@Header("Authorization") String token,
                             @Field("user_id") String user_id,
                             @Field("phone_token") String phone_token,
                             @Field("software_type") String software_type

    );

    @GET("api/search-pharmacy")
    Call<PharmacyDataModel> search(@Query("search_name") String query

    );


    @GET("api/get-profile")
    Call<UserModel> getProfile(@Header("Authorization") String token,
                               @Query("user_id") String user_id

    );

    @GET("api/search-pharmacy-or-bill-code")
    Call<PharmacyDataModel> search_bill_number(@Header("Authorization") String token,
                                               @Query("user_id") String user_id,
                                               @Query("search_key") String query

    );

    @GET("api/my-appointments")
    Call<AppointmentDataModel> getMyAppointments(@Header("Authorization") String token,
                                                 @Query("user_id") String user_id

    );

    @GET("api/search-appointment")
    Call<PharmacyDataModel> searchAppointments(@Header("Authorization") String token,
                                               @Query("user_id") String user_id,
                                               @Query("fired_at") String fired_at


    );


    @FormUrlEncoded
    @POST("api/create-appointment")
    Call<ResponseData> createAppointment(@Header("Authorization") String token,
                                         @Field("user_id") String user_id,
                                         @Field("client_id") String client_id,
                                         @Field("fired_at") String fired_at,
                                         @Field("latitude") double latitude,
                                         @Field("longitude") double longitude

    );


    @GET("api/get-pharmacy-bills")
    Call<InvoiceDataModel> getPharmacyBill(@Header("Authorization") String token,
                                           @Query("user_id") String user_id,
                                           @Query("pharmacy_id") String pharmacy_id

    );

    @POST("api/pay-bills")
    Call<BillResponse> sendData(@Header("Authorization") String token,
                                @Body CartModel cartModel);

    @GET("api/company-item")
    Call<CompanyProductDataModel> getCompanyProduct(@Header("Authorization") String token,
                                                    @Query("company_id") String company_id

    );

    @GET("api/companies")
    Call<CompanyDataModel> getCompanies(@Header("Authorization") String token
    );

    @POST("api/back-bills")
    Call<RetrieveDataModel> retrieveBill(@Header("Authorization") String token,
                                         @Body RetrieveModel retrieveModel);

    @GET("api/get-dept-bills")
    Call<DebtsDataModel> getDebts(@Header("Authorization") String token,
                                  @Query("search_key") String search_key
    );

    @GET("api/items")
    Call<CompanyProductDataModel> getProducts(@Header("Authorization") String token

    );

    @GET("api/get-user-bills")
    Call<InvoiceDataModel> getUserBills(@Header("Authorization") String token,
                                        @Query("user_id") String user_id

    );

    @POST("api/upload-back-bills")
    Call<ResponseData> uploadRetrieveData(@Header("Authorization") String token,
                                          @Body UploadRetrieveModel uploadRetrieveModel

    );

    @POST("api/upload-pay-bills")
    Call<ResponseData> uploadPaidBills(@Header("Authorization") String token,
                                       @Body UploadPaidModel uploadPaidModel

    );

    @GET("api/get-users")
    Call<AllUsersModel> getUsers(@Header("Authorization") String token
    );


    @POST("api/sales")
    Call<SalesDetailsDataModel> addSales(@Header("Authorization") String token,
                                         @Body AddSalesModel addSalesModel

    );

    @FormUrlEncoded
    @POST("api/delete-appointment")
    Call<ResponseData> deleteAppointment(@Header("Authorization") String token,
                                         @Field("id") String id,
                                         @Field("user_id") String user_id,
                                         @Field("client_id") String client_id,
                                         @Field("latitude") String latitude,
                                         @Field("longitude") String longitude


    );


    @FormUrlEncoded
    @POST("api/edit-appointment")
    Call<ResponseData> updateAppointment(@Header("Authorization") String token,
                                         @Field("id") String id,
                                         @Field("fired_at") String fired_at,
                                         @Field("user_id") String user_id,
                                         @Field("client_id") String client_id,
                                         @Field("latitude") String latitude,
                                         @Field("longitude") String longitude


    );

    @FormUrlEncoded
    @POST("api/add-client")
    Call<ResponseBody> newClient(
            @Header("Authorization") String token,
            @Field("user_id") String user_id,
            @Field("title") String title,
            @Field("category_title") String category_title,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("address") String address
    );

}