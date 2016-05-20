package aad.datausage;

import android.app.*;
import android.app.ProgressDialog;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class LoadingActivity extends Activity
{

	final Context context = this;
	private final int SPLASH_DISPLAY_LENGTH = 4000;
	ProgressDialog progressDoalog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alfahmi__loading);
		progressDoalog = new ProgressDialog(LoadingActivity.this);
		progressDoalog.setMax(100);
		progressDoalog.setMessage("Mohon Tunggu....");
		progressDoalog.setTitle("Memverifikasi.");
		progressDoalog.setCanceledOnTouchOutside(false);
		progressDoalog.setCancelable(false);
		progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDoalog.show();
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					/* Create an Intent that will start the Menu-Activity. */
					SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE);
					String utama = sharedPreferences.getString("KodeAkses","e");
					String kedua = sharedPreferences.getString("username","e");


					if ("Alfahmi096@AAD".equals(utama)  && "TDC.AWG".equals(kedua)) {
						Toast.makeText(getApplicationContext(), "Selamat Datang di AAD DataUsage.\n Anda Masuk Sebagai TDC AWG.", Toast.LENGTH_LONG).show();
						Intent a = new Intent(LoadingActivity.this, MainActivity.class);
						startActivity(a);
						finish();
					} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.KNG".equals(kedua)) {
						Toast.makeText(getApplicationContext(), "Selamat Datang di AAD DataUsage.\n Anda Masuk Sebagai TDC KNG.", Toast.LENGTH_LONG).show();
						Intent a = new Intent(LoadingActivity.this, MainActivity.class);
						startActivity(a);
						finish();
					} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.CKJ".equals(kedua)) {
						Toast.makeText(getApplicationContext(), "Selamat Datang di AAD DataUsage.\n Anda Masuk Sebagai TDC CKJ.", Toast.LENGTH_LONG).show();
						Intent a = new Intent(LoadingActivity.this, MainActivity.class);
						startActivity(a);
						finish();
					} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.MJK".equals(kedua)) {
						Toast.makeText(getApplicationContext(), "Selamat Datang di AAD DataUsage.\n Anda Masuk Sebagai TDC MKJ.", Toast.LENGTH_LONG).show();
						Intent b = new Intent(LoadingActivity.this, MainActivity.class);
						startActivity(b);
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "Maaf kode akses yang\nAnda masukan salah.", Toast.LENGTH_LONG).show();
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.putString("KodeAkses", "");
						editor.commit();
						Intent a = new Intent(LoadingActivity.this, MainActivity.class);
						startActivity(a);
						finish();

					}
				}
			}, SPLASH_DISPLAY_LENGTH);
	}

	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDoalog.incrementProgressBy(1);
		}
	};
	
        
  
}
