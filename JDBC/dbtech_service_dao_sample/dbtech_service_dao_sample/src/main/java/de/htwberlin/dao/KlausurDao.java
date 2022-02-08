package de.htwberlin.dao;
import de.htwberlin.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
public class EntityDao {
    private Connection connection;
    public Entity Entitymethod(Integer parameter) {
        ResultSet r = null;
        Entity = null;
        try {
            try (PreparedStatement s = connection.prepareStatement
                    ( "SELECT …")) {
                s.setInt(1, );
                r = s.executeQuery();
            }
            try (PreparedStatement s = connection.prepareStatement
                    ( "SELECT …")) {
                s.setInt(1, );
                r = s.executeQuery();
            }
            try (PreparedStatement s = connection.prepareStatement
                    ( "SELECT …")) {
                s.setInt(1, );
                r = s.executeQuery();
            }
        }
    }
    catch(SQLException exp) {

    }
        return e;
}
}
