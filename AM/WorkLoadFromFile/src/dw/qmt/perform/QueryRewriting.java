/*---------------------------------------------------------------------------------------------*/
package dw.qmt.perform;
/*---------------------------------------------------------------------------------------------*/


/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/
public class QueryRewriting {
	
	private String projectionClose;
	private String fromClose;
	private String selectionConditionClose;
	private String joinConditionClose;
	private String groupbyClose;
	private String orderbyClose;
	private boolean useMaterializeViews;
	private int idQuery;
	private int idMateriazed;
	
	public QueryRewriting(){
		this.projectionClose="";
		this.selectionConditionClose="";
		this.joinConditionClose="";
		this.fromClose="";
		this.groupbyClose="";
		this.orderbyClose="";
		this.useMaterializeViews=false;
		idQuery=-1;	
		this.idMateriazed=-1;
		
	}
	public void addProjection(String string){
		this.projectionClose=string;
	}
	public void addselectionCondition(String string){
		if(this.selectionConditionClose=="")
		    this.selectionConditionClose=string;
		else this.selectionConditionClose=this.selectionConditionClose+" AND "+string;
	}
	public void addjoinCondition(String string){
		if(this.joinConditionClose=="")
		    this.joinConditionClose=string;
		else this.joinConditionClose=this.joinConditionClose+" AND "+string;
	}
	public void addfromClose(String string){
		if(this.fromClose=="")
		    this.fromClose=string;
		else this.fromClose=this.fromClose+","+string;
		if(string.indexOf("MV")!=-1) {this.useMaterializeViews=true;
		   String id=string.substring(2);
		   idMateriazed=Integer.parseInt(id);
		}
	}
    public void addGroupby(String string){
    	this.groupbyClose=string;
    }
    public void addorderby(String string){
    	this.orderbyClose=string;
    }
    public void setIdQuery(int id ){
    	idQuery=id;
    }
    public boolean useView(){
    	return this.useMaterializeViews;
    }
    public int getMaterializedViews(){
    	if(useMaterializeViews) return this.idMateriazed;
    	else  return -1;
    }
    public String getQuery()
    {
    	String s;
    	 s="-- "+idQuery+"\n PROMPT Query__"+idQuery+" \n";
    	 if(this.projectionClose=="") s=s+"SELECT  * ";else   s=s+"SELECT "+this.projectionClose;
    	 s=s+" FROM "+ this.fromClose;
    	 if(this.joinConditionClose!="") {
    		 s=s+ " WHERE "+this.joinConditionClose;
    		 if(this.selectionConditionClose!="")s=s+" AND "+this.selectionConditionClose;
    	 }
    	 else if(this.selectionConditionClose!="")s=s+" WHERE "+this.selectionConditionClose;
    	 
    	 s=s+" "+groupbyClose+" "+orderbyClose+";\n"; 		   	
    	return s;
    }
    public String getScritpCreateMV()
    {
    	String s= s="-- MV"+idQuery+"\n PROMPT QueryCREATEMV__"+idQuery+" \n";
    			s=s+" CREATE  MATERIALIZED VIEW MV"+idQuery +" AS ";
    	
    	 if(this.projectionClose=="") s=s+"SELECT  * ";else   s=s+"SELECT "+this.projectionClose;
    	 s=s+" FROM "+ this.fromClose;
    	 if(this.joinConditionClose!="") {
    		 s=s+ " WHERE "+this.joinConditionClose;
    		 if(this.selectionConditionClose!="")s=s+" AND "+this.selectionConditionClose;
    	 }
    	 else if(this.selectionConditionClose!="")s=s+" WHERE "+this.selectionConditionClose;
    	 
    	 s=s+" "+groupbyClose+" "+orderbyClose+";\n"; 		   	
    	return s;
    }
    public String getScritpDropMV()
    {
    	String s= s="-- MV"+idQuery+"\n PROMPT QueryDROPMV__"+idQuery+" \n";
    			s=s+" DROP  MATERIALIZED VIEW MV"+idQuery + ";\n";    	
    	 	   	
    	return s;
    }
}
