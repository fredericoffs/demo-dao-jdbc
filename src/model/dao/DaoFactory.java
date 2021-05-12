package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

/*
 * Classe auxiliar responsável por instanciar os Daos
 * para não expor a implementação apenas a interface.
 */

public class DaoFactory {
	
	//SellerDao sellerDao = DaoFactory.createSellerDao();
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}

}
