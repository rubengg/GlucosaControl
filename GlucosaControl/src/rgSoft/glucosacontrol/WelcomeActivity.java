package rgSoft.glucosacontrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

public class WelcomeActivity extends Activity {

	private DataBase database;
	private SQLiteDatabase db;
	private Cursor result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		database = new DataBase(this, "GlucosaData", null, 1);
		db = database.getReadableDatabase();
		
		findViewById( R.id.btStart ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if( db != null ){
					result = db.rawQuery("select nombres from pacientes", null);
					if( result.moveToNext() ) lanzarLogin();//Toast.makeText(getApplicationContext(), "se encontraron datos", Toast.LENGTH_SHORT).show();
					else lanzarRegMedico();
				}

			}
		} );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent  event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	finish();
        }
        return true;
	}
	
	private void lanzarRegMedico(){
		Intent i = new Intent(this, Reg_Medico_Activity.class);
		startActivity(i);
		//Intent i = new Intent( this,  );
	}
	private void lanzarLogin(){
		Intent i = new Intent(this, login_activity.class);
		startActivity(i);
	}
}
