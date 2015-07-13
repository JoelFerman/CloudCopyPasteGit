package yaxstudio.com.cloudcopypaste;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RegisterActivity extends Activity implements OnClickListener
{
    private ProgressDialog pDialog;

    JSONParser JSONParser = new JSONParser();

    private static String REGISTER_URL = "http://yaxstudio.host56.com/ccp/CCPRegisterWS.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    EditText txtADDUsername, txtADDPassword, txtADDPassword2, txtADDEmail;
    TextView btnHeaderLeft, btnHeaderRight;


//gggg

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnHeaderLeft = (TextView)findViewById(R.id.btnHeaderLeft);
        btnHeaderRight = (TextView)findViewById(R.id.btnHeaderRight);

        txtADDUsername = (EditText)findViewById(R.id.txtADDUsername);
        txtADDPassword = (EditText)findViewById(R.id.txtADDPassword);
        txtADDPassword2 = (EditText)findViewById(R.id.txtADDPassword2);
        txtADDEmail = (EditText)findViewById(R.id.txtADDEmail);

        btnHeaderLeft.setOnClickListener(this);
        btnHeaderRight.setOnClickListener(this);
    }

    @Override
    public void onBackPressed()
    {
    }

    public void ClearPasswordFields()
    {
        txtADDPassword.setText("");
        txtADDPassword2.setText("");

        txtADDPassword.requestFocus();
    }

    public void ClearAllFields()
    {
        txtADDUsername.setText("");
        txtADDPassword.setText("");
        txtADDPassword2.setText("");
        txtADDEmail.setText("");

        txtADDUsername.requestFocus();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnHeaderLeft:

                Intent intBackLogin = new Intent(RegisterActivity.this, LoginScreenActivity.class);
                finish();
                startActivity(intBackLogin);

                break;

            case R.id.btnHeaderRight:

                if (txtADDPassword2.getText().toString().contentEquals(txtADDPassword.getText().toString()))
                {
                    new CreateUser().execute();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Password Fields Don't Match", Toast.LENGTH_SHORT).show();
                    ClearPasswordFields();
                }

                break;
        }
    }

    class CreateUser extends AsyncTask<String, String, String>
    {
        boolean failure = false;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            // Check for success tag

            int success, min = 1000, max = 99999, i1, i2;

            Random r = new Random();

            i1 = r.nextInt(max - min + 1) + min;
            i2 = r.nextInt(max - min + 1) + min;

            String IDUser = "USER-" + i1;
            String Username = txtADDUsername.getText().toString();
            String Password = txtADDPassword.getText().toString();
            String Email = txtADDEmail.getText().toString();
            String IDLink = "LNKROW-" + i2;

            try
            {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                //params.add(new BasicNameValuePair("IDUser", IDUser));
                params.add(new BasicNameValuePair("username", Username));
                params.add(new BasicNameValuePair("password", Password));
                params.add(new BasicNameValuePair("email", Email));
                //params.add(new BasicNameValuePair("IDLink", IDLink));

                Log.d("request!", "starting");

                //Posting user data to script
                //JSONObject json = JSONParser.makeHttpRequest(REGISTER_URL, "POST", params);
                JSONObject json = JSONParser.makeHttpRequest(GlobalVars.api_ulr + "/register", "POST", params);

                // json success element
                success = json.getInt(TAG_SUCCESS);

                switch (success)
                {
                    case 0:

                        final String errorMessage = json.getString(TAG_MESSAGE);

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    case 1:

                        final String successMessage = json.getString(TAG_MESSAGE);

                        Intent intLoginScreen = new Intent(RegisterActivity.this, LoginScreenActivity.class);
                        finish();
                        startActivity(intLoginScreen);

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(RegisterActivity.this, successMessage, Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    default:

                        break;
                }

//                if (success == 1)
//                {
//                    Log.d("User Created!", json.toString());
//
//                    return json.getString(TAG_MESSAGE);
//                }
//                else
//                {
//
//                    Log.d("Register Failure!", json.getString(TAG_MESSAGE));
//
//                    return json.getString(TAG_MESSAGE);
//
//                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message)
        {
            // dismiss the dialog once product deleted
            pDialog.dismiss();

            if (message != null)
            {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                ClearAllFields();
            }
        }
    }
}
