package com.tbx.gc.greatcash;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.viewKYCPOJO.viewKYCBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class kyc extends Fragment {
    Button submit;
    ImageView iv;
    private int RESULT_LOAD_IMAGE = 1;
    private RelativeLayout image_gallery, rel_dropDown;

    ArrayList<String> permissionToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private final static int ALL_PERMISSIONS_RESULT_DOC = 102;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsRejected = new ArrayList<>();
    private Bitmap bitmap = null;
    private Bitmap bitmap_doc = null;
    private String url = "http://nationproducts.in/great-cash/api/fileapi.php?";
    SharedPreferences pref;
    String id;
    private TextView text_selectType, text_nomDOB;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DropDownKycAdapter adapter;
    private String[] str_name = {"Driving Licence", "Pan Card", "Adhar Card", "Voter ID", "Any Other Government ID"};
    private ImageView img_user, img_doc;
    private Button btn_imgUser, btn_imgDoc;
    private EditText ed_nomName, ed_nomRelation, ed_Id;
    private int mDay, mMonth, mYear;
    private ProgressBar progressBar;
    private String str_selectType, str_nomineeName, str_nomineeRelarion, str_dob, str_id;
    private String picturePathuser, picturePath_doc;
    Uri fileUri = null;
    Uri profileUri = null;

    ScrollView scroll;
    TextView status;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.kyc, container, false);
        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        id = pref.getString("id", "");
        Log.e("user_idddd", "" + id);

        submit = (Button) v.findViewById(R.id.submit);
        rel_dropDown = (RelativeLayout) v.findViewById(R.id.rel_dropdown);
        text_selectType = (TextView) v.findViewById(R.id.text_selected);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_dropDown);
        progressBar = v.findViewById(R.id.progress);
        recyclerView.setVisibility(View.INVISIBLE);

        scroll = v.findViewById(R.id.scroll);
        status = v.findViewById(R.id.status);

        img_user = v.findViewById(R.id.img_user);
        img_doc = v.findViewById(R.id.img_user_doc);
        btn_imgUser = v.findViewById(R.id.btn_uploadUser);
        btn_imgDoc = v.findViewById(R.id.btn_uploadDoc);
        ed_nomName = v.findViewById(R.id.ed_nominee_nmae);
        ed_nomRelation = v.findViewById(R.id.ed_nominee_relation);
        ed_Id = v.findViewById(R.id.ed_Id);
        text_nomDOB = v.findViewById(R.id.ed_nominee_dob);


        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionToRequest = findUnAskedPermission(permissions);

        btn_imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View alertLayout = LayoutInflater.from(getActivity()).inflate(R.layout.choose_camera_popup, null);
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setView(alertLayout);
                final android.app.AlertDialog dialog = alertDialog.create();

                RelativeLayout rel_camera, rel_gallery;
                rel_camera = alertLayout.findViewById(R.id.rel_camera);
                rel_gallery = alertLayout.findViewById(R.id.rel_gallery);

                rel_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 3);
                        dialog.dismiss();

                    }
                });

                rel_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

                        {
                            if (permissionToRequest.size() > 0) {
                                requestPermissions(permissionToRequest.toArray(new String[permissionToRequest.size()]),
                                        ALL_PERMISSIONS_RESULT);
                                Log.d(ContentValues.TAG, "Permission requests");

                            }
                        }

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                        dialog.dismiss();

                    }


                });

                dialog.show();

            }
        });


        btn_imgDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View alertLayout = LayoutInflater.from(getActivity()).inflate(R.layout.choose_camera_popup, null);
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setView(alertLayout);
                final android.app.AlertDialog dialog = alertDialog.create();

                RelativeLayout rel_camera, rel_gallery;
                rel_camera = alertLayout.findViewById(R.id.rel_camera);
                rel_gallery = alertLayout.findViewById(R.id.rel_gallery);

                rel_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 4);
                        dialog.dismiss();


                    }
                });

                rel_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (permissionToRequest.size() > 0) {
                                requestPermissions(permissionToRequest.toArray(new String[permissionToRequest.size()]),
                                        ALL_PERMISSIONS_RESULT_DOC);
                                Log.d(ContentValues.TAG, "Permission requests");

                            }
                        }

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                        dialog.dismiss();

                    }


                });

                dialog.show();

            }
        });


        text_nomDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

//                Log.e("year",""+mYear);
//                Log.e("month",""+mMonth);
//                Log.e("date",""+mDay);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
                        mYear = year;
                        mMonth = monthofyear;
                        mDay = dayofmonth;

                        text_nomDOB.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_selectType = text_selectType.getText().toString();
                str_nomineeName = ed_nomName.getText().toString();
                str_nomineeRelarion = ed_nomRelation.getText().toString();
                str_dob = text_nomDOB.getText().toString();
                str_id = ed_Id.getText().toString();

                if (str_nomineeName.equals("")) {
                    Toast.makeText(getActivity(), "Please enter nominee name", Toast.LENGTH_SHORT).show();
                } else if (str_nomineeRelarion.equals("")) {
                    Toast.makeText(getActivity(), "Please enter nominee relation", Toast.LENGTH_SHORT).show();
                } else if (str_dob.equals("")) {
                    Toast.makeText(getActivity(), "Please select DOB", Toast.LENGTH_SHORT).show();
                } else if (str_selectType.equals("")) {
                    Toast.makeText(getActivity(), "Please select type", Toast.LENGTH_SHORT).show();
                } else if (str_id.equals("")) {
                    Toast.makeText(getActivity(), "Please enter ID", Toast.LENGTH_SHORT).show();
                } else if (img_user.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Please upload user image", Toast.LENGTH_SHORT).show();
                } else if (img_doc.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Please upload doc image", Toast.LENGTH_SHORT).show();
                } else {

                    uploadApi();

                    //updateKyc_Api();
                    //  Toast.makeText(getActivity(),"ddd",Toast.LENGTH_SHORT).show();
                }


            }
        });

        rel_dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new DropDownKycAdapter(getActivity(), str_name);
                recyclerView.setAdapter(adapter);

            }
        });

        return v;
    }

    private void uploadApi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        File file = new File(picturePathuser);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getActivity().getContentResolver().getType(fileUri)),
                        file
                );
        File file1 = new File(picturePath_doc);
        // create RequestBody instance from file
        RequestBody requestFile1 =
                RequestBody.create(
                        MediaType.parse(getActivity().getContentResolver().getType(profileUri)),
                        file1
                );

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("userPic", file1.getName(), requestFile1);
        RequestBody typeId = RequestBody.create(MediaType.parse("text/plain"), str_id);
        RequestBody nomineeNameValue = RequestBody.create(MediaType.parse("text/plain"), str_nomineeName);
        RequestBody nomineeRelValue = RequestBody.create(MediaType.parse("text/plain"), str_nomineeRelarion);
        RequestBody nomineeDobValue = RequestBody.create(MediaType.parse("text/plain"), str_dob);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), str_selectType);
        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "update_kyc");
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), id);
        Call<ResponseBody> responseBodyCall = apiInterface.saveData(type, action, userId, typeId, nomineeNameValue, nomineeRelValue, nomineeDobValue, body, body1);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateKyc_Api() {
//        Log.e("1111", "1111");
//        progressBar.setVisibility(View.VISIBLE);
//
//        bean b = (bean)getContext().getApplicationContext();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(b.BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface cr = retrofit.create(ApiInterface.class);
//
//        File file = new File(picturePath);
//        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getActivity().getContentResolver().getType(fileUri)),
//                        file
//                );
//
//        // challengeRequestBean body = new challengeRequestBean();
//       // RedeemReqBean body = new RedeemReqBean();
//        KycRequest body=new KycRequest();
//
//        //  Data data = new Data();
//       // DataRedReq data = new DataRedReq();
//
//        body.setAction("update_kyc");
//
//        body.setUserId(pref.getString("id", ""));
//        body.setType(str_selectType);
//        body.setFileDoc("file");
//        body.setUserPic("file");
//        body.setIdNumber(str_id);
//        body.setNomineeName(str_nomineeName);
//        body.setNomineeRelation(str_nomineeRelarion);
//        body.setNomineeDob(str_dob);
//
//        Log.e("iidd", pref.getString("id", ""));
//        Log.e("typeee", ""+str_selectType);
//        Log.e("NomineeName", ""+str_nomineeName);
//        Log.e("NomineeRelation", ""+str_nomineeRelarion);
//        Log.e("NomineeDob", ""+str_dob);
//        Log.e("iDDDDDDD", ""+str_id);
//
//        Call<KycBean> call = cr.KycApi(body);
//
//
//        call.enqueue(new Callback<KycBean>()
//        {
//
//            @Override
//            public void onResponse(Call<KycBean> call, retrofit2.Response<KycBean> response) {
//                Log.e("222", "222");
//
//                progressBar.setVisibility(View.GONE);
//
//                if (response.body().getStatus_kyc().equals("1"))
//                {
//                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//
//                }
//
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(Call<KycBean> call, Throwable t)
//            {
//                progressBar.setVisibility(View.GONE);
//            }
//
//        });

    }

//    private void update_Api(final String st_adharNum)
//    {
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
//        {
//            @Override
//            public void onResponse(String response)
//            {
//                Log.e("response_kycccc",""+response);
//                String status="";
//
//                try
//                {
//                    JSONObject jsonObject=new JSONObject(response);
//                    status=jsonObject.getString("status");
//
//                    if(status.equals("1"))
//                    {
//                        String message=jsonObject.getString("message");
//                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//
//            }
//        })
//        {
//            @Override
//            protected java.util.Map<String, String> getParams()
//            {
//                java.util.Map<String, String> params = new HashMap<>();
//
//                params.put("userId",id);
//                params.put("idNumber",st_adharNum);
//                params.put("action","update_kyc");
//                params.put("file","file");
//
//                return params;
//            }
//        };
//
//        int socketTimeout = 60000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//    }


    @Override
    public void onResume() {
        super.onResume();


        progressBar.setVisibility(View.VISIBLE);


        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("user_kyc");

        data.setUserId(pref.getString("id", ""));

        body.setData(data);

        Call<viewKYCBean> call = cr.viewKyc(body);

        call.enqueue(new Callback<viewKYCBean>() {
            @Override
            public void onResponse(Call<viewKYCBean> call, Response<viewKYCBean> response) {



                if (response.body().getStatus().equals("1"))
                {
                    if (response.body().getData().getStatus().equals("Approved"))
                    {
                        scroll.setVisibility(View.GONE);
                        status.setText("Your KYC has been approved");
                        status.setVisibility(View.VISIBLE);
                    }
                    else if (response.body().getData().getStatus().equals("Reject"))
                    {
                        scroll.setVisibility(View.VISIBLE);
                        status.setText("Your KYC has been rejected");
                        status.setVisibility(View.VISIBLE);
                    }
                    else if (response.body().getData().getStatus().equals("Pending"))
                    {
                        scroll.setVisibility(View.GONE);
                        status.setText("Your KYC is pending");
                        status.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        scroll.setVisibility(View.VISIBLE);
                        status.setVisibility(View.GONE);
                    }
                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<viewKYCBean> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            imageFromCamera_user(resultCode, data);

        }

        if (requestCode == 4 && resultCode == RESULT_OK && null != data) {
            imageFromCamera_doc(resultCode, data);
        }


        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            fileUri = data.getData();
            picturePathuser = getPath(getActivity(), fileUri);
            if (picturePathuser != null) {
                Log.e("imgcamera_userrrr", "" + picturePathuser);

                if (picturePathuser != "") {
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePathuser);
                    if (bitmap != null)
                        img_user.setImageBitmap(bitmap);
                }
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            profileUri = data.getData();
            picturePath_doc = getPath(getActivity(), profileUri);
            if (picturePath_doc != null) {
                if (picturePath_doc != "") {
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath_doc);
                    if (bitmap != null)
                        img_doc.setImageBitmap(bitmap);
                }
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageFromCamera_doc(int resultCode, Intent data) {
        this.img_doc.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }

    private void imageFromCamera_user(int resultCode, Intent data) {
        this.img_user.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return "";
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private ArrayList<String> findUnAskedPermission(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String perm) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");

                for (String perms : permissionToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private class DropDownKycAdapter extends RecyclerView.Adapter<DropDownKycAdapter.ViewHolder> {
        private Context context;
        private String[] str_Name;

        public DropDownKycAdapter(FragmentActivity activity, String[] str_name) {
            this.context = activity;
            this.str_Name = str_name;
        }

        @NonNull
        @Override
        public DropDownKycAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.country_icon, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DropDownKycAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.text_type.setText(str_Name[i]);
            viewHolder.relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str_type = str_Name[i];
                    text_selectType.setText(str_type);
                    recyclerView.setVisibility(View.GONE);

                }
            });

        }

        @Override
        public int getItemCount() {
            return str_Name.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text_type;
            private RelativeLayout relative;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text_type = itemView.findViewById(R.id.text_country_name);
                relative = itemView.findViewById(R.id.rel);
            }
        }
    }
}

//userId:1
//        idNumber:1324
//        action:update_kyc
//        file:file