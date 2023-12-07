package com.ramamike.cat.service.models.dut_type;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DUT_Struct {

	public final static String STRUCT = "STRUCT";
	public final static String END_STRUCT = "END_STRUCT";

	private List<DUT_Item> items = new ArrayList<>();

	public void addItem(DUT_Item item) {
		items.add(item);
	}
	
	public List<DUT_Item> getItems() {
		return items;
	}

	public void setItems(List<DUT_Item> items) {
		this.items = items;
	}

	public DUT_Struct parse(List<String> rawData) {
		int size = rawData.size();
		for (int i = 0; i <= size; i++) {
			String s = rawData.get(i);
			if (s.contains(STRUCT)) {
				i++;
				// fill type.stuct
				while (i <= size && !(s = rawData.get(i)).contains(END_STRUCT)) {
					int indexEndOfVarName = s.indexOf(":");
					String varName = s.substring(0, indexEndOfVarName);
					s = s.substring(indexEndOfVarName + 2);
					String type = "";
					String value = "";
					if (s.contains(":")) {
						type = s.substring(0, s.indexOf(":"));
						value = s.substring(s.indexOf("=") + 1, s.indexOf(";"));
					} else {
						type = s.substring(0, s.indexOf(";"));
					}
					String coment = "";
					String rawComent = rawData.get(i + 1);

					if (rawComent.contains("(*")) {
						coment = rawComent.substring(rawComent.indexOf("*") + 1, rawComent.lastIndexOf("*"));
						i++;
					}
					this.addItem(new DUT_Item(varName, type, value, coment));

					i++;
				}
				return this;
			}
		}
		return this;
	}
	
	public static List<DUT_Item> sortItems(List<DUT_Item> items) {
		return items.stream().sorted(Comparator.comparing(DUT_Item::getVarName)).toList();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t" + STRUCT + "\n");
		items.forEach(i -> sb.append(i + "\n"));
		sb.append("\t\t" + END_STRUCT + ";\n");
		return sb.toString();
	}

}
