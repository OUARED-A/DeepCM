/*---------------------------------------------------------------------------------------------*/
package dw.qmt.perform;
import dw.qmt.nodes.*;
/*---------------------------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/
public class IndividualPlan {
	private Node node[];
	private int indexNode[];
	private int nbrNode;
	private int rootNode;
	private long cost;
	private int idPlan;
	private int idQuery;
	private static int NBR_MAX_NODE=60;
	
	    public IndividualPlan (){
		      initComponent();
		      idPlan=-1;
		      rootNode=-1;
	    }
	//-------------------------------------------------------------------------------------------
		private void initComponent(){			
			nbrNode=0;
		    node=new Node[NBR_MAX_NODE];
		    indexNode=new int[NBR_MAX_NODE];
		}
	
	public Node getNode(int index){
		
			int ind=indexNode[index];
			if (ind< nbrNode) {
			return node[ind];
			}
		else {
			System.out.println(" ERROR : Index of node incorect");
			return null;}
		
	}
	public Node getRootNode(){
		
		 
		if (rootNode!=-1) {
		return node[rootNode];
		 }
	   else {
		System.out.println(" ERROR : Root node not affected");
		return null;}
	
}
	
	public void setIdPlan(int id)
	{
		idPlan=id;
	}
	public int getPlanID()
	{
		return idPlan;
	}
	public void setIdQuery(int id)
	{
		idQuery=id;
	}
	public int getQueruyID()
	{
		return idQuery;
	}
	public void addNode(BaseTable n){
		node[nbrNode]=new BaseTable();
		node[nbrNode]=n;
		indexNode[n.getNodeId()]=nbrNode;
		nbrNode++;
	}
	public void addNode(SelectedNode n){
		node[nbrNode]=new SelectedNode();
		node[nbrNode]=n;
		indexNode[n.getNodeId()]=nbrNode;
		nbrNode++;
	}
	public void addNode(JoinNode n){
		node[nbrNode]=new JoinNode();
		node[nbrNode]=n;
		indexNode[n.getNodeId()]=nbrNode;
		nbrNode++;
	}
	public void addNode(ProjectionNode n){
		node[nbrNode]=new ProjectionNode();
		node[nbrNode]=n;
		indexNode[n.getNodeId()]=nbrNode;
		nbrNode++;
	}
		    
	public int getNbrNode()
	{
		return this.nbrNode;
	}
	public String ShowAllNodes()
	{
		String s="";
		for(int i=0; i<nbrNode; i++)
			{s=s+node[i].getinfo();s=s+"\n";}
		return s;
	}
	
	public void calculeRowsNumber()
	{
		long shar=6000000*Node.SF;
		node[indexNode[rootNode]].calculateShar(shar, this);
		node[indexNode[rootNode]].calculateSelectivity(this);
		node[indexNode[rootNode]].calculateNumberRows(this);
		calculePlanCost();
	}
	
	public void affectRootNode(int ind)
	{
			rootNode=ind;
	}
	public void affectRootNode( )
	{
			rootNode=nbrNode-1;
	}
	public String ShowAllNodeSize()
	{
		String s="";
		for(int i=0; i<nbrNode; i++)
			{s=s+node[i].getNodeId()+"  number of Rows  : "+ node[i].getNodeNumberRows();s=s+"\n";}
		return s;
	}
	
	public String ShowPlan()
	{
		String s="Plan n:° "+idPlan+"    "+cost+"   \n";
		s=s+"----------  ----------------------------------------------------\n";
		s=s+"Rows        Execution Plan\n";
		s=s+"----------  ----------------------------------------------------\n";
		s=s+node[indexNode[rootNode]].ShowPlan(this,0);
		return s;
	}
	
	private void calculePlanCost()
	{
		  cost=node[indexNode[rootNode]].getIOCost(this);		
	}
	public long getPlanCost()
	{	
		 return cost;
	}
}
