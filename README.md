# SimpleCallStored

Simple ejecucion de un stored procedure desde java, basicamente lo que se hace en este ejemplo es Generar un CallableStatement para consumir el stored

Se puede bajar el proyecto y configurar la llamada de un stored , como primer paso se debe configurar el .properties . Por default tiene configuración para conectarse a una base de datos local:

>Archivo .properties para conexion Oracle
```
driverOracle=jdbc:oracle:thin:@127.0.0.1:1525:THEDATABASE
userOracle=myUser
passwordOracle=MyPAssword
```

Ejecutar main 
========

1.-Desde la clase Test en el paquete  com.elkardumen.unit que contiene el main se crea objeto de tipo GrupoDao, para generar la conexión y poder hacer el llamado del metodo obtenerGrupos

```Java
    List<Grupo> grupos = new ArrayList<Grupo>();
		IGrupoDao grupoDao = new GrupoDao();
		grupoDao.openConnection("Oracle");
		grupos = grupoDao.obtenerGrupos(null, null);
		grupoDao.endConnection();
```

2.-En el metodo obtenerGrupos  realiza el llamado del stored procedure,  en el ejemplo se envian 2 parametros de entrada y uno de salida.

```Java
            cstmt = connection.prepareCall("{call ElstoredProcedure(?, ?, ?)}");
            cstmt.setString(1, rr);
            
            if (aa != null) {
            	cstmt.setInt(2, Integer.parseInt(aa));
            } else {
            	cstmt.setNull(2, Types.NUMERIC);
            }
			       //parametro de salida
            cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			      cstmt.execute();

            result = (ResultSet) cstmt.getObject(3);
```
3.- Dado que el procedimiendo almacenado ejecuta una serie de operaciones y este regresa una serie de campos, en el ejemplo se espera que el stored regrese el campo "grupo" , este stored puede traer uno o mas registros

```Java
        while (result.next()) {
            	Grupo grupo = new Grupo();
            	grupo.setGrupo(result.getString("grupo"));
            	grupos.add(grupo);
            }
```

