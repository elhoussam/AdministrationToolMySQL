package me.elhoussam.rl;

 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.elhoussam.basic.DataBase;
import me.elhoussam.cmp.Tail;
import me.elhoussam.lng.Lang;
import me.elhoussam.sch.SCH_add;
import net.miginfocom.swing.MigLayout;
 
@SuppressWarnings("serial")
public class ROL_del extends JPanel {

	private JComboBox<String>	db , role ;
	 
	public ROL_del() { 
		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay );
		
    	db = new JComboBox<String>() ;
		SCH_add.create( db ,"select name from sys.databases ","");
		role =   new JComboBox<String>() ;
		SCH_add.create( role ,"Select name From sysusers where name not like '##%##'","master");
		
		
		add(new JLabel( Lang.getWord("Sélectionner") +" "+Lang.getWord("Base de donneé") ) ); 
		add( db ,"  pushx , growx  ");
		
		JButton ref = new JButton(Lang.getWord("Actualiser"));
		ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				 	SCH_add.create( db ,"select name from sys.databases  " ,  (String) db.getSelectedItem() );
			}
		});
		
		
		add( ref , "wrap");
		
		db.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				 	SCH_add.create( role ,"Select name From sysusers where name not like '##%##' " ,  (String) db.getSelectedItem() );
			}
		});
		
	add(new JLabel(Lang.getWord("Rôle")) );
		add( role ,"  pushx , growx , wrap ");
		
		
		JButton del = new JButton(	Lang.getWord("Supprimer")	);
		del.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String Query ="Drop role "+ role.getSelectedItem() ;
				DataBase obj = new DataBase( (String) db.getSelectedItem() );
				if( obj.Update(Query) )
					Tail.setINF(Lang.getWord("Supprimer")+" "+  Lang.getWord("Rôle")+"\n"+
							Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
				 
				
			}
			
		});		
	add( del ," cell 3 2 ");
		
		
		
		
	}

 

}
