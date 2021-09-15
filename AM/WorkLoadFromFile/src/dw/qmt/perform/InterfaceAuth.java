/*---------------------------------------------------------------------------------------------*/
package dw.qmt.perform;
/*---------------------------------------------------------------------------------------------*/
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/
public class InterfaceAuth extends JFrame{
	
/*----------------- ---------------------------------------------------------------------------*/
	 private JTextField jTextServer, jTextUser, jTextDB ;
	 private JPasswordField jTextPwd;
	 private static Connection con = null;
	 public static InterfaceQMT interfaceQMT;
	 public static Statement statement; //= InterfaceAuth.con.createStatement();
/*----------------- ---------------------------------------------------------------------------*/
	public InterfaceAuth (){
		
		            initComponents();
				    this.setLocation(300, 200);
				    this.setSize(400, 200);
				    this.setResizable(false);
				    this.setLocationRelativeTo(null);
				    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    this.setTitle("Requests Optimization  :  Authentification");
				    this.setVisible(true);
		
		
	             }
/*----------------- ---------------------------------------------------------------------------*/

    private void initComponents(){
    	   
			
    	     JButton jBOK;
    	     JPanel jP1,jPSud,jPEast,jPWest,jPNorth;
    	     JLabel jL1,jL2,jL3,jL4;
    	     			 
    	     
    	     jP1=new JPanel();
			 jPNorth=new JPanel();
			 jPSud=new JPanel();
			 jPEast=new JPanel();
			 jPWest=new JPanel();
			 this.setLayout(new BorderLayout ());
			 this.add("West",jPWest); 
	         this.add("Center",jP1); 
	         this.add("North",jPNorth); 
	         this.add("South",jPSud);
	         this.add("East",jPEast);

			 jL1=createLabel("Name of server", "Verdana", 12, 51, 0, 255);
			 jL2=createLabel("Data base", "Verdana", 12, 51, 0, 255);
			 jL3=createLabel("User name", "Verdana", 12, 51, 0, 255);
			 jL4=createLabel("Password", "Verdana", 12, 51, 0, 255);
			 jTextServer= new JTextField ("LIAS-BOUKORCA");
			 jTextUser= new JTextField ("ssb");
			 jTextPwd= new JPasswordField ("ssb");
			 jTextDB= new JTextField ("orcl");
			 jBOK=new JButton("Connect");
			 
			 jP1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter the hot, user name and password of dada warehouse", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 12), new java.awt.Color(51, 0, 255))); // NOI18N
			 this.setBackground(Color.white);
			 jP1.setLayout(new GridLayout(4,2));
             this.add(jP1);
             jP1.add(jL1);
             jP1.add(jTextServer);
             jP1.add(jL2);
             jP1.add(jTextDB);
             jP1.add(jL3);
             jP1.add(jTextUser);
             jP1.add(jL4);
             jP1.add(jTextPwd);
             jPSud.add(jBOK);
             //-------------- events on components ------------------
             jBOK.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                     connectActionPerformed(evt);}
                 });
             jTextServer.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                     connectActionPerformed(evt);}
                 });
             jTextDB.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                     connectActionPerformed(evt);}
                 });
             jTextPwd.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                     connectActionPerformed(evt);}
                 });
             jTextUser.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                     connectActionPerformed(evt);}
                 });



             
		}
/*------------------------------------------------------------------------------------------*/
     private JLabel createLabel(String text, String font, int taille, int r, int g, int b) {
    	JLabel local=new JLabel();
    	 local.setFont(new java.awt.Font(font, 0, taille));
         local.setForeground(new java.awt.Color(r,g,b));
         local.setText(text);
    	 return local;
     }
/*------------------------------------------------------------------------------------------*/
   private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 
    if((jTextServer.getText().compareTo("")!=0) && (jTextDB.getText().compareTo("")!=0) && ( jTextUser.getText().compareTo("")!=0) && (jTextPwd.getText().compareTo("")!=0)){
     try {
             Class.forName("oracle.jdbc.OracleDriver");
             System.out.println("Driver OK..");
             String DBurl= "jdbc:oracle:thin:"+jTextServer.getText()+":1521:"+jTextDB.getText();
             String user = jTextUser.getText();
             String pass= jTextPwd.getText();  
         try{

        	 con = DriverManager.getConnection(DBurl,user,pass);
             System.out.println("Database connected..");
            // JOptionPane.showMessageDialog(null, "Database is connected", "Connection", JOptionPane.INFORMATION_MESSAGE);
              interfaceQMT=new InterfaceQMT();
              this.setVisible(false);
               statement = con.createStatement();
            } catch(SQLException ee){
             System.out.println("Cannot connect to database"+DBurl);
             JOptionPane.showMessageDialog(null, "Cannot connect to database ; verify your username and your password...", "Connection", JOptionPane.ERROR_MESSAGE);
            }

         }catch (ClassNotFoundException e) {
             System.out.println("Cannot load jdbc driver");
        } 
   }
    else
     {
    	 JOptionPane.showMessageDialog(null, "You must fill in all fields empty.", "Connection", JOptionPane.ERROR_MESSAGE);
     }
     
}
}
