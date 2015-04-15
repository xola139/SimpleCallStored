package com.elkardumen.dao;

public interface IGenericDao {
	void openConnection(String tipo);
	void endConnection();
}
