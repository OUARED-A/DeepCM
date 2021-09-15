/*---------------------------------------------------------------------------------------------*/
package dw.qmt.perform;
/*---------------------------------------------------------------------------------------------*/

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import dw.qmt.nodes.AggregationNode;
import dw.qmt.nodes.BaseTable;
import dw.qmt.nodes.JoinNode;
import dw.qmt.nodes.ProjectionNode;
import dw.qmt.nodes.SelectedNode;

import java.util.List;
import java.util.LinkedList;
import java.util.StringTokenizer;




/*----------------- ---------------------------------------------------------------------------*/

/**************************
* @author Ahcène Boukorça *
***************************/
public class ManageFile {
	/*----------------- ---------------------------------------------------------------------------*/
    private List listQuery;
    private int nbrQuery;
    private File file;
    private FileWriter fw;
    private BufferedWriter bw;
    
	
	public ManageFile(){
		listQuery=new LinkedList();
		
	}
	

/*----------------- ---------------------------------------------------------------------------*/
	
	public void readQueryFromFile( String f )   {
		try {
			File file = new File(f);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			//StringBuffer stringBuffer = new StringBuffer();
			String line;
			nbrQuery=0;
			while ((line = bufferedReader.readLine()) != null) {
				listQuery.add(line);
				nbrQuery++;
				//stringBuffer.append(line);
				//stringBuffer.append("\n");
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


		  //--------------------------------------------------------------------------------------------------------
		
			public void readQueryFromFile( Workload w )   {
				try {
					File file = new File("D:\\ApplicationResults\\MVPP\\mvpp.txt");
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					//StringBuffer stringBuffer = new StringBuffer();
					String line;
					if ((line = bufferedReader.readLine()) != null) w.setWorkloadFile(line);
			      while((line = bufferedReader.readLine()) != null)
			        {
			    	 StringTokenizer st = new StringTokenizer(line,",");
			    	// st.hasMoreTokens();
			    	 int idNode=Integer.parseInt(st.nextToken()) ;
			    	 int typeNode=Integer.parseInt(st.nextToken()) ;
			    	 switch (typeNode)
			    	 {
			    	 case 1:{String tableName=st.nextToken();
			    	         BaseTable bt=new BaseTable();
			    	         bt.setNodeID(idNode);
			    	         bt.setTableName(tableName);
			    	         bt.getAutoCaractiristics();
			    	         w.addNode(bt);
			    		     break;}
			    	 case 2:{String predicatSelection=st.nextToken();
			    	         double selectivity=Double.parseDouble( st.nextToken());
			    	         int predecessor=Integer.parseInt(st.nextToken());
	    	         		SelectedNode sel=new SelectedNode();
	    	         		sel.setNodeID(idNode);
	    	         		sel.setPredicat(predicatSelection);
	    	         		sel.setSelectivity(selectivity);
	    	         		sel.setPredecessorNode(predecessor);	    	         		
	    	         		w.addNode(sel);
	    	         		break;}
			    	 case 3:{String JoinPredicat=st.nextToken();
			    	 		st.nextToken();
			    	 		int predecessorleft=Integer.parseInt(st.nextToken());
			    	 		int predecessorright=Integer.parseInt(st.nextToken());
			    	 		JoinNode join=new JoinNode();
			    	 		join.setNodeID(idNode);
			    	 		join.setPredicat(JoinPredicat);
			    	 		join.addLeftPredecessor(predecessorleft);
			    	 		join.addRightPredecessor(predecessorright);
			    	 		w.addNode(join);
			    	 		break;}
			    	 case 4:{String projectionPredicat=st.nextToken();
			    	          projectionPredicat=projectionPredicat.replaceAll(";", ",");
	    	         		   st.nextToken() ;
	    	         		int predecessor=Integer.parseInt(st.nextToken());
	    	         		ProjectionNode proj=new ProjectionNode();
	    	         		proj.setNodeID(idNode);
	    	         		proj.setPredicat(projectionPredicat);
	    	         		proj.setIndexPredecessor(predecessor);
	    	         		w.addNode(proj);
	    	         		
	    	         		w.queryNodesAffected(proj);
	    	         		//System.out.println(idNode);
	         		break;}
			    	 }
			    	 
			    	 
			         }
			            
			      bufferedReader.close();
				    }
			   catch(Exception e)
			   {
				    //e.printStackTrace();
			   }    
				this.readAggregationNode(w);
				w.setNecessaryColumn();
			}  

			 
//-----------------------------------------------------------------------------------------------------
			private void readAggregationNode( Workload w )   {
				try {
					File file = new File(w.getQueryFile());
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					//StringBuffer stringBuffer = new StringBuffer();
					int nbrQ=w.getNumberQuery();
					int i=0;
					String line;
			      while((line = bufferedReader.readLine()) != null && i<nbrQ)
			        {
			    	  i++;
			    	  line=line.toUpperCase();
			    	  line =line.replaceAll("  ", "");
			    	  AggregationNode agg=new AggregationNode();
			    	  int indG=line.indexOf("GROUP BY");
			    	  int indO=line.indexOf("ORDER BY");
			    	  if(indG==-1){
			    		  if(indO!=-1){
			    			  String order=line.substring(indO);
			    			  agg.addOrderbyPredicat(order);
			    		  }
			    	  }
			    	  else
			    	  {
			    		  
			    		  if(indO!=-1){
			    			  String group=line.substring(indG, indO);
			    			  String order=line.substring(indO);
			    			  agg.addOrderbyPredicat(order);
			    			  agg.addGroupbyPredicat(group);
			    		  }
			    		  else
			    		  {
			    			  String group=line.substring(indG );
			    			  agg.addGroupbyPredicat(group);
			    		  }
			    			  
			    	  }
			    		  
			    	 w.addAggregationNode(agg);
			    	
			    	 
			         }
			            
			      bufferedReader.close();
				    }
			   catch(Exception e)
			   {
				    //e.printStackTrace();
			   }    
				
			}  


 
public void openBufferWriter(String nFile){
	try		
    {
   	file = new File(nFile);
  
    if(!file.exists())file.createNewFile();
    fw=new FileWriter(file.getAbsoluteFile());
    bw=new BufferedWriter(fw);
    }	 
	 catch(Exception e)
    {
	    //e.printStackTrace();
    }    
	
}
public void closeBufferWriter(){
	try		
    {
		bw.close();
		
    }	 
	 catch(Exception e)
    {
    }    
	
}
public void addLog(String log)
{
	try		
    {
     
     bw.write(log);  
		bw.newLine();

	}	 
	 catch(Exception e)
     {
	    //e.printStackTrace();
     }    	
    
}
}