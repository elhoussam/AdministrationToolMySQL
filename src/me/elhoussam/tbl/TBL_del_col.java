package me.elhoussam.tbl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.elhoussam.basic.DataBase;
import me.elhoussam.cmp.Tail;
import me.elhoussam.entry.Main;
import me.elhoussam.lng.Lang;
import me.elhoussam.sch.SCH_add;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TBL_del_col  extends JPanel{
	private JComboBox<String>	  db   , tbl , cols ;
	private JTextField name = new JTextField(20);
	public TBL_del_col() {
		MigLayout lay = new MigLayout("wrap 4" ); 
    	setLayout ( lay ); 
    	
    	db =  new JComboBox<String> ()  ;
    	tbl  =  new JComboBox<String> ()  ; 
    	cols  =  new JComboBox<String> ()  ;
		
    	SCH_add.create( db ,"select name from sys.databases", ""); 
    	SCH_add.create(cols , "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=\'"+
		tbl.getSelectedItem()+"\'",(String) db.getSelectedItem() );
		    	db.addActionListener( new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SCH_add.create( tbl ,"select name from sys.tables " ,(String) db.getSelectedItem() );
					}
				});
		    	add(new JLabel( Lang.getWord("Sélectionner") + Lang.getWord("Base de donneé") ) ); 
		    	add( db , "span 3 ,  pushx , growx   , wrap");
    	//--------------------------------------- table 
	    	tbl.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {

					//String query = "exec sp_columns "+ tbl.getSelectedItem() ;
					String query = "select COLUMN_NAME as name	from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME=\'"
									+ tbl.getSelectedItem()+"\'";
					
				 	SCH_add.create( cols ,query, (String) db.getSelectedItem() ); 
 
				}
			});
	    	add(new JLabel( Lang.getWord("Sélectionner") + Lang.getWord("Table") ) ); 
	    		add( tbl , "span 3 ,  pushx , growx   , wrap");
	    	add(new JLabel( Lang.getWord("Sélectionner") + Lang.getWord("Colonne") ) ); 
	    		add( cols , "span 3 ,  pushx , growx   , wrap");
	    	add(new JLabel(  Lang.getWord("Nom") +" "+ Lang.getWord("Colonne") ) ); 
	    		add( name , "span 2 ,  pushx , growx   , wrap");
	    	
	    	JButton rm = new JButton( 		Lang.getWord("Supprimer")		);
			rm.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					 if( cols.getSelectedItem() != null ){
						 String query ="ALTER TABLE  ";
						 query += tbl.getSelectedItem() ;
						 query +=" DROP COLUMN  " + cols.getSelectedItem() ;
						 
						 DataBase obj = new DataBase((String)db.getSelectedItem());
						 if ( obj.Update(query) ) 
							 Tail.setINF(Lang.getWord("Supprimer")+" "+Lang.getWord("Colonne")+" "+
		                             Lang.getWord("Table")+"\n"+
									 Lang.getWord("Pour consulter votre requette") );
						 
					 
					 }
				}
			});
			add( rm  ,"cell 3 3");
			JButton ren = new JButton( Lang.getWord("Renommer") );
			ren.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					 if( cols.getSelectedItem() != null  ){
						 if( name.getText().trim().isEmpty() ){
							 Tail.setError( Lang.getWord("Champ") +" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide") );
						 }else{
								  String query ="EXEC sp_RENAME \'"+ tbl.getSelectedItem() +
											 "."+ cols.getSelectedItem() +"\' , \'"+ name.getText().trim() +
											 "', \'COLUMN\' ";
										 Main.echo(" RENAME : "+query);
										 
										 DataBase obj = new DataBase((String)db.getSelectedItem());
										 if ( obj.Update(query) ) 
											 Tail.setINF(Lang.getWord("Renommer")+" "+Lang.getWord("Colonne")+" "+
						                             Lang.getWord("Table")+"\n"+
													 Lang.getWord("Pour consulter votre requette") );
									 
										 
						 }
					 }else 
					 		Tail.setError(Lang.getWord("Sélectionner")	+" "+Lang.getWord("Colonne"));
				}
			});
			add( ren ,"cell 3 4" );
		}
	
}
