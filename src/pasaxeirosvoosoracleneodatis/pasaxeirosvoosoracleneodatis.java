package pasaxeirosvoosoracleneodatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;

public class pasaxeirosvoosoracleneodatis {

    static Connection conn = null;
    static String dni;
    static int idvoovolta, idvooida;
    static int prezoida,prezovolta;

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
        conexion();
        //prezoreserva();
        lerReservas();
        //nReservas();
        

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
        conn.close();
        odb.close();
    }

    public static void nReservas() throws SQLException {
        Statement state = conn.createStatement();
        odb = ODBFactory.open(ODB_NAME);
        Objects<Reserva> res = odb.getObjects(Reserva.class);
        Reserva auxres = null;
        while (res.hasNext()) {
            auxres = res.next();
            System.out.println(auxres.getDni());
            dni = auxres.getDni();
            state.executeUpdate("update pasaxeiros set NRESERVAS=NRESERVAS+1 where DNI='" + dni + "'");
        }
        System.out.println("Updated");
        conn.close();
        odb.close();
    }

    public static void prezoreserva() throws SQLException {
        int total;
        odb = ODBFactory.open(ODB_NAME);
        Objects<Reserva> resv = odb.getObjects(Reserva.class);
        Reserva ares = null;
        while (resv.hasNext()) {
            ares = resv.next();
            System.out.println("Ida :"+ares.getIdvooida());
            idvooida = ares.getIdvooida();
            System.out.println("Volta :"+ares.getIdvoovolta());
            idvoovolta = ares.getIdvoovolta();
            getprezoIda();
            getprezoVolta();
            
            total = prezovolta + prezoida;
            System.out.println(total);
            ares.setPrezoreserva(total);
            odb.store(ares);
            System.out.println("ares"+ares.getPrezoreserva());
            
            
            
        }
        odb.close();
        conn.close();
    
    }
        public static void getprezoIda() throws SQLException{
            Statement state = conn.createStatement();
            ResultSet rs =state.executeQuery("select prezo from voos where voo='"+idvooida+"'");
            while (rs.next()){
                System.out.println("Prezo ida "+rs.getInt(1));
                prezoida = rs.getInt(1);
                
            }
                
        }
        public static void getprezoVolta() throws SQLException{
            Statement state = conn.createStatement();
            ResultSet rs =state.executeQuery("select prezo from voos where voo='"+idvoovolta+"'");
            while (rs.next()){
                System.out.println("Prezo volta "+rs.getInt(1));
                prezovolta = rs.getInt(1);
            }
        }

}
