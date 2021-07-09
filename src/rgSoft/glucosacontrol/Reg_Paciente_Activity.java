package rgSoft.glucosacontrol;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Reg_Paciente_Activity extends Activity{

	private DataBase database;
	private SQLiteDatabase db;
	
	private String name,
	   fLastName,
	   sLastName,
	   usrName,
	   passwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_paciente_activity);
		
		database = new DataBase(this, "GlucosaData", null, 1);
		db = database.getWritableDatabase();
		
		findViewById( R.id.btPAcept ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_LONG).show();
				name = ( (EditText) findViewById(R.id.editPName) ).getText().toString();
				fLastName= ( (EditText) findViewById(R.id.editP1Name) ).getText().toString();
				sLastName = ( (EditText) findViewById(R.id.editP2Name) ).getText().toString();
				usrName = ( (EditText) findViewById(R.id.editPUsrName) ).getText().toString();
				passwd = ( (EditText) findViewById(R.id.editPPasswd) ).getText().toString();
				
				Validate validate = new Validate();
				if( !validate.validateName(name) ) Toast.makeText(getApplicationContext(), name+" no es un nombre valido", Toast.LENGTH_SHORT).show();
				else if( !validate.validateName(fLastName) ) Toast.makeText(getApplicationContext(), fLastName+" no es un apellido valido", Toast.LENGTH_SHORT).show();
				else if( !validate.validateName(sLastName) ) Toast.makeText(getApplicationContext(), sLastName+" no es un apellido valido", Toast.LENGTH_SHORT).show();
				else if( passwd.length() < 6 ) Toast.makeText(getApplicationContext(), "Se requieren minimo 6 caracteres para la contraseña", Toast.LENGTH_SHORT).show();
				else{
				String sql = " INSERT INTO pacientes (nombres,aPaterno,aMaterno,userName,passwd,idmedicos) VALUES ('"+name+"','"+fLastName+"','"+sLastName+"','"+usrName+"','"+passwd+"', 1) ";
				db.execSQL(sql);
				lanzarWelcomeActivity();
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
        	Toast.makeText(getApplicationContext(), "No se ha completado el registro", Toast.LENGTH_LONG).show();
        }
        return true;
	}
	
	private void lanzarWelcomeActivity(){
		
		Intent i = new Intent( this, WelcomeActivity.class );
		startActivity(i);
		
	}
	
}
