package me.elhoussam.lng;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.elhoussam.cmp.Tail;
import me.elhoussam.entry.Main;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class Lang_mod  extends JPanel {
	 protected JComboBox<String>  all_lng = new JComboBox<String>() ;
	
	public Lang_mod(){
		MigLayout lay = new MigLayout("wrap 4"); 
		setLayout ( lay );
		
		all_lng.addItem("Frensh");
		Lang_del.createAll( all_lng );

    	add(new JLabel( Lang.getWord("Langue")+" "+ Lang.getWord("Disponible") )); 

    	

    	JButton ch = new JButton( Lang.getWord("Modifier") );
    	final JLabel inf = new JLabel("");
    	ch.addActionListener( new ActionListener(){
    	
			@Override
			public void actionPerformed(ActionEvent e) {
				final String name = (String) all_lng.getSelectedItem() ;
				Main.echo("langue selected "+name);
				Lang.setLangSelected(name);
				Tail.setINF( Lang.getWord("Appliquer le changement dans le prochain démarrage") );
			    
			}
    		
    	});
    	
    	JButton ref = new JButton( Lang.getWord("Actualiser") ); 
    	ref.addActionListener( new ActionListener(){
        	
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				  all_lng.removeAllItems(); 
    				  all_lng.addItem("Frensh");
    				  
    				  Lang_del.createAll( all_lng );
    			    
    			}
        		
        	});
    	
    	add( all_lng , "span 2 , pushx , growx ");
    	add( ref );
    	add(ch,"cell 3 2");

    	add(  inf  , " span 3");
	}
 

}
