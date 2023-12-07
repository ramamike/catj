package com.ramamike.cat.service.models;

import java.util.List;

import com.ramamike.cat.service.models.atributes.AtributesDUT;
import com.ramamike.cat.service.models.dut_type.DUT_Type;

public class DUT {

	private AtributesDUT atributes = new AtributesDUT();
	private DUT_Type type = new DUT_Type();

	public DUT() {
	}

	@Override
	public String toString() {
		return atributes.toString() + "\n" + type.toString();
		
	}

	public DUT parseDUT(List<String> rawData) {
		atributes.parse(rawData);
		type.parse(rawData);
		return this;
	}

	public AtributesDUT getAtributes() {
		return atributes;
	}

	public DUT_Type getType() {
		return type;
	}

}
