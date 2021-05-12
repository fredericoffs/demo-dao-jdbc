package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
 		try {
 			st = conn.prepareStatement(
 					"INSERT INTO department\n"
 					+ "(Name)\n"
 					+ "VALUES\n"
 					+ "(?)",
 					Statement.RETURN_GENERATED_KEYS);

 			st.setString(1, obj.getName());

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
 		} catch (SQLException e) {
 			throw new DbException(e.getMessage());
 		} finally {
 			DB.closeStatement(st);
 		}		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
 		try {
 			st = conn.prepareStatement(
 					"UPDATE department\n"
 					+ " SET Name = ?\n"
 					+ "WHERE Id = ?");

 			st.setString(1, obj.getName());
 			st.setInt(2, obj.getId());

 			st.executeUpdate();
 		} catch (SQLException e) {
 			throw new DbException(e.getMessage());
 		} finally {
 			DB.closeStatement(st);
 		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
 		try {
 			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
 			st.setInt(1, id);
 			st.executeUpdate();
 		} catch (SQLException e) {
 			throw new DbException(e.getMessage());
 		} finally {
 			DB.closeStatement(st);
 		}		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT department.*\n"
					+ "FROM department\n"
					+ "WHERE Id = ?");

			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Department dp = instantiateDepartment(rs);
				return dp;
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
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					" SELECT department.*\n"
					+ " FROM department\n"
					+ "ORDER BY Name");

			rs = ps.executeQuery();
			
			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				Department dp = instantiateDepartment(rs);
				list.add(dp);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("Id"));
		dp.setName(rs.getString("Name"));
		return dp;
	}
}
