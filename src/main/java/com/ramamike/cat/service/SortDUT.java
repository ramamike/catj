package com.ramamike.cat.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ramamike.cat.constants.Constants;
import com.ramamike.cat.models.config.Configuration;
import com.ramamike.cat.service.models.DUT;
import com.ramamike.cat.service.models.dut_type.DUT_Item;
import com.ramamike.cat.service.models.dut_type.DUT_Struct;
import com.ramamike.cat.utils.Utils;

public class SortDUT {

	private static final Logger log = LoggerFactory.getLogger(SortDUT.class);
	private static final Configuration configuration = Configuration.getInstance();
	private final String className = this.getClass().getSimpleName();

	public void sort() {
		String path = configuration.getConfigFeutureValue(className, Constants.CONFIG_FEATURE_PATH);

		File file = Utils.chooseFile(path);

		if (file == null) {
			Utils.showAlert("No DUT was selected");
			return;
		}
		String pathToDuts = file.getParentFile().getAbsolutePath();

		// save new path for DUTs
		if (file.getAbsolutePath() != path) {
			configuration.setConfigFeutureValue(className, Constants.CONFIG_FEATURE_PATH, pathToDuts);
		}

		List<String> rawData = Utils.getData(file);
		DUT dut = new DUT();
		dut.parseDUT(rawData);
//		System.out.println(dut);

		DUT_Struct struct = dut.getType().getStruct();

		List<DUT_Item> items = struct.getItems();

		List<String> itemVars = items.stream().map(item -> item.getVarName()).collect(Collectors.toList());

		int startIndexOfInnerStruct = itemVars.indexOf(DUT_Item.dwStart);
		int endIndexOfInnerStruct = itemVars.indexOf(DUT_Item.bEnd);

		List<DUT_Item> items_beforeInnerStruct = new ArrayList<>(3);
		List<DUT_Item> items_innerStruct = new ArrayList<>(3);
		List<DUT_Item> items_afterInnerStruct = new ArrayList<>(3);

		int itemsSize = items.size();

		if (endIndexOfInnerStruct <= 0) {
			struct.setItems(DUT_Struct.sortItems(items));
		} else if ((startIndexOfInnerStruct == 0) && (endIndexOfInnerStruct >= 1)) {
			items_innerStruct = items.subList(0, endIndexOfInnerStruct + 1);
			items_afterInnerStruct = DUT_Struct.sortItems(items.subList(endIndexOfInnerStruct + 1, itemsSize));
			struct.setItems(Stream.concat(items_innerStruct.stream(), items_afterInnerStruct.stream()).toList());
		} else if ((startIndexOfInnerStruct >= 1) && (endIndexOfInnerStruct >= 2)) {
			items_beforeInnerStruct = items.subList(0, startIndexOfInnerStruct);
			items_innerStruct = items.subList(startIndexOfInnerStruct, endIndexOfInnerStruct + 1);
			items_afterInnerStruct = items.subList(endIndexOfInnerStruct + 1, itemsSize);
			items_afterInnerStruct = Stream.concat(items_beforeInnerStruct.stream(), items_afterInnerStruct.stream())
					.toList();
			items_afterInnerStruct = DUT_Struct.sortItems(items_afterInnerStruct);
			struct.setItems(Stream.concat(items_innerStruct.stream(), items_afterInnerStruct.stream()).toList());
		}
		
		
		
		Utils.saveData(dut.toString(), file.getAbsolutePath());
		
		System.out.println("\n" + dut);

		Utils.showAlert("DUT was sorted");

	}

}