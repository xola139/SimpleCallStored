package com.elkardumen.conexion;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;


public class ConexionBD {
	private String bd;
	private java.sql.Connection oConnection = null;

	private static Logger logger=Logger.getLogger(ConexionBD.class);
	public ConexionBD(String bd) {
		try {
			this.bd = bd;
			// getConexionInterno();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}


	public void CierraConexion(){
		try{
			if (!this.oConnection.isClosed()) {
				this.oConnection.close();
			}	
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
	}

	public Connection getConexion() {
		try {
			
			String driver = null;
			String usuario = null;
			String password = null;
			
			Properties configuration = new Properties();
			InputStream in = getClass().getResourceAsStream("/ConexionBD.properties");
			configuration.load(in);
			driver =configuration.getProperty("driver" + this.bd);
			usuario =configuration.getProperty("user" + this.bd);
			password =configuration.getProperty("password" + this.bd);
			
			
			boolean bdConexionExito = false;
			int intentoBD = 0;

			while (!bdConexionExito) {
				intentoBD++;
				try {
					if (intentoBD <= 7) {
						
						
							DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
							oConnection = DriverManager.getConnection(driver,usuario, password);	
						
						
						bdConexionExito = true;
					} else {
						bdConexionExito = true;
					}
				} catch (Exception e) {
					bdConexionExito = false;
				}
			}// end while

		} catch (Exception e) {
			
			logger.info(e.getMessage());

		}
		return oConnection;
	}

	@SuppressWarnings("unused")
	private void closeConexion() throws SQLException {
		this.oConnection.close();

	}

	public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				// logger.error("The result set cannot b closed.", e);
			}
		} else if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				// logger.error("The statement cannot be closed.", e);
			}
		} else if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				// logger.error("The data source connection cannot be closed.",
				// e);
			}
		}
	}

	public static void terminaConnection(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("Error al cerrar la conexion: ");
				//e.printStackTrace();
			}
	}

	public static void terminaConnection(Connection conn,
			PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar Result Set: ");
			//	e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar Prepared Statement: ");
				//e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("Error al cerrar la conexion: ");
//				e.printStackTrace();
			}
		}
	}


}
