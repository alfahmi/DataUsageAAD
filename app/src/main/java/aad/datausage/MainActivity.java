package aad.datausage;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.*;
import java.net.*;
import java.io.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.content.*;
import android.net.*;

public class MainActivity extends Activity {
	ProgressBar pb;
	Button b;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
	
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	//							WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alfahmi__main);
		b = (Button) findViewById(R.id.b1);
        b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HapusFile();
					mulaiDownload();
				}
			});
		alfahmiPengaturan();
		TextView title = (TextView)findViewById(R.id.alfahmi__title_text);
		TextView subtitle = (TextView)findViewById(R.id.alfahmi__subtitle_text);
		subtitle.setText("Server");
		SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE);
		String utama = sharedPreferences.getString("KodeAkses","Masuk");
		String kedua  = sharedPreferences.getString("username","AAD");
		String otomatisDownload = sharedPreferences.getString("autoDownload","mati");
		String otomatisCek = sharedPreferences.getString("autoCheck","mati");
		if ("Alfahmi096@AAD".equals(utama) && "TDC.AWG".equals(kedua)) {
			title.setText("AWG");
		} else if ("Alfahmi096@AAD".equals(utama) && "TDC.KNG".equals(kedua)) {
			title.setText("KNG");
		} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.CKJ".equals(kedua)) {
			title.setText("CKJ");
		} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.MJK".equals(kedua)) {
			title.setText("MJK");
		} else {

			Intent a = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(a);
			finish();

		}
		
		
		if ("hidup".equals(otomatisCek)) {
			CekKuota();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException ie) {
				// Handle exeption
			}
			
		} else {
			
			// Tidak ada Act
		}
		if ("hidup".equals(otomatisDownload))
		{
			b = (Button) findViewById(R.id.b1);
			b.setText("DOWNLOAD ULANG");
			Toast.makeText(getApplicationContext(), "Mulai menyedot kuota", Toast.LENGTH_LONG).show();
			HapusFile();
			mulaiDownload();
			
			
		} 
		else {
			// Tidak Ada Act.
			b = (Button) findViewById(R.id.b1);
			b.setText("DOWNLOAD");
		}
		
    }

    void downloadFile(){
		
		
		try {
		String alfahmiserver="";
			SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE);
		String utama = sharedPreferences.getString("KodeAkses","Masuk");
		String kedua  = sharedPreferences.getString("username","AAD");
			
		if ("Alfahmi096@AAD".equals(utama) && "TDC.AWG".equals(kedua)) {
			alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/awg_aad.zip";
		} else if ("Alfahmi096@AAD".equals(utama) && "TDC.KNG".equals(kedua)) {
			alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/kng_aad.zip";
		} else if ("Alfahmi096@AAD".equals(utama) && "TDC.CKJ".equals(kedua)) {
			alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/ckj_aad.zip";
		} else if ("Alfahmi096@AAD".equals(utama) && "TDC.MJK".endsWith(kedua)) {
			alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/mjk_aad.zip";
		} else {

		}

			
		
    		URL url = new URL(alfahmiserver);
    		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    		urlConnection.setRequestMethod("GET");
    		urlConnection.setDoOutput(true);

    		//connect
    		urlConnection.connect();

    		//set the path where we want to save the file    		
    		File SDCardRoot = Environment.getExternalStorageDirectory(); 
    		//create a new file, to save the downloaded file 
    		File file = new File(SDCardRoot,"aaddatausage.alf");

    		FileOutputStream fileOutput = new FileOutputStream(file);

    		//Stream used for reading the data from the internet
    		InputStream inputStream = urlConnection.getInputStream();

    		//this is the total size of the file which we are downloading
    		totalSize = urlConnection.getContentLength();

    		runOnUiThread(new Runnable() {
					public void run() {
						pb.setMax(totalSize);
					}			    
				});

    		//create a buffer...
    		byte[] buffer = new byte[1024];
    		int bufferLength = 0;

    		while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
    			fileOutput.write(buffer, 0, bufferLength);
    			downloadedSize += bufferLength;
    			// update the progressbar //
    			runOnUiThread(new Runnable() {
						public void run() {
							pb.setProgress(downloadedSize);
							float per = ((float)downloadedSize/totalSize) * 100;
					
							cur_val.setText("Downloaded \n" + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
						}
					});
    		}
    		//close the output stream when complete //
    		fileOutput.close();
			HapusFile();
    		runOnUiThread(new Runnable() {
					public void run() {
						dialog.dismiss();	
						Toast.makeText(getBaseContext(),"Download Selesai.",Toast.LENGTH_SHORT).show();
						SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE);
						String kaluar = sharedPreferences.getString("autoExit","mati");
						String cekOto = sharedPreferences.getString("autoCheck","mati");
						if ("hidup".equals(cekOto)) {
							CekKuota();
						} else {
							
						}
						if ("hidup".equals(kaluar)) {
							finish();
						} else {
							
						}
					}
				});    		

    	} catch (final MalformedURLException e) {
    		showError("Error : MalformedURLException " + e);  		
    		e.printStackTrace();
    	} catch (final IOException e) {
    		showError("Error : IOException " + e);  		
    		e.printStackTrace();
    	}
    	catch (final Exception e) {
    		showError("Error : Please check your internet connection " + e);
    	}    	
    }

    void showError(final String err){
    	runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(MainActivity.this, err, Toast.LENGTH_LONG).show();
					HapusFile();
				}
			});
    }

    void showProgress(String file_path){
    	dialog = new Dialog(MainActivity.this);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.alfahmi__progress_dialog);
    	dialog.setTitle("Download Progress");
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    	TextView text = (TextView) dialog.findViewById(R.id.tv1);
    	
    	cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
    	
    	dialog.show();

    	pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
    	pb.setProgress(0);
    	pb.setProgressDrawable(getResources().getDrawable(R.drawable.alfahmi__progress_bar));
		pb.setMax(100);
		cur_val.setText("Starting download...");
		text.setText("Downloading file from ... " + "\n" + "Alfahmi096 Server, please Wait...");
    }

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		HapusFile();
		SharedPreferences sharedPreferences = getSharedPreferences("Alfahmi",Context.MODE_PRIVATE);
		String otomatisCek = sharedPreferences.getString("autoCheck","mati");
		if ("hidup".equals(otomatisCek)) {
			CekKuota();
		}
		else {
			// Tidak ada Act
		}
		this.finish();
	}
	
	public void HapusFile()
	 {
		 File SDCardRoot = Environment.getExternalStorageDirectory(); 
		 File filem = new File(SDCardRoot, "aaddatausage.alf");
		 filem.delete();
	 }
	 
	 public void CekKuota()
	 {
		 String ussd = Uri.encode("*889#");
		 Intent callIntent = new Intent(Intent.ACTION_CALL);  
		 callIntent.setData(Uri.parse("tel:"+ussd));  
		 startActivity(callIntent);
		
	 }
	 
	 public void mulaiDownload()
	 {
		 String alfahmiserver = "";
		 SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE);
		 String utama = sharedPreferences.getString("KodeAkses","Masuk");
		 String kedua  = sharedPreferences.getString("username","AAD");

		 if ("Alfahmi096@AAD".equals(utama) && "TDC.AWG".equals(kedua)) {
			 alfahmiserver = "https://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/awg_aad.zip";
		 } else if ("Alfahmi096@AAD".equals(utama) && "TDC.KNG".equals(kedua)) {
			 alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/kng_aad.zip";
		 } else if ("Alfahmi096@AAD".equals(utama) && "TDC.CKJ".equals(kedua)) {
			 alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/ckj_aad.zip";
		 } else if ("Alfahmi096@AAD".equals(utama) && "TDC.MJK".endsWith(kedua)) {
			 alfahmiserver = "http://raw.githubusercontent.com/alfahmi/DataUsageAAD/master/server/alfahmi/mjk_aad.zip";
		 } else {

		 }
		 showProgress(alfahmiserver);
		 new Thread(new Runnable() {
				 public void run() {
					 downloadFile();
				 }
			 }).start();
	 }
	 
	 public void alfahmiPengaturan() {
		 final Context context = this;
		 ImageButton mainButton = (ImageButton) findViewById(R.id.alfahmi__pengaturan);
		 mainButton.setOnClickListener(new OnClickListener() {

				 @Override
				 public void onClick(View arg0) {
					 Intent intent = new Intent(context, SettingsActivity.class);
					 startActivity(intent);
					 finish();

				 }
			 });
		 
	 }
	 


}
