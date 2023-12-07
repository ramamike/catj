package com.ramamike.cat.service.models.dut_type;

import java.util.List;
import java.util.function.Function;

import com.ramamike.cat.utils.Utils;

public class DUT_Type {

	public static final String TYPE = "TYPE";
	public static final String END_TYPE = "END_TYPE";

	private String name;
	private String groupPath;
	private DUT_Struct struct = new DUT_Struct();

	public DUT_Type() {
	}

	public DUT_Type parse(List<String> rawData) {
		Utils.parse(rawData, TYPE, DUT_Struct.STRUCT, this, f);
		this.setStruct(struct.parse(rawData));
		return this;
	}

	Function<String, DUT_Type> f = (row) -> {
		if (row.contains(":") && !row.contains(":=")) {
			row = row.substring(0, row.indexOf(":"));
			this.setName(row);
		} else if (row.contains("GroupPath")) {
			row = row.substring(row.indexOf("\'") + 1, row.lastIndexOf("\'"));
			this.setGroupPath(row);
		}
		return this;
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupPath() {
		return groupPath;
	}

	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}

	public DUT_Struct getStruct() {
		return struct;
	}

	public void setStruct(DUT_Struct struct) {
		this.struct = struct;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(TYPE + "\n\n");
		sb.append("\t" + name + ":\n");
		sb.append("\t(**)\n");
		sb.append("\t(*GroupPath=\'" + groupPath + "'*)\n");
		sb.append(struct);
		sb.append("\n");
		sb.append(END_TYPE);
		return sb.toString();
	}

}
