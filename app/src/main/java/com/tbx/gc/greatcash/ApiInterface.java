package com.tbx.gc.greatcash;

import com.tbx.gc.greatcash.CityPOJO.CityBean;
import com.tbx.gc.greatcash.CountryPOJO.countryBean;
import com.tbx.gc.greatcash.EarnMorePOJO.earnMoreBean;
import com.tbx.gc.greatcash.KycPOJO.KycBean;
import com.tbx.gc.greatcash.KycRequestPOJO.KycRequest;
import com.tbx.gc.greatcash.QuestionPOJO.QuestionBean;
import com.tbx.gc.greatcash.RatingPoojo.Profile;
import com.tbx.gc.greatcash.RedeemRequest.RedeemReqBean;
import com.tbx.gc.greatcash.StatePOJO.StateBean;
import com.tbx.gc.greatcash.TicketReq.Ticketrequest;
import com.tbx.gc.greatcash.acPOJO.acBean;
import com.tbx.gc.greatcash.achieversPOJO.achieversBean;
import com.tbx.gc.greatcash.audioPOJO.audioBean;
import com.tbx.gc.greatcash.challengeAcceptPOJO.challengeAcceptBean;
import com.tbx.gc.greatcash.challengePOJO.challengeBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.comboPOJO.comboBean;
import com.tbx.gc.greatcash.createTicketPOJO.ticketBean;
import com.tbx.gc.greatcash.dashboardPOJO.dashboardBean;
import com.tbx.gc.greatcash.editProfileRerquestPOJO.editProileRequestBean;
import com.tbx.gc.greatcash.faqPOJO.faqBean;
import com.tbx.gc.greatcash.getProfileRequestOJO.getProfileRequestBean;
import com.tbx.gc.greatcash.hotListPOJO.hotListBean;
import com.tbx.gc.greatcash.incomeJunctionPOJO.JuctionBean;
import com.tbx.gc.greatcash.loginRequestPOJO.loginRequestBean;
import com.tbx.gc.greatcash.networkPOJO.networkBean;
import com.tbx.gc.greatcash.networkRequestOJO.networkRequestBean;
import com.tbx.gc.greatcash.notificaitonPOJO.notificationBean;
import com.tbx.gc.greatcash.passwordRequestPOJO.passwordRequestBean;
import com.tbx.gc.greatcash.rateUsPojo.rateUsBean;
import com.tbx.gc.greatcash.redeemPOJO.redeemBean;
import com.tbx.gc.greatcash.referRequestPOJO.referRequestBean;
import com.tbx.gc.greatcash.registerRequestPOJO.registerRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.shoppingPOJO.shoppingBean;
import com.tbx.gc.greatcash.submitComboPOJO.submitComboBean;
import com.tbx.gc.greatcash.submitSurveyPOJO.submitSurveyBean;
import com.tbx.gc.greatcash.surveryPOJO.surveyBean;
import com.tbx.gc.greatcash.transactionPOJO.transactionBean;
import com.tbx.gc.greatcash.transactionRequestPOJO.transactionRequetBean;
import com.tbx.gc.greatcash.verifyOtpRequestPOJO.cerifyOtpRequestBean;
import com.tbx.gc.greatcash.videoAmountPOJO.videoAmountBean;
import com.tbx.gc.greatcash.videoPOJO.videoBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> register
            (@Body registerRequestBean body
            );


    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> verifyOtp
            (@Body cerifyOtpRequestBean body
            );


    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> referId
            (@Body referRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> login
            (@Body loginRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> changePassword
            (@Body passwordRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> getProfile
            (@Body getProfileRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> editProfile
            (@Body editProileRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<challengeBean> challenge
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<acBean> ac
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<challengeAcceptBean> challengeComplete
            (@Body challengeRequestBean body
            );


    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<shoppingBean> shopping
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<faqBean> faq
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<notificationBean> notification
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<surveyBean> aurvey
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> submitSurvey
            (@Body submitSurveyBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<achieversBean> achievers
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<videoBean> video
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<registerResponseBean> videoAmount
            (@Body videoAmountBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<dashboardBean> dashboard
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<networkBean> network
            (@Body networkRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<transactionBean> transaction
            (@Body transactionRequetBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<audioBean> audio
            (@Body challengeRequestBean body
            );


    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<comboBean> comboOffers
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<comboBean> submitCombo
            (@Body submitComboBean body
            );



    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<redeemBean> redeem
            (@Body challengeRequestBean body
            );

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<hotListBean> hotListApi
            (@Body challengeRequestBean body );

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<JuctionBean> junctionApi
            (@Body challengeRequestBean body );

    @Headers({"Content-Type: application/json"})
    @POST("great-cash/api/api.php")
    Call<rateUsBean> rateUsApi
            (@Body challengeRequestBean body);

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<earnMoreBean> earnMoreApi
            (@Body challengeRequestBean body );

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<countryBean> countryApi
            (@Body challengeRequestBean body );

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<StateBean> stateApi
            (@Body challengeRequestBean body );

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<CityBean> cityApi
            (@Body challengeRequestBean body );

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<redeemBean> redeenApi
            (@Body RedeemReqBean body );


    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<ticketBean> createTicket
            (@Body Ticketrequest body );

//    @Headers({"Content-Type:application/json"})
//    @POST("great-cash/api/api.php")
//    Call<KycBean> KycApi
//            (@Body KycRequest body );

    @Multipart
    @POST("api/fileapi.php")
    Call<ResponseBody> saveData(
            @Part("type") RequestBody type,
            @Part("action") RequestBody action,
            @Part("userId") RequestBody userId,
            @Part("idNumber") RequestBody idNumber,
            @Part("nominee_name") RequestBody nominee_name,
            @Part("nominee_relation") RequestBody nominee_relation,
            @Part("nominee_dob") RequestBody nominee_dob,
            @Part MultipartBody.Part file, @Part MultipartBody.Part file1);

    @Multipart
    @POST("api/fileapi.php")
    Call<ResponseBody> saveDataProfile(
            @Part("action") RequestBody action,
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part file);

    @Headers({"Content-Type:application/json"})
    @POST("great-cash/api/api.php")
    Call<QuestionBean> questionApi
            (@Body challengeRequestBean body );


    @POST("great-cash/api/api.php")
    Call<Profile> setRating(@Body Map<String, String> map);


}
