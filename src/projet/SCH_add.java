package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SCH_add extends JPanel {
	private JTextField schema_name = new JTextField(20) ; 
	private JComboBox<String>	  role  = new JComboBox<String>() , db = new JComboBox<String>()  ;
	
	public SCH_add(){

		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay ); 
    	add(new JLabel( Lang.getWord("Sélectionner") +" "+ Lang.getWord("Base de donneé") ));  
    		create( db ,"select name from sys.databases ","");
    		add( db , "span 2 , pushx , growx , wrap"); 
    	add(new JLabel( Lang.getWord("Nom") +" "+ Lang.getWord("Schéma")));  
	    	add( schema_name , "span 2 , pushx , growx , wrap"); 
	    add(new JLabel( Lang.getWord("Autorisation")) ); 	
	    create( role ,"Select name From sysusers where name not like '##%##'" ,"");
	    add( role ,"span 2");
	    
	    db.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SCH_add.create( role ,"Select name From sysusers where name not like '##%##'" ,(String) db.getSelectedItem());
			}
		});
	    
	    JButton ref = new JButton(  Lang.getWord("Actualiser") );
		ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				role.removeAllItems();
				SCH_add.create( role , "Select name From sysusers where name not like '##%##'","");
				SCH_add.create(db, "select name from sys.databases ","" ) ;
					
			}
		});
		add( ref ,"wrap ");
		JButton cree = new JButton( Lang.getWord("Création") );
		cree.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 	String query ="CREATE SCHEMA ";
				 	if( schema_name.getText().trim().isEmpty() ){
				 		Tail.setError( Lang.getWord("Champ")+" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide") );
				 	}else{
				 		query+= schema_name.getText().trim() ;
				 		query+="  AUTHORIZATION ";
				 		query+= role.getSelectedItem() ;
				 		System.out.println( query );
				 		DataBase obj = new DataBase( (String) db.getSelectedItem() );
				 		if ( obj.Update(query) )
				 		Tail.setINF(Lang.getWord("Création")+" "+Lang.getWord("Schéma")+"\n"+
								Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
						 
				 		
				 	}
			}
		});
		add( cree , " cell 3 3");
		
	}

	static void create(JComboBox<String> a , String query , String db) {
		DataBase obj = new DataBase(db); 
		a.removeAllItems();
		obj.Execute( query ,true );
		try{
		while( obj.getResult().next() ){
			a.addItem( obj.getResult().getString("name") );
		}
		}catch(Exception e){
			
		} 
		
	}
}
