/*---------------------------------------------------------------------------------------------*/
package dw.qmt.perform;
/*---------------------------------------------------------------------------------------------*/

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;




/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/
public class InterfaceQMT extends JFrame{
	
	 /*------------Declare of variables-----------------*/
	JButton jBOK;
	JPanel jPWork,jPNorth,jPSud,jPEast,jPWest;
	JLabel jL1,jL2,jL3,jL4;
	JLabel jLStubborn,jLBase, jLStatistic;
	JTextField jTextServer, jTextUser, jTextDB ;
	JPasswordField jTextPwd;
	JButton jB1,jB2,jB3,jB4,jB5,jB6,jB7,jB8,jB9,jB10,jB11;
	
	JTextArea jTABase;
    public static JTextArea jTextAreaMainResult;
    private JScrollPane jSP;

	JScrollPane jScrollPBase;
	ShowResultLow resultLow;
	public ManageFile manageFile;
	private int numOperation;
	private JMenuBar menuBar = new JMenuBar();

	
	
	 

	//public static Connection con = null;
	/*-------------------------------------------------*/
	public InterfaceQMT (){
		
		 Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		 Dimension dim = toolkit.getScreenSize();		  
		 this.setSize(dim);
         this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
         this.setTitle("Requests Optimization :  Tests");
         this.setVisible(true);
		 this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
		 numOperation=0;
		
		initComponents();
		manageFile=new ManageFile();
		
		
	             }
    private void initComponents(){
    	
    	
            
    	 
    	     this.setJMenuBar(menuBar);

    	     jPWork=new JPanel();
			 jPNorth=new JPanel();
			 jPSud=new JPanel();
			 jPEast=new JPanel();
			 jPWest=new JPanel();
			 //jPSud.setSize(300,500);
			 
			 JPanel jPN=new JPanel();
			 JPanel jPS=new JPanel();
			 JPanel jPE=new JPanel();
			 JPanel jPW=new JPanel();
			 JPanel jP=new JPanel();
			 
			 jTABase =new JTextArea();
			 jScrollPBase = new JScrollPane();
			 jTABase.setColumns(20);
			 //jTABase.setFont(new java.awt.Font("Verdana", 2, 10));
			// jTABase.setForeground(new java.awt.Color(0, 0, 102));
			 jTABase.setRows(5);
			 jScrollPBase.setViewportView(jTABase);
			 
			 jPWork.setLayout(new BorderLayout ());
			 jPWork.add("West",jPWest); 
			 jPWork.add("Center",jP); 
			 jPWork.add("North",jPNorth); 
			 jPWork.add("South",jPSud);
			 jPWork.add("East",jPEast);
	         jLStatistic=createLabel("===0  Statistics of Results Test  0===", "Verdana", 20, 255, 255, 255);
	         jPEast.add(jLStatistic);
	         jPEast.setBackground(Color.black);

	         // main view 
	         jTextAreaMainResult=new JTextArea();
	         jP.add(jTextAreaMainResult);
	         jSP = new JScrollPane();
	         jTextAreaMainResult.setColumns(20);
	         jTextAreaMainResult.setRows(5);
	 	     jSP.setViewportView(jTextAreaMainResult);

	 	     GroupLayout jPanel1Layout = new GroupLayout(jP);
	 	     jP.setLayout(jPanel1Layout);
	 	     jPanel1Layout.setHorizontalGroup(
	 	        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	 	        .addGroup(jPanel1Layout.createSequentialGroup()
	 	            .addContainerGap()
	 	            .addComponent(jSP, GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
	 	            .addContainerGap())
	 	    );
	 	    jPanel1Layout.setVerticalGroup(
	 	        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	 	        .addGroup(jPanel1Layout.createSequentialGroup()
	 	            .addComponent(jSP, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
	 	            )
	 	    );
	 	    //------------------------------------
			 this.setLayout(new BorderLayout ());
			 this.add("West",jPW); 
	         this.add("Center",jPWork); 
	         this.add("North",jPN); 
	         this.add("South",jPS);
	         this.add("East",jPE);
	         jLStubborn=createLabel("Requests optimization tests", "Verdana", 20, 255, 255, 255);
	         jLBase=createLabel("=0= LIAS-ENSMA   2012 =0=", "Verdana", 10, 255, 255, 255);
	         jPN.add(jLStubborn);
	         jPS.add(jLBase);
	         jPN.setBackground(Color.BLUE);
	         jPS.setBackground(Color.BLUE);
	         jPE.setBackground(Color.BLUE);
	         jPW.setBackground(Color.BLUE);
	       //  jP.setBackground(Color.white);
	         //create button
	         jB1=new JButton("Load querys of tests"); jB2=new JButton("Test 02"); jB3=new JButton("Test 03");jB4=new JButton("Test 04");
	         jB5=new JButton("Test 05"); jB6=new JButton("Test 06"); jB7=new JButton("Test 07");jB8=new JButton("Test 08");
             jB9=new JButton("Test 09"); jB10=new JButton("Test 10"); jB11=new JButton("Test 11");
             jPWest.setLayout(new GridLayout(11,1));
             
	         jPWest.add(jB1);jPWest.add(jB2);jPWest.add(jB3);jPWest.add(jB4);jPWest.add(jB5);jPWest.add(jB6);jPWest.add(jB7);
	         jPWest.add(jB8);jPWest.add(jB9);jPWest.add(jB10);jPWest.add(jB11);
	        // jTABase.setAutoscrolls(true);
	          resultLow =new ShowResultLow(this.getContentPane().getWidth());
	         jPSud.add(resultLow);
	         
	         jB11.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // ceateNewResult(evt);
                 }
             });
	         
	         jB1.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                	 try {
						testMVPP();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 }
             });
	         jB2.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // getDefaultTimeExecuteQuery(evt);
                	 //testMVPP();
                 }
             });
	         jB3.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                 
                 }
             });
	         jB4.addActionListener(new java.awt.event.ActionListener() {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                 
                 }
             });
	         jB5.addActionListener(new java.awt.event.ActionListener()  {
                 public void actionPerformed(java.awt.event.ActionEvent evt) {
                  //  testYangModel(evt);
                	try {
                		testMVPP();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 }
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

    private void ceateNewResult(java.awt.event.ActionEvent evt){
    
    	resultLow.createTabbedPanel("Result tests"); 	
    }
 /*------------------------------------------------------------------------------------------*/

  
   
    
 
     

    
       
 /*------------------------------------------------------------------------------------------*/
 public void  testMVPP() throws CloneNotSupportedException{
	 long startTime = System.currentTimeMillis();
	 Workload workload = new Workload();
	  manageFile.readQueryFromFile(workload);
	  workload.calculeRowsNumber();
	  workload.calculeMVPPCost();
	  workload.calculeMVPPMQOCost();
	  workload.calculeMVPPCostWithMV();
	  setResult(workload.ShowQueryCost());
	  String result="";
	  appendResult("------------------Total Cost -----------------------");
	  String s="Total IO Cost: "+ workload.getMVPPIOCost();
	  result=workload.getMVPPIOCost()+"\t";
	  appendResult(s);
	  appendResult("------------------Total MQO Cost -----------------------");
	  s="Total MQO IO Cost: "+ workload.getMQOCost();
	  result=result+workload.getMQOCost()+"\t";
	  appendResult(s);
	  appendResult("------------------Total IO Cost with MV-----------------------");
	  s="Total MQO IO Cost: "+ workload.getMVPPIOCostWithMV()+"   "+workload.getMVTotalCost()+"    "+ workload.getMVPPIOTotalCostWithMV();
	  result=result+workload.getMVPPIOCostWithMV()+"\t"+workload.getMVTotalCost()+"\t"+ workload.getMVPPIOTotalCostWithMV()+"\t";
	  appendResult(s);
	  s="   Partition schema   ";;
	  appendResult(s);
	  workload.createPartitionSchema();
	  appendResult("------------------Total IO Cost with HP-----------------------");

	  workload.calculeMVPPCostWithHP();
	  s="TOTAL MQO COST WITH PARTTION : "+workload.getMVPPIOCostWithHP();
	  result=result+workload.getMVPPIOCostWithHP()+"\t"+workload.getNbrPartitionPredicat();
	  appendResult(s);
	  s=workload.getPartitonSchema();
	  appendResult(s);
	  appendResult(result);
	 // appendResult(workload.showQueryRewriting());
	 long stopTime = System.currentTimeMillis();
     long elapsedTime = stopTime - startTime;
     System.out.println("execution time is "+ elapsedTime);

 }
 /*------------------------------------------------------------------------------------------*/

public void logOperationTest(String log)
{
	resultLow.appendTextArea(0, "Test n°"+numOperation+" : "+log);
	numOperation++;
	
}
//------------------------------------------------------------------------------------------------
public void setResult(String message){	
 this.jTextAreaMainResult.setText(message);	
}
//------------------------------------------------------------------------------------------------
public void appendResult(String message){	
this.jTextAreaMainResult.setText(jTextAreaMainResult.getText()+"\n"+message);	
}
 

}