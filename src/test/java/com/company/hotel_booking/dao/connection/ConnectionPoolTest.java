//package com.company.hotelBooking.dao.connection;
//
//import java.sql.Connection;
//import java.util.ArrayDeque;
//import java.util.Deque;
//
//public class ConnectionPoolTest {
//
//    private DataSource dataSource = DataSource.getINSTANCE();
//    private ConnectionPool connectionPool = dataSource.getConnectionPool();
//
//    @DataProvider
//    public Object[][] dp() {
//        return new Object[][]{new Object[]
//                {DataSource.getINSTANCE().getConnectionPool()}};
//    }
//
//    @Test(dataProvider = "dp")
//    public void getInstanceTest(ConnectionPool pool) {
//        assertSame(DataSource.getINSTANCE().getConnectionPool(), pool);
//    }
//
//    Deque<Connection> connections = new ArrayDeque<>();
//
//    @Test(dataProvider = "dp", groups = "group1", priority = 2, timeOut = 100)
//    public void getConnectionTest(ConnectionPool pool) {
//        for (int i = 0; i < 11; i++) {
//            Connection connection = DataSource.getINSTANCE().getConnection();
//            connections.push(connection);
//            assertEquals(ProxyConnection.class, connection.getClass());
//        }
//    }
//
////    @AfterMethod(groups = "group1")
////    public void releaseConnectionTest() {
////        Connection connection = dataSource.getConnection();
////        while (connection != null) {
////            DataSource.getINSTANCE().getConnectionPool().releaseConnection(connection);
////        }
////    }
//
//    @Test(dataProvider = "dp", timeOut = 100, priority = 3)
//    public void destroyPoolTest(ConnectionPool pool) {
//        pool.destroyPool();
//    }
//}
