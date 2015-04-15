package com.elkardumen.dao.impl;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.elkardumen.bean.Grupo;
import com.elkardumen.dao.IGrupoDao;

public class GrupoDao extends GenericDao implements IGrupoDao {
	private static Logger logger = Logger.getLogger(GrupoDao.class);

	@Override
	public List<Grupo> obtenerGrupos(String rr, String aa) {
		List<Grupo> grupos = new ArrayList<Grupo>();
		try {
			//Stored Procedure con 2 datos de entrada y uno de salida
			cstmt = connection.prepareCall("{call ElstoredProcedure(?, ?, ?)}");
            cstmt.setString(1, rr);
            
            if (aa != null) {
            	cstmt.setInt(2, Integer.parseInt(aa));
            } else {
            	cstmt.setNull(2, Types.NUMERIC);
            }
			
            cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();

            result = (ResultSet) cstmt.getObject(3);

            while (result.next()) {
            	Grupo grupo = new Grupo();
            	grupo.setGrupo(result.getString("grupo"));
            	grupos.add(grupo);
            }
		} catch(Exception e) {
			logger.error("Error al consultar Stored: " + e.getMessage(), e);
		}
		return grupos;
	}
}
