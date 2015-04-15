package com.elkardumen.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.elkardumen.conexion.ConexionBD;
import com.elkardumen.dao.IGenericDao;



public abstract class GenericDao implements IGenericDao {
	private static Logger logger = Logger.getLogger(GenericDao.class);
	protected Connection connection;
	protected CallableStatement cstmt;
	protected ResultSet result;

	public void openConnection(String tipo) {
		ConexionBD conn = new ConexionBD(tipo);
		connection = conn.getConexion();
	}
	public void endConnection() {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("Error al cerrar Result Set: " + e.getMessage(), e);
			}
		}
		if (cstmt != null) {
			try {
				cstmt.close();
			} catch (SQLException e) {
				logger.error("Error al cerrar Callable Statement: " + e.getMessage(), e);
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				logger.error("Error al cerrar la conexion: " + e.getMessage(), e);
			}
		}
	}
	
}
