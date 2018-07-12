package com.example.q.project2;

import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Filter;


import java.io.DataOutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.net.ProtocolException;
import org.json.JSONArray;
import org.json.JSONStringer;
import org.json.JSONException;
import org.json.JSONObject;


public class Contacts extends Fragment implements View.OnClickListener {

    ListView listview ;
    ArrayList<Map<String,String>> datalist2;
    ArrayList<ListViewItem> datalist;
    private CallbackManager callbackManager;
    private String Idtoken;
    String id ="123";
    String getit;
    String send;
    ProgressDialog pd;
    String Jsondata;
    // ListViewAdapter mAdapter = new ListViewAdapter();
    //private ArrayList<ListViewItem> mItemList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        //mListView.setAdapter(mAdapter);
        callbackManager = CallbackManager.Factory.create();
        LoginButton facebook_btn = (LoginButton) v.findViewById(R.id.login_button);
        ImageButton mbtnadd = (ImageButton) v.findViewById(R.id.buttonadd);
        mbtnadd.setOnClickListener(this);
        ImageButton mbtnget = (ImageButton) v.findViewById(R.id.btnget);
        mbtnget.setOnClickListener(this);
        ImageButton mbtngive = (ImageButton) v.findViewById(R.id.btngive);
        mbtngive.setOnClickListener(this);
        facebook_btn.setReadPermissions(Arrays.asList("public_profile", "email","user_friends"));
        facebook_btn.setFragment(this);
        listview = (ListView) v.findViewById(R.id.listview);
        refresh();



       // EditText editText = v.findViewById(R.id.editTextFilter);
       // editText.addTextChangedListener(new TextWatcher() {
       //     @Override
       //     public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       //     }

        //    @Override
        //    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // ((ListViewBtnAdapter) listview.getAdapter()).getFilter().filter(charSequence);
         //   }

         //   @Override
         //   public void afterTextChanged(Editable editable) {
          //      String filterText = editable.toString() ;
                //if (filterText.length() > 0) { listview.setFilterText(filterText) ; }
                //  else { listview.clearTextFilter() ; }

            //    ((ListViewBtnAdapter) listview.getAdapter()).getFilter().filter(filterText);

         //   }
       // });


        facebook_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                        Idtoken = Profile.getCurrentProfile().getId().toString();
                        Log.v("token",Idtoken);
                        id = Idtoken;

                    }
                });




                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Idtoken="";
                id = "123";
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("로그인실패",error.toString());
            }
        });






        return v;
    }



    public void onClick(View v){
        switch (v.getId()) {
            case R.id.buttonadd:
                Context context = getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                AlertDialog.Builder add = new AlertDialog.Builder(getContext());

                add.setTitle("추가하기");
                //add.setMessage("이름");
                final TextView name2 = new TextView(context);
                name2.setText("이름");
                layout.addView(name2);
                final EditText name1 = new EditText(context);
                //name1.setHint("이름");
                layout.addView(name1);
                //add.setView(name1);
                final TextView phone2 = new TextView(context);
                phone2.setText("전화번호");
                layout.addView(phone2);
                final EditText phone1 = new EditText(context);
                phone1.setInputType(0x00000003);
                //phone1.setHint("전화번호");
                layout.addView(phone1);
                //add.setView(phone1);

                add.setView(layout);

                add.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ( name1.getText().toString().length() == 0 ) {
                            AlertDialog.Builder add2 = new AlertDialog.Builder(getContext());
                            add2.setTitle("이름이 공백입니다");
                            add2.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            add2.show();

                        }

                        if ( phone1.getText().toString().length() == 0 ) {
                            AlertDialog.Builder add2 = new AlertDialog.Builder(getContext());
                            add2.setTitle("전화번호가 공백입니다");
                            add2.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            add2.show();


                        }

                        else {
                            String value1 = name1.getText().toString();
                            String value2 = phone1.getText().toString();
                            addContact(value2, value1);
                            refresh();
                        }
                    }
                });

                add.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                add.show();

                refresh();
                break;

            case R.id.btnget:
                if(id == "123"){
                    final Toast tag = Toast.makeText(getContext(), "로그인 하세요",Toast.LENGTH_LONG);
                    tag.show();
                    break;
                }


                new JsonTask().execute("http://52.231.68.137:8080/loadData");
                Cursor cursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                    getContext().getContentResolver().delete(uri, null, null);
                }

                try {
                    jsontocontact(getit);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                refresh();
                break;

            case R.id.btngive:
                if(id == "123"){
                    final Toast tag2 = Toast.makeText(getContext(), "로그인 하세요",Toast.LENGTH_LONG);
                    tag2.show();
                    break;
                }
                parsing_json();
                post_string(Jsondata, "/addrec");
                Log.i("post","posttest");

                refresh();
                break;
        }
    }



    public void parsing_json(){
        Jsondata = "";
        if(datalist2.size()!=0) {
            Jsondata ="["+ "{\"name\"" + ":" + "\"" + datalist2.get(0).get("name") + "\"" + ","
                    + "\"phone\"" + ":" + "\"" + datalist2.get(0).get("phone") + "\"" + "}";
            for (int i = 1; i < datalist2.size(); i++) {
                String tempJson = ","+"{\"name\"" + ":" + "\"" + datalist2.get(i).get("name") + "\"" + ","
                        + "\"phone\"" + ":" + "\"" + datalist2.get(i).get("phone") + "\"" + "}";
                Jsondata = Jsondata + tempJson;
            }
            Jsondata = Jsondata + "]";
        }

        Jsondata = "{\"ID\":\""+id+"\",\"phonebook\":\n" + Jsondata+"}";
    }







    public void jsontocontact(String str) throws JSONException{

        str = "{"+"\""+"what"+"\""+":"+str+"}";

        JSONObject jsonObject = new JSONObject(str);
        JSONArray jsonArray = jsonObject.getJSONArray("what");
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject curr = jsonArray.getJSONObject(i);

           String nm = curr.getString("name");
            String ph = curr.getString("phone");
            addContact(ph, nm);
//Do stuff with the Prize String here
//Add it to a list, print it out, etc.
        }

        //JSONArray jsonarray = new JSONArray(str);


      //  for (int i = 0; i < jsonarray.length(); i++) {
       //     JSONObject jsonobject = jsonarray.getJSONObject(i);
       //     String nm = jsonobject.getString("name");
       //     String ph = jsonobject.getString("phone");
       //     addContact(ph, nm);
       // }
    }


    public void refresh(){
        ListViewBtnAdapter adapter;
        datalist = new ArrayList<ListViewItem>() ;
       datalist2 = new ArrayList<Map<String,String>>();


        Cursor c = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");
        while (c.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            // 연락처 id 값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            // 연락처 대표 이름
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            map.put("name", name);
            // ID로 전화 정보 조회
            Cursor phoneCursor = getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null);
            // 데이터가 있는 경우
            String number = "";
            if (phoneCursor.moveToFirst()) {
                number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                map.put("phone", number);
            }
            phoneCursor.close();
            datalist2.add(map);

        }// end while
        c.close();

        for(Map<String,String> item:datalist2){
            datalist.add(new ListViewItem(item.get("name"),item.get("phone")));
        }



        adapter = new ListViewBtnAdapter(getActivity(), R.layout.listview_contacts, datalist);

         listview.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public class ListViewItem {
        private String name;
        private String phone;
        public ListViewItem(String name1, String phone1){
            name= name1;
            phone = phone1;
        }

        public void setName(String text) {
            name = text ;
        }

        public void setPhone(String text) {
            phone = text ;
        }
        public String getName() {
            return this.name ;
        }

        public String getPhone() {
            return this.phone ;
        }
    }


    public static void post_string(String sent, final String destination){
        try {
            final JSONObject contact_jsonobj = new JSONObject(sent); // The actual JSON Object to be sent
            new Thread(){
                public void run(){
                    try {
                        // SEND REQUEST TO POST METHOD CODE
                        // 52.162.211.235:7714 / contact?hash=213412341 :받아오기(GET)
                        // /contactHandle    (POST) hash : 132412341, contacts : [{ "name" : "asdfh"
                        URL url = new URL("http://52.231.68.137:8080" + destination);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setRequestProperty("Content-Type","application/json");
                        httpURLConnection.setRequestProperty("Accept","application/json");

                        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                        wr.write(contact_jsonobj.toString().getBytes());
                        Integer responsecode = httpURLConnection.getResponseCode();

                        BufferedReader bufferedReader;

                        if(responsecode>199 && responsecode < 300){
                            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        } else {
                            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                        }
                        // get response part
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line=bufferedReader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        bufferedReader.close();

                        Log.i("response",content.toString());
                        /// POST TO SERVER
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getContext());
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
           getit = result;
        }
    }

    public void deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            cur.close();

        }
    }
    public void addContact( String phone, String name){
        final String value1 =phone;
        final String value2 = name;
        Thread thread2 = new Thread(){
            @Override
            public void run() {
        ArrayList<ContentProviderOperation> list = new ArrayList<>();
        try {
            list.add(
                    ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build()
            );

            list.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)

                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, value2)   //이름

                            .build()
            );

            list.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)

                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, value1)           //전화번호
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)   //번호타입(Type_Mobile : 모바일)

                            .build()
            );
            getContext().getApplicationContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, list);  //주소록추가
            list.clear();   //리스트 초기화
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
            }
        };
        thread2.start();
        try{thread2.join();}
        catch (InterruptedException e){}
    }


    public  class ListViewBtnAdapter extends ArrayAdapter implements  Filterable {

        int resourceId ;
        //private ArrayList<ListViewItem> a = datalist ;
       // private ArrayList<ListViewItem> filteredItemList = a ;
        //Filter listFilter ;

       // @Override public int getCount() { return filteredItemList.size() ; }



        // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
        ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewItem> list) {
            super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
            this.resourceId = resource ;


    }

        // 새롭게 만든 Layout을 위한 View를 생성하는 코드
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position ;
            final Context context = parent.getContext();

            // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_contacts/*R.layout.listview_btn_item*/, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
            final TextView textTextView1 = (TextView) convertView.findViewById(R.id.textView1);
            final TextView textTextView2 = (TextView) convertView.findViewById(R.id.textView2);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final ListViewItem listViewItem = (ListViewItem) getItem(position);

            // 아이템 내 각 위젯에 데이터 반영
            textTextView1.setText(listViewItem.getName());
            textTextView2.setText(listViewItem.getPhone());

            ImageButton button1 = (ImageButton) convertView.findViewById(R.id.delete_info);
            button1.setOnClickListener(new ImageButton.OnClickListener() {
                public void onClick(View v) {
                   deleteContact(getContext(), datalist2.get(pos).get("phone"), datalist2.get(pos).get("name"));
                    refresh();
                }
            });

            // button2의 TAG에 position값 지정. Adapter를 click listener로 지정.
            ImageButton button2 = (ImageButton) convertView.findViewById(R.id.change_info);
            button2.setOnClickListener(new ImageButton.OnClickListener() {
                public void onClick(View v) {
                    final String phone = datalist2.get(pos).get("phone");
                    final String name = datalist2.get(pos).get("name");
                    Context context = getContext();
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    AlertDialog.Builder add = new AlertDialog.Builder(getContext());

                    add.setTitle("수정하기");
                    //add.setMessage("이름");
                    final TextView name2 = new TextView(context);
                    name2.setText("이름");
                    layout.addView(name2);
                    final EditText name1 = new EditText(context);
                    name1.setHint(name);
                    name1.setText(name);
                    layout.addView(name1);
                    //add.setView(name1);
                    final TextView phone2 = new TextView(context);
                    phone2.setText("전화번호");
                    layout.addView(phone2);
                    final EditText phone1 = new EditText(context);
                    phone1.setInputType(0x00000003);
                    phone1.setHint(phone);
                    phone1.setText(phone);
                    layout.addView(phone1);
                    //add.setView(phone1);

                    add.setView(layout);

                    add.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (name1.getText().toString().length() == 0) {
                                AlertDialog.Builder add2 = new AlertDialog.Builder(getContext());
                                add2.setTitle("이름이 공백입니다");
                                add2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                add2.show();


                            } else if (phone1.getText().toString().length() == 0) {
                                AlertDialog.Builder add2 = new AlertDialog.Builder(getContext());
                                add2.setTitle("전화번호가 공백입니다");
                                add2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                add2.show();
                            } else {
                                deleteContact(getContext(), datalist2.get(pos).get("phone"), datalist2.get(pos).get("name"));
                                final String value1 = phone1.getText().toString();
                                final String value2 = name1.getText().toString();
                                addContact(value1, value2);
                                refresh();
                                }
                        }
                    });

                    add.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    add.show();


                }});
            //button2.setOnClickListener(this);




            return convertView;
        }

       // @Override public long getItemId(int position) { return position ; }
       // @Override public Object getItem(int position) { return filteredItemList.get(position) ; }
        //@Override public Filter getFilter() { if (listFilter == null) { listFilter = new ListFilter() ; } return listFilter ; }
       // private class ListFilter extends Filter {

       //     @Override
       //     protected FilterResults performFiltering(CharSequence constraint) {
        //        FilterResults results = new FilterResults();

         //       if (constraint == null || constraint.length() == 0) {
         //           results.values = a;
          //          results.count = a.size();
           //     } else {
            //        ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();

           //         for (ListViewItem item : a) {
             //           if (item.getName().toUpperCase().contains(constraint.toString().toUpperCase()) ||
            //                    item.getPhone().toUpperCase().contains(constraint.toString().toUpperCase())) {
            //                itemList.add(item);
             //           }
             //       }

                //    results.values = itemList;
               //     results.count = itemList.size();
              //  }
          //      return results;
           // }

          //  @Override protected void publishResults(CharSequence constraint, FilterResults results) {
                // update listview by filtered data list.
           //     filteredItemList = (ArrayList<ListViewItem>) results.values ;
                // notify
             //   if (results.count > 0) { notifyDataSetChanged() ; } else { notifyDataSetInvalidated() ; } }
      //  }
    }
}
