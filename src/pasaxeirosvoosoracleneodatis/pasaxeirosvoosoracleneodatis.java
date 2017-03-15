
package pasaxeirosvoosoracleneodatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;


public class pasaxeirosvoosoracleneodatis {
    static Connection conn = null;
    static String  dni;
    public static void conexion() throws SQLException {

        String driver = "jdbc:oracle:thin:";
        String host = "localhost.localdomain";
        String porto = "1521";
        String sid = "orcl";
        String usuario = "hr";
        String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;
        conn = DriverManager.getConnection(url);

    }
    public static final String ODB_NAME = "internacional";
    static ODB odb;
    static String depName = null;
    
    public static void main(String[] args) throws Exception {
        lerReservas();
    }
    public static void lerReservas() throws Exception {
        odb = ODBFactory.open(ODB_NAME);
        Objects<Reserva> reservas = odb.getObjects(Reserva.class);
        
        Reserva res = null;
        while (reservas.hasNext()) {
            res = reservas.next();
            res.setConfirmado(1);
            System.out.println(res.toString());
            
        }
        odb.close();
    }
        public static void aumentar_nreservas(){
            
        }
    }
    
        
    
    


