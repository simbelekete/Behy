package adompo.ayyash.behay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    public static final String KEY_NAMA = "nama";
    public static final String KEY_JK = "jenis_kelamin";
    public static final String KEY_TGL_LAHIR= "tanggal_lahir";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASS = "password";
    String jenisKelamin="";


    Button tomboldaftar;
    ProgressDialog progressDialog;

    EditText txt_nama,  txt_ttl, txt_email, txt_username, txt_password;
    RadioGroup rgjk;
    RadioButton jeniskelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        txt_nama = (EditText)findViewById(R.id.txtNama);
        rgjk = (RadioGroup)findViewById(R.id.jk);

        txt_ttl      = (EditText) findViewById(R.id.txtDate);
        txt_email   = (EditText)findViewById(R.id.txtemail);
        txt_username     = (EditText)findViewById(R.id.txtusername);




        txt_password = (EditText)findViewById(R.id.txtPass);

        tomboldaftar = (Button)findViewById(R.id.btnDaftar);

        tomboldaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedJK = rgjk.getCheckedRadioButtonId();

                jeniskelamin = (RadioButton)findViewById(selectedJK);


                    if(txt_nama.getText().toString().isEmpty()
                            || txt_ttl.getText().toString().equals("")
                            || txt_email.getText().toString().equals("")
                            || txt_username.getText().toString().equals("")
                            || txt_password.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"pastikan data terisi semua", Toast.LENGTH_LONG).show();
                    } else {
                       // Toast.makeText(getApplicationContext(),"sip: " + jeniskelamin.getText().toString(), Toast.LENGTH_LONG).show();
                      //  Toast.makeText(getApplicationContext(),"jadi: " + txt_ttl.getText().toString(),Toast.LENGTH_LONG).show();

                        save();
                    }

                   // Toast.makeText(getApplicationContext(),"nama: " + txt_nama.getText().toString(), Toast.LENGTH_LONG).show();

            }
        });







    }



    private void save() {
        progressDialog.show();
        final String nama = txt_nama.getText().toString().trim();
        final String jenis_kelamin = jeniskelamin.getText().toString().trim();
        final String tanggal_lahir = txt_ttl.getText().toString().trim();
        final String email = txt_email.getText().toString().trim();
        final String username = txt_username.getText().toString().trim();
        final String password = txt_password.getText().toString().trim();




        StringRequest sR = new StringRequest(Request.Method.POST, ConfigUmum.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                         Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                        progressDialog.dismiss();
                      //  signinhere.setText(response);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAMA, nama);
                params.put(KEY_JK, jenis_kelamin);
                params.put(KEY_TGL_LAHIR, tanggal_lahir);
                params.put(KEY_EMAIL, email);
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASS, password);

                return params;
            }

        };
//        Toast.makeText(getApplicationContext(), txt_email + " makanan = " + makanan, Toast.LENGTH_LONG).show();
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        sR.setRetryPolicy(policy);
        requestQueue.add(sR);
    }
}
