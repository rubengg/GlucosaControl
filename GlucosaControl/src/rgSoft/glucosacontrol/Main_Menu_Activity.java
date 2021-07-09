package rgSoft.glucosacontrol;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Main_Menu_Activity extends Activity{
	
	private float points[];
	private String dates[];
	private DataBase database;
	private SQLiteDatabase db;
	private Cursor result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_activity);
		
		database = new DataBase(this, "GlucosaData", null, 1);
		db = database.getReadableDatabase();
		
		//BOTON AGREGAR CONTROL		
		findViewById( R.id.btCrtlAdd ).setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarControlActivity();
			}
		} );
		
		//BOTON VER CONTROLES
		findViewById( R.id.btShowCtrl ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				lanzarShowControlActivity();
				
			}
		} );
		
		//BOTON GRAFICAS
		findViewById( R.id.btGraph ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
							
				String sql;
				
				
				int maxid = 0;
				result = db.rawQuery("SELECT max(idregistros) FROM registros", null);
				if(result.moveToNext()){
				maxid = result.getInt(0);
				
				points = new float[maxid];
				dates = new String[maxid];
				
				sql = "SELECT mgPorDl,fechaHora FROM registros";
				result = db.rawQuery(sql, null);
				
					if(result.moveToNext()){
						int i = 0;
						points[i] = result.getFloat(0);
						dates[i] = result.getString(1).substring(0, 3)+ getMonth( Integer.parseInt( result.getString(1).substring(4, 5) ));
						//dates[i] = result.getString(1).substring(0, 3)+ getMonth( Integer.parseInt( result.getString(1).substring(4, 5) ))+"-"+result.getString(1).substring(6);
						i++;
						for( ; result.moveToNext() ; i++ ){
							points[i] = result.getFloat(0);
							//dates[i] = result.getString(1).substring(0, 3)+ getMonth( Integer.parseInt( result.getString(1).substring(4, 5) ))+"-"+result.getString(1).substring(6);
							dates[i] = result.getString(1).substring(0, 3)+ getMonth( Integer.parseInt( result.getString(1).substring(4, 5) ));
							//dates[i] = result.getString(1);
						}
						lanzarGraphActivity();
					}else Toast.makeText(getApplicationContext(), "Aun no se han capturado controles", Toast.LENGTH_SHORT).show();
				}
				else Toast.makeText(getApplicationContext(), "Aun no se han capturado controles", Toast.LENGTH_SHORT).show();
				
			}
		} );
		/*
		//BOTON CONFIGURACIONES
		findViewById( R.id.btSettings ).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				lanzarSettingsActivity();
				
			}
		} );*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent  event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	finish();
        }
        return true;
	}
	
	private void lanzarControlActivity(){ 
		startActivity( new Intent(this,Control_Activity.class) ); }

	private void lanzarShowControlActivity(){ 
		Intent i = new Intent( this, ShowControlsActivity.class );
		startActivity(i);
	}
	
	private void lanzarGraphActivity(){
		Intent i = new Intent(this, Graphics_Activity.class);
		//Intent i = new Intent( this, MultitouchAndroidplotActivity.class );
		i.putExtra("puntos", points);
		i.putExtra("fechas", dates);
		startActivity( i );
	}
	
	private String getMonth(int m){
		if( m == 1 ) return "Ene";
		else if( m == 2 ) return "Feb";
		else if( m == 3 ) return "Mar";
		else if( m == 4 ) return "Abr";
		else if( m == 5 ) return "May";
		else if( m == 6 ) return "Jun";
		else if( m == 7 ) return "Jul";
		else if( m == 8 ) return "Ago";
		else if( m == 9 ) return "Sep";
		else if( m == 10 ) return "Oct";
		else if( m == 11 ) return "Nov";
		//else if( m == 12 ) return "Dic";
		else return "Dic";
	}
	
	//private void lanzarSettingsActivity(){ Toast.makeText(getApplicationContext(), "No implementado", Toast.LENGTH_SHORT).show(); }
}
