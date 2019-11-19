package projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser; 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField; 

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DB_mod extends JPanel {
	
	private String path_l;

	
	public DB_mod(){ 
    	setLayout (  new MigLayout("wrap 4") );
    	path_l = " ";
    	final JComboBox<String>  alldb = createAll();
    	JButton mod = new JButton( Lang.getWord("Modifier") );
    	final JTextField newname = new JTextField(20);
    	newname.setText(" ");
    	JButton info = new JButton( Lang.getWord("Information") );
    	JButton path = new JButton("...");
    	
    	add( new JLabel( Lang.getWord("Sélectionner")  ) , "cell 2 2 ,pushx , growx");

    	info.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String n = (String) alldb.getSelectedItem() ;
				DB_info dialog = new DB_info( n );
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
    		
    	});
    	
    	
    	add( alldb ,"cell 3 2 , pushx , growx" );
    	add( info ,   "cell 4 2");
    	mod.addActionListener( new ActionListener(){
    		@Override
			public void actionPerformed(ActionEvent arg0) {
				
    			String sql ="ALTER DATABASE "+ alldb.getSelectedItem() +" MODIFY FILE (";
				if( ! newname.getText().trim().isEmpty()  ){
					DataBase obj = new DataBase( (String)alldb.getSelectedItem() );
					obj.Execute("Select name from sys.database_files ",true);
					try {
						Main.echo("try bloc");
							obj.getResult().next() ;
							sql += "NAME = "+ obj.getResult().getString("name") +", NEWNAME =" +newname.getText().trim() ;
							obj.Desconnect();

							Main.echo("try Deconnect , sql for now : "+ sql );
							// if spicify the path
							if( ! path_l.trim().isEmpty()  ){
								sql +=" ,\nFILENAME = \'"+path_l +"\')\n";
							}else sql +=")";
							
							if( obj.Update( sql ) )
								Tail.setINF(  Lang.getWord("Modifier")+" "+Lang.getWord("Base de donneé")+" \n"+ 
										Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette") );
	                         
							//obj.Desconnect(); 
							
							Main.echo("fin bloc try , sql for now : "+ sql );
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					 Tail.setError(  Lang.getWord("Champ")+" "+Lang.getWord("Secondaire")+" "+Lang.getWord("Est Vide") );
				}
			}
    	});
    	add( new JLabel( Lang.getWord("Principal") ) , "cell 2 3");
    	add( newname , "cell 3 3") ;
    	path.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				   JFileChooser chooser = new JFileChooser();
                   chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                   
                   chooser.showOpenDialog( Main.getPrinc() );
                   try{
                   path_l = chooser.getSelectedFile().getAbsolutePath() ;
                   }catch( Exception ea ){
                	   Main.echo("Cancel Selected [Change Class]");
                   }
                   System.out.println( path_l );
				
			}
    		
    	});
    	add( path , "cell 4 3");
    	add( mod ,"cell 4 5" );
    	
	}
	private JComboBox<String> createAll(){
		JComboBox<String> a = new  JComboBox<String>();
		DataBase obj = new DataBase("");
		obj.Execute("Select name from sys.databases ",true);
		try{
		while( obj.getResult().next() ){
			a.addItem( obj.getResult().getString("name") );
		}
		}catch(Exception e){
			
		}
		return a;
		
	}
}
