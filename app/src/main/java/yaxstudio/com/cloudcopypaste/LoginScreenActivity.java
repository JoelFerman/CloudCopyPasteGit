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
    private static final String TAG_USERNAME = "Username_User";
    private static final String TAG_PASSWORD = "Password_User";

    String GVUserID;

    //Comentario de prueba

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        lblForgotPassword = (TextView)findViewById(R.id.lblForgotPassword);
        lblRegisterNewUser = (TextView)findViewById(R.id.lblRegisterNewUser);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        //ytgkjkkj

        btnLogin.setOnClickListener(this);
        lblForgotPassword.setOnClickListener(this);
        lblRegisterNewUser.setOnClickListener(this);
    }

    public static long back_pressed;
    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
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
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute ()
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

                // checking  log for json response
                Log.d("Login attempt", json.toString());

                // Get and Store Data From JSON
                success = json.getInt(TAG_SUCCESS);
                GVUserID = json.getString(TAG_USERID);

                GlobalVars.GVUserID = GVUserID;

                // Variables To Compare Credentials
                String userCompare = json.getString(TAG_USERNAME);
                String passCompare = json.getString(TAG_PASSWORD);

                switch (success)
                {
                    case 0:

                        Toast.makeText(LoginScreenActivity.this, "TEST", Toast.LENGTH_SHORT).show();

                        Log.d("Invalid Credentials", json.toString());

                        break;

                    case 1:

                        if (txtPassword.getText().toString().contentEquals(passCompare) && txtUsername.getText().toString().contentEquals(userCompare))
                        {
                            Log.d("Successful Login!", json.toString());

                            Intent intMainScreen = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                            finish();
                            startActivity(intMainScreen);

                            break;

                        }
                        else if (!txtPassword.getText().toString().contentEquals(passCompare) && txtUsername.getText().toString().contentEquals(userCompare))
                        {
                            Toast.makeText(LoginScreenActivity.this, "Credentials don't match", Toast.LENGTH_LONG).show();

                            break;
                        }

                    default:

                        Toast.makeText(LoginScreenActivity.this, "Credentials don't match", Toast.LENGTH_LONG).show();

                        break;
                }
                Toast.makeText(LoginScreenActivity.this, "Credentials don't match", Toast.LENGTH_LONG).show();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/

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
