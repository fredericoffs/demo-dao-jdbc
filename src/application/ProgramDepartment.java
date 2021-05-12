package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class ProgramDepartment {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Scanner sc = new Scanner(System.in);

		System.out.println("=== TEST Nro 1: Department findByAll ===");
		List<Department> list = departmentDao.findAll();

		for (Department sl : list) {
			System.out.println(sl);
		}

		System.out.println("\n=== TEST Nro 2: Department findById ===");
		Department department = departmentDao.findById(3);
		System.out.println(department);
		
		System.out.println("\n=== TEST 3: Department insert =====");
		Department newDepartment = new Department(null, "Store");
		departmentDao.insert(newDepartment);
 		System.out.println("Inserted! New id = " + newDepartment.getId());
 		
 		System.out.println("\n=== TEST 4: Department update =====");
 		department = departmentDao.findById(1);
 		department.setName("Perfume");
 		departmentDao.update(department);
 		System.out.println("Update completed");

		System.out.println("\n=== TEST 5: Department delete =====");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");
		sc.close();
	}
}
