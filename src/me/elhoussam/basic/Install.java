package me.elhoussam.basic;

import me.elhoussam.entry.Main;

public class Install {

	public static void main( String [] args ){
		String query="create database myowndb";
		DataBase obj = new DataBase("");
		if( ! obj.Update( query ) ){
			query="drop database myowndb";
			obj.Update( query );
			Main.echo("drop DataBase : "+query);
		}else{	

			//Creation database
					Main.echo("Created DataBase : "+query);
					
					query ="CREATE TABLE lang_select("
									+"[id] [int] NOT NULL,"
									+"[name] [varchar](50) NOT NULL )" ;
					
					obj.SetNameDB("myowndb");
		
			//create first table	  	
					if( obj.Update( query ) ){
						Main.echo("Created Table of Selected Langue ");
						
			// inset into first table			
						query ="INSERT INTO  lang_select"+
									"([id] ,[name])"+
									"VALUES  ( '0' , 'Frensh')";
						if( obj.Update( query ) ){
							Main.echo("insert into  Table of Selected Langue ");
						}
						
					}
				
			// create seconde table 		
					query="CREATE TABLE langue("
								+"[id] [int] IDENTITY(1,1) NOT NULL ," 
								+"[lng] [varchar](50) NULL	)";
					if( obj.Update( query ) ){
						Main.echo("Created Table of  Langue : "+query);
						
			// insert into table 			
						query ="INSERT INTO langue  ([lng])"
									+"VALUES ('fransh')";
						
							
							for ( short i = 1 ; i <= 69 ; i++){
								if( obj.Update( query ) ){
										Main.echo( i+"	: insert into  Table of Selected Langue ");
								}
							}
						
					}
					
					query="ALTER TABLE langue DROP COLUMN lng";
					if( obj.Update( query ) ){
						Main.echo("Created Table of  Langue : "+query);
					}
			// create Thrid table type 
					query="CREATE TABLE [dbo].[type]("+
								"[name] [varchar](10) NOT NULL)";
					if( obj.Update( query ) ){
						Main.echo("Created Table of  type : "+query);
					}

					query="INSERT INTO [dbo].[type]   ([name])"+
						   "VALUES (\'";
					String [] key = {
							"int","smallint","tinyint",
							"bigint","bit","char","Decimal",
							"varchar","nchar","nvarchar",
							"date","time","ntext",
							
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
