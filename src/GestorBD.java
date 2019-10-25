import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("");
		}
		catch (SQLException e) {
			throw new BDException("No se pudo crear la table 'usuario'", e);
		}

		
	}

}
