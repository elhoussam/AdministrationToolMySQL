package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class Main{
  // private attribut
  private static JFrame princ ;
  static JSplitPane split , splitVer;
  static JPanel mainn = new JPanel() ;

  JPanel tailoo = new Tail();

  public static void echo( String a){
    System.out.println(a);
  }

  //Constructors for Main class
  public Main(){
    Main.echo(" in Main.Main");
    // init main frame
    Lang.InitLang();

    Main.echo(" in Main" + ".lang");

    JFrame aaaz = new JFrame( Lang.getWord("Outil Admin Sql Server 2014") );
    setPrinc( aaaz );
    getPrinc().setSize(new Dimension(900,599));
    getPrinc().setIconImage( (new ImageIcon( getClass().getResource("icons/icon_main.png")  )).getImage() );

    //load driver of connection
    DataBase.LoadDriver();

    // init other compenent of this frame

    Main.echo(" in Main.InitCompenent ");
    InitCompenent();


    // other option of frame
    final Toolkit toolkit = Toolkit.getDefaultToolkit();
    final Dimension screenSize = toolkit.getScreenSize();
    final int x = (screenSize.width - getPrinc().getWidth()) / 2;
    final int y = (screenSize.height - getPrinc().getHeight()) / 2;
    getPrinc().setLocation(x, y);

    getPrinc().setVisible(true);
    getPrinc().setLocationRelativeTo( null );
    getPrinc().setDefaultCloseOperation( 3 );

    // login dialog

    //   new Login(  getPrinc() );
    //   getPrinc().setEnabled( false );
  }



  //	for add component into Main Panel
  public static void intoMainn(Component c){
    mainn.removeAll();
    if( c != null )
      mainn.add(c );
    mainn.setVisible( false );
    mainn.setVisible( true );
  }
  // hide / show . arbre
  public static void ToggleHideArb(){
    split.setDividerLocation(0.75d);
    split.getRightComponent().setVisible( ! split.getRightComponent().isVisible() ) ;
  }
  // hide / show . option  panel
  public static void ToggleHideOption(){
    splitVer.setDividerLocation(0.75d);
    splitVer.getBottomComponent().setVisible( ! splitVer.getBottomComponent().isVisible() ) ;
  }


  private  void InitCompenent() {

    mainn.setLayout( new BorderLayout() );
    // split contain ( mainn , arbre )
    split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT , mainn  , new arbre());
    split.setDividerSize(1);
    split.setBackground(  new Color( 213, 214, 217)   );
    split.setResizeWeight(0.75d);

    // princp contain ( split : , MENUU )
    JPanel princp = new JPanel();
    princp.setLayout( new BorderLayout( ));
    princp.add( new MENUU() , BorderLayout.WEST );
    princp.add( split , BorderLayout.CENTER );

    // splitVer contain ( princp , Tail()	)
    splitVer = new JSplitPane( JSplitPane.VERTICAL_SPLIT , princp , tailoo );
    splitVer.setDividerSize(1);
    splitVer.setBackground(  new Color( 213, 214, 217)   );
    splitVer.setResizeWeight(1.0d);

    // add object into Main Frame
    getPrinc().setLayout( new BorderLayout() );
    getPrinc().setBackground( new Color( 233, 234, 237) );
    getPrinc().add( splitVer , BorderLayout.CENTER );
    getPrinc().add( new Header() , BorderLayout.NORTH );
  }


  // this methode for change Look&Feel of Apps
  public static void apply (){
    SwingUtilities.updateComponentTreeUI(  getPrinc()  );
  }
  // getter of main frame
  public static JFrame getPrinc() {
    return princ;
  }
  // setter of main frame
  public static void setPrinc(JFrame princ) {
    Main.princ = princ;
  }



  public static void main(String[] args) {
    new Main();
  }


}
