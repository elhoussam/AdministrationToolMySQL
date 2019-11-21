package me.elhoussam.cmp;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Font; 
//import javax.swing.ImageIcon; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import me.elhoussam.entry.Main;
import me.elhoussam.lng.Lang;

@SuppressWarnings("serial")
public class Tail extends JPanel{
	
	//Attribut
	static JTabbedPane a ; 
    static  JTextArea t_sql=null , error=null , information =null; 
    
    // Constructeur
    public Tail(){
    
    	setSize(300,150);
    	setLayout( new BorderLayout());
    	this.setBackground(  new Color( 233, 234, 237)  );
        
    	

    	// Init composent
    	a = new JTabbedPane();
        error  = new JTextArea();
	        error.setEditable(false);
	        error.setFont( new Font("Monospaced", Font.BOLD, 13) );
	        error.setWrapStyleWord(true);
	        error.setForeground( Color.RED );
	    t_sql = new JTextArea();
	        t_sql.setEditable(false);
	        t_sql.setFont( new Font("Monospaced", Font.BOLD, 13) );
	        t_sql.setForeground( new Color(68, 180, 73) );
	        t_sql.setWrapStyleWord(true);
	    information = new JTextArea();
	    	information.setWrapStyleWord(true);
	    	information.setEditable(false);
	    	information.setFont( new Font("Monospaced", Font.BOLD, 13) );
	    	information.setForeground( Color.BLUE );
        
	    // add into JTABBED	
	  
	    /*	
	    ImageIcon img1 = 	new ImageIcon( getClass().getClassLoader().getResource("icons/tail_error.png") ) ;
	    ImageIcon img2 = new ImageIcon( getClass().getClassLoader().getResource("icons/tail_sql.png") ) ;
	    ImageIcon  img3 = new ImageIcon( getClass().getClassLoader().getResource("icons/tail_inf.png") ) ;
	    
	    */
	    
        a.addTab( Lang.getWord("Erreur") ,  null , (new JPanel()).add( new JScrollPane(error) ) , "Error Execute Sql "); 
        a.addTab( Lang.getWord("T_SQL") , null ,(new JPanel()).add( new JScrollPane(t_sql) ) ,  "Query Execute Sql " );
        a.addTab( Lang.getWord("Information") ,  null ,(new JPanel()).add(new JScrollPane(information) ) ,"Error Execute Sql " ); 
        //add into panel
        add( a );
    }
    // activate ongle
    private static void setActiveTab( int rang ){
        a.setSelectedIndex(rang);
    }
    
    // change contain of error tab
    public static void  setError( String newErr ){
    	Main.echo( newErr );
    	if( error != null ) {
    	error.setText( newErr );
        Tail.setActiveTab(0);
    	}
	}
    // change contain of sql tab
    public static void setSQL( String newSQL ){
    	Main.echo( newSQL );
    	if( t_sql != null ) {
    	t_sql.setText(newSQL );
        Tail.setActiveTab(1);
    }}
    // change contain of information tab
    public static void setINF( String newINF ){
    	Main.echo( newINF );
    	if( information != null ) {
    	information.setText(newINF );
        Tail.setActiveTab(2);
    }}
}
