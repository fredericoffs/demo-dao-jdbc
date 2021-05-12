package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/*
 * classe implementação JDBC do SellerDao
 */

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
 		try {
 			st = conn.prepareStatement(
 					"INSERT INTO seller "
 					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
 					+ "VALUES "
 					+ "(?, ?, ?, ?, ?)",
 					Statement.RETURN_GENERATED_KEYS);

 			st.setString(1, obj.getName());
 			st.setString(2, obj.getEmail());
 			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
 			st.setDouble(4, obj.getBaseSalary());
 			st.setInt(5, obj.getDepartment().getId());

 			int rowsAffected = st.executeUpdate();

 			if (rowsAffected > 0) {
 				ResultSet rs = st.getGeneratedKeys();
 				if (rs.next()) {
 					int id = rs.getInt(1);
 					obj.setId(id);
 				}
 				DB.closeResultSet(rs);
 			}
 			else {
 				throw new DbException("Unexpected error! No rows affected!");
 			}
 		}
 		catch (SQLException e) {
 			throw new DbException(e.getMessage());
 		}
 		finally {
 			DB.closeStatement(st);
 		}

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					" SELECT seller.*,department.Name as DepName\n" 
					+ " FROM seller INNER JOIN department\n"
					+ "   ON seller.DepartmentId = department.Id\n" 
					+ "WHERE seller.Id = ?");

			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Department dp = instantiateDepartment(rs);
				Seller sl = instantiateSeller(rs, dp);
				return sl;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					" SELECT seller.*,department.Name as DepName\n"
					+ " FROM seller INNER JOIN department\n"
					+ "   ON seller.DepartmentId = department.Id\n"
					+ "ORDER BY Name");

			rs = ps.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				
				// Buscar dentro do map se já existe o DepartmentId do banco
				Department dp = map.get(rs.getInt("DepartmentId"));
				
				// Caso não exista cria um novo instantiateDepartment
				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}
				
				Seller sl = instantiateSeller(rs, dp);
				list.add(sl);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}
	
	
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					" SELECT seller.*,department.Name as DepName\n"
					+ " FROM seller INNER JOIN department\n"
					+ "   ON seller.DepartmentId = department.Id\n"
					+ "WHERE DepartmentId = ?\n"
					+ "ORDER BY Name");

			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				
				// Buscar dentro do map se já existe o DepartmentId do banco
				Department dp = map.get(rs.getInt("DepartmentId"));
				
				// Caso não exista cria um novo instantiateDepartment
				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}
				
				Seller sl = instantiateSeller(rs, dp);
				list.add(sl);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller sl = new Seller();
		sl.setId(rs.getInt("Id"));
		sl.setName(rs.getString("Name"));
		sl.setEmail(rs.getString("Email"));
		sl.setBaseSalary(rs.getDouble("BaseSalary"));
		sl.setBirthDate(rs.getDate("BirthDate"));
		sl.setDepartment(dp);
		return sl;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}

}
