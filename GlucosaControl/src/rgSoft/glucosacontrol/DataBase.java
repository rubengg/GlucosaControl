package rgSoft.glucosacontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {


	public DataBase(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE medicos ("+
				  "idmedicos INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"+
				  "nombres TEXT,"+
				  "aPaterno TEXT,"+
				  "aMaterno TEXT,"+
				  "eMail TEXT NOT NULL ,"+
				  "telefono TEXT NOT NULL)");
		db.execSQL("CREATE TABLE pacientes ("+
				  "idpacientes INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"+
				  "nombres TEXT,"+
				  "aPaterno TEXT,"+
				  "aMaterno TEXT,"+
				  "userName TEXT NOT NULL ,"+
				  "passwd TEXT NOT NULL ,"+
				  "idmedicos INTEGER NOT NULL)");
		db.execSQL("CREATE TABLE registros ("+
				  "idregistros  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"+
				  "mgPorDl DECIMAL NOT NULL ,"+
				  "fechaHora DATETIME NOT NULL ,"+
				  "idpacientes INTEGER NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
