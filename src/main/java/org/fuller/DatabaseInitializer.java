package org.fuller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void init() throws SQLException {
        try (Connection conn = Main.ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users (" //
                    + "id BIGINT IDENTITY NOT NULL PRIMARY KEY, " //
                    + "email VARCHAR(100) NOT NULL, " //
                    + "password VARCHAR(100) NOT NULL, " //
                    + "createdAt BIGINT NOT NULL, " //
                    + "UNIQUE (email))")) {
                ps.executeUpdate();
            }
        }
    }
}
