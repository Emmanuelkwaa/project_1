package com.skillstorm.data.Repositories.implementations;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.RepositoryInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A generic DAO which is inherited by all other DAOs
 * Can be used to make general GET request - get(id), get(name), getAll() - and also delete(id)
 * Each method receives a table name
 * @param <T> The return type if one is provided
 */
public abstract class Repository<T> implements RepositoryInterface {
	private InventoryManagementDB db;

	public Repository(InventoryManagementDB db) {
		this.db = db;
	}

	/**
	 * Get by ID method
	 * @param id			ID of the row of data to get
	 * @param tableName		Name of table to get data from
	 * @return				JSON Object
	 */
	@Override
	public Object get(int id, String tableName) {
		String sql = "SELECT * FROM " + tableName + " WHERE id = " + id;
		try (Connection conn = this.db.getInstance().getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			ResultSetMetaData md = resultSet.getMetaData();
			int numCols = md.getColumnCount();

			JSONObject returnObj = null ;

			List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
				try {
					return md.getColumnName(i + 1);
				} catch (SQLException e) {
					e.printStackTrace();
					return "?";
				}
			}).collect(Collectors.toList());

            while (resultSet.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(cn -> {
                    try {
                        row.put(cn, resultSet.getObject(cn));
                    } catch (JSONException | SQLException e) {
                        e.printStackTrace();
                    }
                });
                returnObj =  row;
            }
            return returnObj;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get by NAME method
	 * @param name			Name - a column in the table - of the row of data to get
	 * @param tableName		Name of table to get data from
	 * @return				JSON Object
	 */
	@Override
	public Object getByName(String name, String tableName) {
        String sql = "SELECT * FROM ? WHERE name = ?";
        try (Connection conn = this.db.getInstance().getConnection()) {
            // Instead of using Statement, we will use PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, tableName);
            ps.setString(2, name); // This auto escapes any malicious characters

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData md = resultSet.getMetaData();

            int numCols = md.getColumnCount();

            JSONObject returnObj = null ;

            List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
                try {
                    return md.getColumnName(i + 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "?";
                }
            }).collect(Collectors.toList());

            while (resultSet.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(cn -> {
                    try {
                        row.put(cn, resultSet.getObject(cn));
                    } catch (JSONException | SQLException e) {
                        e.printStackTrace();
                    }
                });
                returnObj =  row;
            }
            return returnObj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}

	/**
	 * Get ALL data method
	 * @param tableName		Name of table to get data from
	 * @return				A Collection of JSON Object of the type(T) provided
	 */
	@Override
	public Collection<T> getAll(String tableName) {
		String sql = "SELECT * FROM " + tableName;

		// Connection will auto close in the event of a failure. Due to Autocloseable
		try (Connection conn = this.db.getInstance().getConnection()) {
			// ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
			// Create a Statement using the Connection object
			Statement stmt = conn.createStatement();

			// Executing the query returns a ResultSet which contains all of the values
			// returned
			ResultSet resultSet = stmt.executeQuery(sql);
			List<T> returnObjs = new LinkedList<T>();
			ResultSetMetaData md = resultSet.getMetaData();
			int numCols = md.getColumnCount();
			List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
				try {
					return md.getColumnName(i + 1);
				} catch (SQLException e) {
					e.printStackTrace();
					return "?";
				}
			}).collect(Collectors.toList());

			List<JSONObject> result = new LinkedList<>();
			while (resultSet.next()) {
				JSONObject row = new JSONObject();
				colNames.forEach(cn -> {
					try {
						row.put(cn, resultSet.getObject(cn));
					} catch (JSONException | SQLException e) {
						e.printStackTrace();
					}
				});
				result.add(row);
			}

			for (JSONObject obj : result) {
				returnObjs.add((T) obj);
			}

			return returnObjs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Delete by ID method
	 * @param id			ID of the row of data to delete
	 * @param tableName		Name of table to delete row from
	 * @return				A boolean - true: if deleted, false: if not deleted
	 */
	@Override
	public boolean delete(int id, String tableName) {
		String sql = "DELETE FROM " + tableName + " WHERE id = " + id;
		try (Connection conn = this.db.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);

//			ps.setString(1, tableName);
//			ps.setInt(2, id);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
				return true;
			}

			conn.rollback();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
