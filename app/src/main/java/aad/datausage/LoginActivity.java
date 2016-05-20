package aad.datausage;


import android.app.*;
import android.content.*;
import android.content.SharedPreferences.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.text.*;
import android.widget.CompoundButton.*;
import android.text.method.*;
import android.webkit.*;

public class LoginActivity extends Activity implements OnClickListener {


	EditText editText;
	EditText editText2;
	Button button;
	CheckBox cekbox;
	LinearLayout LayoutLogin;
	LinearLayout LayoutDesc;
	LinearLayout LayoutMargin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alfahmi__login);
		
		getWindow().setBackgroundDrawableResource(R.color.alfahmi__black);
		
		LayoutLogin = (LinearLayout) findViewById(R.id.alfahmi__layour_login);
		LayoutDesc = (LinearLayout) findViewById(R.id.alfahmi__layour_desc);
		LayoutMargin = (LinearLayout)findViewById(R.id.alfahmi__margin);
		
		WebView wv = (WebView) findViewById(R.id.alfahmi__id_keterangan);
		wv.setBackgroundColor(android.R.color.transparent);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl("file:///android_asset/about.html");
		
		LayoutDesc.setVisibility(View.GONE);
		LayoutMargin.setVisibility(View.GONE);
		
		CheckBox cb = (CheckBox) findViewById(R.id.alfahmi__aad_checkbox);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (!isChecked) {
						LayoutLogin.setVisibility(View.VISIBLE);
						LayoutDesc.setVisibility(View.GONE);
						LayoutMargin.setVisibility(View.GONE);
						
					} else {
						LayoutLogin.setVisibility(View.GONE);
						LayoutDesc.setVisibility(View.VISIBLE);
						LayoutMargin.setVisibility(View.VISIBLE);
					}
				}
			});
		ImageView mImageView = (ImageView)findViewById(R.id.alfahmi__aad_logo);
		mImageView.setColorFilter(getResources().getColor(R.color.alfahmi__red), android.graphics.PorterDuff.Mode.MULTIPLY);
		editText = (EditText) findViewById(R.id.kodeakses);
		editText2 = (EditText) findViewById(R.id.username);
		cekbox = (CheckBox) findViewById(R.id.showhidepass);
		cekbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
				} else {
					editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
			}
		});
		
		button = (Button) findViewById(R.id.login);
		button.setOnClickListener(this);
		loadSavedPreferences();
		loadSavedPreferences2();
		
	}

	private void loadSavedPreferences() {
		SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
		String name = sp.getString("KodeAkses", "");

		editText.setText(name);
	}
	private void loadSavedPreferences2() {
		SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
		String name2 = sp.getString("Username", "");

		editText2.setText(name2);
	}
	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	@Override
	public void onClick(View v) {
		savePreferences("KodeAkses", editText.getText().toString());
		savePreferences("username", editText2.getText().toString());
		Intent settingsIntent = new Intent();
		settingsIntent.setClass(this, LoadingActivity.class);
		startActivity(settingsIntent);

		finish();
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		this.finish();
	}

}
