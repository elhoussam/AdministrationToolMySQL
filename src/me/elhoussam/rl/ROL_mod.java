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
public class ROL_mod extends JPanel {

	private JComboBox<String>	db , role ;
	private JTextField   name ;
	
	public ROL_mod() {
		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay );
		
    	db = new JComboBox<String>() ;
		SCH_add.create( db ,"select name from sys.databases ","");
		role =   new JComboBox<String>() ;
		SCH_add.create( role ,"Select name From sysusers where name not like '##%##'","master");
		
		name = new JTextField(20);
		
		add(new JLabel(  Lang.getWord("Sélectionner") +" "+Lang.getWord("Base de donneé")  ) ); 
		add( db ,"  pushx , growx  ");
		
		JButton ref = new JButton("refresh");
		ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				 	SCH_add.create( db ,"select name from sys.databases  " ,  (String) db.getSelectedItem() );
			}
		});
		
		
		add( ref , "wrap");
		
		add( new JLabel(  Lang.getWord("Nouveau") +" "+Lang.getWord("Nom")  ) ); 
		add( name ,"  pushx , growx , wrap ");
		db.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				 	SCH_add.create( role ,"Select name From sysusers where name not like '##%##' " ,  (String) db.getSelectedItem() );
			}
		});
		
	add(new JLabel( Lang.getWord("Rôle") ) );
		add( role ,"  pushx , growx , wrap ");
		
		
		JButton mod = new JButton("modifie");
		mod.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( ! name.getText().trim().isEmpty() ){
					String Query ="ALTER ROLE "+ role.getSelectedItem() +" WITH NAME="+name.getText().trim();
					DataBase obj = new DataBase( (String) db.getSelectedItem() );
					if( obj.Update(Query) )
						Tail.setINF(Lang.getWord("Renommer") +" "+Lang.getWord("Rôle")+ "\n"+
								Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
					 
				}else{
					Tail.setError(Lang.getWord("Champ")+" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide")); 
				}
			}
			
		});		
	add( mod ," cell 3 3 ");
		
	}
 

}
