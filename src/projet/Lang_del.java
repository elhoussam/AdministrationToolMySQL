package projet;
 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class Lang_del extends JPanel {

	protected JComboBox<String>  all_lng = new JComboBox<String>() ;
	
	public Lang_del() {

		MigLayout lay = new MigLayout("wrap 4"); 
		setLayout ( lay );
		Lang_del.createAll( all_lng );

    	add(new JLabel( Lang.getWord("Langue")+" "+ Lang.getWord("Disponible") )); 
    	
    	
    	JButton del = new JButton( Lang.getWord("Supprimer") ); 
    	del.addActionListener( new ActionListener(){
    	
			@Override
			public void actionPerformed(ActionEvent e) {
				if( all_lng.getSelectedItem() != null ){
					String query ="ALTER TABLE  langue  DROP COLUMN  "+all_lng.getSelectedItem();
					DataBase obj = new DataBase("pfe");
					 if ( obj.Update(query) ) 
						 Tail.setINF(Lang.getWord("Supprimer")+" "+Lang.getWord("Langue")+" "+ "\n"+
								 Lang.getWord("Pour consulter votre requette") );
				}else{
					Tail.setError( Lang.getWord("Sélectionner")+" "+Lang.getWord("Langue"));
				}
			}
    		
    	});
    	
    	JButton ref = new JButton( Lang.getWord("Actualiser") ); 
    	ref.addActionListener( new ActionListener(){
        	
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				  all_lng.removeAllItems(); 
    				 Lang_del.createAll( all_lng );
    			    
    			}
        		
        	});
    	
    	
    	add( all_lng , "span 2 , pushx , growx ");
    	add( ref );
    	add( del ,"cell 3 2");
    	

    	
	}
	
	
	static  void  createAll( JComboBox<String> a) {
		 
		
		DataBase obj = new DataBase("pfe");
		obj.Execute("select COLUMN_NAME as name from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=\'langue\' ",true);
		try{
		while( obj.getResult().next() ){
			if( !obj.getResult().getString("name").equals("id") )
			a.addItem( obj.getResult().getString("name") );
		}
		}catch(Exception e){
			
		} 
		
	}
	
	

}
