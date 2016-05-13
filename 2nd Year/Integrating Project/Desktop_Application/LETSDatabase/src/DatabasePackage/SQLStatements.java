package DatabasePackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DataModel.Address;
import DataModel.Advert;
import DataModel.AdvertList;
import DataModel.AdvertType;
import DataModel.AdvertTypeList;
import DataModel.Bid;
import DataModel.BidList;
import DataModel.Category;
import DataModel.CategoryList;
import DataModel.ItemType;
import DataModel.ItemTypeList;
import DataModel.Member;
import DataModel.MemberList;
import DataModel.Review;
import DataModel.ReviewList;
import Password.PasswordList;
import Password.Password;
import DataModel.Rule;
import DataModel.RuleList;
import DataModel.Transaction;
import DataModel.TransactionList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 *
 * @author PRCSA
 */
public class SQLStatements {

    /**
     * @param args the command line arguments
     */
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rset;
    private Statement stmt;


    private final AdvertList advertList;
    private final CategoryList categoryList;
    private final AdvertTypeList advertTypeList;
    private final TransactionList transactionList;
    private final ItemTypeList itemTypeList;
    private final PasswordList passList;
    private final ReviewList reviewList;
    private final BidList bidList;

    /**
     * Default constructor.
     */
    public SQLStatements() {
        advertList = new AdvertList();
        categoryList = new CategoryList();
        advertTypeList = new AdvertTypeList();
        transactionList = new TransactionList();
        itemTypeList = new ItemTypeList();
        passList = new PasswordList();
        reviewList = new ReviewList();
        bidList = new BidList();
    }

    /* Creates a connection to the database */
    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createConnection() throws SQLException, ClassNotFoundException {
        /*Set connection properties******************************************/
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Properties props = new Properties();
        props.setProperty("user", "PRCSA");
        props.setProperty("password", "dreamTeam#15");
        props.setProperty("ssl", "true");
        String url = "jdbc:oracle:thin:@tom.uopnet.plymouth.ac.uk:1522:ORCL12c";
        conn = DriverManager.getConnection(url, props);
        /***********************************************************************/
    }

    /* Closes the connection to the database */
    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void closeConnection() throws SQLException, ClassNotFoundException {
        conn.close();
    }

    /* Fills the GUI's memberList with a list of members */
    /**
     *
     * @param members
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public MemberList allMembers(MemberList members) throws SQLException, ClassNotFoundException {
        
        String queryString = "";
        createConnection();
        queryString = "SELECT * FROM Members";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                Address add = new Address(rset.getString(6), rset.getString(7),
                        rset.getString(8), rset.getString(9));
                Member member;
                member = new Member(rset.getInt(1), rset.getString(2), rset.getString(3),
                        rset.getDate(4), rset.getString(11), rset.getString(10), add, rset.getString(12));

                members.addMember(member);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
        return members;
    }

    /* Fills the GUI's ruleList with a list of rules */
    /**
     *
     * @param ruleList
     * @return
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public RuleList allRules(RuleList ruleList) throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();
        queryString = "SELECT * FROM RULES";

        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                Rule newRule = new Rule(rset.getInt(1), rset.getString(2));
                ruleList.addRule(newRule);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }

        return ruleList;
    }
    
    /**
     *  SQL Query to return all the bids in the database.
     * @return - BidList value filled with a list of bids
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public BidList allBids() throws SQLException, ClassNotFoundException
    {
       String queryString;
       createConnection();

        queryString = "SELECT * FROM BIDS";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                Bid bid;
                bid = new Bid(rset.getInt(1),rset.getInt(2),rset.getInt(4),
                rset.getString(6),rset.getDate(7));
                bidList.addBid(bid);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        return bidList;        
    }

    /* Fills the GUI's adveryList with a list of adverts */
    /**
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public AdvertList allAds() throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();

        queryString = "SELECT * FROM ADVERTS";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                Advert ad;
                ad = new Advert(rset.getInt(1), rset.getInt(2),
                        rset.getInt(3), rset.getInt(4), rset.getInt(5),
                        rset.getString(6), rset.getString(7), rset.getDate(8), rset.getDate(9),
                        rset.getInt(10), rset.getString(11), rset.getDate(12), rset.getBlob(13), rset.getString(14)
                );
                advertList.addAdvert(ad);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        return advertList;
    }
    /* Fills the GUI's categoryList with a list of categorys */

    /**
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */

    public CategoryList allCats() throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();
        queryString = "SELECT * FROM Categories";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            Category cat = new Category();

            while (rset.next()) {
                cat = new Category(rset.getInt(1), rset.getString(2));
                categoryList.addCatType(cat);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }

        }
        return categoryList;
    }

    /* Fills the GUI's adveryTypeList with a list of advert types  */
    /**
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public AdvertTypeList allAdType() throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();
        queryString = "SELECT * FROM Advert_Types";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            AdvertType adType = new AdvertType();

            while (rset.next()) {
                adType = new AdvertType(rset.getInt(1), rset.getString(2));
                advertTypeList.addAdvertType(adType);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }

        }
        return advertTypeList;
    }
    /* Fills the GUI's itemTypeList with a list of item types */

    /**
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public ItemTypeList allItemType() throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();

        queryString = "SELECT * FROM Item_Types";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            ItemType itemType = new ItemType();

            while (rset.next()) {
                itemType = new ItemType(rset.getInt(1), rset.getString(2));
                itemTypeList.addItemType(itemType);

            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }

        }
        return itemTypeList;
    }

    /** Returns a ReviewList of all the reviews in the database.
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public ReviewList allReviews() throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();
        queryString = "SELECT * FROM REVIEWS";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            Review review = new Review();

            while (rset.next()) {
                review = new Review(rset.getInt(1), rset.getInt(2), rset.getInt(3), rset.getString(4));
                
                reviewList.addReview(review);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }

        }
        return reviewList;
    }

    /* Fills the GUI's transactionList with a list of transactions */
    /**
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public TransactionList allTransactions() throws SQLException, ClassNotFoundException {
        String queryString;
        createConnection();
        queryString = "SELECT * FROM Transactions";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            Transaction transaction = new Transaction();

            while (rset.next()) {
                transaction = new Transaction(rset.getInt(1), rset.getInt(2), rset.getInt(3), rset.getInt(4), rset.getInt(5), rset.getDate(6));
                transactionList.addTransaction(transaction);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
        return transactionList;
    }

    /** Returns the passwords (hashed) from the database for the administration login
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public PasswordList getAdminLogin() throws SQLException, ClassNotFoundException {
        
        String queryString;
        createConnection();
        queryString = "SELECT * FROM ADMIN_ACCESS";
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();

            Password pass;
            while (rset.next()) {
                pass = new Password(rset.getString(2), rset.getString(3), rset.getInt(1));
                passList.addPassword(pass);
            }
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
        return passList;
    }
    /* Updates a members details */

    /** SQL statement used to update a members details
     *
     * @param member - Member value being the member to update.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateMemberDetails(Member member) throws SQLException, ClassNotFoundException {
        //Not updated the dates
        String forename = member.getForename();
        String surname = member.getSurname();
        String contactNo = member.getContactNo();
        String email = member.getEmail();

        String add1 = member.getMemberAddress().getAddress_1();
        String add2 = member.getMemberAddress().getAddress_2();
        String city = member.getMemberAddress().getCity();
        String postcode = member.getMemberAddress().getPostcode();
        
        int memberNo = member.getMemberID();

        String SQL = "UPDATE MEMBERS SET FORENAME = '" + forename
                + "', SURNAME = '" + surname
                + "', ADDRESS_1 = '" + add1
                + "', ADDRESS_2 = '" + add2
                + "', CITY = '" + city
                + "', POSTCODE = '" + postcode
                + "', CONTACT_NUMBER = '" + contactNo
                + "', EMAIL = '" + email 
                + "', DATE_OF_BIRTH  = ?" 
                + "   WHERE MEMBER_ID = " + memberNo;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setDate(1,member.getDateOfBirth());
            rset = pstmt.executeQuery();
            pstmt.clearParameters();
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }

    /* Updates an adverts details */
    /**
     *
     * @param ad
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateAdvertDetails(Advert ad) throws SQLException, ClassNotFoundException {
        //Date Added, Date_Exp, Cost, Image, Transport, 
        //Adert Tags, Date-Completed.

        String adName = ad.getAdTitle();
        String adDescription = ad.getAdDescription();

        int adCost = ad.getAdCost();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

        String date_adv = sdf.format(ad.getAdPosted());

        String date_exp = sdf.format(ad.getAdExpires());

        String transportation = ad.getTransportInc();

        int adItemNum = ad.getItemTypeID();
        int adCatNum = ad.getCatID();
        int adAdTypeNum = ad.getAdTypeID();
        int advertID = ad.getAdvertNo();

        //Advert tags needs adding, so does Date completed and Image.
        String SQL = "UPDATE ADVERTS SET TITLE = '" + adName
                + "',DESCRIPTION = '" + adDescription
                + "',ITEM_TYPE_ID ='" + adItemNum
                + "',ADVERT_TYPE_ID ='" + adAdTypeNum
                + "',CATEGORY_ID = '" + adCatNum
                + "', COST = '" + adCost
                + "', DATE_ADV = '" + date_adv
                + "', DATE_EXP = '" + date_exp
                + "', TRANSPORT = '" + transportation
                + "' WHERE ADVERT_ID = " + advertID;
        try {
            pstmt = conn.prepareStatement(SQL);
            rset = pstmt.executeQuery();
            pstmt.clearParameters();
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }

    /* Adds a rule to the database */
    /**
     *
     * @param rule
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addRules(Rule rule) throws SQLException, ClassNotFoundException {

        String ruleAdd = rule.getRule();

        String SQL = "INSERT INTO RULES " + "(RULE) " + "VALUES" + " (" + "'" + ruleAdd + "'" + ")";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }

    /* Modifys a rule */
    /**
     *
     * @param rule
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void modifyRules(Rule rule) throws SQLException, ClassNotFoundException {
        if (rule != null) {
            String ruleAdd = rule.getRule();
            int ruleID = rule.getRuleID();

            String SQL = "UPDATE RULES SET RULE = '" + ruleAdd + "' WHERE RULE_ID =" + ruleID;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(SQL);
                stmt.close();
            } catch (SQLException se) {
                se.getSQLState();
            } catch (Exception e) {
                e.getLocalizedMessage();
            } finally {
                try {
                    if (conn != null) {
                        closeConnection();
                    }
                } catch (SQLException se) {
                    se.getSQLState();
                }
            }
        }
    }

    /* Remove a rule */
    /**
     *
     * @param rule
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void removeRule(Rule rule) throws SQLException, ClassNotFoundException {

        int removeRule = rule.getRuleID();

        String SQL = "DELETE FROM RULES WHERE RULE_ID = " + removeRule;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }

    /*  Fills the passWorldList with a list of admin logins */
    /**
     *
     * @return @throws SQLException
     */
    /*  Sets a members status to unactive */
    /**
     *
     * @param member
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void setUnactiveMember(Member member) throws SQLException, ClassNotFoundException {
        String isActive = member.getIsActive();
        int memberID = member.getMemberID();
        String queryString;
        queryString = "UPDATE MEMBERS SET IS_ACTIVE = '" + isActive + "' WHERE MEMBER_ID = " + memberID;
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();
            pstmt.clearParameters();
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }

    /* Sets an adverts status to unactive */
    /**
     *
     * @param ad
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void setUnactiveAdvert(Advert ad) throws SQLException, ClassNotFoundException {
        String isActive = ad.getIsActive();
        int advertID = ad.getAdvertNo();
        String queryString;
        queryString = "UPDATE ADVERTS SET IS_ACTIVE = '" + isActive + "' WHERE ADVERT_ID = " + advertID;
        try {
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery();
            pstmt.clearParameters();
            rset.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }
    
    /** SQL statement to reset a password
     *
     * @param password - String value being the new hashed password
     * @param email - String value being the email used to identify the users.
     * @throws ClassNotFoundException
     */
    public void resetPassword(String password, String email) throws ClassNotFoundException
    {
        String queryString;
        
        queryString = "UPDATE USER_ACCESS SET PASSWORD_HASH = '" + password + "' WHERE EMAIL = '" +  email + "'";
        System.out.println(queryString);
        
        try {
                stmt = conn.createStatement();
                stmt.executeUpdate(queryString);
                stmt.close();
        } catch (SQLException se) {
            se.getSQLState();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            try {
                if (conn != null) {
                    closeConnection();
                }
            } catch (SQLException se) {
                se.getSQLState();
            }
        }
    }
}
