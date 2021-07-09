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

public class Reg_Medico_Activity extends Activity{
	

	private DataBase database;
	private SQLiteDatabase db;
	
	private String name,
		   fLastName,
		   sLastName,
		   eMail,
		   phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_medico_activity);
		
		database = new DataBase(this, "GlucosaData", null, 1);
		db = database.getWritableDatabase();
		
		findViewById(R.id.btAcept).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				name = ( (EditText) findViewById(R.id.editDrName) ).getText().toString();
				fLastName = ( (EditText) findViewById(R.id.editDr2LastName) ).getText().toString();
				sLastName = ( (EditText) findViewById(R.id.editDr1LastName) ).getText().toString();
				eMail = ( (EditText) findViewById(R.id.editDrMail) ).getText().toString();
				phone = ( (EditText) findViewById(R.id.editDrPhone) ).getText().toString();
				
				Validate validate = new Validate();
				
				if( !validate.validateName(name) ) Toast.makeText(getApplicationContext(), name+" no es un nombre valido", Toast.LENGTH_SHORT).show();
				else if( !validate.validateName(fLastName) ) Toast.makeText(getApplicationContext(), fLastName+" no es un apellido valido", Toast.LENGTH_SHORT).show();
				else if( !validate.validateName(sLastName) )  Toast.makeText(getApplicationContext(), sLastName+" no es un apellido valido", Toast.LENGTH_SHORT).show();
				else if( !validate.validateMail(eMail) )  Toast.makeText(getApplicationContext(), eMail+" no es un correo electronico valido", Toast.LENGTH_SHORT).show();
				else if( !validate.validatePhone(phone) )  Toast.makeText(getApplicationContext(), phone+" no es un numero telefonico valido", Toast.LENGTH_SHORT).show();
				else{
					String sql = " INSERT INTO medicos (nombres,aPaterno,aMaterno,eMail,telefono) VALUES ('"+name+"','"+fLastName+"','"+sLastName+"','"+eMail+"','"+phone+"') ";
					db.execSQL(sql);
					lanzaRegPaciente();
				}

				//Toast.makeText(getApplicationContext(), sql , Toast.LENGTH_LONG).show();

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
        	Toast.makeText(getApplicationContext(), "No se ha completado el registro", Toast.LENGTH_SHORT).show();
        }
        return true;
	}
	
	private void lanzaRegPaciente(){
		
		Intent i = new Intent(this, Reg_Paciente_Activity.class );
		startActivity(i);
		
	}

}
