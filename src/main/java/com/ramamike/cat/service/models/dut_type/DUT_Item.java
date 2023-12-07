package com.ramamike.cat.service.models.dut_type;

public class DUT_Item {
	private String varName;
	private String varType;
	private String varValue;
	private String comment;
	
	public static final String dwStart = "dwStart";
	public static final String bEnd = "bEnd";
	
	public DUT_Item(String varName, String varType, String varValue, String comment) {
		this.varName = varName;
		this.varType = varType;
		this.varValue = varValue;
		this.comment = comment;
	}
	
	
	
	public String getVarName() {
		return varName;
	}



	public String getVarType() {
		return varType;
	}



	public String getVarValue() {
		return varValue;
	}



	public String getComment() {
		return comment;
	}



	@Override
	public String toString() {
		if(varValue.equals("")) {
			return "\t\t\t" + varName + ": " + varType + ";" + "\n\t\t\t\t (*" + comment +"*)";
		}
		return "\t\t\t" + varName + ": " + varType + ":=" + varValue + ";" + "\n\t\t\t\t (*" + comment +"*)";
	}
	
}
