package projet;

public class Install {
  static String langue [] = {"Outil Admin Sql Server 2014 ",
      "Nom",
      "Connecter",
      "Afficher / Masquer Arbre",
      "Afficher / Masquer Option" ,
      "Thème",
      "A propos",
      "Aîde",
      "Connectez Exiger",
      "il ya un champ vide",
      "les informations incorrect",
      "Base de donneé" ,
      "Table",
      "Schéma",
      "Log in",
      "Rôle",
      "Langue",
      "Erreur",
      "T_SQL",
      "Information",
      "Renommer",
      "Supprimer",
      "Création",
      "principal",
      "secondaire",
      "Modifier",
      "Sélectionner",
      "N dossier",
      "Type",
      "taille",
      "Max_Taille",
      "chemin",
      "Cliquez ici pour",
      "Ajouter colonne",
      "Supprimer ou renommer la colonne",
      "Modification de  colonne",
      "Null",
      "cle primaire",
      "Incrémentation automatique",
      "Actualiser",
      "Colonne",
      "Autorisation",
      "Par défaut",
      "Mot de pass",
      "Serveur",
      "Nouveau",
      "Disponible",
      "Appliquer le changement dans le prochain démarrage",
      "Champ",
      "Est Vide",
      "Comme",
      "Ongle T-SQL",
      "pour consulter votre requette",
      "Est ce que vous etes sûr",
      "Et apres",
      "Active",
      "Disactive",
      "Ligne",
      "il y a",
      "Nullable",
      "dupliqué",
      "Différent Integer",
      "Choisser un fichier",
      "importer",
      "Erreur fichier : Structure inconnu",
      "Attend quelque second",
      "Importation terminer",
      "Sélectionner un fichier",
      "Cliquez pour"
  };
  public static void main( String [] args ){
    //Tracking.setFolderName("folder");

    String query="create database pfe";
    DataBase obj = new DataBase("");
    if( ! obj.Update( query ) ){
      query="drop database pfe";
      obj.Update( query );
      Main.echo("drop DataBase : "+query);
    }else{

      //Creation database
      Main.echo("Created DataBase : "+query);

      query ="CREATE TABLE lang_select("
          +"id INT(6) AUTO_INCREMENT PRIMARY KEY,"
          +"name VARCHAR(30) NOT NULL )" ;

      obj.SetNameDB("pfe");

      //create first table
      if( obj.Update( query ) ){
        Main.echo("Created Table of Selected Langue ");

        // inset into first table
        query ="INSERT INTO  lang_select"+
            "(id ,name)"+
            "VALUES  ( '0' , 'Frensh')";
        if( obj.Update( query ) ){
          Main.echo("insert into  Table of Selected Langue ");
        }

      }

      // create seconde table
      query="CREATE TABLE langue("
          +"id INT(6) AUTO_INCREMENT PRIMARY KEY  ,"
          +"lng VARCHAR(50) NOT NULL	)";
      if( obj.Update( query ) ){
        Main.echo("Created Table of  Langue : "+query);

        // insert into table
        query ="INSERT INTO langue  (id,lng)";



        for ( short i = 1 ; i <= 69 ; i++){
          String str = query+" VALUES ("+i+",'"+langue[i-1]+"')"   ;
          if( obj.Update(  str ) ){
            Main.echo( str );
          }
        }


      }

      query="ALTER TABLE langue DROP COLUMN lng";
      if( obj.Update( query ) ){
        Main.echo("Created Table of  Langue : "+query);
      }
      // create Thrid table type
      query="CREATE TABLE type("+
          " name varchar(10) NOT NULL)";
      if( obj.Update( query ) ){
        Main.echo("Created Table of  type : "+query);
      }

      query="INSERT INTO type (name)"+
          "VALUES (\'";
      String [] key = {
          "int","smallint","tinyint",
          "bigint","boolean","char","Decimal",
          "varchar", "date","time","tinytext",
          "text",

      };

      for ( String one : key ){
        if( obj.Update(query+ one +"\')" ) ){
          Main.echo("Query insertion : "+query+ one +"\')");
        }
      }





      // Insertion of table type



    }


  }
}
