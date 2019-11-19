package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class LOG_del extends JPanel{
	
	
	private JComboBox<String>	users = new JComboBox<String>() ;
	
	public LOG_del(){

		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay );
		
    	add(new JLabel( Lang.getWord("Sélectionner") +" "+Lang.getWord("Log in")) );
    	 create( users );
    	add( users , "span 3 , pushx , growx "); 
		
    	JButton del = new JButton(  Lang.getWord("Supprimer") );
		del.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String Query ="Drop login "+ users.getSelectedItem() ;
				DataBase obj = new DataBase("");
				if ( obj.Update( Query ) ) 
					Tail.setINF(Lang.getWord("Supprimer")+" "+Lang.getWord("Log in")+"\n"+
							Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
					 
				users.removeAllItems();
				
			}
		});
		JButton ref = new JButton( Lang.getWord("Actualiser") );
		ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				users.removeAllItems();
				LOG_del.create( users );
				 
					
			}
		});
		add( ref , "cell 2 4"); 
		add( del , "cell 3 4"); 
	}
	static void create(  JComboBox<String> a){ 
		DataBase obj = new DataBase("");
		// \'G\'\'U\' 
		if( obj.Execute("SELECT name  FROM sys.server_principals WHERE TYPE IN (  \'S\') and name not like \'%##%\'  ",true) ) 
			Tail.setINF( Lang.getWord("Actualiser")+" "+Lang.getWord("Nouveau")+" "+Lang.getWord("Log in")+"\n"+
					Lang.getWord("Pour consulter votre requette"));
		try{
			while( obj.getResult().next() ){
				a.addItem( obj.getResult().getString("name") );
			}
		}catch(Exception e){
			
		} 
		
	}
	
	/*
	 * 
	 *
	 * 
	 * 
	 * SELECT name  
FROM sys.server_principals 
WHERE TYPE IN ('U', 'S', 'G')
and name not like '%##%' 
	 * 
	 * */
}
