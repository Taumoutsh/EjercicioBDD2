import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorBD {
	
	private Connection conn = null;
	
	public void connectar() throws BDException{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:data/usuario.sql.bd");
		} catch (ClassNotFoundException e) {
			throw new BDException("No se puede cargar el driver", e);
		} catch (SQLException e)
		{
			throw new BDException("No se a podida conectar a la BD", e);
		}
	}
	
	public void disconnectar() throws BDException{

		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			throw new BDException("No se ha podido cerrar la conexion a la BD", e);
		}
	}
	
	public void crearTableUsuario() throws BDException {
		try (Statement stmt = conn.createStatement()) {
			String sql = "CREATE TABLE usuario(id INTEGER(11) AUTO INCREMENT, nombre VARCHAR(30), apellido VARCHAR(30), PRIMARY KEY (id))";
			stmt.executeUpdate(sql);
		}
		catch (SQLException e) {
			throw new BDException("No se pudo crear la table 'usuario'", e);
		}
	}
	
	public void borrarTableUsuario() throws BDException {
		try (Statement stmt = conn.createStatement()) {
			String sql = "DROP TABLE usuario";
			stmt.executeUpdate(sql);
		}
		catch (SQLException e) {
			throw new BDException("No se pudo borrar la table 'usuario'", e);
		}	
	}
	
	public List<Usuario> obtenerTodosUsuario() throws BDException{
		
		List<Usuario> listUsuario = new ArrayList<>();
		
		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * usuario";
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO");
			while(rs.next()) {
				listUsuario.add(new Usuario(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido")));
			}
		}
		catch (SQLException e) {
			throw new BDException("No se pudo obtener la lista la table 'usuario'", e);
		}	
		
		return listUsuario;
	}
	
	public Usuario obtenerUsuario(int id) throws BDException{
		Usuario u = new Usuario();
		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * usuario WHERE id="+id;
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO");
			if(rs.next()){
				u = new Usuario(
							rs.getInt("id"),
							rs.getString("nombre"),
							rs.getString("apellido"));
			}
		}
		catch (SQLException e) {
			throw new BDException("No se pudo obtener el usuario con el id"+id, e);
		}
		return u;
	}
	
	public List<Usuario> obtenerUsuarioPorSuApellido(String apellido) throws BDException{
		List<Usuario> listUsuario = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * usuario WHERE apellido='"+apellido+"'";
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO");
			while(rs.next()) {
				listUsuario.add(new Usuario(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido")));
			}
		}
		catch (SQLException e) {
			throw new BDException("No se pudo obtener el usuario con el apellido"+apellido, e);
		}
		return listUsuario;
	}
	
	public List<Usuario> obtenerUsuarioPorSuApellidoYSuNombre(String apellido, String nombre) throws BDException{
		List<Usuario> listUsuario = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * usuario WHERE apellido='"+apellido+"' AND nombre='"+nombre+"'";
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO");
			while(rs.next()) {
				listUsuario.add(new Usuario(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido")));
			}
		}
		catch (SQLException e) {
			throw new BDException("No se pudo obtener el usuario con el apellido"+apellido, e);
		}
		return listUsuario;
	}
	
	public void gardar(Usuario u) throws BDException{
		try (Statement stmt = conn.createStatement()) {
			String sql = "INSERT INTO usuario VALUES ("+u.getId()+", '"+u.getNombre()+"', '"+u.getApellido()+"')";
			stmt.executeUpdate(sql);
		}
		catch(SQLException e){
			throw new BDException("No se pudo garder el usuario", e);
		}
	}

}
