package com.tbx.gc.greatcash;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbx.gc.greatcash.editProfileRerquestPOJO.editProileRequestBean;
import com.tbx.gc.greatcash.getProfileRequestOJO.Data;
import com.tbx.gc.greatcash.getProfileRequestOJO.getProfileRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;

import java.io.File;
import java.util.ArrayList;

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
import static android.content.Context.MODE_PRIVATE;

public class Profile extends Fragment {

    TextView refId, name, phone, reedemwallet,makeselect,maleunselect,femaleselect,femaleunselect,yesselected,yesunselected,noselected,nounselected,earnMore , upline;

    ProgressBar progress;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    TextView logout;

    Button update;
    ImageView img_profile;

    EditText email, country, state, dob, nominee, relation;
    Uri fileUri = null;
    String picturePathuser;
    Button btn_profileupdate;

    ArrayList<String> permissionToRequest;
    ArrayList<String> permissions =new ArrayList<>();
    ArrayList<String> permissionsRejected=new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT=101;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        email = view.findViewById(R.id.editText);
        country = view.findViewById(R.id.editText2);
        state = view.findViewById(R.id.editText3);
        dob = view.findViewById(R.id.editText4);
        nominee = view.findViewById(R.id.editText5);
        relation = view.findViewById(R.id.editText6);

        upline = view.findViewById(R.id.textView61);

        logout = view.findViewById(R.id.textView76);

        update = view.findViewById(R.id.button2);

        pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        edit = pref.edit();

        refId = view.findViewById(R.id.textView58);
        name = view.findViewById(R.id.textView62);
        phone = view.findViewById(R.id.textView63);
        reedemwallet = view.findViewById(R.id.textView75);

        makeselect = view.findViewById(R.id.textView70);
        maleunselect = view.findViewById(R.id.textViewmalenot);
        femaleunselect = view.findViewById(R.id.textView71);
        femaleselect  = view.findViewById(R.id.textViewfemalesel);
        earnMore=view.findViewById(R.id.textView69);
        btn_profileupdate=view.findViewById(R.id.btn_profileUpdate);


        yesselected  = view.findViewById(R.id.textView72);
        yesunselected  = view.findViewById(R.id.textViewyesunselected);
        nounselected  = view.findViewById(R.id.textView73);
        noselected = view.findViewById(R.id.textViewnounselected);

        img_profile=view.findViewById(R.id.view3);

        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionToRequest=findUnAskedPermission(permissions);

        progress = view.findViewById(R.id.progressBar8);
        id= pref.getString("id" , "");

        loadData();

        femaleunselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeselect.setVisibility(View.GONE);
                maleunselect.setVisibility(View.VISIBLE);
                femaleselect.setVisibility(View.VISIBLE);
                femaleunselect.setVisibility(View.GONE);

            }
        });

        maleunselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeselect.setVisibility(View.VISIBLE);
                maleunselect.setVisibility(View.GONE);
                femaleselect.setVisibility(View.GONE);
                femaleunselect.setVisibility(View.VISIBLE);



            }
        });
        //yesselected,yesunselected,noselected,nounselected;

        nounselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesselected.setVisibility(View.GONE);
                yesunselected.setVisibility(View.VISIBLE);
                noselected.setVisibility(View.VISIBLE);
                nounselected.setVisibility(View.GONE);

            }
        });

        yesunselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesselected.setVisibility(View.VISIBLE);
                yesunselected.setVisibility(View.GONE);
                noselected.setVisibility(View.GONE);
                nounselected.setVisibility(View.VISIBLE);



            }
        });

        btn_profileupdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                File file = new File(picturePathuser);
                // create RequestBody instance from file
                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(getActivity().getContentResolver().getType(fileUri)),
                                file
                        );

                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "update_pic");
                RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), "382");
                Call<ResponseBody> responseBodyCall = apiInterface.saveDataProfile(action, userId,body);
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        reedemwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showDialog();

            }
        });


        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progress.setVisibility(View.VISIBLE);


                bean b = (bean) getContext().getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                ApiInterface cr = retrofit.create(ApiInterface.class);

                editProileRequestBean body = new editProileRequestBean();

                com.tbx.gc.greatcash.editProfileRerquestPOJO.Data data = new com.tbx.gc.greatcash.editProfileRerquestPOJO.Data();

                data.setCountry(country.getText().toString());
                data.setDob(dob.getText().toString());
                data.setEmail(email.getText().toString());
                data.setNominee(nominee.getText().toString());
                data.setRelationNominee(relation.getText().toString());
                data.setState(state.getText().toString());
                data.setUserId(pref.getString("id", ""));

                body.setAction("update_profile");
                body.setData(data);

                Call<registerResponseBean> call = cr.editProfile(body);

                call.enqueue(new Callback<registerResponseBean>() {
                    @Override
                    public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            edit.putString("id", response.body().getData().getUserId());
                            edit.putString("refId", response.body().getData().getRefId());
                            edit.putString("parentId", response.body().getData().getParentId());
                            edit.putString("name", response.body().getData().getName());
                            edit.putString("email", response.body().getData().getEmail());
                            edit.putString("phone", response.body().getData().getMobile());
                            edit.putString("pic", response.body().getData().getUserPic());
                            edit.putString("dob", response.body().getData().getBirthDate());
                            edit.putString("country", response.body().getData().getCountry());
                            edit.putString("state", response.body().getData().getState());
                            edit.putString("city", response.body().getData().getCity());
                            edit.putString("pid", response.body().getData().getPid());
                            edit.apply();

                            loadData();

                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<registerResponseBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit.remove("id");
                edit.remove("refId");
                edit.remove("parentId");
                edit.remove("name");
                edit.remove("email");
                edit.remove("phone");
                edit.remove("pic");
                edit.remove("dob");
                edit.remove("country");
                edit.remove("state");
                edit.remove("city");
                edit.remove("pid");
                edit.remove("passwordSave");

                edit.apply();

                Intent intent = new Intent(getContext() , Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });


        img_profile.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (permissionToRequest.size() > 0)
                    {
                        requestPermissions(permissionToRequest.toArray(new String[permissionToRequest.size()]),
                                ALL_PERMISSIONS_RESULT);
                        Log.d(ContentValues.TAG, "Permission requests");

                    }
                }

                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);


                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        }

    public void loadData() {

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        getProfileRequestBean body = new getProfileRequestBean();

        body.setAction("get_profile");

        Data data = new Data();

        data.setUserId(id);
        Log.e("idddddd_profile",""+id);

        body.setData(data);

        Call<registerResponseBean> call = cr.getProfile(body);
        call.enqueue(new Callback<registerResponseBean>() {
            @Override
            public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                if (response.body().getStatus().equals("1")) {
                    refId.setText("REF ID : " + response.body().getData().getRefId());
                    name.setText(response.body().getData().getName());
                    phone.setText(response.body().getData().getMobile());

                    email.setText(response.body().getData().getEmail());
                    country.setText(response.body().getData().getCountry());
                    state.setText(response.body().getData().getState());
                    dob.setText(response.body().getData().getBirthDate());
                    nominee.setText(response.body().getData().getNominee());
                    relation.setText(response.body().getData().getRelationNominee());
                    earnMore.setText("$ " + response.body().getData().getEarningAmount());

                    upline.setText(response.body().getData().getParentId());

                    String img_user=response.body().getData().getUserPic();
                    Log.e("img_linkkkk",""+img_user);

                    if(img_user.equals(""))
                    {
                        img_profile.setImageDrawable(getResources().getDrawable(R.drawable.user_default));
                    }
                    else
                        {
                            Glide.with(getActivity()).load(Uri.parse(img_user)).into(img_profile);
                        }



                    }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<registerResponseBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


    public void showDialog()
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.redemewalletaleart, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditTextredeem);
        final Button submit = (Button) subView.findViewById(R.id.btnTextredeem);




        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Your GC bal. = 300");
        builder.setMessage("Enter Amount");
        builder.setView(subView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    alertDialog.dismiss();


            }
        });



        alertDialog.show();
    }


    private ArrayList<String> findUnAskedPermission(ArrayList<String> wanted)
    {
        ArrayList result= new ArrayList();
        for(String perm : wanted)
        {
            if(!hasPermission(perm))
            {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String perm)
    {
        if(canAskPermission())
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                return (getActivity().checkSelfPermission(perm)== PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission()
    {
        return (Build.VERSION.SDK_INT >Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK && null!=data)
        {
            fileUri = data.getData();
            picturePathuser = getPath(getActivity(), fileUri);
            if (picturePathuser != null)
            {
                if (picturePathuser != "")
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePathuser);
                    if (bitmap != null)
                        img_profile.setImageBitmap(bitmap);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri)
    {

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

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
    {

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


}
