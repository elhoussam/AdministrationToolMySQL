package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TBL_add_col extends JPanel {
	  JButton addligne = new JButton("+");
	  JButton delligne = new JButton("-");
	private JComboBox<String>	  db = new JComboBox<String> () , tbl =  new JComboBox<String> ()  ;
	public TBL_add_col() {

		final table_cree exe = new table_cree();
		exe.setVisible( false );
		MigLayout lay = new MigLayout("wrap 4" ); 
    	setLayout ( lay ); 
    	
    	SCH_add.create( db ,"select name from sys.databases", "");
    	db.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SCH_add.create( tbl ,"select name from sys.tables " ,(String) db.getSelectedItem());
			}
		});
    	add(new JLabel(  Lang.getWord("Sélectionner")  + Lang.getWord("Base de donneé") ) ); 
    	add( db , "span 3 ,  pushx , growx   , wrap");
    	

        
        
    	tbl.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exe.setVisible( true );
				addligne.setVisible( true );
				delligne.setVisible( true );
			}
		});
    	
    	add(new JLabel( Lang.getWord("Sélectionner")+" "  + Lang.getWord("Table")) ); 
    	add( tbl , "span 3 ,  pushx , growx   , wrap");
    	

        addligne.setToolTipText("Add Ligne");
        addligne.addActionListener( new ActionListener(){
                    @Override
                    public void actionPerformed( ActionEvent e){
                    		exe.addLine();
                    } 
                });
        		delligne.setToolTipText("Del Ligne");
        delligne.addActionListener( new ActionListener(){
        	           @Override
        	           public void actionPerformed( ActionEvent a){
        	        	  exe.delLine();
        	        	   
        	           }
        	        });
        
        
    	add(  exe ,"span 4 4 , pushx , growx , pushy , growy  ");
    	JButton add = new JButton( Lang.getWord("Ajouter colonne")   );
		add.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 	
				 	 
				 	if(   tbl.getSelectedItem() == null  ){
				 		
				 		Tail.setError(Lang.getWord("Sélectionner")	+" "+Lang.getWord("Table"));
				 	}else{
				 		String query ="ALTER TABLE ";
				 		query+= String.valueOf( tbl.getSelectedItem() ).trim()+" ADD" ;
				 		
				 		if( exe.Test_saisie( ) ){
				 			query = query+exe.getSQL() ;
				 			query = ( query ).substring(0,query.length()-3 ) ;
				 			 
				 		 System.out.println("query : \n"+  query );

					 	 	DataBase obj = new DataBase( (String) db.getSelectedItem() );
					 	 	if ( obj.Update( query  ) )
					 	 		Tail.setINF(Lang.getWord("Création")+" "+Lang.getWord("Table")+" \n"+ 
					 	 					Lang.getWord("Pour consulter votre requette") );
				 		}
				 		 
				 	}
			}
		});
		addligne.setVisible( false );
		delligne.setVisible( false );
        add( addligne );
        add( delligne );
		add( add  );
    	
	}

}
