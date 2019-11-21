package me.elhoussam.lng;
   
import java.util.Vector;

import me.elhoussam.basic.DataBase;
import me.elhoussam.entry.Main;
 

public class Lang {
	 
	private static Vector<String> fr ;
	private static Vector<String> acc_l;
	//Default lang  , you can modify the lang 
	static String lang_acc ="Frensh" ; // Frensh  
	 
        
        
        private static boolean isHere( String lang ){
            DataBase obj = new DataBase("myowndb");
		obj.Execute("select COLUMN_NAME as name from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=\'langue\' ",false);
		try{
                    while( obj.getResult().next() ){
                            if( !obj.getResult().getString("name").equals("id") )
                                if( obj.getResult().getString("name").equals( lang ) ){
                                        return true ;
                                }  
                    }
                    
		}catch(Exception e){
                    Main.echo( e.toString() );
		} 
                return false ;
        }
	public static void InitLang(   ){ 
                Main.echo("Init Lang");
                // test si lang exist or not 
                lang_acc = getLangSelected() ;  
                if( ! isHere( lang_acc ) ) lang_acc ="Frensh" ;
                
                Main.echo("lang selected "+ lang_acc );
		fr = new Vector<String>();
		InitFR();
		acc_l = new Vector<String>();
		getLang();
	}
	// just for testing
	public Vector<String> getFR(){
		return fr ;
	}
	// intialisation of lang FR
	private static void InitFR(){
		fr.add("Outil Admin Sql Server 2014");
		fr.add("Nom");
		fr.add("Connecter");
		fr.add("Afficher / Masquer Arbre");
		fr.add("Afficher / Masquer Option");
		fr.add("Théme");
		fr.add("A propos");
		fr.add("Aîde");
		fr.add("Connectez Exiger");
		fr.add("il ya un champ vide");
		fr.add("les informations incorrect");
		fr.add("Base de donneé");
		fr.add("Table");
		fr.add("Schéma");
		fr.add("Log in");
		fr.add("Rôle");
		fr.add("Langue");
		fr.add("Erreur");
		fr.add("T_SQL");
		fr.add("Information");
		fr.add("Renommer");
		fr.add("Supprimer");
		fr.add("Création");
		fr.add("Principal");
		fr.add("Secondaire");
		fr.add("Modifier");
		fr.add("Sélectionner");
		fr.add("N dossier");
		fr.add("Type");
		fr.add("Taille");
		fr.add("Max_Taille");
		fr.add("Chemin");
		fr.add("Cliquez ici pour");
		fr.add("Ajouter colonne");
		fr.add("Supprimer ou renommer la colonne");
		fr.add("Modification de  colonne");
		fr.add("Null");
		fr.add("Cle primaire");
		fr.add("Incrémentation automatique");
		fr.add("Actualiser");
		fr.add("Colonne");
		fr.add("Autorisation");
		fr.add("Par défaut");
		fr.add("Mot de pass");
		fr.add("Serveur");
		fr.add("Nouveau");
		fr.add("Disponible");
		fr.add("Appliquer le changement dans le prochain démarrage");
		fr.add("Champ");
		fr.add("Est Vide");
		fr.add("Comme");
		fr.add("Ongle T-SQL");
		fr.add("Pour consulter votre requette");
		fr.add("Est ce que vous etes sûr");
		fr.add("Et apres");
		fr.add("Active");
		fr.add("Disactive");
		fr.add("Ligne");
		fr.add("il y a");
		fr.add("Nullable");
		fr.add("Dupliqué");
		fr.add("Différent Integer");
		fr.add("Choisser un fichier");
		fr.add("Importer");
		fr.add("Erreur fichier : Structure inconnu");
		fr.add("Attend quelque second");
		fr.add("Importation terminer");
		fr.add("Sélectionner un fichier");
		fr.add("Cliquez pour");

	}
	// for return word spesify			getWord("Outil Admin Sql Server 2014")
	public static String getWord(String word){
		if(   lang_acc.equals("Frensh") ) {
			 return word ;
		}else{
			
			int index =  fr.indexOf( word )  ; 

			Main.echo(index+" : "+word);
			String name = acc_l.elementAt( index );
			return name ;
		}
	}
	// for get all word of lang choix
	private static String getLangSelected(){
		DataBase obj = new DataBase("myowndb");
		obj.Execute("SELECT name  FROM lang_select   where id =0", false);
		try {
			obj.getResult().next();
			return obj.getResult().getString("name");
			
		} catch (Exception e) { 
			e.printStackTrace();
			return new String("Frensh") ;
		}
		
	}
	
	public static void setLangSelected( String  lang ){ 
		DataBase obj = new DataBase("myowndb");
		obj.Update("UPDATE  lang_select  SET   name  = \'"+ lang +"\' WHERE id = 0 ");
	}
	
	
	public static void getLang(){

		if( ! lang_acc.equals("Frensh") ) {
				Main.echo("Is not frensh ");
				DataBase obj = new DataBase("myowndb");
				Main.echo("Select "+ lang_acc +" from langue ");
				obj.Execute("Select "+ lang_acc +" from langue ",false);
				try {

					while( obj.getResult().next() ){
						Main.echo( obj.getResult().getString( lang_acc ) );
						Lang.acc_l.add(	obj.getResult().getString( lang_acc ) );
					}
				} catch (Exception e) { 
					Main.echo("Acune problem inchallah  : "+e);
				}
		}
	}
	/*
	  
 	 public static void main(String[] args){
 
         Lang a = new Lang();
 	    for( int i = 0 ; i < a.getFR().size() ; i++	){
 	    	String name = a.getFR().elementAt( i ) ; 
 	    	System.out.println((i+1)+" )" + (name+" ") +"|"+ Lang.getWord( name ) );
 	    }
    }
    */
	
} 
