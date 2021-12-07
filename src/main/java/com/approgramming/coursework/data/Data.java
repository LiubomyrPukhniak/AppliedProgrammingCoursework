package com.approgramming.coursework.data;
import com.approgramming.coursework.attachment.Attachment;
import com.approgramming.coursework.authorization.Authorization;
import com.approgramming.coursework.bank.Bank;
import com.approgramming.coursework.customer.Customer;
import com.approgramming.coursework.offer.Offer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Data {
    static String urlDBconnection = "jdbc:sqlserver://NENEGR\\SQLEXPRESS;databaseName=Invest;";
    static String userName = "sa";
    static String pass = "sa";
    static Connection dbConn;
    public Data() {
        try{
            dbConn = DriverManager.getConnection(urlDBconnection, userName, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Customer authSearch(String email, String password) {
        try {
            PreparedStatement statement = dbConn.prepareStatement("SELECT email, password, customerId, name, surname, moneyCount" +
                                                                      " FROM Customers.CustomerGeneral" +
                                                                      " WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            if(res.next()) {
                if(res.getInt("customerId") != 0) {
                    return new Customer(res.getString("name"),
                            res.getString("surname"),
                            res.getInt("customerId"),
                            res.getString("email"),
                            res.getString("password"),
                            res.getDouble("moneyCount"));
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer newCustomer(String email, String password, String name, String surname, double money) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "INSERT INTO Customers.CustomerGeneral (email, password, name, surname, moneyCount)" +
                    " VALUES (\'"+ email +"\', \'"+ password +"\', \'"+ name +"\', \'"+ surname +"\', "+ money +")";
            statement.executeUpdate(sql);
            PreparedStatement prepStatement = dbConn.prepareStatement(
                    "SELECT customerId" +
                    " FROM Customers.CustomerGeneral" +
                    " WHERE email = ? AND password = ?");
            prepStatement.setString(1, email);
            prepStatement.setString(2, password);
            ResultSet res = prepStatement.executeQuery();
            if(res.next()) {
                return new Customer(name, surname, res.getInt("customerId"), email, password, money);
            }
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Attachment> getAttachments(Customer customer) {
        ObservableList<Attachment> list = FXCollections.observableArrayList();
        try {
            PreparedStatement prepStatement = dbConn.prepareStatement(
                        "SELECT *" +
                            " FROM Attachments.Attachments as att" +
                            " JOIN Banks.BankOffers as offer ON offer.offerId = att.offerId" +
                                " JOIN Banks.BankGeneral as ban ON ban.bankId = offer.bankId" +
                            " WHERE att.customerId = ?" );
            prepStatement.setInt(1, customer.getCustomerId());
            ResultSet res = prepStatement.executeQuery();
            while(res.next()) {
                list.add(new Attachment(res.getInt("attachmentId"),
                            new Offer(res.getInt("mounthCount"),
                                    res.getDouble("percents"),
                                    res.getInt("offerId"),
                                    res.getString("bankName"),
                                    res.getInt("bankId")),
                        res.getDouble("moneySum"),
                        res.getDouble("firstMoneySum"),
                        res.getBoolean("isOutOfSchedule"),
                        res.getString("dateOfEnd"),
                        res.getString("dateOfStart")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<String> getOffers(Integer bankId) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            Statement statement = dbConn.createStatement();
            ResultSet res = statement.executeQuery(
                        "SELECT * FROM Banks.BankOffers as offer" +
                                " JOIN Banks.BankGeneral as ban ON ban.bankId = offer.bankId");
            while(res.next()) {
                list.add(new Offer(res.getInt("mounthCount"),
                        res.getDouble("percents"),
                        res.getInt("offerId"),
                        res.getString("bankName"),
                        res.getInt("bankId")).toString());
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Offer getOfferById(Integer offerId) {
        try {
            PreparedStatement prepStatement = dbConn.prepareStatement(
                    "SELECT * FROM Banks.BankOffers as offer" +
                    " JOIN Banks.BankGeneral as ban ON ban.bankId = offer.bankId" +
                    " WHERE offer.offerId = ?");
            prepStatement.setInt(1, offerId);
            ResultSet res = prepStatement.executeQuery();
            if(res.next()) {
                return new Offer(res.getInt("mounthCount"),
                        res.getDouble("percents"),
                        offerId,
                        res.getString("bankName"),
                        res.getInt("bankId"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean newAttachment(Attachment attachment) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "INSERT INTO Attachments.Attachments (offerId, customerId, moneySum, firstMoneySum, dateOfEnd, dateOfStart, isOutOfschedule)" +
                    " VALUES ("+ attachment.getOffer().getOfferId() +", "+ Authorization.customer.getCustomerId()
                    +", "+ attachment.getMoney() +", "+ attachment.getMoney() +", \'"+ Date.valueOf(attachment.getDateOfEnd()) +"\', \'"+
                    Date.valueOf(attachment.getDateOfStart()) + "\', \'"+ attachment.getOutOfSchedule() +"\')";
            int res = statement.executeUpdate(sql);
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAttachment(Attachment attachment) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "DELETE FROM Attachments.Attachments WHERE attachmentId = " + attachment.getAttachmentId();
            int res = statement.executeUpdate(sql);
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMoneyCount(Integer customerId, Double moneyInc) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "UPDATE Customers.CustomerGeneral" +
                    " SET moneyCount = " + (Authorization.customer.getMoneyCount() + moneyInc)
                    + " WHERE customerId = " + customerId;
            int res = statement.executeUpdate(sql);
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAttMoneyCount(Attachment attachment, Double moneyInc) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "UPDATE Attachments.Attachments" +
                    " SET moneySum = " + (attachment.getMoney() + moneyInc)
                    + " WHERE attachmentId = " + attachment.getAttachmentId();
            int res = statement.executeUpdate(sql);
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Attachment> checkReadyAttachments() {
        ArrayList<Attachment> readyAtt = new ArrayList<>();
        try {
            Statement statement = dbConn.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM Attachments.Attachments" +
                    " WHERE dateOfEnd <= GETDATE() AND customerId = " + Authorization.customer.getCustomerId());
            while(res.next()) {
                readyAtt.add(new Attachment(res.getInt("attachmentId"),
                        new Offer(res.getInt("mounthCount"),
                                res.getDouble("percents"),
                                res.getInt("offerId"),
                                res.getString("bankName"),
                                res.getInt("bankId")),
                        res.getDouble("moneySum"),
                        res.getDouble("firstMoneySum"),
                        res.getBoolean("isOutOfSchedule"),
                        res.getString("dateOfEnd"),
                        res.getString("dateOfStart")));
            }
            return readyAtt;
        } catch (SQLException e) {
            e.printStackTrace();
            return readyAtt;
        }
    }

    public boolean completeAttachments(ArrayList<Attachment> readyAtt) {
        int res = 0;
        double moneyAdded = 0;
        for (Attachment att: readyAtt) {
            moneyAdded += att.getMoney() + (att.getMoney() / 100.0) * att.getPercents();
        }
        updateMoneyCount(Authorization.customer.getCustomerId(), moneyAdded);
        try {
            Statement statement = dbConn.createStatement();
            for (Attachment att: readyAtt) {
                String sql = "DELETE FROM Attachments.Attachments WHERE attachmentId = " + att.getAttachmentId();
                res += statement.executeUpdate(sql);
            }
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer authBankSearch(String email, String password) {
        try {
            PreparedStatement statement = dbConn.prepareStatement("SELECT email, password, bankId, bankName" +
                    " FROM Banks.BankGeneral" +
                    " WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            if(res.next()) {
                if(res.getInt("bankId") != 0) {
                    return new Customer(res.getString("bankName"),
                            "",
                            res.getInt("bankId"),
                            res.getString("email"),
                            res.getString("password"),
                            0.0);
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Offer> getOffersByBankId(Integer bankId) {
        ObservableList<Offer> list = FXCollections.observableArrayList();
        try {
            Statement statement = dbConn.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT * FROM Banks.BankOffers as offer" +
                            " JOIN Banks.BankGeneral as ban ON ban.bankId = offer.bankId" +
                            " WHERE ban.bankId = " + bankId);
            while(res.next()) {
                list.add(new Offer(res.getInt("mounthCount"),
                        res.getDouble("percents"),
                        res.getInt("offerId"),
                        res.getString("bankName"),
                        res.getInt("bankId")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean newOffer(Offer offer) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "INSERT INTO Banks.BankOffers (percents, mounthCount, bankId)" +
                    " VALUES ("+ offer.getPercents() +", "+ offer.getMonthCount() +", "+ offer.getBankId() + ")";
            int res = statement.executeUpdate(sql);
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOffer(Offer offer) {
        try {
            Statement statement = dbConn.createStatement();
            String sql = "DELETE FROM Banks.BankOffers WHERE offerId = " + offer.getOfferId();
            int res = statement.executeUpdate(sql);
            return res == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try{
            dbConn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
