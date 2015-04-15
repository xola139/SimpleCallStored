package com.elkardumen.dao;

import java.util.List;

import com.elkardumen.bean.Grupo;

public interface IGrupoDao extends IGenericDao {
	List<Grupo> obtenerGrupos(String razonSocial, String afiliacion);
}
