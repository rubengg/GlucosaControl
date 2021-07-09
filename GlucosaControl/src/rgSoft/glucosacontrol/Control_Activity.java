package rgSoft.glucosacontrol;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Control_Activity extends Activity{
	
	private DataBase database;
	private SQLiteDatabase db;
	private Cursor result;
	
	private String date;
	private float mgdl;

	public void send(String msj,String phone){
				
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_activity);
		
		database = new DataBase(this, "GlucosaData", null, 1);
		
		findViewById( R.id.btCrtlAdd ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		 
				date = ( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss") ).format( (Calendar.getInstance() ).getTime() );
				
				try{
				mgdl =  Float.parseFloat(  ( (EditText) findViewById(R.id.editCtrlMgdl) ).getText().toString() );
				
				String sql =" INSERT INTO registros (mgPorDl,fechaHora,idpacientes) VALUES ("+mgdl+",'"+date+"',1); ";
				//Toast.makeText(getApplicationContext(), sql, Toast.LENGTH_LONG).show();
				db = database.getWritableDatabase();
				db.execSQL(sql);
				db.close();
				
				String  phone,
						email,
						name = null;

				if( mgdl<70 ){					
					Toast.makeText(getApplicationContext(), "Glucosa Baja", Toast.LENGTH_SHORT).show();
					
					db = database.getReadableDatabase();
					result = db.rawQuery("SELECT telefono,eMail FROM medicos", null);
					if( result.moveToNext() ){
						
						phone = result.getString(0);
						email = result.getString(1);
						result = db.rawQuery("SELECT nombres,aPaterno,aMaterno FROM pacientes", null);
						if(result.moveToNext())						
						name = result.getString(0)+" "+result.getString(1)+" "+result.getString(2);
						
						/****ENVIO DE CORREO ELECTRONICO****/						
						sendmail(email, name+" ha presentado un nivel de glucosa bajo","Su paciente "+name+" presento un nivel de glucosa de "+mgdl+" mg/dl, el dia "+date);
						
						/******ENVIO DE SMS******/
						Toast.makeText(getApplicationContext(), "Se esta intentando enviar un sms a "+phone, Toast.LENGTH_LONG).show();
						sendsms( phone,name+" ha presentado un nivel de glucosa bajo" );
					}
					

				}else if( mgdl>110 ){
					Toast.makeText(getApplicationContext(), "Glucosa Alta", Toast.LENGTH_SHORT).show();
					
					db = database.getReadableDatabase();
					result = db.rawQuery("SELECT telefono,eMail FROM medicos", null);
					if( result.moveToNext() ){

						phone = result.getString(0);
						email = result.getString(1);
						
						result = db.rawQuery("SELECT nombres,aPaterno,aMaterno FROM pacientes", null);
						if(result.moveToNext())
							name = result.getString(0)+" "+result.getString(1)+" "+result.getString(2);
						
						/*****ENVIO DE CORREO ELECTRONICO*****/
						sendmail(email, name+" ha presentado un nivel de glucosa alto","Su paciente "+name+" presento un nivel de glucosa de "+mgdl+" mg/dl, el dia "+date);
						
						/*****ENVIO DE SMS*****/
						Toast.makeText(getApplicationContext(), "Se esta intentando enviar un sms a "+phone, Toast.LENGTH_LONG).show();
						sendsms( phone,name+" ha presentado un nivel de glucosa alto" );
						
						//SmsManager smsmanager = SmsManager.getDefault();
						//sms.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent)
						//smsmanager.sendTextMessage(phone, null, "Se ha detectado un nivel alto de glucosa", null, null);
						//sms.sendSMS("Se ha detectado un nivel bajo de glucosa", phone);
					}
					//	sms.sendSMS("Se ha detectado un nivel alto de glucosa", result.getString(0));
				}			
				db.close();
				lanzarMainMenu();
				
				}catch(Exception ex){ Toast.makeText(getApplicationContext(), "No es un valor valido", Toast.LENGTH_SHORT).show();}
			}
		} );
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	private void lanzarMainMenu(){ startActivity( new Intent(this,Main_Menu_Activity.class) ); }
	
	private void sendsms(String phone,String msj){
		SmsManager smsmanager = SmsManager.getDefault();
		smsmanager.sendTextMessage(phone, null, msj, null, null);
	}
	
	private void sendmail(String destine, String subject, String body){
		/* es necesario un intent que levante la actividad deseada */
        Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
                        
        /* vamos a enviar texto plano a menos que el checkbox est� marcado */
        itSend.setType("plain/text");
                        
        /* colocamos los datos para el env�o */
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ destine });                            
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, subject );
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, body );
        /* iniciamos la actividad */
        startActivity(itSend);

	}

}
