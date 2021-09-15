/*---------------------------------------------------------------------------------------------*/
package dw.qmt.perform;
import java.util.ArrayList;
import java.util.List;

import dw.qmt.nodes.*;
/*---------------------------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/
public class Workload   {
	//ManageFile manageFile ;
	//private Query query[];  //frequency of queries
	private int nbrQuery;
	private static int NBR_MAX_QUERY=20000;
	private Node node[];
	private int indexNode[];
	private int nbrNode;
	private int rootNode[];
	private static int NBR_MAX_NODE=40000;
	private int indexBaseTable[];
	private int nbrbasetable;
	private List idPlan;
	private long costMVPP=0;
	private long costMQO=0;
	private long  costMVPPwithMV;
	private long  costMVPPwithHP;
	private long costMV;
	private List <String> PartitionSchema;
	private List <QueryRewriting> listRewriting;
	private List <QueryRewriting> listTemp ;
	private List <AggregationNode> aggregationNode;
	private int nbrPartitionTab[];
	private int nbrMaxPartition;
	private int nbrPartition;
	private String workloadFile;
	
	    public Workload(){
		      initComponent();
		      
	    }
	 
	    
	    
	//-------------------------------------------------------------------------------------------
		private void initComponent(){			
			nbrQuery=0;
			nbrNode=0;
		    //query=new Query[NBR_MAX_QUERY];	
		    rootNode=new int[NBR_MAX_QUERY];	
		    node=new Node[NBR_MAX_NODE];
		    indexNode=new int[NBR_MAX_NODE];
		    indexBaseTable=new int[10];
		    nbrbasetable=0;
		    idPlan=new ArrayList<>();
		    this.PartitionSchema = new ArrayList<String>();
		    this.listRewriting = new ArrayList<QueryRewriting>();
		    this.listTemp  = new ArrayList<QueryRewriting>();
		    this.aggregationNode = new ArrayList<AggregationNode>();
		    nbrPartitionTab=new int[10];
		    for(int i=0; i<10;i++)nbrPartitionTab[i]=1;
		    nbrPartition=0;
		    nbrMaxPartition=500;
		}
	
	public Node getNode(int index){
		if (index< nbrNode) {
			return node[indexNode[index]];
			}
		else {
			System.out.println(" ERROR : Index of node incorect");
			return null;}
		
	}
	public void setWorkloadFile(String s){
		this.workloadFile=s;
	}
	public String getWorkloadFile()
	{
		return workloadFile;
	}
	public int getNumberQuery(){
		return this.nbrQuery;
	}
	public String getQueryFile(){
		return this.workloadFile;
	}
	public void addAggregationNode(AggregationNode agg)
	{
		aggregationNode.add(agg);
	}
	public void addNode(BaseTable n){
		node[nbrNode]=new BaseTable();
		node[nbrNode]=n;
		indexNode[n.getNodeId()]=nbrNode;
		indexBaseTable[nbrbasetable]=n.getNodeId();
		nbrbasetable++;
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
		for (int i=0; i<nbrQuery; i++)
		{
			calculeQueryRowsNumber(i);
		}	
	}
	public void calculeMVPPMQOCost()
	{
		 costMQO=0;
		setNoMaterialization();
		for (int i=0; i<nbrQuery; i++)
		{
			costMQO=costMQO+ node[rootNode[i]].getIOCostMQO(this); 
		}	
		 
	}
	public void calculeMVPPCost()
	{
		 costMVPP=0;
		setNoMaterialization();
		for (int i=0; i<nbrQuery; i++)
		{
			costMVPP=costMVPP+ node[rootNode[i]].getIOCost(this); 
		}	
		 
	}
	private void setNoMaterialization()
	{
		 
		for (int i=0; i<nbrNode; i++)
		{
			node[i].setMaterialization(this,  false);
		}	
		
		 
	}
	private void materializeJoinNode()
	{
		costMV=0;
		/*for(int i=0; i<nbrNode; i++)
			if (node[i] instanceof JoinNode){
				JoinNode jn=new JoinNode();
				jn=(JoinNode) node[i];				
				if(this.getNode(jn.getLeftNode()) instanceof BaseTable ||this.getNode(jn.getLeftNode()) instanceof SelectedNode)
				{
					costMV=costMV+node[i].getIOCost(this);
					node[i].setMaterialization(this, true);
				}		
			}*/
		for (int i=0; i<nbrQuery; i++)
		{
			  node[rootNode[i]].materializeNodes(this); 
		}
		this.rewriteQuery();
	/*	for (int i=0; i<nbrQuery; i++)
		{
			  node[rootNode[i]].materializeNodes(this); 
		}*/
		ManageFile manageFile=new ManageFile();
		manageFile.openBufferWriter("D:\\ApplicationResults\\MVPP\\QUERYREWRITE.SQL");
		for(int i=0; i<nbrNode; i++){
			if(node[i].isMaterialized()) { //costMV=costMV+node[i].getNodeNumberPage()+node[i].getIOCost(this);
			costMV=costMV+node[i].getNodeNumberPage()+node[i].getIOCost(this);
			
			this.orderQueryWithMV(node[i].getNodeId());
			if(!listTemp.isEmpty()){
			manageFile.addLog(this.getScriptCreateVM(i));
			for(int j=0;j<listTemp.size();j++)
				manageFile.addLog(listTemp.get(j).getQuery());
			manageFile.addLog(this.getScriptDropVM(i));
			}
			  System.out.println(node[i].getNodeId());
			 }
			
		}
		manageFile.closeBufferWriter();
		
	}
	public void calculeMVPPCostWithMV()
	{
		 costMVPPwithMV=0;
		setNoMaterialization();
		materializeJoinNode();
		for (int i=0; i<nbrQuery; i++)
		{
			costMVPPwithMV=costMVPPwithMV+ node[rootNode[i]].getIOCost(this); 
		}	
		 
	}
	public void calculeMVPPCostWithHP()
	{
		 this.costMVPPwithHP=0;
		setNoMaterialization();
		for (int i=0; i<nbrQuery; i++)
		{
			costMVPPwithHP=costMVPPwithHP+ node[rootNode[i]].getIOCost(this); 
		}	
		 
	}
	public void createPartitionSchema()
	{ 
		int nbrP=0;
		while (nbrPartition<nbrMaxPartition){
		for (int i=0; i<nbrQuery; i++)
		{
			  node[rootNode[i]].createPartitionSchema(this); 
		}	
		if(nbrP==nbrPartition){
			System.out.println(nbrPartition);
			 
			 break;
		}
		nbrP=nbrPartition;
		}
		System.out.println("NBR PARTITIONS: "+nbrPartition);
		ratePartitionAffected();
	}
	
	public long getMQOCost()
	{
		return costMQO;
	}
	public long getMVPPIOCost()
	{
		return costMVPP;
	}
	public long getMVPPIOTotalCostWithMV()
	{
		return (costMVPPwithMV+costMV);
	}
	public long getMVTotalCost()
	{
		return (costMV);
	}
	public long getMVPPIOCostWithMV()
	{
		return (costMVPPwithMV);
	}
	public long getMVPPIOCostWithHP()
	{
		return this.costMVPPwithHP;
	}
	
	public void queryNodesAffected(Node n)
	{
		    int ind=indexNode[n.getNodeId()];
			rootNode[nbrQuery]=ind;
			nbrQuery++;
	}
	private void queryNodesAffected( int ind)
	{
		    
		rootNode[nbrQuery]=ind;
		nbrQuery++;
	}
	private void calculeQueryRowsNumber(int numQ)
	{   long shar=6000000*Node.SF;
		node[rootNode[numQ]].calculateShar(shar, this);
		node[rootNode[numQ]].calculateSelectivity(this);
		node[rootNode[numQ]].calculateNumberRows(this);
	}
	
	public String ShowAllNodeSize()
	{
		String s="";
		for(int i=0; i<nbrNode; i++)
			{s=s+node[i].getNodeId()+"  number of Rows  : "+ node[i].getNodeNumberRows();s=s+"\n";}
		return s;
	}
	
	public String ShowQueryCost()
	{
		String s="";
		for(int i=0; i<nbrQuery; i++)
		{
			s=s+ "Plan of query :°   "+i+ "\n";
			s=s+"----------  ----------------------------------------------------\n";
			s=s+"Rows        Execution Plan\n";
			s=s+"----------  ----------------------------------------------------\n";
			s=s+node[rootNode[i]].ShowPlan(this,0);
		}
			 
		return s;
	}
	public void addIndividualPlan(IndividualPlan plan )throws CloneNotSupportedException
	{
		idPlan.add(plan.getQueruyID());
		idPlan.add(plan.getPlanID());
		int root=plan.getRootNode().getNodeId(); 
		int ind=addNode(plan, root) ;
		 queryNodesAffected(    ind);
		 
		
	}
	private int addNode(IndividualPlan plan,  int index) throws CloneNotSupportedException
	{
		
		int ind=-1;
		Node n=new Node();
		n=(Node) plan.getNode(index).clone(); ;
		if(n instanceof BaseTable){
			BaseTable b=new BaseTable();
			b= (BaseTable) n;
			ind=addBaseTablePlan( b);
		}
		else 
			if(n instanceof SelectedNode){
				SelectedNode s=new SelectedNode();
				s=(SelectedNode) n;
				int pred= addNode(plan, s.getpredecessorNode());
				s.setPredecessorNode(pred);
				ind=addSelectionNodePlan(s) ;
			}
			else if(n instanceof JoinNode){
				JoinNode j=new JoinNode();
				j=(JoinNode) n;
				int left= addNode(plan, j.getLeftNode());
				int right= addNode(plan, j.getRightNode());
				j.addLeftPredecessor(left);
				j.addRightPredecessor(right);
				ind=addJoinNodePlan(j) ;
			}
				
		return ind;
		
	}
	private int addBaseTablePlan(BaseTable b){
		int ind =findBaseTable(b);
		if(ind==-1){ 
			ind=nbrNode; b.setNodeID(nbrNode);
			addNode(b);}
		return ind;
		
	}
	private int findBaseTable(BaseTable b){
		int ind=-1;
		for(int i=0; i<nbrbasetable;i++)
			if( node[indexBaseTable[i]].getTableName().compareTo(b.getTableName())==0) return indexBaseTable[i];
		return ind;
	}
	private int addSelectionNodePlan(SelectedNode s){
		int ind =findSelectionNode(s);
		if(ind==-1){ 
			       ind=nbrNode; s.setNodeID(nbrNode);addNode(s);
			}
		return ind;		
	}
	private int findSelectionNode(SelectedNode s){
		int ind=-1;
		SelectedNode s1=new SelectedNode();
		
		for(int i=0; i<nbrNode;i++)
		{
			if(node[i] instanceof SelectedNode){
				
				s1=(SelectedNode) node[i];
				//System.out.println(s1.getpredecessorNode()+"  --  "+s.getpredecessorNode());
				if((s1.getpredecessorNode()   == s.getpredecessorNode()) && (s.getPredicat().compareTo(s1.getPredicat())==0))
				{
					return i;
				}
			}
		}
			 
		return ind;
	}
	private int addJoinNodePlan(JoinNode j){
		int ind =findJoinNode(j);
		if(ind==-1){ 
			       ind=nbrNode; j.setNodeID(nbrNode);addNode(j);
			}
		return ind;		
	}
	private int findJoinNode(JoinNode j){
		int ind=-1;
		JoinNode j1=new JoinNode();
		
		for(int i=0; i<nbrNode;i++)
		{
			if(node[i] instanceof JoinNode){
				j1=(JoinNode) node[i];
				if(j1.getLeftNode()==j.getLeftNode() && j1.getRightNode()==j.getRightNode())
				{
					return i;
				}
			}
		}
			 
		return ind;
	}
	
	public String showMVPP()
	{
		String s="";
		for(int i=0; i<idPlan.size();i=i+2)
			s=s+" P("+idPlan.get(i)+","+idPlan.get(i+1)+") ";		 
			 
		return s;
		
	}
	
	public String getDependence()
	{
		String s="";
		for(int i=0; i<nbrQuery; i++)
		{
			String tmp1=""; 
			if(node[rootNode[i]] instanceof BaseTable) tmp1=node[rootNode[i]].getTableName();
			else if (node[rootNode[i]] instanceof SelectedNode) tmp1="S"+node[rootNode[i]].getNodeId();
			else if (node[rootNode[i]] instanceof JoinNode) tmp1="J"+node[rootNode[i]].getNodeId();
			s=s+"P"+i+"\t1\t"+tmp1+"\n";
			s=s+node[rootNode[i]].getDependence(this );
		}
			 
		return s;
	}
	 
	public String getPartitonSchema()
	{
		String s="";
		for(int i=0; i<PartitionSchema.size();i++)
			s=s+i+" :"+ PartitionSchema.get(i)+"\n";
		return s;
	}
	
	public void insertPartitionPredict(String pred){
		if(nbrPartition<nbrMaxPartition){
		boolean val=false;
		for(int i=0; i<PartitionSchema.size();i++) 
			if(PartitionSchema.get(i).compareTo(pred)==0){
			 val=true;
			 break;
		    }
		 if(!val) {
			 String first=pred.substring(0, 2);
			 if(first.compareTo("L_")==0) this.nbrPartitionTab[0]++;
			 else if(first.compareTo("D_")==0) this.nbrPartitionTab[1]++;
			 else if(first.compareTo("S_")==0) this.nbrPartitionTab[2]++;
			 else if(first.compareTo("C_")==0) this.nbrPartitionTab[3]++;
			 else if(first.compareTo("P_")==0) this.nbrPartitionTab[4]++;
			 nbrPartition=1;
			 for(int i=0;i<5;i++)
			 nbrPartition=nbrPartition*nbrPartitionTab[i];
			 if(nbrPartition<nbrMaxPartition) PartitionSchema.add(pred);
		 }
	 }
	}
	
	public boolean isPartitionPredicat(String pred)
	{
		boolean exist=false;
		for(int i=0; i<PartitionSchema.size();i++)
			if(PartitionSchema.get(i).compareTo(pred)==0){
				 exist=true;
				 break;
			    }
		return exist;
	}
	private void ratePartitionAffected()
	{
		for (int i=0; i<nbrQuery; i++)
		{
			  node[rootNode[i]].setPartitionRate(this); 
		}	
	}
	 public int getNbrPartitionPredicat(){
   	  return this.PartitionSchema.size();
     }
	
	 private void rewriteQuery(){
		 for (int i=0; i<nbrQuery; i++)
			{
			    QueryRewriting rq=new QueryRewriting();
			    rq.setIdQuery(i);
				node[rootNode[i]].rewriteQuery(this, rq);
				rq.addGroupby(aggregationNode.get(i).getGroupByPredicat());
				rq.addorderby(aggregationNode.get(i).getOrderByPredicat());
				// System.out.println(rq.getQuery());
				listRewriting.add(rq);
			}	
		
	 }
	 public void setNecessaryColumn(){
		 for (int i=0; i<nbrQuery; i++)
			{
			    List <String> list=new ArrayList<String>();
				node[rootNode[i]].setNecessaryColumn(this, list); 
				 
			}	
		
	 }
	 
	 private String getScriptCreateVM(int idN){
		 String s="";
		 if(node[idN].isMaterialized())
		 {
			 node[idN].setMaterialization(this, false);
			 QueryRewriting rq=new QueryRewriting();
			 rq.setIdQuery(node[idN].getNodeId());
			 node[idN].rewriteQuery(this, rq);
			 rq.addProjection(node[idN].getNecessaryColumn());
			 if(rq.useView())
			 {
				 int id=rq.getMaterializedViews();
				 s=s+getScriptCreateVM(id);
			 }
			 node[idN].setMaterialization(this, true);
			 s=s+rq.getScritpCreateMV();
		 }
		 else s=" ERROR IN CREATIN VIEWS SCRIPT";
		return s;
		
	 }
	 private String getScriptDropVM(int idN){
		 String s="";
		 if(node[idN].isMaterialized())
		 {
			 node[idN].setMaterialization(this, false);
			 QueryRewriting rq=new QueryRewriting();
			 rq.setIdQuery(node[idN].getNodeId());
			 node[idN].rewriteQuery(this, rq);
			 s=s+rq.getScritpDropMV();
			 if(rq.useView())
			 {
				 int id=rq.getMaterializedViews();
				 s=s+getScriptDropVM(id);
			 }
			 node[idN].setMaterialization(this, true);
			 
		 }
		 else s=" ERROR IN DROP VIEWS SCRIPT";
		return s;
		
	 }
	 private void orderQueryWithMV(int idN){
		 listTemp.clear();
		 for(int i=0; i<listRewriting.size();i++){
			 int id=listRewriting.get(i).getMaterializedViews();
			 if(id==idN){
				 QueryRewriting rq=new QueryRewriting();
				 rq=listRewriting.get(i);
				 listTemp.add(rq);
 			 }
		 }
	 }
	public String showQueryRewriting(){
		String s="";
		for(int i=0; i<listRewriting.size();i++){
			 s=s+listRewriting.get(i).getQuery();
			 }
		return s;
	}
}
