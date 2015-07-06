package yaxstudio.com.cloudcopypaste;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LoginScreenActivity extends Activity implements OnClickListener
{
    EditText txtUsername, txtPassword;
    TextView lblForgotPassword, lblRegisterNewUser;
    Button btnLogin;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://yaxstudio.host56.com/ccp/CCPLoginWS.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USERID = "ID_User";

    String GVUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        lblForgotPassword = (TextView) findViewById(R.id.lblForgotPassword);
        lblRegisterNewUser = (TextView) findViewById(R.id.lblRegisterNewUser);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        lblForgotPassword.setOnClickListener(this);
        lblRegisterNewUser.setOnClickListener(this);
    }

    public static long back_pressed;

    @Override
    public void onBackPressed()
    {
        if (back_pressed + 3000 > System.currentTimeMillis())
        {
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Press back button once again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:

                new AttemptLogin().execute();

                break;

            case R.id.lblForgotPassword:

                Toast.makeText(LoginScreenActivity.this, "Feature Coming Soon!", Toast.LENGTH_SHORT).show();

                break;

            case R.id.lblRegisterNewUser:

                Intent intRegisterNewUser = new Intent(LoginScreenActivity.this, RegisterActivity.class);
                finish();
                startActivity(intRegisterNewUser);

                break;

            default:

                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String>
    {
        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreenActivity.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;

            String Username = txtUsername.getText().toString();
            String Password = txtPassword.getText().toString();

            try
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Username", Username));
                params.add(new BasicNameValuePair("Password", Password));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // Get and Store Data From JSON
                success = json.getInt(TAG_SUCCESS);

                Log.d("success: ", String.valueOf(success));

                switch (success)
                {
                    case 0:

                        final String errorMessage = json.getString(TAG_MESSAGE);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginScreenActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    case 1:

                        GVUserID = json.getString(TAG_USERID);
                        GlobalVars.GVUserID = GVUserID;

                        final String welcomeMessage = json.getString(TAG_MESSAGE);

                        Intent intMainScreen = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                        finish();
                        startActivity(intMainScreen);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginScreenActivity.this, welcomeMessage, Toast.LENGTH_LONG).show();
                            }
                        });

                        break;

                    default:

                        break;
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * *
         */

        protected void onPostExecute(String message)
        {
            pDialog.dismiss();

            if (message != null)
            {
                Toast.makeText(LoginScreenActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}