package com.ramamike.cat.service.models.atributes;

public class SingleAtribute {

	private String key;
	private String value;

	public SingleAtribute(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return key + "=" + value;
	}

}
