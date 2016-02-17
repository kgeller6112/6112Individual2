import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class QueryWithContext {

    public static void query(PrintWriter out) throws NamingException {
        Context context = null;
        DataSource datasource = null;
        Connection connect = null;
        Statement statement = null;

        try {
            // Get the context and create a connection
            context = new InitialContext();
            datasource = (DataSource) context.lookup("java:/comp/env/jdbc/test");
            connect = datasource.getConnection();

            // Create the statement to be used to get the results.
            statement = connect.createStatement();
            String query = "SELECT * FROM TestData";

            // Execute the query and get the result set.
            ResultSet resultSet = statement.executeQuery(query);
            out.println("<table border=1><tr><th>ID</th><th>Name</th></tr>");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                
                out.println("<tr><td>" + id + "</td><td>" + name + "<td></tr>");
            }
            out.println("</table>");
        } catch (SQLException e) { e.printStackTrace(out);
        } finally {
            // Close the connection and release the resources used.
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(out); }
            try { connect.close(); } catch (SQLException e) { e.printStackTrace(out); }
        }
    }
}
