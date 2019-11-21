package me.elhoussam.log;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import me.elhoussam.basic.DataBase;
import me.elhoussam.cmp.Header;
import me.elhoussam.cmp.arbre;
import me.elhoussam.lng.Lang;

import javax.swing.ImageIcon; 

import java.awt.Font; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 

@SuppressWarnings("serial")
public class Login extends JDialog {
	
	// Attribut
		private JTextField userField;
		private JPasswordField passField;
		private JLabel ErrLabel = new JLabel("");
		private JFrame frame;
		
	//Constructeur
	public Login(  JFrame fram ) {  
		// super( fram   );
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// init composent and setup some attribut 
		
		
		
		
		this.frame = fram ;  
		this.setAlwaysOnTop( true ); 
		this.setTitle( Lang.getWord("Connectez Exiger") );
		this.setIconImage(  (new ImageIcon( getClass().getClassLoader().getResource("icons/icon_main.png") ) ).getImage()    );
		this.setSize(391, 296);
		this.setResizable( false );
		this.setBackground( Color.WHITE );
		
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - this.getWidth()) / 2;
		final int y = (screenSize.height - this.getHeight()) / 2;
		this.setLocation(x, y);
		
		getContentPane().setLayout(null);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 233, 389, 33);
		buttonPane.setBackground( Color.WHITE );
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		 
		// Creation Button of login
		JButton okButton = new JButton( Lang.getWord("Connecter") );
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
						if( userField.getText().trim().isEmpty() || String.valueOf( passField.getPassword() ).trim().isEmpty() ){
							ErrLabel.setText(	Lang.getWord("il ya un champ vide") );
							
						}else{
							ErrLabel.setText("USER : "+userField.getText().trim()+" PASS : "+String.valueOf( passField.getPassword() ) );
							DataBase obj = new DataBase(userField.getText().trim() , String.valueOf( passField.getPassword() ) );
							if( ! obj.tryConn() ){
								ErrLabel.setText(Lang.getWord("les informations incorrect") );
							 }else{ // connection is goode 
								ErrLabel.setText("Connect with succes");
								dispose();
								frame.setEnabled(true);
								arbre.showDatabase(); 
								// Other Operation
								Header.userName.setText( Lang.getWord("Nom")+ " : "+userField.getText() );
								DataBase.login = true ;
							} 
					        obj.Desconnect();
					         
						}
					}
				});
			
		// make some design 	
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground( Color.white );
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setBounds(0, 0, 451, 100);
		getContentPane().add(panel_2);
		
		JLabel log_head = new JLabel("");
		log_head.setIcon( new ImageIcon( getClass().getClassLoader().getResource("icons/login-header.png") ));
		panel_2.add(log_head);
		
		JLabel userLabel = new JLabel("");
		userLabel.setIcon( new ImageIcon( getClass().getClassLoader().getResource("icons/Login_user.png") ) );
		userLabel.setBounds(93, 130, 30, 28);
		getContentPane().add(userLabel);
		
		JLabel passLabel = new JLabel("");
		passLabel.setIcon(new ImageIcon( getClass().getClassLoader().getResource("icons/Login_lock.png")) );
		passLabel.setBounds(93, 180, 30, 28);
		getContentPane().add(passLabel);
		
		userField = new JTextField();
		userField.setFont(new Font("Monospaced", Font.BOLD, 15));
		userField.setBounds(145, 130, 154, 28);
		getContentPane().add(userField);
		userField.setColumns(20);
		userField.setBorder(null);
		
		passField = new JPasswordField();
		passField.setFont(new Font("Monospaced", Font.PLAIN, 14));
		passField.setColumns(20);
		passField.setBounds(145, 180, 154, 28);
		passField.setBorder(null);
		getContentPane().add(passField);
		getContentPane().setBackground( Color.WHITE );
		
		ErrLabel.setBounds(60, 210, 336, 33);
		ErrLabel.setBackground( Color.BLUE);
		getContentPane().add(ErrLabel);

		this.setVisible(true);
		
		
	}
}
