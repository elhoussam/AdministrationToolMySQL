package projet;

import java.awt.BorderLayout;
import java.awt.FlowLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea; 
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class table_cree extends JPanel {
	private JTable tabl ;
	String Query ="";

	//Lang.getWord("")
	
	final JTextArea err = new JTextArea();
	public table_cree() {
		
		setLayout( new BorderLayout() );
		final Object[][] data = {
	            /*0Name  1Type  2Length  3Null  4primay  5A_I */  // index
	              };
		String title []= { Lang.getWord("Nom") , Lang.getWord("Type") , Lang.getWord("Taille") ,
						Lang.getWord("Null") , Lang.getWord("Cle primaire") , Lang.getWord("Incrémentation automatique") };
		
		 // creation type des colons
		 tabl = new JTable( new DefaultTableModel(data,title){
            
            @Override
                public Class<?> getColumnClass( int col){
            	
            	if( col == 0 || col == 1 ){ // name type
            		return String.class ;
            	}else if( col == 2 ){ // Lenght 
            		return Integer.class ;
            	}else{ // allow null  , primary   , Auto increment
            		return   Boolean.class ;            	
            	} 
            }
        });
		// Combobox Cells  
       // table.getColumnModel().getColumn(1).setCellEditor( new DefaultCellEditor( generateBox("Type") ) ); 
        tabl.getColumn(  Lang.getWord("Type") ).setCellEditor( new DefaultCellEditor( generateBox("type") ) ); 
        
        tabl.setRowHeight(20);
        
        tabl.getColumn( Lang.getWord("Null") ).setPreferredWidth(20);
        tabl.getColumn( Lang.getWord("Cle primaire")  ).setPreferredWidth(20);
        tabl.getColumn( Lang.getWord("Incrémentation automatique") ).setPreferredWidth(20);
        
        JPanel lpan = new JPanel( new FlowLayout( FlowLayout.LEFT ));
        
        
        
        
        
        JButton extract = new JButton("Extract");
        extract.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent a){
            	
            	Test_saisie();
                    }// fin method
            
        
        }); 
        
       // lpan.add( addligne ); 
       // lpan.add( delligne );
       // lpan.add( extract );  
        
            add(new JScrollPane( tabl ));
            add( lpan , BorderLayout.SOUTH );
           // add( new JScrollPane(err) , BorderLayout.SOUTH );
            
        
        
		
	}
	
	protected JTable getTable(){
		return tabl ;
	}
	// add new line empty
	public void addLine(){
		
		DefaultTableModel mdl = ( DefaultTableModel ) tabl.getModel();
        mdl.addRow(new Object[]{});
       
	}

	// del selected line 
	public void delLine(){
		int selectedrow = tabl.getSelectedRow() ;
        if( selectedrow != -1 ){
         DefaultTableModel mdl = ( DefaultTableModel ) tabl.getModel();
          mdl.removeRow( selectedrow );
       }
	}
	
	public String getSQL(){
		return Query ;
	}
	Boolean Test_saisie( ){
		
        //err.setEditable(false);
        //err.setRows(4);
   	 Boolean mistake = false ;
        //err.setText("");
        
          Query="\n";
       int row = tabl.getRowCount();
       int col = tabl.getColumnCount();
       System.out.printf("------------------------\nrow : %d , col : %d\n",row,col);
   	int clef = 0 ;
      
       for( int i = 0 ; !mistake  && i<  row; i ++){
    	   Query+="\n";
       	for( int j = 0 ; !mistake  && j < col ; j++){
       		/*0Name  	1Type  		2Length  3Null  	4primay  5A_I */  // index
       		// String   String		int		 boolean	boolean
       		
       		if(j== 0 || j == 1){	 //pour name , type
       			if(  tabl.getValueAt(i,j) == null  || ((String) tabl.getValueAt(i,j) ).trim().isEmpty() ){
       				Tail.setError(   "\n"+  Lang.getWord("Ligne") +" "+ (i+1) + " : "+
			       					 Lang.getWord("il y a")+" "+  
			       					 Lang.getWord("Erreur") + "[" + Lang.getWord("Nom") +","+ Lang.getWord("Type") +"]"+
			       					 Lang.getWord("Est Vide")
       							);  
                       mistake=true;
       			}else   Query+=  "["+String.valueOf( tabl.getValueAt(i,j)).trim() + "] " ;
           	}
           	if( j == 2 ){ // pour length
           		if( String.valueOf( tabl.getValueAt(i,1) ).trim().equals("char") ||
           			String.valueOf( tabl.getValueAt(i,1) ).trim().equals("nchar") ||
           			String.valueOf( tabl.getValueAt(i,1) ).trim().equals("varchar") ||
           			String.valueOf( tabl.getValueAt(i,1) ).trim().equals("nvarchar") ){
           				//System.out.println("TYPE : "+String.valueOf( tabl.getValueAt(i,j) ).trim());
			                		if(  tabl.getValueAt(i,2) == null     ){
			                			Tail.setError(   "\n"+  Lang.getWord("Ligne") +" "+ (i+1) + " : "+
						       					 Lang.getWord("il y a")+" "+  
						       					 Lang.getWord("Erreur") + "[" + Lang.getWord("Taille")   +"]"+
						       					 Lang.getWord("Est Vide")
			       							);  
			                			
			                			//Tail.setError("\nLigne "+(i+1)+" : There is Error [LENGTH] is Empty");  
			                            mistake=true;
			                		}else   Query+= "("+ String.valueOf( tabl.getValueAt(i,j)).trim() + ") " ;
           		}
				}
           	  // pour 3null  
           	if(j == 3    ){
		         	if(  tabl.getValueAt(i,j) != null && (Boolean) tabl.getValueAt(i,3) == true  ) Query += "NULL ";
		            else Query += " NOT NULL ";
		        }  
           	// 4 primary
				if(j == 4   ){
		         	if(  tabl.getValueAt(i,4) != null  && (Boolean) tabl.getValueAt(i,4) == true ) {
		         		if( tabl.getValueAt(i,3) != null && (Boolean) tabl.getValueAt(i,3) == true  ){
			         		
		         			Tail.setError(   "\n"+  Lang.getWord("Ligne") +" "+ (i+1) + " : "+
			       					 Lang.getWord("il y a")+" "+  
			       					 Lang.getWord("Erreur") + "[" + Lang.getWord("Cle primaire") +"]" +
			       					 Lang.getWord("Nullable")
      							);  
		         			
		         			
		         		//	Tail.setError("\nLigne "+(i+1)+"  : There is Error [primary] " + ",primary key is nullable");  
		                		mistake=true;				         		
			         	}else{
				            Query+="primary key " ;				      
				            clef++ ;
			         	}
			        }
		       
		         	if( clef > 1 ){
		         		Tail.setError(   "\n"+  Lang.getWord("Ligne") +" "+ (i+1) + " : "+
		       					 Lang.getWord("il y a")+" "+  
		       					 Lang.getWord("Erreur") + "[" + Lang.getWord("Primaire") +"]" +
		       					 Lang.getWord("dupliqué")
 							);  
		         		
		         		//Tail.setError("\nLigne "+(i+1)+"  : There is Error [primary] " 	+ ",primary key is duplicated");  
	                		mistake=true;				         		
		         	}
		         	
		         	
		        }
				// 5 Auto Increment
		        if( j == 5 && tabl.getValueAt(i,5) != null ){
		        	if(  tabl.getValueAt(i,3) != null &&  (Boolean )tabl.getValueAt(i,3) == true ){
		        		Tail.setError(      "\n"+  Lang.getWord("Ligne") +" "+ (i+1) + " : "+
		       					 Lang.getWord("il y a")+" "+  
		       					 Lang.getWord("Erreur") + "[" + Lang.getWord("Incrémentation automatique") +"]" +
		       					 Lang.getWord("Nullable")
							);  
		         		
		        		
	//                	Tail.setError("\nLigne "+(i+1)+"  : There is Error [Auto increment] "+ ", is nullable  ");  //champ is allow null value
	        		    mistake=true;	
	                } else  if( (Boolean ) tabl.getValueAt(i,5) == true &&
	                		!( String.valueOf( tabl.getValueAt(i,1) ).trim().equals("int") ||
	                		String.valueOf( tabl.getValueAt(i,1) ).trim().equals("bigint") ||
	                		String.valueOf( tabl.getValueAt(i,1) ).trim().equals("smallint"))  ){
	                	
	                	Tail.setError(      "\n"+  Lang.getWord("Ligne") +" "+ (i+1) + " : "+
		       					 Lang.getWord("il y a")+" "+  
		       					 Lang.getWord("Erreur") + "[" + Lang.getWord("Incrémentation automatique") +"]" +
		       					"["+String.valueOf( tabl.getValueAt(i,1) ).trim()+"]" +   Lang.getWord("Différent Integer") 
							);  
	                	
//	    				Tail.setError("\nLigne "+(i+1)+"  : There is Error [Auto increment] ,"+ " champ is not integer  ["+String.valueOf( tabl.getValueAt(i,1) ).trim()+"]");  
	        		        mistake=true;	
	                }else{
			                	if( (Boolean) tabl.getValueAt(i,5) == true )  Query+="IDENTITY(1,1) ";  
			        }
		        }  
               	
       		}// fin for2
       	Query+=" ,";
       	}// fin for 1 
       if( !mistake && row>0 ) System.out.println(" IN SIDE table_cree "+ Query );
       
       return ( !mistake && row>0 ) ;

	}
	private JComboBox<String> generateBox(String a){
        JComboBox<String>  bx=new JComboBox<String>();
        DataBase obj = new DataBase("pfe");
        obj.Execute("SELECT [name] FROM  [type]",true);
        try {
			while( obj.getResult().next() ){
			            bx.addItem( obj.getResult().getString("name") );

			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
        return bx;
    } 

 

}
