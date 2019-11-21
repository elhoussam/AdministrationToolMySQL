package me.elhoussam.db;

import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
 


import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.elhoussam.basic.DataBase;
import me.elhoussam.lng.Lang;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DB_info extends JDialog {

	private final JPanel contentPanel = new JPanel();
 
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public DB_info( String name ) {
		
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - this.getWidth()) / 2;
		final int y = (screenSize.height - this.getHeight()) / 2;
		this.setLocation(x, y);
		
		
		setTitle( Lang.getWord("Information")+" : "+ name );
		DataBase obj = new DataBase(name );
		obj.Execute("Select * from sys.database_files ",true);
		this.setLocationRelativeTo( null );
		setBounds(100, 100, 477, 301);
		this.setResizable( false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblFileid = new JLabel( Lang.getWord("N dossier"));
		lblFileid.setBounds(21, 58, 57, 14);
		contentPanel.add(lblFileid);
		
		JLabel lblName = new JLabel( Lang.getWord("Nom"));
		lblName.setBounds(21, 83, 57, 14);
		contentPanel.add(lblName);
		
		JLabel lblTypeDesc = new JLabel( Lang.getWord("Type"));
		lblTypeDesc.setBounds(21, 106, 68, 14);
		contentPanel.add(lblTypeDesc);
		
		JLabel lblPhyname = new JLabel( Lang.getWord("Taille")  );
		lblPhyname.setBounds(21, 131, 57, 14);
		contentPanel.add(lblPhyname);
		
		JLabel lblMaxsize = new JLabel( Lang.getWord("Max_Taille") );
		lblMaxsize.setBounds(21, 156, 68, 14);
		contentPanel.add(lblMaxsize);
		
		JLabel lblPath = new JLabel( Lang.getWord("Chemin") );
		lblPath.setBounds(10, 204, 68, 14);
		contentPanel.add(lblPath);
		
		
		try{
			obj.getResult().next() ;
						JLabel id1 = new JLabel(obj.getResult().getString("file_id") ); //obj.getResult().getString("file_id") 
						
						id1.setBounds(118, 58, 82, 14);
						contentPanel.add(id1); 
						
						JLabel name1 = new JLabel( );
						name1.setText( obj.getResult().getString("name") );
						name1.setBounds(118, 83, 82, 14);
						contentPanel.add(name1); 
						
						JLabel type1 = new JLabel(obj.getResult().getString("type_desc"));
						type1.setBounds(118, 106, 82, 14);
						contentPanel.add(type1); 
						
						JLabel size1 = new JLabel(obj.getResult().getString("size"));
						size1.setBounds(118, 131, 82, 14);
						contentPanel.add(size1); 
						
						JLabel max1 = new JLabel(obj.getResult().getString("max_size"));
						max1.setBounds(118, 156, 82, 14);
						contentPanel.add(max1); 
						
						JLabel path1 = new JLabel(obj.getResult().getString("physical_name") );
						path1.setToolTipText( obj.getResult().getString("physical_name") );
						path1.setBounds(97, 204, 372, 14);
						contentPanel.add(path1); 
						
						

						obj.getResult().next() ;
						
						
						JLabel lblNewLabel_2 = new JLabel(  Lang.getWord("Principal") );
						lblNewLabel_2.setBounds(118, 33, 46, 14);
						contentPanel.add(lblNewLabel_2); 
						
						JLabel label = new JLabel( Lang.getWord("Secondaire") );
						label.setBounds(320, 33, 46, 14);
						contentPanel.add(label); 
						
						JLabel id2 = new JLabel(obj.getResult().getString("file_id"));
						id2.setBounds(320, 58, 76, 14);
						contentPanel.add(id2); 
						
						JLabel name2 = new JLabel( obj.getResult().getString("name") );
						name2.setBounds(320, 83, 76, 14);
						contentPanel.add(name2); 
						
						JLabel type2 = new JLabel(obj.getResult().getString("type_desc"));
						type2.setBounds(320, 106, 76, 14);
						contentPanel.add(type2); 
						
						JLabel size2 = new JLabel(obj.getResult().getString("size"));
						size2.setBounds(320, 131, 76, 14);
						contentPanel.add(size2); 
						
						JLabel max2 = new JLabel(obj.getResult().getString("max_size"));
						max2.setBounds(320, 156, 76, 14);
						contentPanel.add(max2); 
						
						JLabel path2 = new JLabel(obj.getResult().getString("physical_name"));
						path2.setBounds(97, 229, 372, 14);
						path2.setToolTipText( obj.getResult().getString("physical_name") );
						contentPanel.add(path2);
						
						JLabel lblPathlogique = new JLabel( Lang.getWord("Chemin") );
						lblPathlogique.setBounds(10, 229, 68, 14);
						contentPanel.add(lblPathlogique);
		}catch( Exception e){
			System.out.println("EXC : "+e);
		}
	}
}
