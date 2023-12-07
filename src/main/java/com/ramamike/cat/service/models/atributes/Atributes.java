package com.ramamike.cat.service.models.atributes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.ramamike.cat.utils.Utils;

public abstract class Atributes {

	protected SingleAtribute atributeSaveVersion;
	protected SingleAtribute atributeName;
	protected SingleAtribute atributeClassId;

	public static String BEGIN_ATTRIBUTES = "BEGIN_ATTRIBUTES";
	public static String END_ATTRIBUTES = "END_ATTRIBUTES";

	protected List<SingleAtribute> atributes = new ArrayList<>(5);

	public Atributes() {
	}

	public Atributes(String saveVersion, String name, String classId) {
		this.atributeSaveVersion = new SingleAtribute("SaveVersion", saveVersion);
		this.atributeName = new SingleAtribute("Name", name);
		this.atributeClassId = new SingleAtribute("ClassId", classId);
		this.atributes.add(this.atributeSaveVersion);
		this.atributes.add(this.atributeName);
		this.atributes.add(this.atributeClassId);
	}

	public abstract SingleAtribute getAtributeSaveVersion();

	public abstract SingleAtribute getAtributeName();

	public abstract SingleAtribute getAtributeClassId();

	public abstract List<SingleAtribute> getAtributes();

	public Atributes parse(List<String> rawData) {
		return Utils.parse(rawData, BEGIN_ATTRIBUTES, END_ATTRIBUTES, this, f);
	}

	Function<String, Atributes> f = (row) -> {
		SingleAtribute atribute = new SingleAtribute(row.substring(0, row.indexOf("=")),
				row.substring(row.indexOf("=") + 1));
		this.getAtributes().add(atribute);
		return this;
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(BEGIN_ATTRIBUTES + "\n");
		atributes.forEach(a -> sb.append("\t" + a.toString() + "\n"));
		sb.append(END_ATTRIBUTES);
		return sb.toString();
	}
}
