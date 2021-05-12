package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("=== TEST Nro 1: Seller findById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n=== TEST Nro 2: Seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list  = sellerDao.findByDepartment(department);
		
		for (Seller sl : list) {
			System.out.println(sl);
		}
		
		System.out.println("\n=== TEST Nro 3: Seller findByAll ===");
		list  = sellerDao.findAll();
		
		for (Seller sl : list) {
			System.out.println(sl);
		}

		
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		System.out.println("\n=== TEST Nro : Department findById ===");
		Department dp = departmentDao.findById(1);
		System.out.println(dp);
	}

}
