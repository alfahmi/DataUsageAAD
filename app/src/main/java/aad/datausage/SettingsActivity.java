package aad.datausage;

import android.content.*;
import android.os.*;
import android.preference.*;
import android.preference.Preference.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;



public class SettingsActivity extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.alfahmi__settings);
		addPreferencesFromResource(R.xml.alfahmi__settings);
		final Context context = this ;
		ImageButton pangaturan = (ImageButton)findViewById(R.id.alfahmi__logout);
		TextView title = (TextView)findViewById(R.id.alfahmi__title_text);
		TextView subtitle = (TextView)findViewById(R.id.alfahmi__subtitle_text);
		subtitle.setText("Settings");
		pangaturan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					SharedPreferences sharedPreferences = getSharedPreferences("aad.datausage_preferences",Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("KodeAkses", "");
					editor.commit();
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
					finish();
				}
			});
		String utama = sharedPreferences.getString("KodeAkses","Masuk");
		String kedua  = sharedPreferences.getString("username","AAD");
		if ("Alfahmi096@AAD".equals(utama) && "TDC.AWG".equals(kedua)) {
			title.setText("AWG");
			pangaturan.setVisibility(View.VISIBLE);
		} else if ("Alfahmi096@AAD".equals(utama) && "TDC.KNG".equals(kedua)) {
			title.setText("KNG");
			pangaturan.setVisibility(View.GONE);
		} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.CKJ".equals(kedua)) {
			title.setText("CKJ");
			pangaturan.setVisibility(View.GONE);
		} else if ("Alfahmi096@AAD".equals(utama)  && "TDC.MJK".equals(kedua)) {
			title.setText("MJK");
			pangaturan.setVisibility(View.GONE);
		} 
		String download = sharedPreferences.getString("autoDownload","mati");
		((ListPreference)findPreference("autoDownload")).setSummary(download);
		((ListPreference)findPreference("autoDownload")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String type = (String.valueOf(newValue));
				Intent i = new Intent();
				i.putExtra("download",type);
				preference.setSummary(type);
				SharedPreferences sharedPreferences = getSharedPreferences("Alfahmi",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("autoDownload", type);
				editor.commit();
				return true;
			}
		});
	
	
		String check = sharedPreferences.getString("autoCheck","mati");
		((ListPreference)findPreference("autoCheck")).setSummary(check);
    	((ListPreference)findPreference("autoCheck")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String type = (String.valueOf(newValue));
				Intent i = new Intent();
				i.putExtra("check",type);
				preference.setSummary(type);
				SharedPreferences sharedPreferences = getSharedPreferences("Alfahmi",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("autoCheck", type);
				editor.commit();
				return true;
			}

		});
		
		String exit = sharedPreferences.getString("autoExit","mati");
		((ListPreference)findPreference("autoExit")).setSummary(exit);
    	((ListPreference)findPreference("autoExit")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					String type = (String.valueOf(newValue));
					Intent i = new Intent();
					i.putExtra("exit",type);
					preference.setSummary(type);
					SharedPreferences sharedPreferences = getSharedPreferences("Alfahmi",MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("autoExit", type);
					editor.commit();
					return true;
				}

			});
	
	}
	
		
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		final Context context = this ;
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	
}
