package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SCH_del extends JPanel {
	private JComboBox<String>	db , schs ;
	
	public SCH_del(){
		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay );
		
		db = new JComboBox<String>() ;
		SCH_add.create( db ,"select name from sys.databases ","");
		schs =   new JComboBox<String>() ;
		SCH_add.create( schs ,"Select name From sys.schemas  ","master");
		
		add(new JLabel(  Lang.getWord("Sélectionner") +" "+ Lang.getWord("Base de donneé") ) ); 
			add( db ,"  pushx , growx , wrap ");
			
			db.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) { 
					 	SCH_add.create( schs ,"Select name From sys.schemas  " ,  (String) db.getSelectedItem() );
				}
			});
			
		add(new JLabel( Lang.getWord("Schéma") ) );
			add( schs ,"  pushx , growx , wrap ");
			
			JButton del = new JButton(Lang.getWord("Supprimer"));
			del.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String Query ="Drop schema "+ schs.getSelectedItem() ;
					DataBase obj = new DataBase( (String) db.getSelectedItem() );
					if( obj.Update(Query) )
						Tail.setINF(Lang.getWord("Supprimer")+" "+Lang.getWord("Schéma")+"\n"+
								Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette"));
					 
				}
				
			});		
    	add( del ," cell 3 2 ");
	}
}
