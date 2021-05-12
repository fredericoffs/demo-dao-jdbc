package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		System.out.println("=== TEST Nro 1: Seller findById ===");
		SellerDao sellerDao = DaoFactory.createSellerDao();
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

		System.out.println("\n=== TEST 4: seller insert =====");
 		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
 		sellerDao.insert(newSeller);
 		System.out.println("Inserted! New id = " + newSeller.getId());
 		
 		System.out.println("\n=== TEST 5: seller update =====");
 		seller = sellerDao.findById(1);
 		seller.setName("Martha Waine");
 		sellerDao.update(seller);
 		System.out.println("Update completed");
		
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		System.out.println("\n=== TEST Nro : Department findById ===");
		Department dp = departmentDao.findById(1);
		System.out.println(dp);
	}

}
