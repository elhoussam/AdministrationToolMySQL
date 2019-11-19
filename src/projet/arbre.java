package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


@SuppressWarnings("serial")
public class arbre extends JPanel {
  // Attribut
  private static JTree arbre ;
  private boolean database ;
  private String SelectNode , SelectParent ;
  static DefaultMutableTreeNode root , SelectedNode ;


  public arbre(){
    this.setBackground(  new Color( 233, 234, 237)  );
    this.setLayout( new BorderLayout());
    root  = new DefaultMutableTreeNode("Databases" );

    // creation refresh :  get list of database
    JPanel option = new JPanel();
    option.setLayout( new BorderLayout());
    JLabel ref = new JLabel("");
    ref.setIcon( new ImageIcon( getClass().getResource("icons/tree_refresh.png") ));
    ref.setToolTipText( Lang.getWord("Cliquez ici pour")+" "+Lang.getWord("Actualiser") );
    ref.addMouseListener( new MouseAdapter(){
      @Override
      public void mouseClicked( MouseEvent me ){
        showDatabase();
      }
    });
    option.add( ref );

    // make NODE OF JTREE with icon
    arbre = new JTree( root , true );
    DefaultTreeCellRenderer CellRender = new DefaultTreeCellRenderer();
    CellRender.setClosedIcon( new ImageIcon( getClass().getResource("icons/tree_database.png") ) );
    CellRender.setOpenIcon( new ImageIcon( getClass().getResource("icons/tree_database.png") ) );
    CellRender.setLeafIcon( new ImageIcon( getClass().getResource("icons/tree_table.png") ) );

    arbre.setCellRenderer( CellRender );
    arbre.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION);

    // Create JPopupMenu  ( rename , delete )
    final JPopupMenu popmenu = new JPopupMenu ();
    JMenuItem rename = new JMenuItem( Lang.getWord("Renommer") );
    rename.setIcon( new ImageIcon( getClass().getResource("icons/rename.png") ) );
    rename.addActionListener( new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Database Selected for rename : "+SelectNode );
        //JOptionPane.showInputDialog(Main ,"these wooords","Title de JDialog", JOptionPane.QUESTION_MESSAGE );
        String nv_nom = JOptionPane.showInputDialog(Lang.getWord("Nouveau")+" " +Lang.getWord("Nom"));
        if( nv_nom != null ){
          String query ;
          DataBase obj ;
          // test rename db or table
          if( !database ) { obj = new DataBase( SelectParent );

          // rename the object db , table
          if( database )  	query = "RENAME DATABASE "+SelectNode+" TO "+nv_nom;//"ALTER DATABASE "+SelectNode+" Modify Name="+nv_nom  ;
          else   query = "RENAME TABLE "+SelectNode+" TO "+nv_nom;//"exec sp_rename "+ SelectNode +" , "+ nv_nom;
          if(obj.Update( query )  )
            Tail.setINF(  Lang.getWord("Renommer")+" "+Lang.getWord("Base de donneé")+" \n"+
                Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette") );

          //SelectedNode.setUserObject( nv_nom );
          obj.Desconnect();
          }
        }

      }

    });
    popmenu.add( rename );

    JMenuItem delete = new JMenuItem( Lang.getWord("Supprimer"));
    delete.setIcon( new ImageIcon( getClass().getResource("icons/delete.png") ) );
    delete.addActionListener( new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent arg0) {
        if( SelectParent.equals("Databases")) SelectParent ="" ;
        DataBase obj = new DataBase(SelectParent);

        String query ="";
        // Decide if database or table
        if( database ) query= "DROP DATABASE "+SelectNode ;
        else query=" DROP TABLE  "+SelectNode ;
        if(  JOptionPane.showConfirmDialog(null ,	Lang.getWord("Est ce que vous etes sûr")+" "+Lang.getWord("Supprimer")) == 0 )// OK
          if(obj.Update( query )  )
            Tail.setINF(  Lang.getWord("Supprimer")+" "+Lang.getWord("Base de donneé")+" \n"+
                Lang.getWord("Cliquez pour")+" "+  Lang.getWord("Ongle T-SQL")+" "+ Lang.getWord("Pour consulter votre requette") );

        obj.Desconnect();
      }
    });
    popmenu.add( delete );

    arbre.setRootVisible(false);
    arbre.setShowsRootHandles(true);

    /* MOUSE LISTENER of JTree
     * 
     *   if click over NODE show's up the pop menu
     *   and get information about this NODE (db , table )
     ***/
    arbre.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent me) {
        // get the information about Node Clicked
        String pathSelect ="";
        DefaultMutableTreeNode  selectedNode = null  ;
        TreePath tp = arbre.getPathForLocation(me.getX(), me.getY())  ;
        if (tp != null){
          arbre.setSelectionPath(tp );
          pathSelect = tp.toString() ;
          selectedNode = ( DefaultMutableTreeNode ) arbre.getLastSelectedPathComponent() ;
          SelectedNode = selectedNode ;
        }
        /*
         * Checks if Database Or Table
         * treatment String  : is table if , more than one
         * 						else is datbase
         ***/
        byte count = 0 ;
        for( int i = 0 ; i < pathSelect.length() ; i ++ ){
          if( pathSelect.charAt( i ) == ',' ) count++ ;
        }
        if( count == 1 ) database = true ;
        else database = false ; // is table

        /*
         * if click the right button of the mouse
         * get inf + show the popmenu
         * */
        if( me.getButton() == MouseEvent.BUTTON3 ){ // Click with right button

          if( selectedNode != null  ){
            // selectedNode.setUserObject(selectedNode+" .."); // for change name of JTree Node
            SelectNode = selectedNode.toString() ;
            SelectParent = selectedNode.getParent().toString();
            System.out.print("Selected "+selectedNode.toString()+" Parent is -> "+SelectParent );

            if (database )System.out.println("\tis DataBase ");
            else System.out.println("\tis Table ");

            // show the menu
            popmenu.show(me.getComponent(), me.getX(), me.getY());
          }

          /*
           * if click the right button of the mouse
           * get all table of this database
           * */
        }else if(  me.getButton() == MouseEvent.BUTTON1 ){ // Click into Left Button
          // Get the Node Clicke into it
          if( selectedNode != null  ){
            System.out.println("Selected "+selectedNode.toString()+" Parent is -> "+selectedNode.getParent().toString() );
            if( database ){
              // comment instruction : for show table of one database in one time
              //DefaultTreeModel model = (DefaultTreeModel) arbre.getModel() ;
              selectedNode.removeAllChildren();
              //model.reload();
              showTable( selectedNode.toString() , selectedNode );
            }

          }
        }
      }
    });
    // add tree into the panel
    add( ref , BorderLayout.NORTH );
    add( new JScrollPane(  arbre  ) , BorderLayout.CENTER );
  }

  // cree node of JTree with name all databases
  static void showDatabase(){
    DataBase conn  = new DataBase("") ;
    root.removeAllChildren();
    try{
      conn.Execute("show databases",true); // select name from sys.
      while( conn.getResult().next() ){
        String nameDB = conn.getResult().getString("database") ; // name
        System.out.println("----[" + nameDB + "]-->");
        // create node for database
        createNode(nameDB , root , 0);
        // Zero pour Database
      }
      if ( root.getChildCount() > 0 ){
        (( DefaultTreeModel )arbre.getModel()).reload() ;
      }
    }catch(  Exception e ){
      System.out.printf("There is Exception in  show Database\n{%s}\n\n",e.getMessage());
      System.exit( 1 );
    }finally{
      conn.Desconnect();
    }

  }
  // create Node with parent
  private static DefaultMutableTreeNode createNode( String title , DefaultMutableTreeNode parent , int a){

    DefaultMutableTreeNode show ;
    show = new DefaultMutableTreeNode( title );
    if( a == 1 ) //table
      show.setAllowsChildren(false);
    parent.add(show);
    return  show;
  }
  // create Node of All Table of Databases
  private  void  showTable( String DB , DefaultMutableTreeNode root ) {
    DataBase conn  = new DataBase(DB)  ;
    try{  // Get connection
      conn.tableOf(DB);
      conn.Execute("show tables ",true);// select name from sys.
      short count = 0 ;
      // get the result one by one
      while ( conn.getResult().next() ){
        String nameof = conn.getResult().getString("tables_in_"+DB);
        createNode( nameof , root , 1); // Un pour cree un table
        System.out.println("\t["+nameof+"]");
        count++;
      }
      System.out.println(count + " TABLE -----------------------DONE ")  ;
    }catch( Exception d){
      System.out.println("There is Exception show table\n"+d);
    }finally{
      conn.Desconnect();
    }
  }
}