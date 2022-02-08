import de.htwberlin.dao.Ausgabe;
import de.htwberlin.dao.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AusgabeDao {
    private Connection connection;
    public Ausgabe ladeAusgabe(Integer auNr) {
        ResultSet r = null;
        Ausgabe a = null;
        try {
            try (PreparedStatement s = connection.prepareStatement
                    ( "SELECT * FROM Ausgabe WHERE AuNr = ?")) {
                s.setInt(1, auNr);
                r = s.executeQuery();
                if (!r.next()) { // Ausgabe nicht gefunden
                    throw new DataAccessException("Ausgabe nicht gefunden");
                } else a = new Ausgabe();
                a.setAuNr(auNr);
                a.setAusgabeDatum(r.getDate("Datum"));
            }

                try (PreparedStatement s = connection.prepareStatement(
                        "SELECT MAX(LENGTH(Inhalt)) AS Maxlnhalt FROM Artikel WHERE AuNr = ?")) {
                    s.setInt(1, auNr);
                r = s.executeQuery();
                if (r.next())
                    ( a.setZeichenLaengsterArtikel(r.getInt("MaxInhalt"));
            }

        try (PreparedStatement s = connection.prepareStatement(
                "SELECT Titel FROM Artikel WHERE AuNr = ?")) {
            s.setInt(1, auNr);
        r = s.executeQuery();
        a.setTitel(new LinkedList<String>());
        while (r.next()) {
            List<String> titelListe = a.getTitel();
            titelListe.add(r.getString("Titel"));
        }
        }
    }
    catch(SQLException exp) {
        // Freigabe aller Ressourcen
    }
        return a;
    }

}
