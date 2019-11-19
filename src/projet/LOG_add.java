package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class LOG_add extends JPanel {
	
	private JTextField userField;
	private JPasswordField passField;
	private JComboBox<String>	default_db , role_server ;
	
	public LOG_add(){
		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay ); 
    	
    	add(new JLabel( Lang.getWord("Nom")+" "+Lang.getWord("Log in"))); 
    	userField = new JTextField(20) ;
	    	add( userField , "span 3 , pushx , growx "); 
	    

	    add(new JLabel(Lang.getWord("Log in")		+" "+	Lang.getWord("Mot de pass") )); 
	    passField = new JPasswordField(20) ;
		   	add( passField , "span 3 , pushx , growx "); 
		
		add(new JLabel( Lang.getWord("Par défaut")+" "+Lang.getWord("Base de donneé") ) ); 
		default_db = createAll(); 
			   	add( default_db , "span 3 , pushx , growx "); 
	   
			   	role_server	 = new JComboBox<String>();
	    role_server.addItem("public");
	    role_server.addItem("dbcreator");
	    role_server.addItem("diskadmin");
	    role_server.addItem("processadmin");
	    role_server.addItem("securityadmin");
	    role_server.addItem("setupadmin");
	    role_server.addItem("sysadmin");
	    
	    	add( new JLabel( Lang.getWord("Rôle")+" "+Lang.getWord("Serveur") )); 
	    	add( role_server , "span 3 , pushx , growx "); 
	    	
		JButton add = new JButton( Lang.getWord("Création") );
		add.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
			String Query ="create login ";
			if( ! userField.getText().trim().isEmpty() ){ // champ not vide 
				Query+=  userField.getText().trim() ;
				
				if( ! String.valueOf( passField.getPassword() ).trim().isEmpty()  ){  // champ PASSWORD not vide 
					Query+="	WITH PASSWORD =\'" + String.valueOf( passField.getPassword() ).trim() + "\' , " ;
					Query+="DEFAULT_DATABASE = "+default_db.getSelectedItem()+" , " ; 
					Query+="DEFAULT_LANGUAGE = [us_english]  , " ;
					Query+="CHECK_EXPIRATION=OFF , ";
					Query+="CHECK_POLICY=ON \n";
					
					DataBase obj = new DataBase("");
					obj.Update( Query ); Main.echo("Exe first update");
							Query +="ALTER LOGIN "+ userField.getText().trim() +" DISABLE \n";
							
					obj.Update(" ALTER LOGIN "+ userField.getText().trim() +" DISABLE " ); Main.echo("Exe seconde update");
						   Query +="ALTER SERVER ROLE ["+ role_server.getSelectedItem() +"] ADD MEMBER "+userField.getText().trim()+"\n" ;
					obj.Update("ALTER SERVER ROLE ["+ role_server.getSelectedItem() +"] ADD MEMBER "+userField.getText().trim() ); Main.echo("Exe Third update");
						   Query += "ALTER LOGIN "+userField.getText().trim()+" ENABLE" ;
					obj.Update("ALTER LOGIN "+userField.getText().trim()+" ENABLE");
					Tail.setSQL( Query );
					Tail.setINF(Lang.getWord("Création")+" "+Lang.getWord("Log in")+"\n"+
							Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
					 
				}else{
					Tail.setError( Lang.getWord("Champ")+" "+Lang.getWord("Mot de pass")+" "+Lang.getWord("Est Vide"));
				}
			}else{
				Tail.setError( Lang.getWord("Champ")+" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide"));
			}
			}
		});
		add( add , "cell 3 4"); 
	}
	
	private JComboBox<String> createAll(){
		JComboBox<String> a = new  JComboBox<String>();
		DataBase obj = new DataBase("");
		obj.Execute("Select name from sys.databases ",true);
		try{
		while( obj.getResult().next() ){
			a.addItem( obj.getResult().getString("name") );
		}
		}catch(Exception e){
			
		}
		return a;
		
	}
}
