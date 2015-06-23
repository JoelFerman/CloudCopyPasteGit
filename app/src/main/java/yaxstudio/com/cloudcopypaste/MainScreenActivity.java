package yaxstudio.com.cloudcopypaste;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.ClipboardManager;
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

public class MainScreenActivity extends Activity implements OnClickListener
{
    TextView btnHeaderLeft, btnHeaderDownload, btnHeaderUpload, btnHeaderRight;
    TextView copyLink1, copyLink2, copyLink3, copyLink4, copyLink5;
    TextView pasteLink1, pasteLink2, pasteLink3, pasteLink4, pasteLink5;
    EditText txtLink1, txtLink2, txtLink3, txtLink4, txtLink5;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String DOWNLOADLINKS_URL = "http://yaxstudio.host56.com/ccp/CCPDownloadLinkWS.php";
    private static final String UPLOADLINKS_URL = "http://yaxstudio.host56.com/ccp/CCPUploadLinkWS.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_LINK1 = "Link1";
    private static final String TAG_LINK2 = "Link2";
    private static final String TAG_LINK3 = "Link3";
    private static final String TAG_LINK4 = "Link4";
    private static final String TAG_LINK5 = "Link5";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        castObjects();
    }

    @Override
    public void onBackPressed()
    {

    }

    public void ClearLinks()
    {
        txtLink1.setText("");
        txtLink2.setText("");
        txtLink3.setText("");
        txtLink4.setText("");
        txtLink5.setText("");
    }

    public void castObjects()
    {
        btnHeaderLeft = (TextView)findViewById(R.id.btnHeaderLeft);
        btnHeaderDownload = (TextView)findViewById(R.id.btnHeaderDownload);
        btnHeaderUpload = (TextView)findViewById(R.id.btnHeaderUpload);
        btnHeaderRight = (TextView)findViewById(R.id.btnHeaderRight);

        copyLink1 = (TextView)findViewById(R.id.copyLink1);
        copyLink2 = (TextView)findViewById(R.id.copyLink2);
        copyLink3 = (TextView)findViewById(R.id.copyLink3);
        copyLink4 = (TextView)findViewById(R.id.copyLink4);
        copyLink5 = (TextView)findViewById(R.id.copyLink5);

        pasteLink1 = (TextView)findViewById(R.id.pasteLink1);
        pasteLink2 = (TextView)findViewById(R.id.pasteLink2);
        pasteLink3 = (TextView)findViewById(R.id.pasteLink3);
        pasteLink4 = (TextView)findViewById(R.id.pasteLink4);
        pasteLink5 = (TextView)findViewById(R.id.pasteLink5);

        txtLink1 = (EditText)findViewById(R.id.txtLink1);
        txtLink2 = (EditText)findViewById(R.id.txtLink2);
        txtLink3 = (EditText)findViewById(R.id.txtLink3);
        txtLink4 = (EditText)findViewById(R.id.txtLink4);
        txtLink5 = (EditText)findViewById(R.id.txtLink5);

        btnHeaderLeft.setOnClickListener(this);
        btnHeaderDownload.setOnClickListener(this);
        btnHeaderUpload.setOnClickListener(this);
        btnHeaderRight.setOnClickListener(this);

        copyLink1.setOnClickListener(this);
        copyLink2.setOnClickListener(this);
        copyLink3.setOnClickListener(this);
        copyLink4.setOnClickListener(this);
        copyLink5.setOnClickListener(this);

        pasteLink1.setOnClickListener(this);
        pasteLink2.setOnClickListener(this);
        pasteLink3.setOnClickListener(this);
        pasteLink4.setOnClickListener(this);
        pasteLink5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnHeaderDownload:

                ClearLinks();

                new DownloadLinks().execute();

                break;

            case R.id.btnHeaderUpload:

                new UploadLinks().execute();

                break;

            case R.id.btnHeaderRight:

                Intent intLogout = new Intent(MainScreenActivity.this, LoginScreenActivity.class);
                finish();
                startActivity(intLogout);

                break;

            case R.id.copyLink1:

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy1", txtLink1.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainScreenActivity.this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

                break;

            case R.id.pasteLink1:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                txtLink1.setText(item.getText());

                break;

            case R.id.copyLink2:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText("copy2", txtLink2.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainScreenActivity.this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

                break;

            case R.id.pasteLink2:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                item = clipboard.getPrimaryClip().getItemAt(0);
                txtLink2.setText(item.getText());

                break;

            case R.id.copyLink3:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText("copy3", txtLink3.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainScreenActivity.this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

                break;

            case R.id.pasteLink3:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                item = clipboard.getPrimaryClip().getItemAt(0);
                txtLink3.setText(item.getText());

                break;

            case R.id.copyLink4:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText("copy4", txtLink4.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainScreenActivity.this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

                break;

            case R.id.pasteLink4:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                item = clipboard.getPrimaryClip().getItemAt(0);
                txtLink4.setText(item.getText());

                break;

            case R.id.copyLink5:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText("copy5", txtLink5.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainScreenActivity.this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

                break;

            case R.id.pasteLink5:

                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                item = clipboard.getPrimaryClip().getItemAt(0);
                txtLink5.setText(item.getText());

                break;

            default:

                break;
        }
    }

// ************************************************ DOWNLOAD LINKS WEB SERVICE CALLER ************************************************
// ***********************************************************************************************************************************

    class DownloadLinks extends AsyncTask<String, String, String>

    {
        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainScreenActivity.this);
            pDialog.setMessage("Downloading Links...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            // here Check for success tag
            //int success;

            String IDUser = GlobalVars.GVUserID;

            try
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("IDUser", IDUser));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(DOWNLOADLINKS_URL, "POST", params);

                final String Link1 = json.getString(TAG_LINK1);
                final String Link2 = json.getString(TAG_LINK2);
                final String Link3 = json.getString(TAG_LINK3);
                final String Link4 = json.getString(TAG_LINK4);
                final String Link5 = json.getString(TAG_LINK5);

                // checking  log for json response
                Log.d("Login attempt", json.toString());

                // success tag for json
                int success = json.getInt(TAG_SUCCESS);

                switch (success)
                {
                    case 1:

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                txtLink1.setText(Link1);
                                txtLink2.setText(Link2);
                                txtLink3.setText(Link3);
                                txtLink4.setText(Link4);
                                txtLink5.setText(Link5);
                            }
                        });

//                        txtLink1.setText(Link1);
//                        txtLink2.setText(Link2);
//                        txtLink3.setText(Link3);
//                        txtLink4.setText(Link4);
//                        txtLink5.setText(Link5);

                        break;

                    case 0:

                        return json.getString(TAG_MESSAGE);

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
                Toast.makeText(MainScreenActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

// ************************************************ UPLOAD LINKS WEB SERVICE CALLER ************************************************
// *********************************************************************************************************************************

    class UploadLinks extends AsyncTask<String, String, String>
    {
        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainScreenActivity.this);
            pDialog.setMessage("Uploading Links...");
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

            String IDUser = GlobalVars.GVUserID;
            String Link1 = txtLink1.getText().toString();
            String Link2 = txtLink2.getText().toString();
            String Link3 = txtLink3.getText().toString();
            String Link4 = txtLink4.getText().toString();
            String Link5 = txtLink5.getText().toString();

            try
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("IDUser", IDUser));
                params.add(new BasicNameValuePair("Link1", Link1));
                params.add(new BasicNameValuePair("Link2", Link2));
                params.add(new BasicNameValuePair("Link3", Link3));
                params.add(new BasicNameValuePair("Link4", Link4));
                params.add(new BasicNameValuePair("Link5", Link5));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(UPLOADLINKS_URL, "POST", params);

                // checking  log for json response
                Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);

                if (success == 1)
                {
                    return json.getString(TAG_MESSAGE);
                }
                else
                {
                    return json.getString(TAG_MESSAGE);
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
                Toast.makeText(MainScreenActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
