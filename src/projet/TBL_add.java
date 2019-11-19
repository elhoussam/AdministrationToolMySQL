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
public class TBL_add extends JPanel {
	private JTextField nameField = new JTextField(20) ; 
	private JComboBox<String>	  db = new JComboBox<String> ();
	
	public TBL_add() {
		
		final table_cree exe = new table_cree();
		
		MigLayout lay = new MigLayout("wrap 4" ); 
    	setLayout ( lay ); 
    	
    	SCH_add.create( db ,"select name from sys.databases", "");
    	
    	add(new JLabel( Lang.getWord("Nom") +" "+ Lang.getWord("Table")  ) ); 
    	add( nameField , "span 3 ,  pushx , growx   , wrap");
    	
    	
    	JButton ref = new JButton(  Lang.getWord("Actualiser") );
		ref.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				 	SCH_add.create( db ,"select name from sys.databases  " ,  (String) db.getSelectedItem() );
			}
		});
		add(	new JLabel( Lang.getWord("Sélectionner")  + Lang.getWord("Base de donneé") ) ); 
    	add(	db, "span 2 , pushx , growx   ");
    	add(	ref, "wrap ");
    	
    	
    	add(  exe ,"span 4 4 , pushx , growx , pushy , growy  ");
    	

        JButton addligne = new JButton("+");
        addligne.setToolTipText("Add Ligne");
        addligne.addActionListener( new ActionListener(){
                    @Override
                    public void actionPerformed( ActionEvent e){
                    		exe.addLine();
                    } 
                });
        JButton delligne = new JButton("-");
        		delligne.setToolTipText("Del Ligne");
        delligne.addActionListener( new ActionListener(){
        	           @Override
        	           public void actionPerformed( ActionEvent a){
        	        	  exe.delLine();
        	        	   
        	           }
        	        });
    	JButton cree = new JButton( Lang.getWord("Création") );
		cree.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 	String query ="CREATE TABLE ";
				 	 
				 	
				 	if( nameField.getText().trim().isEmpty() ){
				 		Tail.setError( Lang.getWord("Champ") +" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide") );
				 	}else{
				 		query+= nameField.getText().trim() ;
				 		
				 		if( exe.Test_saisie( ) ){
				 			 System.out.println("query : "+query +"("+exe.getSQL() +")" );

					 		DataBase obj = new DataBase( (String) db.getSelectedItem() );
					 		if ( obj.Update( 	query+"("+exe.getSQL() +")"	)  )
					 			Tail.setINF(Lang.getWord("Création")+" "+Lang.getWord("Table")+"\n"+
					 					Lang.getWord("Pour consulter votre requette")); 
				 		}
				 		 
				 	}
			}
		});

        add( addligne );;
        add( delligne );
		add( cree  );
    	
	}

	 

}
