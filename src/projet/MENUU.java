package projet ; 

import javax.swing.ImageIcon;  
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants; 
import java.awt.Dimension;
import java.awt.Font; 
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.util.Vector;



@SuppressWarnings("serial")
public class MENUU extends JPanel { 
	
	/* INTERIEUR CLASS */
	static class option extends JPanel {
		//  Attribut 
		private static Vector<JPanel> pans = new Vector<JPanel>() ; 
		private	JLabel icon ,  Arrow, lab ;
		private ImageIcon icon_active ;
		
		/**
		 * Create the panel.
		 */
		public option(String Op_name , ImageIcon img ) {
			
			icon_active = img ;
			setPreferredSize( new Dimension(150,30 ) );
			setBackground(new Color(255, 255, 255)); 
			
			// This is Action when Click on it 
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
			
					System.out.println("JPanel Clicked "+((option) e.getSource()).getLab().getText() );
					// Diable all option 
					for( int i = 0 ; i < pans.size() ;  i++){
						// Initialise all Options 
						((option)pans.get(i) ).Disable();
						
					}
					// enable the last selected one
					((option) e.getSource()).Enable();
				}
			});
			
			setLayout(new BorderLayout(0, 0));
			
			icon = new JLabel("");
			icon.setIcon( new ImageIcon( getClass().getResource("icons/menu_close.png") )  );
			icon.setHorizontalAlignment(SwingConstants.RIGHT );
			icon.setBackground(new Color(255, 255, 255));
			 
			add(icon, BorderLayout.WEST);
			
			 lab  = new JLabel( Op_name );
			 lab.setFont(new Font("Arial", Font.PLAIN, 13));
			 lab.setHorizontalAlignment(SwingConstants.CENTER);
			 lab.setBackground(new Color(255, 255, 255));
			
			add(lab, BorderLayout.CENTER); 
			// Save all reference of Object instance
			pans.addElement( this  );
			
			Arrow = new JLabel("");
			Arrow.setVisible(false);
			Arrow.setIcon(new ImageIcon( getClass().getResource("icons/arrow.png") ) );

			Arrow.setHorizontalAlignment( SwingConstants.LEFT );
			add(Arrow, BorderLayout.EAST);
		}
		
		// setter for change the icon
		protected void setIconDefault() { 
			 icon.setIcon( new ImageIcon( getClass().getResource("icons/menu_close.png") ));
			 
		}
		//getter for : kown whose the option selected
		protected JLabel getLab() { 
			return lab ;
		}  
		
		// enable , disable   option panel
		protected void Enable(){ 
			 icon.setIcon(  icon_active );
			 lab.setForeground( Color.WHITE );
			 lab.setFont(new Font("Arial", Font.PLAIN + Font.BOLD , 14)); 
			 Arrow.setVisible(true);
			 this.setBackground(new Color( 233, 234, 237 ) ) ;
		}

		protected void Disable(){ 
			
			this.getLab().setFont( new Font("Arial", Font.PLAIN  , 13) );
			this.getLab().setForeground( Color.BLACK );
			Arrow.setVisible( false );
			this.setBackground(new Color(255, 255, 255));
			this.setIconDefault();
		}
		
	 
	}
	/* -------------------------------------------------------------------------------- */
	 
	/**
	 * Create the JPanel .
	 */
	String tour = "none" ;
	public MENUU() {
			setLayout( new GridLayout(0, 1) );
                        
				final option db = new option( Lang.getWord("Base de donneé"), new ImageIcon( getClass().getResource("icons/menu_database.png") ) );
				 
				db.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						if( ! tour.equals("database") ){

							JTabbedPane dbs = new JTabbedPane();
							dbs.addTab( Lang.getWord("Création") , null , new DB_add() );
							dbs.addTab( Lang.getWord("Modifier") , null , new DB_mod() );
							
							Main.intoMainn( (new JPanel()).add( dbs ) );
							tour = "database" ;
							System.out.println( tour );
						}
					}
				});
				
				option tb = new option( Lang.getWord("Table") , new ImageIcon( getClass().getResource("icons/menu_table.png") ) );
				tb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						if( ! tour.equals("table") ){
							JTabbedPane tps = new JTabbedPane();
							tps.addTab(  Lang.getWord("Création") , null , new TBL_add() );
							tps.addTab( Lang.getWord("Ajouter colonne"), null ,  new TBL_add_col() ); 
							tps.addTab( Lang.getWord("Supprimer ou renommer la colonne"), null ,  new TBL_del_col() ); 
							tps.addTab(  Lang.getWord("Modification de  colonne") , null ,  new TBL_mod() );
							Main.intoMainn( (new JPanel()).add( tps ) );
							tour = "table" ;
							System.out.println( tour );
						}
					}
				});	
				option sch = new option( Lang.getWord("Schéma") , new ImageIcon( getClass().getResource("icons/menu_schema.png") ) );
				sch.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						if( ! tour.equals("schema") ){
							JTabbedPane tps = new JTabbedPane();
							tps.addTab(Lang.getWord("Création"), null , new SCH_add() ); 
							tps.addTab( Lang.getWord("Supprimer") , null ,  new SCH_del() );
							
							Main.intoMainn( (new JPanel()).add( tps ) );
							tour = "schema" ;
							System.out.println( tour );
						}
					}
				});
				option log = new option(  Lang.getWord("Log in") ,new ImageIcon( getClass().getResource("icons/menu_user.png") ) ) ;
				
				log.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						if( ! tour.equals("login") ){
							JTabbedPane tps = new JTabbedPane();
							tps.addTab(Lang.getWord("Création"), null , new LOG_add() );
							tps.addTab( Lang.getWord("Modifier"), null ,  new LOG_mod() );
							tps.addTab( Lang.getWord("Supprimer"), null ,  new LOG_del() );
							Main.intoMainn( (new JPanel()).add( tps ) );
							tour = "login";
							System.out.println( tour );
						}
					}
				});
				option lng = new option( Lang.getWord("Langue"),new ImageIcon( getClass().getResource("icons/menu_langue.png") ) ) ;
				lng.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						if( ! tour.equals("langue") ){
							JTabbedPane tps = new JTabbedPane();
							tps.addTab(Lang.getWord("Création"), null , new Lang_add() );
							tps.addTab(Lang.getWord("Modifier"), null , new Lang_mod() ); 
							tps.addTab(Lang.getWord("Supprimer"), null , new Lang_del() ); 
							Main.intoMainn( (new JPanel()).add( tps ) );
							tour = "langue" ;
							System.out.println( tour );
						}
					}
				});
				option rol = new option( Lang.getWord("Rôle") ,new ImageIcon( getClass().getResource("icons/menu_user.png") ) ) ;
				rol.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						if( ! tour.equals("role") ){
							JTabbedPane tps = new JTabbedPane();
							tps.addTab(Lang.getWord("Création"), null , new ROL_add() );
							tps.addTab(Lang.getWord("Modifier"), null ,  new ROL_mod() );
							tps.addTab(Lang.getWord("Supprimer"), null ,  new ROL_del() );

							Main.intoMainn( (new JPanel()).add( tps ) );
							tour = "role" ;
							System.out.println( tour );
						}
					}
				});
				
					add( db );	
					add( tb );	
					add( sch );	
					add( log );	
					add( rol );
					add( lng );
		setVisible(true);
		
	}

}
