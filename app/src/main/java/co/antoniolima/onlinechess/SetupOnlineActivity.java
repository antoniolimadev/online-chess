package co.antoniolima.onlinechess;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static co.antoniolima.onlinechess.Constants.PORT;
import static co.antoniolima.onlinechess.Constants.SERVER;

public class SetupOnlineActivity extends AppCompatActivity {

    GameController gameController;
    TextView whatAmI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_online);
        gameController = (GameController) getApplication();

        whatAmI = findViewById(R.id.textViewWhatAmI);
        if (gameController.isWhat() == SERVER){
            whatAmI.setText("SERVER");
        } else {
            whatAmI.setText("CLIENTE");
        }

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {

            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameController.isWhat() == SERVER) {
            this.serverDlg();

        } else  // CLIENT
            this.clientDlg();
    }

    public void serverDlg() {

        ProgressDialog pd;
        String ip = gameController.getLocalIpAddress();
        pd = new ProgressDialog(this);
        pd.setMessage("Waiting for a client..." + "\n(IP: " + ip + ")");
        pd.setTitle("CHESSS (SERVER)");

        //setOnCancel é chamado sempre que é feito um back, ou o um toque fora da dialog box
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        pd.show();

        gameController.runServer();
    }

    public void clientDlg() {
        final Context context = this;
        final EditText edtIP = new EditText(this);

        edtIP.setText("192.168.1.143");
        //edtIP.setText("10.0.2.15");

        AlertDialog ad = new AlertDialog.Builder(context).setTitle("CHESSS (CLIENTE)")
                .setMessage("Server IP").setView(edtIP)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        gameController.runClient(edtIP.getText().toString(), 8899);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).create();
        ad.show();


    }
}
