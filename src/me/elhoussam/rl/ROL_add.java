package me.elhoussam.rl;

 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.elhoussam.basic.DataBase;
import me.elhoussam.cmp.Tail;
import me.elhoussam.lng.Lang;
import me.elhoussam.sch.SCH_add;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ROL_add extends JPanel {
	private JTextField roleField = new JTextField(20) ; 
	private JComboBox<String>	  logins   , db ;
	
	public ROL_add() {

		MigLayout lay = new MigLayout("wrap 4" ); 
    	setLayout ( lay ); 
    	
    	db = new JComboBox<String>() ;
    	logins =  new JComboBox<String>() ;
    	
    	SCH_add.create( db ,"select name from sys.databases", "");
    	SCH_add.create( logins ,"SELECT name  FROM sys.database_principals where type in (\'S\') and name not like \'##%##\' ", "");
    	
    	
    	add(new JLabel( Lang.getWord("Rôle") +" "+Lang.getWord("Nom")) ); 
    	add( roleField , "span 3 ,  pushx , growx   , wrap");
    	
    	
    	add(	new JLabel( Lang.getWord("Autorisation") ) ); 
    	add(	logins, "span 3 ,  pushx , growx   ,wrap ");
    	
    	JButton ref = new JButton(	Lang.getWord("Actualiser")	);
		ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				 	SCH_add.create( db ,"select name from sys.databases  " ,  (String) db.getSelectedItem() );
			}
		});
		
    	add(	new JLabel( Lang.getWord("Sélectionner") +" "+Lang.getWord("Base de donneé")  ) ); 
    	add(	db, "span 2 , pushx , growx   ");
    	add(	ref, "wrap ");
    	
    	JButton cree = new JButton(	Lang.getWord("Création") );
		cree.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 	String query ="CREATE ROLE ";
				 	if( roleField.getText().trim().isEmpty() ){
				 		Tail.setError( Lang.getWord("Champ")+" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide"));
				 	}else{
				 		query+= roleField.getText().trim() ;
				 		query+="  AUTHORIZATION ";
				 		query+= logins.getSelectedItem() ;
				 		System.out.println( query );
				 		 DataBase obj = new DataBase( (String) db.getSelectedItem() );
				 		if ( obj.Update(query) )
				 		Tail.setINF(Lang.getWord("Création")+" "+Lang.getWord("Rôle")+"\n"+
								Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
						 
				 		 
				 	}
			}
		});
		add( cree  );
    	
    	 
    	
	}

	

}
