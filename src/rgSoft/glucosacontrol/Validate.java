package rgSoft.glucosacontrol;

public class Validate {
		
	public Validate(){}
	
	public boolean validateName(String name){
		String subname = name.toLowerCase();
		int space = 0;
		if(subname.trim().length() == 0) return false;
		else{
			for( int i = 0 ; i < subname.length() ; i++){
				if( subname.charAt(i) != ' ' ){
					if( subname.charAt(i) < 'a' || subname.charAt(i) > 'z')
						return false;			
				}else{
					space++;
					if( space > 1 ) return false;
					}
			}
		}
		return true;
	}
	
	public boolean validatePhone(String phone){
		if(phone.trim().length() == 0) return false;
		else if(phone.trim().length() != 10) return false;
		else{
			for( int i = 0 ; i < phone.trim().length() ; i++)
				if( phone.charAt(i) < '0' && phone.charAt(i) > '9' ) return false;
		}
		
		return true;
	}
	
	public boolean validateMail(String mail){
		String server ="";
		String submail = mail.trim().toLowerCase();
		boolean flag = false;
		int index = 0;
		int arrobas = 0;
		if( submail.length() == 0 ) return false;
		else{
			for( int i = 0 ; i < submail.length() ; i++ ){
				if( submail.charAt(i) == '@' ){ flag = true; index = i; arrobas++; break; }
			}
			
			if( flag && arrobas == 1){
				server = mail.substring(index+1);
				if( server.equals("hotmail.com") );
				else if( server.equals("gmail.com") );
				else if( server.equals("live.com") );
				else if( server.equals("live.com.mx") );
				else if( server.equals("yahoo.com") );
				else if( server.equals("yahoo.com.mx") );
				else if( server.equals("cs.buap.mx") );
				//else if( server.equals("cs.buap.mx") );
				else return false;
			}
			else return false;
			
		}
		
		return true;		
	}
}
