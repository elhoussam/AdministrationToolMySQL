package me.elhoussam.db;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser; 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.elhoussam.basic.DataBase;
import me.elhoussam.cmp.Tail;
import me.elhoussam.entry.Main;
import me.elhoussam.lng.Lang;
import net.miginfocom.swing.MigLayout;
 

@SuppressWarnings("serial")
public class DB_add extends JPanel {
	private JTextField name , log , phi ;
	private String pathlog ="D:\\Programm_Files\\Microsoft SQL Server\\MSSQL12.SQLEXPRESS\\MSSQL\\DATA", pathphi = pathlog ;

	public DB_add(){
		setBackground( Color.white );
		MigLayout lay = new MigLayout("wrap 4"); 
    	setLayout ( lay ); 

    	
    	add(new JLabel(  Lang.getWord("Nom") )); 
    	name = new JTextField(20) ;
	    	add( name , "span 3 , pushx , growx "); 
	    	
    	add(new JLabel( Lang.getWord("Principal") ));
    	log = new JTextField(20);
	    	add( log , "span 2 , pushx , growx"); 
	    	JButton path_l = new JButton("...") ;
	    	path_l.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					   JFileChooser chooser = new JFileChooser();
	                   chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
	                   chooser.showOpenDialog( Main.getPrinc() ); 
	                   try{
	                   pathlog = chooser.getSelectedFile().getAbsolutePath()  ;
	                   }catch( Exception ea){
	                	    Main.echo("No path Selected for new Database");
	                   }
				}
	    		
	    	});
	    	add( path_l );   
     	phi = new  JTextField(20);
     		add(new JLabel(Lang.getWord("Secondaire")));
	    	add( phi , "span 2 , pushx , growx"); 
	    	JButton path_p = new JButton("...") ;
	    	path_p.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					   JFileChooser chooser = new JFileChooser();
	                   chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
	                   chooser.showOpenDialog( null );
	                   chooser.showOpenDialog( Main.getPrinc() );
	                   try{
	                	   pathphi = chooser.getSelectedFile().getAbsolutePath() ;
						
	                   }catch( Exception ea ){
	                	   Main.echo("Cancel Selected [add Class]");
	                   }   
				}
	    		
	    	});
	    	add( path_p  );  

	    JButton cree = new JButton( Lang.getWord("Création") );
	    cree.addActionListener( new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String Query = ""  ;
                if( name.getText().isEmpty() ){ 
                	Tail.setError( Lang.getWord("Champ") +" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide")	); 
                   
                }else { 
                	DataBase obj =new DataBase("");
                	 Query = "create database "+name.getText().trim() +"\n";  
                	 if( log.getText() != null && phi.getText() != null ){
                         System.out.println("is Deferent null ");
                         if( log.getText().trim().equalsIgnoreCase( phi.getText().trim() ) && !log.getText().trim().isEmpty() ){
                             Tail.setError( Lang.getWord("Nom")+" "+  Lang.getWord("Principal")+" "+
                            		 		Lang.getWord("Comme")+" "+Lang.getWord("Secondaire")); 
                              

                         }else{ 
                             // Si le logic et phisiqu Defferent
                             if( ! phi.getText().isEmpty()  ){
                                 Query += "On primary\n( Name=\'"+ phi.getText().trim()+"\'"  ;

                                     if(  pathphi != null ){
                                         Query += ",  FileName=\'"+pathphi+ "\\" +phi.getText().trim()+".mdf\'" ;
                                     } 
                                 Query +=")\n";
                             }
                             if( ! log.getText().isEmpty()  ){
                                 Query += "log on\n( Name=\'"+ log.getText().trim() +"\'"  ;

                                     if(  pathlog != null  ){
                                         Query += ",  FileName=\'"+pathlog+ "\\"+log.getText().trim()+".ldf\'" ;
                                     } 
                                 Query +=")\n";
                             }  
                             System.out.println( Query );
                             if( obj.Update(Query)  )
                            	 	Tail.setINF( Lang.getWord("Création")+" "+Lang.getWord("Base de donneé")+" \n"+
                             Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette") );
                         }
                 }
	         }
            
        }
        });
    	add( cree  , "cell 3 4" );   
    	 
	}
}
