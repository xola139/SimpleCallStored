package com.elkardumen.unit;

import java.util.ArrayList;
import java.util.List;

import com.elkardumen.bean.Grupo;
import com.elkardumen.dao.IGrupoDao;
import com.elkardumen.dao.impl.GrupoDao;



public class Test {

	public static void main(String[] args) {
		List<Grupo> grupos = new ArrayList<Grupo>();
		IGrupoDao grupoDao = new GrupoDao();
		grupoDao.openConnection("Oracle");
		grupos = grupoDao.obtenerGrupos(null, null);
		grupoDao.endConnection();
		
		System.out.println(grupos);

	}

}
