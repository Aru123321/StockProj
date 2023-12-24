import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Queries {
    static Connection connection = Database.getConnection();

    // Fetch all stocks from the database
    public static ResultSet fetchAllStocks() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stocks");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process and display fetched stock data
           /* while (resultSet.next()) {
                int stockId = resultSet.getInt("ID");
                String ticker = resultSet.getString("Ticker");
                String name = resultSet.getString("Name");
                double price = resultSet.getDouble("Price");

                System.out.println("Stock ID: " + stockId + ", Ticker: " + ticker + ", Name: " + name + ", Price: " + price);
            }*/

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Add a new stock to the database
    public static void addStock(String ticker, String name, double price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO stocks (Ticker, Name, Price) VALUES (?, ?, ?)");
            preparedStatement.setString(1, ticker);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Double fetchPriceByTicker(String ticker) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Price FROM stocks WHERE Ticker = ?");
            preparedStatement.setString(1, ticker);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Double.parseDouble(resultSet.getString("Price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    // Fetch all users from the database
    public static ResultSet fetchAllUsers() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process and display fetched user data
            /*while (resultSet.next()) {
                int userId = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double unProfits = resultSet.getDouble("unProfits");
                double profits = resultSet.getDouble("Profits");
                int pAccountID = resultSet.getInt("PaccountID");
                String options = resultSet.getString("Options");
                Integer balance = resultSet.getInt("Balance");

                System.out.println("User ID: " + userId + ", Name: " + name + ", Username: " + username + ", Password: " + password +
                        ", UnProfits: " + unProfits + ", Profits: " + profits + ", PAccountID: " + pAccountID +
                         ", Options: " + options + ", balance: " + balance);
            }*/

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new user to the database
    public static void addUser(String name, String username, String password, double unProfits, double profits,
                               int pAccountID, String options, int balance) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (Name, username, password, unProfits, Profits, PaccountID, Options, Balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setDouble(4, unProfits);
            preparedStatement.setDouble(5, profits);
            preparedStatement.setInt(6, pAccountID);
            preparedStatement.setString(7, options);
            preparedStatement.setInt(8, balance);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteShare(String userName, String ticker) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shares WHERE userName = ? AND ticker = ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, ticker);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions or errors accordingly
        }
    }

    // Delete a user from the database by ID
    public static void deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE ID = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions or errors accordingly
        }
    }

    // Update user details by ID
    public static void updateUserDetails(int userId, String name, String username, String password, double unProfits, double profits,
                                         int pAccountID, String options, double Balance) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET Name = ?, username = ?, password = ?, unProfits = ?, Profits = ?, PaccountID = ?, Options = ?, Balance = ? WHERE ID = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setDouble(4, unProfits);
            preparedStatement.setDouble(5, profits);
            preparedStatement.setInt(6, pAccountID);
            preparedStatement.setString(7, options);
            preparedStatement.setDouble(8, Balance);
            preparedStatement.setDouble(9, userId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update stock details by ID
    public static void updateStockByID(int stockId, String ticker, String name, double price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stocks SET Ticker = ?, Name = ?, Price = ? WHERE ID = ?");
            preparedStatement.setString(1, ticker);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, stockId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStockByTicker(String ticker, String name, double price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stocks SET Ticker = ?, Name = ?, Price = ? WHERE Ticker = ?");
            preparedStatement.setString(1, ticker);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, ticker);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a user by ID
    public static ResultSet fetchUserById(int userId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE ID = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Retrieve a user by username
    public static ResultSet fetchUserByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    // Retrieve a stock by ID
    public static ResultSet fetchStockById(int stockId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stocks WHERE ID = ?");
            preparedStatement.setInt(1, stockId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process and display fetched stock data
            while (resultSet.next()) {
                // Retrieve stock details
                int fetchedStockId = resultSet.getInt("ID");
                String ticker = resultSet.getString("Ticker");
                String name = resultSet.getString("Name");
                double price = resultSet.getDouble("Price");

                System.out.println("Stock ID: " + fetchedStockId + ", Ticker: " + ticker + ", Name: " + name + ", Price: " + price);
            }

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    // Retrieve a stock by ticker
    public static ResultSet fetchStockByTicker(String ticker) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stocks WHERE Ticker = ?");
            preparedStatement.setString(1, ticker);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet fetchUserByUsernamePassword(String username, String password) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet fetchSharesByUsername(String username) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM shares WHERE userName = ?");
            preparedStatement.setString(1, username);
            return preparedStatement.executeQuery();
            /*while (resultSet.next()) {
                String ticker = resultSet.getString("ticker");
                int numShares = resultSet.getInt("numShares");
                double avgPrice = resultSet.getDouble("avgPrice");

                System.out.println("Ticker: " + ticker + ", NumShares: " + numShares + ", AvgPrice: " + avgPrice);
            }*/


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateShare(String userName, String ticker, int numShares, double avgPrice) {
        String query = "UPDATE Shares SET userName=?, ticker=?, numShares=?, avgPrice=? WHERE userName=? AND ticker=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, ticker);
            preparedStatement.setInt(3, numShares);
            preparedStatement.setDouble(4, avgPrice);
            preparedStatement.setString(5, userName);
            preparedStatement.setString(6, ticker);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Share updated successfully!");
            } else {
                System.out.println("DID NOT EXIST, Share added");
                addShare(userName,  ticker,  numShares, avgPrice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addShare(String userName, String ticker, int numShares, double avgPrice) {
        String query = "INSERT INTO Shares (userName, ticker, numShares, avgPrice) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, ticker);
            preparedStatement.setInt(3, numShares);
            preparedStatement.setDouble(4, avgPrice);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Share added successfully!");
            } else {
                System.out.println("Failed to add share.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserBalance(String userName, double newBalance) {
        String query = "UPDATE users SET Balance = ? WHERE Username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setString(2, userName);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer balance updated successfully!");
            } else {
                System.out.println("Failed to update customer balance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Share> convertResultSetToShareList(ResultSet resultSet, String username) throws SQLException {
        ArrayList<Share> shareList = new ArrayList<>();

        while (resultSet.next()) {
            String ticker = resultSet.getString("ticker");
            int number = resultSet.getInt("numShares");
            double avgPrice = resultSet.getDouble("avgPrice");

            Share share = new Share(ticker, number, avgPrice, username);
            shareList.add(share);
        }
        return shareList;
    }

    public static int fetchNumSharesByTicker(String ticker) throws SQLException {
        int numShares = 0;

        PreparedStatement statement = connection.prepareStatement("SELECT numShares FROM Shares WHERE ticker = ?");
        statement.setString(1, ticker);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            numShares = resultSet.getInt("numShares");
        }

        return numShares;
    }

    public static double fetchAvgPriceByTicker(String ticker, String userName) throws SQLException {
        double avgPrice = 0.0;
        PreparedStatement statement = connection.prepareStatement("SELECT avgPrice FROM Shares WHERE ticker = ? AND userName =?");
        statement.setString(1, ticker);
        statement.setString(2, userName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            avgPrice = resultSet.getDouble("avgPrice");
        }

        return avgPrice;
    }


}

