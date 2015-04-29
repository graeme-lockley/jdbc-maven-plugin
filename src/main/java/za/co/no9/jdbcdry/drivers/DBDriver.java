package za.co.no9.jdbcdry.drivers;

import za.co.no9.jdbcdry.port.jsqldslmojo.Configuration;
import za.co.no9.jdbcdry.tools.DatabaseMetaData;
import za.co.no9.jdbcdry.tools.TableMetaData;
import za.co.no9.jdbcdry.tools.TableName;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public interface DBDriver {
    void setConfiguration(Configuration configuration, Connection connection);

    Connection getConnection();

    DatabaseMetaData databaseMetaData();

    TableMetaData tableMetaData(TableName tableName) throws SQLException;

    TableMetaData resolveForeignConstraints(Map<TableName, TableMetaData> tables, TableMetaData tableMetaData) throws SQLException;

    Optional<String> getDBCatalogue();

    Optional<String> getDBSchemaPattern();

    Optional<String> getDBTablePattern();
}
