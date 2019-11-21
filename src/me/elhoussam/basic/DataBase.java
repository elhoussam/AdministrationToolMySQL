
package me.elhoussam.basic;

import java.sql.Connection;
import java.sql.DatabaseMetaData; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement;

import me.elhoussam.cmp.Tail;
import me.elhoussam.entry.Main;


public   class DataBase {
		public static boolean login = false ;
		private String NameDB ;
		static String UserDB ="ROOT" ; // sa : is login -- myowndb is user
		private static String PassDB ="samsam" ;
    @SuppressWarnings("unused")
	private final DatabaseMetaData Metadata;
    private Connection link ; 
    private ResultSet result ;
    private Statement stmt ;

        public DataBase(String user , String pass ){
        	Metadata = null ;
            link = null ;
            NameDB = "";
            DataBase.UserDB = user ; // user
            DataBase.PassDB = pass ; //pass
        }
        public DataBase( String name  ) {
        	Metadata = null ;
            link = null ;
       		this.NameDB = name ;
        }
        // 1 Get the Driver ready
		public static void LoadDriver(){
            try{
            	// Load the Driver of connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //    System.out.println("Driver is Loaded");
                
            }catch( Exception e){
                System.out.println("Exception class Database Construction "+e);
            }
        }
        // 2 Get a connection to database 
        private boolean  Connect( )   { 
          try{ 
             String lien =  "jdbc:sqlserver://localhost" ;
             if( !this.NameDB.isEmpty() )
            	 lien +=  ";databasename="+this.NameDB ; 
           //  System.out.println("Connect with [ "+ lien +" ]");
             this.link = DriverManager.getConnection( lien ,DataBase.UserDB,DataBase.PassDB);
            // System.out.println("*Connect is done :)");
             return true ;
          }catch( Exception e ){
              Tail.setError("*Connect is not Work ;( :\n"+e.toString()); 
              return false ;
          } 
        }
        // 1.2. Get a connection to database 
        public void Desconnect( )   { 
          try{  
        	  
        	 if( result != null )this.result.close();
        	 if( stmt != null ) this.stmt.close();
             if( link != null) link.close();
             //System.out.println("*Deconnect is done :)");
          }catch(SQLException e ){

              Tail.setError("*Deconnect is not Work ;( :\n"+e);
              System.exit( 1 );
          } 
        }
        public Connection getConn(){
            return this.link ;
        }
        public ResultSet getResult(){
            return this.result ;
        }
        public Statement getStatement(){
            return this.stmt ;
        }
        // 2 . Setter Methode 
        public void SetNameDB(String name ){
            this.NameDB = name ;
        } 
        //  . Reconnect with new Database 
        public void Reconnect( String name ){
            this.Desconnect();
            this.SetNameDB(name);
            this.Connect();
        }
        // 3 . Execute the query   
        public boolean Execute(String query , boolean show){
        	this.Connect();
            try{
            
                this.stmt = this.link.createStatement();
                //System.out.println("CREATE STATEMENT IN EXECUTE ");
                this.result = this.stmt.executeQuery(query);
                //System.out.println("CREATE RESULT IN EXECUTE ");
                if( show ) Tail.setSQL( query );
                return true;
            }catch( Exception e){
            	//System.out.println("METHOD :"+);	
            	e.printStackTrace(); 
            	if( show )  Tail.setError( "Execute Methode in Database Class ["+e.getMessage()+"]");
            	 return false ;
            }
            
        }
        public boolean Update( String query ){
        	this.Connect();
            System.out.println(" METHODE UPDATE ");
            try{
            
                this.stmt = this.link.createStatement();

                //System.out.println("CREATE STATEMENT IN UPDATE ");
                this.stmt.executeUpdate(query);

                //System.out.println("  UPDATE DONE ;)");
              //  if( show )  Tail.setSQL( query );
                return true ;
            }catch( Exception e){
            //	if( show ) Tail.setError("PROBLEM IN update Methode in Database "+e);
            	Main.echo( e.toString() );
            	return false ;
            }finally{
            	this.Desconnect();
            	
            }
        }
        public boolean tryConn(){
        	return this.Connect() ;
        }
        // 4 . get Result of tables 
        public void tableOf( String dbname){
        	this.Connect();
        	try{   
                this.Execute("select name from sys.tables ",true); 
                 
            }catch( Exception e){
                System.out.printf("There is Exception TABLE OF : %s\n",e) ; 
            }
            }
}
        
 