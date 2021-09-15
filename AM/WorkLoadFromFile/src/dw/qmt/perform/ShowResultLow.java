package dw.qmt.perform;
/*---------------------------------------------------------------------------------------------*/


import javax.swing.*;


/*----------------- ---------------------------------------------------------------------------*/

/**************************
* @author Ahcène Boukorça *
***************************/
public class ShowResultLow extends JPanel {
	
	/*------------------------------------------------------------------------------------------*/

	private JScrollPane jScrollPane0,jScrollPane1,jScrollPane2,jScrollPane3,jScrollPane4,jScrollPane5,jScrollPane6,jScrollPane7;
	private int nbrTabbedPane=0;
	private int widthTabbed;
	private JTabbedPane jTabbedPane1;
	private JTextArea jTextArea0,jTextArea1,jTextArea2,jTextArea3,jTextArea4,jTextArea5,jTextArea6,jTextArea7;
	/*------------------------------------------------------------------------------------------*/
	
	public ShowResultLow(int val)
	{
		widthTabbed=val;
		initComponents();
	}
	/*------------------------------------------------------------------------------------------*/

private void initComponents(){
	jTabbedPane1 = new JTabbedPane();
	jScrollPane0 = new JScrollPane();jScrollPane1 = new JScrollPane();jScrollPane2 = new JScrollPane();jScrollPane3 = new JScrollPane();jScrollPane4 = new JScrollPane();jScrollPane5 = new JScrollPane();jScrollPane6 = new JScrollPane();jScrollPane7 = new JScrollPane();
	jTextArea0 = new JTextArea();jTextArea1 = new JTextArea();jTextArea2 = new JTextArea();jTextArea3 = new JTextArea();jTextArea4 = new JTextArea();jTextArea5 = new JTextArea();jTextArea6 = new JTextArea();jTextArea7 = new JTextArea();
	createTabbedPanel("Console");
	this.setTextArea(0, "======0: Log of operations tests :0======");

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, widthTabbed, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );   

}



/*------------------------------------------------------------------------------------------*/
public void createTabbedPanel(String nameTabbedPanel)
   {
	  if (nbrTabbedPane<8){
	    JPanel jP1 = new JPanel();
	    JScrollPane jSP;
	    JTextArea jTA;
	    jSP = new JScrollPane();
	    jTA = new JTextArea();
	    switch (nbrTabbedPane)
	    {
	    case 0:{jSP=jScrollPane0;jTA=jTextArea0; break;}
	    case 1:{jSP=jScrollPane1;jTA=jTextArea1; break;}
	    case 2:{jSP=jScrollPane2;jTA=jTextArea2; break;}
	    case 3:{jSP=jScrollPane3;jTA=jTextArea3; break;}
	    case 4:{jSP=jScrollPane4;jTA=jTextArea4; break;}
	    case 5:{jSP=jScrollPane5;jTA=jTextArea5; break;}
	    case 6:{jSP=jScrollPane6;jTA=jTextArea6; break;}
	    case 7:{jSP=jScrollPane7;jTA=jTextArea7; break;}  
	    
	    } /**/
	      

	    jTA.setColumns(20);
	    jTA.setRows(5);
	    jSP.setViewportView(jTA);

	    GroupLayout jPanel1Layout = new GroupLayout(jP1);
	    jP1.setLayout(jPanel1Layout);
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
	            .addContainerGap()
	            .addComponent(jSP, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
	            .addContainerGap())
	    );
	   
	    jTabbedPane1.addTab(nameTabbedPanel, jP1);
	   nbrTabbedPane++;
   }
  }
/*------------------------------------------------------------------------------------------*/
public void appendTextArea(int num, String t)
{
	switch (num)
    {
    case 0:{ jTextArea0.setText(jTextArea0.getText()+"\n"+t); break;}
    case 1:{ jTextArea1.setText(jTextArea1.getText()+"\n"+t); break;}
    case 2:{ jTextArea2.setText(jTextArea2.getText()+"\n"+t); break;}
    case 3:{ jTextArea3.setText(jTextArea3.getText()+"\n"+t); break;}
    case 4:{ jTextArea4.setText(jTextArea4.getText()+"\n"+t); break;}
    case 5:{ jTextArea5.setText(jTextArea5.getText()+"\n"+t); break;}
    case 6:{ jTextArea6.setText(jTextArea6.getText()+"\n"+t); break;}
    case 7:{ jTextArea7.setText(jTextArea7.getText()+"\n"+t); break;}  
    
    } 
	
}
/*------------------------------------------------------------------------------------------*/
public void setTextArea(int num, String t)
{
	switch (num)
    {
    case 0:{ jTextArea0.setText(t); break;}
    case 1:{ jTextArea1.setText(t); break;}
    case 2:{ jTextArea2.setText(t); break;}
    case 3:{ jTextArea3.setText(t); break;}
    case 4:{ jTextArea4.setText(t); break;}
    case 5:{ jTextArea5.setText(t); break;}
    case 6:{ jTextArea6.setText(t); break;}
    case 7:{ jTextArea7.setText(t); break;}  
    
    } 
	
}

}


