package me.elhoussam.cmp;

import java.awt.BorderLayout;
import java.awt.Color; 
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

import javax.swing.Box; 
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;   
import javax.swing.UIManager;

import me.elhoussam.entry.Main;
import me.elhoussam.lng.Lang;
import me.elhoussam.log.Login;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class Header extends JPanel {
	// Attribut 
	public static JLabel userName = new JLabel("") ;

    //Constructors for HEADER class
	public Header(){  
		//set the img principal of pgm
        setLayout( new BorderLayout() ); 
        setBackground( new Color(213, 215, 217) );
        JLabel img = new JLabel("");
		img.setIcon(new ImageIcon( getClass().getClassLoader().getResource("icons/sqlserver.png") ) );
		
		// information login data , name 
		JPanel inf_login = new JPanel();
		inf_login.setLayout( new BorderLayout() );
		inf_login.setBackground( new Color(213, 215, 217) );
		
		/*
		 * this for name login name 
		 * this data of login
		 *
		 ****/
		userName.setFont( new Font("Monospaced", Font.BOLD, 15) );
		userName.setForeground(new Color( 0, 0, 0));
	
		inf_login.add( userName , BorderLayout.CENTER ); 
		inf_login.add(	new JLabel( "Connect : "+(new Date() ).toString() )	, BorderLayout.SOUTH );
		
		add(img , BorderLayout.WEST ); 	 
		add( inf_login , BorderLayout.EAST ); 	 
		
		
		// Creation pop menu  
		 final JPopupMenu popmenu = new JPopupMenu ();

			JMenuItem conn = new JMenuItem(Lang.getWord("Connecter"),new ImageIcon( getClass().getClassLoader().getResource("icons/head_Login.png") ));
			JMenuItem arbre = new JMenuItem(Lang.getWord("Afficher / Masquer Arbre") ,new ImageIcon( getClass().getClassLoader().getResource("icons/head_Tree.png") ));
			JMenuItem option = new JMenuItem(Lang.getWord("Afficher / Masquer Option"),new ImageIcon( getClass().getClassLoader().getResource("icons/head_hide.png") ));
			JMenuItem about = new JMenuItem(Lang.getWord("A propos"),new ImageIcon( getClass().getClassLoader().getResource("icons/head_about.png") )); 
			JMenuItem aide = new JMenuItem(Lang.getWord("Aîde"),new ImageIcon( getClass().getClassLoader().getResource("icons/head_aide.png") )); 
		 final JMenu theme = new JMenu(Lang.getWord("Théme"));
		 				theme.setIcon( new ImageIcon( getClass().getClassLoader().getResource("icons/head_theme.png") ) );
		// get all Look And Feel in this system
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
	    
		for (final UIManager.LookAndFeelInfo look_actual : looks) {
			
			// Traitement of String for pick just the name of L&F
			      String loook =  String.valueOf( look_actual.getClassName() )  ;
			      String look_feel ="";
			      for( short p = 0 ; p < loook.length() ; p++ ){
			    	  char par = loook.charAt( p );
			    	  if( par =='.' ) look_feel="";
			    	  else look_feel += par ;
			      }
			      look_feel = look_feel.split("LookAndFeel")[0];
			      
		 // creation JCheckBox and add action when click on it 
			      final JCheckBoxMenuItem cp = new JCheckBoxMenuItem( look_feel );
			           cp.addActionListener( new ActionListener( ){
		
						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								// apply this look  when you click 
								UIManager.setLookAndFeel( look_actual.getClassName() );

								//apply look
								Main.apply();  
								
								// make all checkbox off 
								for(short ii = 0 ;  ii <  theme.getItemCount() ; ii++ ) 
										((JCheckBoxMenuItem) theme.getItem( ii ) ).setSelected( false );
								 
								// make the last one selected ON
									cp.setSelected( true ); 
									
							} catch ( Exception e) {
									e.printStackTrace();
							} 
						}
			    	  
			      });
	      theme.add( cp );
	    }
		 
		 // add actions for JMenuItem
		conn.addActionListener( new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) { 
						Main.getPrinc().setEnabled( false );
						new Login( Main.getPrinc() );
					}
		 });
		 arbre.addActionListener( new ActionListener(){
			 	@Override
	            public void actionPerformed(ActionEvent e) {
			 			Main.ToggleHideArb();
	            }
		 });
		 option.addActionListener( new ActionListener(){
			 	@Override
	            public void actionPerformed(ActionEvent e) {
			 			Main.ToggleHideOption();
	            }
		 });
		 about.addActionListener( new ActionListener(){
			 	@Override
	            public void actionPerformed(ActionEvent e) {
			 			// creation JDialog
			 			JDialog abt = new JDialog( Main.getPrinc()  , "about");
			 			abt.setLocationRelativeTo( Main.getPrinc()  );
			 			
			 			abt.setSize(400,252);
			 			
			 			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			    		final Dimension screenSize = toolkit.getScreenSize();
			    		final int x = (screenSize.width - abt.getWidth()) / 2;
			    		final int y = (screenSize.height - abt.getHeight()) / 2;
			    		abt.setLocation(x, y);
			    		
			 			JPanel img = new JPanel( new BorderLayout() );
			 			JLabel lab_img = new JLabel( new ImageIcon( getClass().getClassLoader().getResource("icons/about_image.png") ) );
			 			img.setBackground( Color.WHITE ) ;
			 			img.add( lab_img	, BorderLayout.SOUTH );
			 			
			 			JPanel left = new JPanel(new MigLayout("wrap 1") );
			 			left.add( new JLabel("Universiter Mouhamed Khaider") ); 
			 			left.add(  Box.createVerticalStrut(30)  );
			 			left.add( new JLabel("Administration sql server 2014 V1") );
			 			 

			 			left.add(  Box.createVerticalStrut(35)  );
			 			left.add( new JLabel("Encadreur : ") );
			 			left.add( new JLabel("	1 . ********************* ") );
			 			
			 			left.add(  Box.createVerticalStrut(10)  );
			 			left.add( new JLabel("Realisateur : ") );
			 			left.add( new JLabel("	1 . ******************* ") );
			 			left.add( new JLabel("	1 . ******************* ") );
			 			
			 			abt.getContentPane().setLayout( new BorderLayout()  )	;
			 			abt.add( img , BorderLayout.EAST );
			 			abt.add( left , BorderLayout.WEST );
			 			abt.setVisible( true );
			 			abt.setResizable(false);
	            }
		 });
		 aide.addActionListener( new ActionListener(){
			 	@Override
	            public void actionPerformed(ActionEvent e) {
			 		File f = new File("aide.html");
			 		try{
			 			Desktop.getDesktop().open( f.getAbsoluteFile() );
			 			
			 		}catch( Exception eee ){
			 			Main.echo( eee.toString() );
			 		}
	            }
		 });
		
		 // add JMenuItem , JMenu into  popmenu

		 popmenu.add( conn ); 
		 popmenu.addSeparator();
		 popmenu.add( arbre ); 
		 popmenu.add( option ); 

		 popmenu.addSeparator();
		 popmenu.add( theme );
		 popmenu.add( about );
		 popmenu.add( aide );
	
		 /*
		  * 
		  *  add Mouse Listener for Header 
		  *  when you click right button of mouse 
		  *  	the pop menu show's up
		  *	*/
		 addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
            	  if( me.getButton() == MouseEvent.BUTTON3 ){ // Click with right button 
            		  	
            		  popmenu.show( me.getComponent() , me.getX() , me.getY());
            	  }else{
            		  System.out.println("Header Clicked ");
            	  }
            }
		});
	}
	
	// setter for UserName login
	public static void setUser( String name ){
		userName.setText(name);
	}
}
