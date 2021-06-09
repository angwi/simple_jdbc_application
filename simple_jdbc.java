package jdbcapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//Simple program to show how to connect to MySql database version 8 using JDBC
public class JdbcApp {
// JDBC driver name and database URL                              
  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        
  static final String DATABASE_URL = "jdbc:mysql://localhost/your_database_name?serverTimezone=UTC";
  
    public static void main(String[] args) {
        // TODO code application logic here
        Connection connection = null;
        Statement statement = null;
        try
        {
           Class.forName( JDBC_DRIVER ); // load database driver class

           // establish connection to database                              
           connection =                                         
              DriverManager.getConnection( DATABASE_URL, "your_user_name", "your_password" );
           // create Statement for querying database
           statement = connection.createStatement();

           // query database                                        
           ResultSet resultSet = statement.executeQuery(            
             "SELECT employeeId, fullName, status FROM employees" );
           // process query results
           ResultSetMetaData metaData = resultSet.getMetaData();
           int numberOfColumns = metaData.getColumnCount();     
           System.out.println( "Employees:" );

           for ( int i = 1; i <= numberOfColumns; i++ )
              System.out.printf( "%-8s\t\t", metaData.getColumnName( i ) );
           System.out.println();

           while ( resultSet.next() )
          {
              for ( int i = 1; i <= numberOfColumns; i++ )
                 System.out.printf( "%-8s\t\t ", resultSet.getObject( i ) );
              System.out.println();
           } // end while
        } // end try
        catch ( SQLException sqlException )
        {
           sqlException.printStackTrace();
           System.exit( 1 );
        } // end catch
        catch ( ClassNotFoundException classNotFound )
        {
           classNotFound.printStackTrace();
           System.exit( 1 );
        } // end catch
        finally // ensure statement and connection are closed properly
        {                                                             
           try                                                        
           {                                                          
              statement.close();                                      
              connection.close();                                     
           } // end try                                               
           catch ( Exception exception )                              
           {                                                          
              exception.printStackTrace();                            
              System.exit( 1 );                                       
           } // end catch                                             
        } // end final
    }
    
}
