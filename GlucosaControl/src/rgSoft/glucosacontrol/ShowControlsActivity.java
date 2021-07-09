package rgSoft.glucosacontrol;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowControlsActivity extends Activity {
	
	private ListView lista;
	
	private DataBase database;
	private SQLiteDatabase db;
	private Cursor result;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);

		database = new DataBase(this, "GlucosaData", null, 1);
		
		db = database.getReadableDatabase();
		
		String sql = "SELECT mgPorDl,fechaHora FROM registros";
		
		result = db.rawQuery(sql, null);
		        
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();  
        //70 110
        while( result.moveToNext() ){
        	if( result.getFloat(0) > 110 || result.getFloat(0) < 70 )
        	datos.add(new Lista_entrada(R.drawable.gotatriste, result.getString(0)+" mg/dl" , "Fecha y hora: "+result.getString(1)));
        	else datos.add(new Lista_entrada(R.drawable.gotafeliz, result.getString(0)+" mg/dl" , "Fecha y hora: "+result.getString(1))); 
        }

        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
		        if (entrada != null) {
		            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
		            if (texto_superior_entrada != null) 
		            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima()); 
		              
		            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
		            if (texto_inferior_entrada != null)
		            	texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); 
		              
		            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            if (imagen_entrada != null)
		            	imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
		        }
			}
		});
        
        lista.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion); 
                
                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
                Toast toast = Toast.makeText(ShowControlsActivity.this, texto, Toast.LENGTH_LONG);
                toast.show();
			}
        });
        
        
        
    }
    
}

