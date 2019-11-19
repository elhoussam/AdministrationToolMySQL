package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class LOG_mod extends JPanel {
	
	private JTextField userField = new JTextField(20);
	private JPasswordField passField = new JPasswordField(20);
	private JComboBox<String>	login = new JComboBox<String>() ;
	
	public LOG_mod(){
		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay ); 
    	
    	add(new JLabel( Lang.getWord("Sélectionner")+" "+Lang.getWord("Log in") )); 
    	LOG_del.create( login ) ;
	    	add( login , "span 2 , pushx , growx "); 
	    
	    JButton ref = new JButton( Lang.getWord("Actualiser") );
	    ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				login.removeAllItems();
				LOG_del.create( login );
				}
		});
	    add( ref  );
	    add(new JLabel( Lang.getWord("Nouveau")+" "+Lang.getWord("Nom")	));  
	    add( userField , "cell 1 1 , span 2 , pushx, growx ");
	    

	    add(new JLabel(Lang.getWord("Nouveau")+" "+ Lang.getWord("Mot de pass") ) , "cell 0 2");  
	    add( passField , "cell 1 2 ,span 2 , pushx, growx ");
	    
	    JButton mod = new JButton( Lang.getWord("Modifier") );
	    mod.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String query ="ALTER LOGIN "+	login.getSelectedItem();
				// tester si les champs est vide
				if( ! userField.getText().trim().isEmpty() && ! String.valueOf( passField.getPassword() ).trim().isEmpty() ){
					Tail.setError( 	Lang.getWord("Modifier")+" "+Lang.getWord("Nom")+" "+Lang.getWord("Et apres")
									+" "+Lang.getWord("Modifie")+" "+Lang.getWord("Mot de pass")
							 );
				}else{
					if(! userField.getText().trim().isEmpty() || ! String.valueOf( passField.getPassword() ).isEmpty()  ){
							DataBase obj = new DataBase("");
							if( ! userField.getText().trim().isEmpty() ) query+=" with name=\'"+userField.getText().trim()+"\'";
							if( ! String.valueOf( passField.getPassword() ).isEmpty() ) query+=" with password=\'"+String.valueOf( passField.getPassword() )+"\'";
							
							if( obj.Update(query) )
							Tail.setINF( 	Lang.getWord("Modifier")+" "+ 	Lang.getWord("Log in")+" "+	Lang.getWord("Information")+"\n"+
									Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
					}else{
						Tail.setError( Lang.getWord("Champ")+" "+ Lang.getWord("Mot de pass")+" | "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide"));
					}
			  }
			}
		});  
	    add( mod , "cell 3 2");
	    
	    JButton enable = new JButton(Lang.getWord("Active") + "/"+Lang.getWord("Disactive"));
	    enable.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				  String query ="select is_disabled from sys.server_principals where name=\'"+login.getSelectedItem()+"\'";
				  DataBase obj = new DataBase("");
				  obj.Execute(query,true);
				  try {
					  query = "ALTER LOGIN "+login.getSelectedItem() ;
					obj.getResult().next();
					String dis =  obj.getResult().getString("is_disabled") ;
					
					if (dis.equals("1") ) query+=" Enable";
					else query+=" Disable" ;	
					
					obj.Update(query);
					Tail.setINF( Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette") );
				  
				  } catch (SQLException e) {
				
					e.printStackTrace();
				}
				  
				}
		});  
	    add( enable , "cell 3 3");
	    
	}
	
	 
	
}
