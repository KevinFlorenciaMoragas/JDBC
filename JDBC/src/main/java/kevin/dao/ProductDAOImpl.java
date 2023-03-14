package kevin.dao;
import kevin.model.Product;

import java.sql.*;
public class ProductDAOImpl implements ProductDAO{

    static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/m3?SSL=false";
    static final String DB_USER = "root";
    static final String DB_PASS = "monlau21!";
    private void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("ERROR: failed to load MySQL JDBC driver.");
            e.printStackTrace();
        }
    }


    @Override
    public void insert(Product product) {
        Connection conn = null;
        try {
            registerDriver();
            // abrir la conexion
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            try (Statement stmt = conn.createStatement()) {
                // enviar el commando insert
                stmt.executeUpdate("insert into product values ("
                        + product.getId() + ",'"
                        + product.getName() + "',"
                        + product.getPrice() + ");");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = null;
        try {
            registerDriver();
            // abrir la conexion
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            // consulta select (selecciona el producto con ID especificado)
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE from product where id = ?")) {
                // indicar el ID que buscamos
                ps.setInt(1, id);
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("A user was deleted successfully!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        Connection conn = null;
        try {
            registerDriver();
            // abrir la conexion
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            try (Statement stmt = conn.createStatement()) {
                // enviar el commando insert
                String sql = "UPDATE product SET name=?, price=? WHERE id=?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, product.getName());
                statement.setDouble(2, product.getPrice());
                statement.setInt(3, product.getId());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("An existing user was updated successfully!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public Product read(Integer id) {
        Connection conn = null;
        Product product = null;

        try {
            registerDriver();
            // abrir la conexion
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            // consulta select (selecciona el producto con ID especificado)
            try (PreparedStatement ps = conn.prepareStatement(
                    "select * from product where id = ?")) {
                // indicar el ID que buscamos
                ps.setInt(1, id);
                // ejecutar la consulta
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // obtener cada una de la columnas y mapearlas a la clase Product
                        product = new Product(id,
                                rs.getString("name"),
                                rs.getDouble("price"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return product;
    }





}
