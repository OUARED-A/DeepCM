/*---------------------------------------------------------------------------------------------*/
package dw.qmt.nodes;
import dw.qmt.perform.*;
/*---------------------------------------------------------------------------------------------*/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/*----------------- ---------------------------------------------------------------------------*/
/**************************
* @author Ahcène Boukorça *
***************************/
public class Columns {
	
	private String nameColumn;
	private String tableName;
	private int avgSize;
	private int sizeColumn;
	
	
	public Columns(){
		nameColumn="";
		avgSize=0;
		sizeColumn=0;
	}
	public void setNameColumn(String name){
		this.nameColumn=name;
		
	}
	public void setTableName(String name){
		this.tableName=name;
		
	}
	public String getTableName(){
		return this.tableName;
		
	}
	public String getName(){
		return this.nameColumn;
		
	}
	public int getAverageSize(){
		return this.avgSize;		
	}
	public int getSize(){
		return this.sizeColumn;		
	}
	public void setSizeColumn(int v){
		this.sizeColumn=v;
		
	}
	public void setAvgSizeColumn(int v){
		this.avgSize=v;
		
	}

}
