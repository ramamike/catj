package com.ramamike.cat.service.models.atributes;

import java.util.List;

public class AtributesDUT extends Atributes {
	
	public AtributesDUT() {
		super();
	}

	public AtributesDUT(String atributeSaveVersion, String atributeName, String atributeClassId) {
		super(atributeSaveVersion, atributeName, atributeClassId);
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
	
	@Override
	public String toString() {
		return super.toString();
	}

}
