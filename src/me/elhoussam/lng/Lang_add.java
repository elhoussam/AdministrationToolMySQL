package me.elhoussam.lng;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.elhoussam.basic.DataBase;
import me.elhoussam.cmp.Tail;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class Lang_add extends JPanel {
	 
	JTextField path_fl = new JTextField(20),
			name_lang = new JTextField(20) ;
	
	public Lang_add(){
		MigLayout lay = new MigLayout("wrap 4"); 
		setLayout ( lay );
		

    	add(new JLabel( Lang.getWord("Nom") )	 );
    	add( name_lang 	,"span 2 , pushx , growx , wrap"  );
    	
    	add(new JLabel( Lang.getWord("Choisser un fichier") )	 );

    	JButton pick = new JButton("...");
	    	pick.addActionListener( new ActionListener(){
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					 JFileChooser file = new JFileChooser();
					 
					 file.addChoosableFileFilter( new FileNameExtensionFilter("Simple Text","txt")  );
	    			 if( file.showDialog( null , "Open file") == JFileChooser.APPROVE_OPTION ){
	    				 File f = file.getSelectedFile() ; 
	    				 path_fl.setText(  f.getAbsolutePath() ); 
	    				 if(  !  path_fl.getText().endsWith(".txt") ){
	    					 path_fl.setText("");
	    				 }
	    			 }
				    
				}
	    		
	    	});
    	path_fl.setEditable(false);
    	add( path_fl  ,"pushx , growx " );
    	add( pick );
	
    	JButton imp = new JButton( Lang.getWord("Importer") );
    	imp.addActionListener( new ActionListener(){
        	
			

			@Override
			public void actionPerformed(ActionEvent e) {
				if( name_lang.getText().trim().isEmpty() ){
					Tail.setError( Lang.getWord("Champ") +" "+Lang.getWord("Nom")+" "+Lang.getWord("Est Vide")	); 
	                   
				}else
				if( !path_fl.getText().isEmpty()  ){
					FileInputStream input = null;
					BufferedReader in = null ;
					try {
						File f = new File( path_fl.getText() );
						input = new FileInputStream( f );
						//out.write(str.getBytes("windows-1256"));
				
						in = new BufferedReader(
							 	new InputStreamReader( input , "UTF8")
							 );
						String str ;
						Vector<String> mot = new Vector<String>();
						while ((str = in.readLine()) != null) {
							if( ! str.trim().isEmpty() ){
								mot.add( str.trim() ); 
							    System.out.println("("+str.trim()+")");
							}
						}
						
						System.out.println("Nombre des mots :"+mot.size()+" : "+mot.get(3)	);
						
						// Number word in this  file
						if( mot.size() != 69 ){
							Tail.setError(  Lang.getWord("Erreur fichier : Structure inconnu") );
							mot.clear();
						}else{
							Tail.setINF( Lang.getWord("Attend quelque second")  );
							// Query for add this ligne
							 DataBase obj = new DataBase("myowndb");
							String Query = "ALTER TABLE langue ADD "+ name_lang.getText().trim()
							+"	[varchar] (50)  ";
							System.out.println( Query );
							if( obj.Update(Query) ){
									int ind = 0 ;
									 
									Query="";
									while( ind < mot.size() ){
										//UPDATE [dbo].[langue]   SET [En] = 'ziga' WHERE
										Query="UPDATE [dbo].[langue] SET "+  name_lang.getText().trim() +" = "+
												" \'"+ mot.elementAt( ind )+"\'  WHERE id="+(ind+1);
										obj.Update(Query);
										System.out.println(ind+" : "+ Query ) ;
										
									ind++;
									}
		
									//System.out.println(" Query : "+Query);
									Tail.setINF(  Lang.getWord("Importation terminer") );
							}
						}
						
					} catch ( Exception e1) { 
						e1.printStackTrace();
					}
					
				}else{
					Tail.setError( Lang.getWord("Sélectionner un fichier") );
				}
			    
			}
    		
    	});
    	add( imp );
	
	}
}