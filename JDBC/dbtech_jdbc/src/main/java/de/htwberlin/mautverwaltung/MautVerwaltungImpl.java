package de.htwberlin.mautverwaltung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.htwberlin.entity.Mautabschnitt;
import de.htwberlin.exceptions.DataException;

/**
 * Die Klasse realisiert die Mautverwaltung.A
 *
 * @author Patrick Dohmeier
 */
public class MautVerwaltungImpl implements IMautVerwaltung {

    private static final Logger L = LoggerFactory.getLogger(MautVerwaltungImpl.class);
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    @Override
    public String getStatusForOnBoardUnit(long fzg_id) {

        PreparedStatement preparedstatement = null;
        ResultSet resultSet = null;
        String query = "SELECT STATUS FROM FAHRZEUGGERAT WHERE FZG_ID = ?";
        String res = "";
        try {

            preparedstatement = getConnection().prepareStatement(query);
            preparedstatement.setLong(1, fzg_id);
            resultSet = preparedstatement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getString("STATUS");
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        } catch (NullPointerException exp) {
            throw new RuntimeException(exp);
        }
        return res;
    }

    @Override
    public int getUsernumber(int maut_id) {
        PreparedStatement preparedstatement = null;
        ResultSet resultSet = null;
        String query = "SELECT Nutzer.Nutzer_ID FROM Nutzer"
                + " INNER JOIN Fahrzeug ON Nutzer.Nutzer_ID = Fahrzeug.Nutzer_ID"
                + " INNER JOIN Fahrzeuggerat ON Fahrzeug.FZ_ID = Fahrzeuggerat.FZ_ID"
                + " INNER JOIN Mauterhebung ON Mauterhebung.FZG_ID = Fahrzeuggerat.FZG_ID"
                + " WHERE MAUT_ID = ?";
        int res = 0;
        try {
            preparedstatement = getConnection().prepareStatement(query);
            preparedstatement.setInt(1, maut_id);
            resultSet = preparedstatement.executeQuery();
            while (resultSet.next()) {
                res = resultSet.getInt(1);
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        } catch (NullPointerException exp) {
            throw new RuntimeException(exp);
        }
        return res;
    }

    @Override
    public void registerVehicle(long fz_id, int sskl_id, int nutzer_id, String kennzeichen, String fin, int achsen,
                                int gewicht, String zulassungsland) {
        PreparedStatement preparedstatement = null;
        ResultSet resultSet = null;
        String ddl = "INSERT INTO Fahrzeug(fz_id, sskl_id, nutzer_id, kennzeichen, fin, achsen, gewicht, zulassungsland) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedstatement = getConnection().prepareStatement(ddl);
            preparedstatement.setLong(1, fz_id);
            preparedstatement.setInt(2, sskl_id);
            preparedstatement.setInt(3, nutzer_id);
            preparedstatement.setString(4, kennzeichen);
            preparedstatement.setString(5, fin);
            preparedstatement.setInt(6, achsen);
            preparedstatement.setInt(7, gewicht);
            preparedstatement.setString(8, zulassungsland);
            resultSet = preparedstatement.executeQuery();
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        } catch (NullPointerException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public void updateStatusForOnBoardUnit(long fzg_id, String status) {
        PreparedStatement preparedstatement = null;
        ResultSet resultSet = null;
        String dml = "UPDATE fahrzeuggerat SET Status = ? WHERE fzg_id = ?";
        try {
            preparedstatement = getConnection().prepareStatement(dml);
            preparedstatement.setString(1, status);
            preparedstatement.setLong(2, fzg_id);
            resultSet = preparedstatement.executeQuery();
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        } catch (NullPointerException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public void deleteVehicle(long fz_id) {
        PreparedStatement preparedstatement = null;
        ResultSet resultSet = null;
        String dml = "DELETE FROM Fahrzeug WHERE fz_id=?";
        try {

            preparedstatement = getConnection().prepareStatement(dml);
            preparedstatement.setLong(1, fz_id);
            resultSet = preparedstatement.executeQuery();
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        } catch (NullPointerException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public List<Mautabschnitt> getTrackInformations(String abschnittstyp) {

        PreparedStatement preparedstatement = null;
        ResultSet resultSet = null;
        ArrayList<Mautabschnitt> res = new ArrayList<>();
        String query = "SELECT abschnitts_id, laenge, "
                + "start_koordinate, ziel_koordinate, name,"
                + " abschnittstyp FROM Mautabschnitt WHERE Abschnittstyp  = ?";
        try {
            preparedstatement = getConnection().prepareStatement(query);
            preparedstatement.setString(1, abschnittstyp);
            resultSet = preparedstatement.executeQuery();
            while (resultSet.next()) {

                Mautabschnitt mautabschnitt = new Mautabschnitt();
                mautabschnitt.setAbschnitts_id(resultSet.getInt(1));
                mautabschnitt.setLaenge(resultSet.getInt(2));
                mautabschnitt.setStart_koordinate(resultSet.getString(3));
                mautabschnitt.setZiel_koordinate(resultSet.getString(4));
                mautabschnitt.setName(resultSet.getString(5));
                mautabschnitt.setAbschnittstyp(resultSet.getString(5));
                res.add(mautabschnitt);
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        } catch (NullPointerException exp) {
            throw new RuntimeException(exp);
        }
        return res;
    }

}