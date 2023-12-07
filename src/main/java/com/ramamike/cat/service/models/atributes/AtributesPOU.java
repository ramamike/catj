package com.ramamike.cat.service.models.atributes;

import java.util.List;

public class AtributesPOU extends Atributes {
	
	private SingleAtribute atributeBodyClassId;
	
	public AtributesPOU(String saveVersion, String name, String classId, String bodyClassId) {
		super(saveVersion, name, classId);
		this.atributeBodyClassId = new SingleAtribute("BodyClassId", bodyClassId);
		super.atributes.add(this.atributeBodyClassId);
	}

	public SingleAtribute getAtributeBodyClassId() {
		return atributeBodyClassId;
	}

	@Override
	public SingleAtribute getAtributeSaveVersion() {
		return super.atributeSaveVersion;
	}

	@Override
	public SingleAtribute getAtributeName() {
		return super.atributeName;
	}

	@Override
	public SingleAtribute getAtributeClassId() {
		return super.atributeClassId;
	}

	@Override
	public List<SingleAtribute> getAtributes() {
		return super.atributes;
	}
}
