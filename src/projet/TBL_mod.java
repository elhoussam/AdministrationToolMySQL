package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TBL_mod   extends JPanel{
	private JComboBox<String>	  db   , tbl , cols ;
	 public TBL_mod(){
			MigLayout lay = new MigLayout("wrap 4" ); 
	    	setLayout ( lay ); 

			final table_cree exe = new table_cree();
			exe.setVisible( false );	    	
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
			    	add(new JLabel(  Lang.getWord("Sélectionner") + Lang.getWord("Base de donneé") ) ); 
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

		    	cols.addActionListener( new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if( cols.getSelectedItem() != null ){	

					         DefaultTableModel mdl = ( DefaultTableModel ) exe.getTable().getModel();
					         for( int i=0;i< mdl.getRowCount() ;i++){
					        	 mdl.removeRow( i );
					         }
					         
							exe.setVisible(true);
							exe.addLine();
							
							DataBase obj = new DataBase( (String) db.getSelectedItem() );
							String query =" select    COLUMN_NAME ,  DATA_TYPE , CHARACTER_MAXIMUM_LENGTH   ,IS_NULLABLE  "+
											" from INFORMATION_SCHEMA.COLUMNS where "+
											"TABLE_NAME="+ "\'"+  tbl.getSelectedItem() +"\'" +
											"AND COLUMN_NAME=" + "\'"+  cols.getSelectedItem() +"\'" ;
							
							if( obj.Execute(query,true) ) {
									obj.getResult();
									try {
										obj.getResult().next() ;
										// name 	
										exe.getTable().setValueAt(cols.getSelectedItem(), 0, 0);
										// datatype
										exe.getTable().setValueAt( obj.getResult().getString("DATA_TYPE") , 0, 1);
										// CHARACTER_MAXIMUM_LENGTH
										if( obj.getResult().getString("CHARACTER_MAXIMUM_LENGTH") != null )
											exe.getTable().setValueAt( obj.getResult().getString("CHARACTER_MAXIMUM_LENGTH")  , 0, 2);
										// IS_NULLABLE
										if( obj.getResult().getString("IS_NULLABLE").equals("NO")){
										exe.getTable().setValueAt( false , 0, 3);
										
										//if  not null  may be  is primary key
											query ="SELECT   column_name \n"+
													"FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n"+
													"WHERE OBJECTPROPERTY(OBJECT_ID(constraint_name), \'IsPrimaryKey\') = 1 \n"+
													"AND TABLE_NAME="+ "\'"+  tbl.getSelectedItem() +"\'" +
													"AND COLUMN_NAME=" + "\'"+  cols.getSelectedItem() +"\'" ;
											Main.echo("primary"+ query );
											obj.Execute(query,true);
											if( obj.getResult().next() )
												exe.getTable().setValueAt( true , 0, 4);
										//check if auto increment
											query ="SELECT  is_identity as auto\n"+
												    "FROM sys.columns   WHERE\n"+ 
												    " object_id = object_id(\'"+ tbl.getSelectedItem() +"')\n"+
												    "  AND name = \'"+ cols.getSelectedItem()  +"\'";
											Main.echo(" auto : "+ query );
											obj.Execute(query,true);
											if( obj.getResult().next() )
												if( obj.getResult().getString("auto").equals("1") )
													exe.getTable().setValueAt( true , 0,5);
										
										}else	exe.getTable().setValueAt( true , 0, 3);
									} catch (SQLException e) { 
										e.printStackTrace();
									}
							}else {
								Main.echo("query   :"+query);
							}
						}	
	 
					}
				}); 
		    	add(new JLabel( Lang.getWord("Sélectionner")+" " + Lang.getWord("Table")  ) ); 
		    	add( tbl , "span 3 ,  pushx , growx   , wrap");
		    	add(new JLabel( Lang.getWord("Sélectionner")+" " + Lang.getWord("Colonne")) ); 
		    	add( cols , "span 3 ,  pushx , growx   , wrap");
		    	

		    	add(  exe ,"span 4 4 , pushx , growx , pushy , growy  ");
		    	
		    	
		    	
		    	
		    	JButton mod = new JButton( Lang.getWord("Modifier") );
		    	mod.addActionListener( new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) { 
						if( cols.getSelectedItem() != null ){
						  
								if ( exe.Test_saisie() ){
									String query = "ALTER TABLE "+ tbl.getSelectedItem() +
												 "\nALTER COLUMN "+ cols.getSelectedItem() +
												 " " ;
								String a  = exe.getSQL() ;
								
								short i = 0 ;	
								for(i= 0 ; i < a.length() && a.charAt(i)!=']' ; i++);
									a = a.substring( i + 1 , a.length() );
									query += a ;
									query = query.substring(0, query.length()-3 );
									
									Main.echo(" SQL MODIFY : \n"+ query );
									
									 DataBase obj = new DataBase((String)db.getSelectedItem());
									 if ( obj.Update(query) ) 
										 Tail.setINF( Lang.getWord("Modifier")+" "+Lang.getWord("Information")+" "+
					                             Lang.getWord("Colonne")+"\n"+   Lang.getWord("Pour consulter votre requette") );
												 
												  
								
								}
						
					}else{

				 		Tail.setError(Lang.getWord("Sélectionner")	+" "+Lang.getWord("Colonne"));
					}
				}
				});
		    	
		    	add( mod );
	 }
}
