package rgSoft.glucosacontrol;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login_activity extends Activity{
	
	private DataBase database;
	private SQLiteDatabase db;
	private Cursor result;
	
	private String usrName,
				   passwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	
		database = new DataBase(this, "GlucosaData", null, 1);	
		
		//DATOS DE PRUEBA
		//((EditText) findViewById( R.id.editLogUsrName )).setText("josel");
		//((EditText) findViewById( R.id.editLogPasswd )).setText("123456");
		
		findViewById( R.id.btLogStart ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				usrName = ( (EditText) findViewById( R.id.editLogUsrName ) ).getText().toString();
				passwd = ( (EditText) findViewById( R.id.editLogPasswd) ).getText().toString();
				
				String sql = "SELECT userName,passwd FROM pacientes";
				
				db = database.getReadableDatabase();
				result = db.rawQuery(sql, null);
				
				if(result.moveToNext()){
					//Toast.makeText(getApplicationContext(), "user: "+result.getString(0)+" pass: "+result.getString(1), Toast.LENGTH_LONG).show();
					if(result.getString(0).equals(usrName) && result.getString(1).equals(passwd))
						lanzaMainMenu();
						//Toast.makeText(getApplicationContext(), "correcto", Toast.LENGTH_LONG).show();
					else Toast.makeText(getApplicationContext(), "El usuario o la contraseña no son validos", Toast.LENGTH_LONG).show();					
				}
				db.close();
			}
		} );
		
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	public void lanzaMainMenu(){
		
		Intent i = new Intent( this, Main_Menu_Activity.class );
		startActivity( i );
		
	}
	
}
