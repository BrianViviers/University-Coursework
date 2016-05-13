package prcsadatabase;

import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.Advert;
import entities.Member;
import entities.Bid;
import entities.Review;
import entities.Rule;
import entities.Transaction;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Statement;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;

/**
 * This class holds all the SQL for the RESTful Web Services API. Each call to a
 * web service creates a connection to the database, retrieves the data and
 * closes the connection. All connections use SSL security.
 *
 * @author BrianV
 */
public class DBConnector {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rset;

    /**
     * Default constructor
     */
    public DBConnector() {
    }

    /**
     * Creates a database connection to Oracle database.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Properties props = new Properties();
        props.setProperty("user", "PRCSA");
        props.setProperty("password", "dreamTeam#15");
        props.setProperty("ssl", "true");
        String url = "jdbc:oracle:thin:@tom.uopnet.plymouth.ac.uk:1522:ORCL12c";

        conn = DriverManager.getConnection(url, props);
    }

    /**
     * Closes the connection to the Oracle database.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void closeConnection() throws SQLException, ClassNotFoundException {
        conn.close();
    }

    // SQL for adverts ---------------------------------------------------------
    // -------------------------------------------------------------------------
    /**
     * Retrieves all details of a single advert using an the adverts ID.
     *
     * @param ID - The ID of the advert
     * @return - All the details relating to that advert
     * @throws SQLException
     */
    public Advert getAdvertByID(Long ID) throws SQLException {
        String SQL = "SELECT advert_id, title, description, date_adv, name, type AS category, \n"
                + "ITEM_TYPE_DESCRIPTION AS item_type, date_exp, cost, transport, is_active, member_id \n"
                + "FROM adverts NATURAL JOIN advert_types NATURAL JOIN categories NATURAL JOIN item_types\n"
                + "WHERE advert_id = " + ID;

        Advert advert = new Advert();
        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        if (rset.next()) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);

                advert.setId(Long.parseLong(rset.getString(1)));
                advert.setTitle(rset.getString(2));
                advert.setDescription(rset.getString(3));
                advert.setDate_adv(format.parse(rset.getString(4)));
                advert.setAdvert_type(rset.getString(5));
                advert.setCategory(rset.getString(6));
                advert.setItem_type(rset.getString(7));
                advert.setDate_exp(format.parse(rset.getString(8)));
                advert.setCost(Integer.parseInt(rset.getString(9)));
                if (rset.getString(10).equalsIgnoreCase("Y")) {
                    advert.setTransport(true);
                } else {
                    advert.setTransport(false);
                }
                advert.setIs_active(rset.getString(11));
                advert.setMember_id(Long.parseLong(rset.getString(12)));

            } catch (ParseException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return advert;
    }

    /**
     * Retrieves all adverts of a single member that are able to be browsed by
     * other members.
     *
     * @param id - The ID of the member
     * @return - A List of all the active adverts.
     * @throws SQLException
     */
    public List<Advert> getMemberCurrentAdverts(Long id) throws SQLException {
        String queryString = "SELECT advert_id, title, description, date_adv, name, type AS category, \n"
                + "ITEM_TYPE_DESCRIPTION AS item_type, date_exp, cost, transport, is_active, member_id \n"
                + "FROM adverts NATURAL JOIN advert_types NATURAL JOIN categories NATURAL JOIN item_types\n"
                + "WHERE member_id = " + id + " \n"
                + "AND date_completed IS NULL \n"
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND IS_ACTIVE = 'Y' "
                + "ORDER BY date_adv DESC";
        List<Advert> adverts = getAdverts(queryString);
        return adverts;
    }

    /**
     * Retrieves all adverts of a single member that are no longer available to
     * be browsed by other members.
     *
     * @param id - The ID of the member
     * @return - A List of all the inactive adverts.
     * @throws SQLException
     */
    public List<Advert> getMemberPastAdverts(Long id) throws SQLException {
        String queryString = "SELECT advert_id, title, description, date_adv, name, type AS category, \n"
                + "ITEM_TYPE_DESCRIPTION AS item_type, date_exp, cost, transport, is_active, member_id \n"
                + "FROM adverts NATURAL JOIN advert_types NATURAL JOIN categories NATURAL JOIN item_types\n"
                + "WHERE member_id = " + id + " \n"
                + "AND (date_completed IS NOT NULL \n"
                + "OR to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') < to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') "
                + "OR is_active = 'N')"
                + "ORDER BY date_adv DESC";
        List<Advert> adverts = getAdverts(queryString);
        return adverts;
    }

    /**
     * Retrieves all the advert in the database which can be browsed by other
     * members.
     *
     * @return - A List of all the active adverts of all members.
     * @throws SQLException
     */
    public List<Advert> getAllCurrentAdverts() throws SQLException {
        String queryString = "SELECT advert_id, title, description, date_adv, name, type AS category, \n"
                + "ITEM_TYPE_DESCRIPTION AS item_type, date_exp, cost, transport, is_active, member_id \n"
                + "FROM adverts NATURAL JOIN advert_types NATURAL JOIN categories NATURAL JOIN item_types\n"
                + "WHERE date_completed IS NULL \n"
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND IS_ACTIVE = 'Y' "
                + "ORDER BY date_adv DESC";
        List<Advert> adverts = getAdverts(queryString);
        return adverts;
    }

    /**
     * Retrieves all the advert in the database which can not be browsed by
     * other members.
     *
     * @return - A List of all the inactive adverts of all members.
     * @throws SQLException
     */
    public List<Advert> getAllPastAdverts() throws SQLException {
        String queryString = "SELECT advert_id, title, description, date_adv, name, type AS category, \n"
                + "ITEM_TYPE_DESCRIPTION AS item_type, date_exp, cost, transport, is_active, member_id \n"
                + "FROM adverts NATURAL JOIN advert_types NATURAL JOIN categories NATURAL JOIN item_types\n"
                + "WHERE (date_completed IS NOT NULL \n"
                + "OR to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') < to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') "
                + "OR is_active = 'N')"
                + "ORDER BY date_adv DESC";
        List<Advert> adverts = getAdverts(queryString);
        return adverts;
    }

    /**
     * Retrieves all the adverts that have the search term associated to it when
     * a member searches for adverts.
     *
     * @param tags - The words or phrases that a user has entered in the search.
     * @return - A List of all the adverts with the search terms.
     * @throws SQLException
     */
    public List<Advert> getSearchedForAdverts(String tags) throws SQLException {
        tags = RemoveAllCommaWhitespace(tags);
        String queryString = "SELECT advert_id, title, description, date_adv, name, type AS category, \n"
                + "ITEM_TYPE_DESCRIPTION AS item_type, date_exp, cost, transport, is_active, member_id \n"
                + "FROM adverts NATURAL JOIN advert_types NATURAL JOIN categories NATURAL JOIN item_types\n"
                + "WHERE contains(dummy_indexing, '" + tags + "') > 0 AND date_completed IS NULL \n"
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND IS_ACTIVE = 'Y' "
                + "ORDER BY date_adv DESC";
        List<Advert> adverts = getAdverts(queryString);
        return adverts;
    }

    private List<Advert> getAdverts(String SQL) throws SQLException {
        List<Advert> adverts = new ArrayList<>();
        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
                Advert advert = new Advert();

                advert.setId(Long.parseLong(rset.getString(1)));
                advert.setTitle(rset.getString(2));
                advert.setDescription(rset.getString(3));
                advert.setDate_adv(format.parse(rset.getString(4)));
                advert.setAdvert_type(rset.getString(5));
                advert.setCategory(rset.getString(6));
                advert.setItem_type(rset.getString(7));
                advert.setDate_exp(format.parse(rset.getString(8)));
                advert.setCost(Integer.parseInt(rset.getString(9)));
                if (rset.getString(10).equalsIgnoreCase("Y")) {
                    advert.setTransport(true);
                } else {
                    advert.setTransport(false);
                }
                advert.setIs_active(rset.getString(11));
                advert.setMember_id(Long.parseLong(rset.getString(12)));

                adverts.add(advert);

            } catch (ParseException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return adverts;
    }

    /**
     * Uploads an image into the database using an advert ID.
     *
     * @param imageObject - This object contains the image and the advert ID to
     * which the image should belong.
     * @return - 0 if success else -1 if failed to upload.
     * @throws SQLException
     */
    public int uploadImage(JsonObject imageObject) throws SQLException {
        if (!imageObject.get("image").getAsString().isEmpty()) {
            String queryString = "UPDATE adverts SET image = ? WHERE advert_id = ?";

            byte[] image = DatatypeConverter.parseBase64Binary(imageObject.get("image").getAsString());
            InputStream input = new ByteArrayInputStream(image);

            int len = (int) image.length;
            pstmt = conn.prepareStatement(queryString);
            pstmt.setBinaryStream(1, input, len);
            pstmt.setInt(2, imageObject.get("advert_id").getAsInt());
            pstmt.executeQuery();
            pstmt.clearParameters();
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Retrieves the image associated with an advert.
     *
     * @param id - The ID of the advert for which the image is sought.
     * @return - A String array containing the image and the advert ID.
     * @throws SQLException
     */
    public String[] getAdvertImageById(Long id) throws SQLException {
        String SQL = "SELECT advert_id, image FROM adverts "
                + "WHERE advert_id = " + id;

        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        byte barr[];
        String[] image = new String[2];
        while (rset.next()) {
            if (rset.getBlob(2) != null) {
                image[0] = rset.getString(1);
                Blob b = rset.getBlob(2);
                barr = b.getBytes(1, (int) b.length());
                image[1] = DatatypeConverter.printBase64Binary(barr);
            } else {
                //System.out.println("Advert has no image");
            }
        }
        pstmt.clearParameters();
        rset.close();
        return image;
    }

    /**
     * Retrieves all the images of adverts of a single member that are able to
     * be browsed by other members.
     *
     * @param id - The ID of the member for which the adverts images are sought.
     * @return - A String array containing the image and the advert ID all in a
     * List.
     * @throws SQLException
     * @throws IOException
     */
    public List<String[]> getMemberCurrentAdvertsImages(Long id) throws SQLException, IOException {
        String queryString = "SELECT advert_id, image FROM adverts "
                + "WHERE member_id = " + id + " \n"
                + "AND date_completed IS NULL \n"
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND IS_ACTIVE = 'Y' "
                + "ORDER BY date_adv DESC";
        List<String[]> images = getAdvertImages(queryString);
        return images;
    }

    /**
     * Retrieves all the images of adverts of a single member that are not able
     * to be browsed by other members.
     *
     * @param id - The ID of the member for which the adverts images are sought.
     * @return - A String array containing the image and the advert ID all in a
     * List.
     * @throws SQLException
     * @throws IOException
     */
    public List<String[]> getMemberPastAdvertsImages(Long id) throws SQLException, IOException {
        String queryString = "SELECT advert_id, image FROM adverts "
                + "WHERE member_id = " + id + " \n"
                + "AND (date_completed IS NOT NULL \n"
                + "OR to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') < to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') "
                + "OR is_active = 'N')"
                + "ORDER BY date_adv DESC";
        List<String[]> images = getAdvertImages(queryString);
        return images;
    }

    /**
     * Retrieves all the images of adverts in the database which can be browsed
     * by other members.
     *
     * @return - A String array containing the image and the advert ID all in a
     * List.
     * @throws SQLException
     * @throws IOException
     */
    public List<String[]> getAllCurrentAdvertsImages() throws SQLException, IOException {
        String queryString = "SELECT advert_id, image FROM adverts "
                + "WHERE date_completed IS NULL \n"
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND IS_ACTIVE = 'Y' "
                + "ORDER BY date_adv DESC";
       
        List<String[]> images = getAdvertImages(queryString);
        return images;
    }

    /**
     * Retrieves all the images of adverts in the database which can not be
     * browsed by other members.
     *
     * @return - A String array containing the image and the advert ID all in a
     * List.
     * @throws SQLException
     * @throws IOException
     */
    public List<String[]> getAllPastAdvertsImages() throws SQLException, IOException {
        String queryString = "SELECT advert_id, image FROM adverts "
                + "WHERE (date_completed IS NOT NULL \n"
                + "OR to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') < to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS'))"
                + "ORDER BY date_adv DESC";
        List<String[]> images = getAdvertImages(queryString);
        return images;
    }

    /**
     * Retrieves all the images of adverts that have the search term associated
     * to it when a member searches for adverts.
     *
     * @param tags - The words or phrases that a user has entered in the search.
     * @return - A String array containing the image and the advert ID all in a
     * List.
     * @throws SQLException
     * @throws IOException
     */
    public List<String[]> getSearchedAdvertsImages(String tags) throws SQLException, IOException {
        tags = RemoveAllCommaWhitespace(tags);
        String queryString = "SELECT advert_id, image FROM adverts "
                + "WHERE contains(dummy_indexing, '" + tags + "') > 0 AND date_completed IS NULL \n"
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND IS_ACTIVE = 'Y' "
                + "ORDER BY date_adv DESC";
        List<String[]> images = getAdvertImages(queryString);
        return images;
    }

    private List<String[]> getAdvertImages(String SQL) throws SQLException, FileNotFoundException, IOException {
        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        byte barr[];
        List<String[]> images = new ArrayList<>();
        while (rset.next()) {
            if (rset.getBlob(2) != null) {
                String[] image = new String[2];
                image[0] = rset.getString(1);
                Blob b = rset.getBlob(2);
                barr = b.getBytes(1, (int) b.length());
                image[1] = DatatypeConverter.printBase64Binary(barr);
                images.add(image);
            } else {
                //System.out.println("Advert has no image");
            }
        }
        pstmt.clearParameters();
        rset.close();
        return images;
    }

    /**
     * Creates an advert entry in the database
     *
     * @param advert - The advert object containing all the details of the
     * advert except for the image.
     * @return - The ID of the new advert.
     * @throws SQLException
     */
    public Long createAdvert(Advert advert) throws SQLException {
        String SQL = "INSERT INTO ADVERTS (advert_type_id, member_id, category_id, "
                + "item_type_id, title, description, cost, transport, "
                + "dummy_indexing) \n"
                + "VALUES "
                + "(" + getAdvertTypeId(advert.getAdvert_type()) + ","
                + " " + advert.getMember_id() + ","
                + getCategoryID(advert.getCategory()) + ","
                + getItemTypeId(advert.getItem_type()) + ","
                + "'" + advert.getTitle() + "',"
                + "'" + advert.getDescription() + "',"
                + advert.getCost() + ","
                + "'" + isTransport(advert.getTransport()) + "',"
                + " null)";

        Long generatedKey = -1l;
        pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.executeUpdate();
        PreparedStatement ps = conn
                .prepareStatement("select adverts_seq.currval from dual");
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                generatedKey = rs.getLong(1);
            }
            pstmt.clearParameters();
        }
        return generatedKey;
    }

    private String isTransport(Boolean transport) {
        if (transport == true) {
            return "Y";
        } else {
            return "N";
        }
    }

    private int getItemTypeId(String itemType) {
        switch (itemType.toLowerCase()) {
            case "product":
                return 1;
            case "service":
                return 2;
            default:
                return 1;
        }
    }

    private int getAdvertTypeId(String advertType) {
        switch (advertType.toLowerCase()) {
            case "offer":
                return 1;
            case "request":
                return 2;
            default:
                return 1;
        }
    }

    private int getCategoryID(String category) {
        int catID;
        switch (category.toLowerCase()) {
            case "accounting":
                catID = 1;
                break;
            case "cleaning":
                catID = 2;
                break;
            case "computingandelectronics":
                catID = 3;
                break;
            case "education":
                catID = 4;
                break;
            case "health":
                catID = 5;
                break;
            case "homeandgarden":
                catID = 6;
                break;
            case "maintenance":
                catID = 7;
                break;
            case "printing":
                catID = 8;
                break;
            case "recreation":
                catID = 9;
                break;
            case "transport":
                catID = 10;
                break;
            case "other":
                catID = 11;
                break;
            default:
                catID = 11;
                break;
        }
        return catID;
    }

    private String RemoveAllCommaWhitespace(String tags) {
        tags = tags.replaceAll(" +", ",");
        tags = tags.replaceAll(",+", ",");
        tags = tags.replaceAll(",$", "");
        return tags;
    }

    /**
     * Makes an advert inactive when a member wishes to take down one of their
     * adverts.
     *
     * @param id - The ID of the advert to make inactive
     * @return - 0 if failed to make inactive else the number of rows affected.
     * @throws SQLException
     */
    public int RemoveAdvert(String id) throws SQLException {
        int success;
        String SQL = "UPDATE adverts SET "
                + "is_active='N' "
                + "WHERE advert_id = " + id;

        pstmt = conn.prepareStatement(SQL);
        success = pstmt.executeUpdate();

        return success;
    }

    // SQL for members ---------------------------------------------------------
    // -------------------------------------------------------------------------
    /**
     * Retrieves the member's hashed password .
     *
     * @param email - The email of the member for which the hashed password is
     * sought.
     * @return - The hashed password as a String
     * @throws SQLException
     */
    public String getMemberPassword(String email) throws SQLException {
        String queryString = "SELECT password_hash FROM "
                + "user_access where email = '" + email + "'";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        String password = "";
        while (rset.next()) {
            password = rset.getString(1);
        }
        return password;
    }

    private Boolean checkIfEmailInUse(String table, String email) throws SQLException {
        String SQL = "SELECT email FROM " + table + " where email = '" + email + "'";
        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        pstmt.clearParameters();
        Boolean exists = rset.isBeforeFirst();
        rset.close();
        return exists;
    }

    /**
     * Creates an entry in the members table using the new member details
     * supplied.
     *
     * @param member - The Member object containing all the members details.
     * @return - A string indicating if success or failure or supplied email
     * already in use.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String createMember(Member member) throws SQLException, ClassNotFoundException {
        int success;
        String email = member.getEmail().toLowerCase();

        Boolean emailExistsInMembersT = checkIfEmailInUse("members", email);
        Boolean emailExistsInUserAccessT = checkIfEmailInUse("user_access", email);

        if (!emailExistsInMembersT && !emailExistsInUserAccessT) {
            String SQL = "INSERT INTO members (forename, surname, date_of_birth, "
                    + "balance, address_1, address_2, "
                    + "city, postcode, contact_number, email) "
                    + "VALUES ("
                    + "'" + member.getForename() + "',"
                    + "'" + member.getSurname() + "',"
                    + "?,"
                    + 50 + ","
                    + "'" + member.getAddline1() + "',"
                    + "'" + member.getAddline2() + "',"
                    + "'" + member.getCity() + "',"
                    + "'" + member.getPostcode().toUpperCase() + "',"
                    + "'" + member.getContact_number() + "',"
                    + "'" + email + "')";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setDate(1, member.getDob());
            success = pstmt.executeUpdate();
            pstmt.clearParameters();

            if (success > 0) {
                SQL = "INSERT INTO user_access (email, password_hash) "
                        + "VALUES ('" + email + "','" + member.getPassword() + "')";
                pstmt = conn.prepareStatement(SQL);
                success = pstmt.executeUpdate();
                pstmt.clearParameters();
            }

            if (success > 0) {
                return "Success";
            } else {
                return "Failed to create an account. Contact admin";
            }
        } else {
            return "Email address already in use.";
        }
    }

    /**
     * Updates a member's password with a new password given by the member.
     * Password is always hashed before entry in the database
     *
     * @param member - The member object containing the member's ID and hashed
     * password.
     * @return - 0 if failure to update else the number of rows affected.
     * @throws SQLException
     */
    public int UpdateMemberPassword(Member member) throws SQLException {
        int success;
        String email = member.getEmail().toLowerCase();
        String SQL = "UPDATE user_access SET password_hash='" + member.getPassword() + "'"
                + " WHERE email = '" + email + "'";

        pstmt = conn.prepareStatement(SQL);
        success = pstmt.executeUpdate();
        pstmt.clearParameters();

        return success;
    }

    /**
     * Updates a members name.
     *
     * @param member - The Member object containing the member's ID and new
     * name.
     * @return - 0 if failure to update else the number of rows affected.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int UpdateMemberName(Member member) throws SQLException, ClassNotFoundException {
        int success;
        String SQL = "UPDATE members SET "
                + "forename='" + member.getForename() + "', "
                + "surname='" + member.getSurname() + "' "
                + "WHERE member_id = ?";

        pstmt = conn.prepareStatement(SQL);
        pstmt.setLong(1, member.getId());
        success = pstmt.executeUpdate();
        pstmt.clearParameters();

        return success;
    }

    /**
     * Updates a members date of birth.
     *
     * @param member - The Member object containing the member's ID and new date
     * of birth.
     * @return - 0 if failure to update else the number of rows affected.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int UpdateMemberDob(Member member) throws SQLException, ClassNotFoundException {
        int success;
        String SQL = "UPDATE members SET "
                + "date_of_birth = ? "
                + "WHERE member_id = ?";

        pstmt = conn.prepareStatement(SQL);
        pstmt.setDate(1, member.getDob());
        pstmt.setLong(2, member.getId());
        success = pstmt.executeUpdate();
        pstmt.clearParameters();

        return success;
    }

    /**
     * Updates a members address.
     *
     * @param member - The Member object containing the member's ID and new
     * address.
     * @return - 0 if failure to update else the number of rows affected.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int UpdateMemberAddress(Member member) throws SQLException, ClassNotFoundException {
        int success;
        String SQL = "UPDATE members SET "
                + "address_1='" + member.getAddline1() + "', "
                + "address_2='" + member.getAddline2() + "', "
                + "city='" + member.getCity() + "', "
                + "postcode='" + member.getPostcode().toUpperCase() + "' "
                + "WHERE member_id = ?";

        pstmt = conn.prepareStatement(SQL);
        pstmt.setLong(1, Long.parseLong(member.getId().toString()));
        success = pstmt.executeUpdate();
        pstmt.clearParameters();

        return success;
    }

    /**
     * Updates a members email address.
     *
     * @param member - The Member object containing the member's ID and new
     * email address.
     * @return - A string indicating if success or failure or supplied email
     * already in use.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String UpdateMemberEmail(Member member) throws SQLException, ClassNotFoundException {
        int success = -1;
        String email = member.getEmail().toLowerCase();

        Boolean emailExistsInMembersT = checkIfEmailInUse("members", email);
        Boolean emailExistsInUserAccessT = checkIfEmailInUse("user_access", email);

        if (!emailExistsInMembersT && !emailExistsInUserAccessT) {
            String SQL = "SELECT email FROM members WHERE member_id = " + member.getId();
            pstmt = conn.prepareStatement(SQL);
            rset = pstmt.executeQuery();
            String oldEmail = "";
            while (rset.next()) {
                oldEmail = rset.getString(1);
            }
            pstmt.clearParameters();
            rset.close();

            if (!oldEmail.equals("")) {
                SQL = "UPDATE members SET "
                        + "email='" + email + "' "
                        + "WHERE member_id = ?";

                pstmt = conn.prepareStatement(SQL);
                pstmt.setLong(1, Long.parseLong(member.getId().toString()));
                success = pstmt.executeUpdate();
                pstmt.clearParameters();

                if (success > 0) {
                    SQL = "UPDATE user_access SET "
                            + "email='" + email + "' "
                            + "WHERE email = '" + oldEmail + "'";

                    pstmt = conn.prepareStatement(SQL);
                    success = pstmt.executeUpdate();
                    pstmt.clearParameters();
                }
            }
            if (success > 0) {
                return "Success";
            } else {
                return "Failed to change the email address. Contact admin";
            }
        } else {
            return "Email address already in use.";
        }
    }

    /**
     * Updates a members contact number.
     *
     * @param member - The Member object containing the member's ID and new
     * contact number.
     * @return - 0 if failure to update else the number of rows affected.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int UpdateMemberContact(Member member) throws SQLException, ClassNotFoundException {
        int success;
        String SQL = "UPDATE members SET "
                + "contact_number='" + member.getContact_number() + "' "
                + "WHERE member_id = ?";

        pstmt = conn.prepareStatement(SQL);
        pstmt.setLong(1, Long.parseLong(member.getId().toString()));
        success = pstmt.executeUpdate();

        return success;
    }

    /**
     * Updates all the members details in one statement.
     *
     * @param member - The Member object containing all the new details of the
     * member.
     * @return - 0 if failure to update else the number of rows affected.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int UpdateMemberDetails(Member member) throws SQLException, ClassNotFoundException {
        int success;
        String SQL = "UPDATE members SET "
                + "forename='" + member.getForename() + "', "
                + "surname='" + member.getSurname() + "', "
                + "date_of_birth=?, "
                + "address_1='" + member.getAddline1() + "', "
                + "address_2='" + member.getAddline2() + "', "
                + "city='" + member.getCity() + "', "
                + "postcode='" + member.getPostcode().toUpperCase() + "', "
                + "contact_number='" + member.getContact_number() + "' "
                + "WHERE member_id = ?";

        pstmt = conn.prepareStatement(SQL);
        pstmt.setDate(1, member.getDob());
        pstmt.setLong(2, Long.parseLong(member.getId().toString()));
        success = pstmt.executeUpdate();
        pstmt.clearParameters();

        return success;
    }

    /**
     * Retrieves all the details of a member using their ID.
     *
     * @param id - The ID of the member for which details are sought.
     * @return - A Member object containing all the details of the member.
     * @throws SQLException
     * @throws ParseException
     */
    public Member getMemberAccountDetailsById(Long id) throws SQLException, ParseException {
        String SQL = "SELECT * FROM members WHERE member_id = " + id;
        return getMemberDetails(SQL);
    }

    /**
     * Retrieves all the details of a member using their email address.
     *
     * @param email - The email of the member for which details are sought.
     * @return - A Member object containing all the details of the member.
     * @throws SQLException
     * @throws ParseException
     */
    public Member getMemberAccountDetailsByEmail(String email) throws SQLException, ParseException {
        String SQL = "SELECT * FROM members WHERE email = '" + email + "'";
        return getMemberDetails(SQL);
    }

    private Member getMemberDetails(String sql) throws SQLException, ParseException {
        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();

        Member member = new Member();
        while (rset.next()) {
            String dob = rset.getString(4);
            Date dateOfBirth;
            dateOfBirth = new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(dob).getTime());

            member.setId(Long.parseLong(rset.getString(1)));
            member.setForename(rset.getString(2));
            member.setForename(rset.getString(2));
            member.setSurname(rset.getString(3));
            member.setDob(dateOfBirth);
            member.setBalance(Long.parseLong(rset.getString(5)));
            member.setAddline1(rset.getString(6));
            member.setAddline2(rset.getString(7));
            member.setCity(rset.getString(8));
            member.setPostcode(rset.getString(9));
            member.setContact_number(rset.getString(10));
            member.setEmail(rset.getString(11));
            member.setIs_active(rset.getString(12));
        }
        pstmt.clearParameters();
        rset.close();
        return member;
    }

    /**
     * Records that the user has logged into the system on this date.
     *
     * @param id - The ID of the member logging into the system.
     * @throws SQLException
     */
    public void recordUserSession(Long id) throws SQLException {
        String SQL = "INSERT INTO sessions (member_id) VALUES (" + id + ")";
        pstmt = conn.prepareStatement(SQL);
        pstmt.executeQuery();
        pstmt.clearParameters();
    }

    /**
     * Retrieves all members with the name searched for by other members.
     *
     * @param tags - The names that the other member has searched for as a
     * String.
     * @return - A List of member object containing ID, name and balance of all
     * members with the searched names.
     * @throws SQLException
     */
    public List<Member> GetSearchedForMembers(String tags) throws SQLException {
        tags = RemoveAllCommaWhitespace(tags);
        String SQL = "SELECT member_id, forename, surname, balance "
                + "FROM members "
                + "WHERE contains (dummy_indexing, '" + tags + "') > 0 "
                + "ORDER BY lower(surname) ASC";
        return GetMembers(SQL);
    }

    /**
     * Retrieves all members in the database
     *
     * @return - A list of Member objects containing ID, name and balance.
     * @throws SQLException
     */
    public List<Member> GetAllMembers() throws SQLException {
        String SQL = "SELECT member_id, forename, surname, balance "
                + "FROM members "
                + "ORDER BY lower(surname) ASC";
        return GetMembers(SQL);
    }

    private List<Member> GetMembers(String SQL) throws SQLException {
        List<Member> members = new ArrayList<>();
        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Member member = new Member();
            member.setId(Long.parseLong(rset.getString(1)));
            member.setForename(rset.getString(2));
            member.setSurname(rset.getString(3));
            member.setBalance(Long.parseLong(rset.getString(4)));
            members.add(member);
        }
        return members;
    }

    /**
     * Retrieves all the reviews given to a member
     *
     * @param memberID - The ID of the member for which the reviews are sought.
     * @return - A list of review object containing reviews and ratings.
     * @throws SQLException
     */
    public List<Review> GetMemberReviews(String memberID) throws SQLException {
        String SQL = "SELECT review_text, review_value\n"
                + "FROM reviews r JOIN transactions t ON r.transaction_id = t.transaction_id\n"
                + "JOIN adverts ad ON ad.advert_id = t.advert_id\n"
                + "JOIN members m ON m.member_id = ad.member_id\n"
                + "WHERE t.payee = " + memberID
                + " ORDER BY t.date_completed DESC";
        List<Review> memberReviews = new ArrayList<>();
        pstmt = conn.prepareStatement(SQL);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Review review = new Review();
            review.setReviewText(rset.getString(1));
            review.setReviewValue(Integer.parseInt(rset.getString(2)));
            memberReviews.add(review);
        }
        return memberReviews;
    }

    // SQL for transactions ----------------------------------------------------
    // -------------------------------------------------------------------------
    /**
     * Retrieves all transactions for a member where the member is paying out
     * credits and the transactions has not been paid finished.
     *
     * @param id - The ID of the member.
     * @return - A list of Transaction objects containing the details of the
     * transaction.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Transaction> GetTransactionsOutgoing(Long id) throws SQLException, ParseException {
        String sql = "SELECT transaction_id, t.advert_id, m1.forename ||' '|| m1.surname AS payee, "
                + "title, credit_paid, ad.advert_type_id \n"
                + "FROM adverts ad JOIN transactions t ON ad.advert_id = t.advert_id \n"
                + "INNER JOIN members m1 ON t.payee = m1.member_id\n"
                + "WHERE payer = " + id + " AND t.date_completed IS NULL"
                + " ORDER BY t.date_completed ASC";
        return GetTransactions("outgoing", sql, false);
    }

    /**
     * Retrieves all transactions for a member where the member is paying out
     * credits and the transactions has been paid finished.
     *
     * @param id - The ID of the member.
     * @return - A list of Transaction objects containing the details of the
     * transaction.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Transaction> GetTransactionsCompletedOutgoing(Long id) throws SQLException, ParseException {
        String sql = "SELECT t.transaction_id, t.advert_id, m1.forename ||' '|| m1.surname AS payee, "
                + "title, credit_paid, ad.advert_type_id, review_value, review_text, t.date_completed \n"
                + "FROM adverts ad JOIN transactions t ON ad.advert_id = t.advert_id \n"
                + "INNER JOIN members m1 ON t.payee = m1.member_id\n"
                + "LEFT JOIN reviews re ON re.transaction_id = t.transaction_id "
                + "WHERE payer = " + id + " AND t.date_completed IS NOT NULL"
                + " ORDER BY t.date_completed ASC";
        return GetTransactions("outgoing", sql, true);
    }

    /**
     * Retrieves all transactions for a member where the member is receiving
     * credits and the transactions has not been paid finished.
     *
     * @param id - The ID of the member.
     * @return - A list of Transaction objects containing the details of the
     * transaction.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Transaction> GetTransactionsIncoming(Long id) throws SQLException, ParseException {
        String sql = "SELECT transaction_id, t.advert_id, m1.forename ||' '|| m1.surname AS payer, "
                + "title, credit_paid, ad.advert_type_id \n"
                + "FROM adverts ad JOIN transactions t ON ad.advert_id = t.advert_id\n"
                + "INNER JOIN members m1 ON t.payer = m1.member_id\n"
                + "WHERE payee = " + id + " AND t.date_completed IS NULL"
                + " ORDER BY t.date_completed ASC";
        return GetTransactions("incoming", sql, false);
    }

    /**
     * Retrieves all transactions for a member where the member is receiving
     * credits and the transactions has been paid finished.
     *
     * @param id - The ID of the member.
     * @return - A list of Transaction objects containing the details of the
     * transaction.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Transaction> GetTransactionsCompletedIncoming(Long id) throws SQLException, ParseException {
        String sql = "SELECT t.transaction_id, t.advert_id, m1.forename ||' '|| m1.surname AS payer, "
                + "title, credit_paid, ad.advert_type_id, review_value, review_text, t.date_completed \n"
                + "FROM adverts ad JOIN transactions t ON ad.advert_id = t.advert_id\n"
                + "INNER JOIN members m1 ON t.payer = m1.member_id\n"
                + "LEFT JOIN reviews re ON re.transaction_id = t.transaction_id "
                + "WHERE payee = " + id + " AND t.date_completed IS NOT NULL"
                + " ORDER BY t.date_completed ASC";
        return GetTransactions("incoming", sql, true);
    }

    /**
     * Retrieves all transactions that are associated with the advert ID
     * supplied.
     *
     * @param id - The ID of the advert on which the transactions belong.
     * @return - A list of Transaction objects containing the details of the
     * transaction.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Transaction> GetTransactionsByAdvertID(Long id) throws SQLException, ParseException {
        String sql = "SELECT transaction_id, t.advert_id, m1.forename ||' '|| m1.surname AS payer, "
                + "title, credit_paid, ad.advert_type_id \n"
                + "FROM adverts ad JOIN transactions t ON ad.advert_id = t.advert_id\n"
                + "INNER JOIN members m1 ON t.payer = m1.member_id\n"
                + "WHERE t.advert_id = " + id
                + " ORDER BY t.date_completed ASC";
        return GetTransactions("incoming", sql, false);
    }

    private List<Transaction> GetTransactions(String whichTransaction, String sql, boolean completed)
            throws SQLException, ParseException {
        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();
        List<Transaction> transactionsList = new ArrayList<>();
        Transaction transaction;
        while (rset.next()) {
            transaction = new Transaction();
            transaction.setId(Long.parseLong(rset.getString(1)));
            transaction.setAdvertID(Long.parseLong(rset.getString(2)));

            if (whichTransaction.equals("incoming")) {
                transaction.setPayer(rset.getString(3));
            } else {
                transaction.setPayee(rset.getString(3));
            }
            transaction.setTitle(rset.getString(4));
            transaction.setCreditPaid(Long.parseLong(rset.getString(5)));
            transaction.setAdvertTypeID(Integer.parseInt(rset.getString(6)));

            if (completed == true) {
                transaction.setReviewRating(Integer.parseInt(rset.getString(7)));
                transaction.setReviewText(rset.getString(8));
                java.util.Date date = new java.util.Date(
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse(rset.getString(9)).getTime());

                transaction.setDateCompleted(date);
            }
            transactionsList.add(transaction);
        }
        pstmt.clearParameters();
        rset.close();
        return transactionsList;
    }

    /**
     * Sets a transaction to completed and uploads a review given by the payer.
     * Funds are transfered to the relevant member on completion.
     *
     * @param review - The review given by the payer as A Review object.
     * @return -0 if failure to update else the number of rows affected.
     * @throws SQLException
     */
    public int FinishTransaction(Review review) throws SQLException {
        int success = -1;
        String sql = "SELECT payee, balance, credit_paid "
                + "FROM members m JOIN transactions t ON m.member_id = t.payee "
                + "WHERE t.transaction_id = " + review.getTransactionID();

        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();

        int payeeBalance = -999;
        Long payeeID = -1l;
        int creditPaid = -1;
        if (rset.next()) {
            payeeID = Long.parseLong(rset.getString(1));
            payeeBalance = Integer.parseInt(rset.getString(2));
            creditPaid = Integer.parseInt(rset.getString(3));
        }

        if (payeeBalance != -999 && payeeID != -1 && creditPaid != -1) {
            sql = "UPDATE transactions SET date_completed = sysdate "
                    + "WHERE transaction_id = " + review.getTransactionID();
            pstmt = conn.prepareStatement(sql);
            success = pstmt.executeUpdate();
            System.out.println(success);

            AddFunds(payeeID, payeeBalance, creditPaid);

            sql = "INSERT INTO reviews (transaction_id, review_value, review_text) "
                    + "VALUES ("
                    + review.getTransactionID() + ","
                    + review.getReviewValue() + ","
                    + "'" + review.getReviewText() + "')";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }
        return success;
    }

    /**
     * Retrieves a count of how many transactions a member needs to finalise in
     * their account.
     *
     * @param memberID - Long representing the ID of the member on which the
     * transactions belong.
     * @return - Integer representing the number of transactions.
     * @throws SQLException
     */
    public int countTransactionsNeedPaying(Long memberID) throws SQLException {
        String sql = "SELECT count(transaction_id) "
                + "FROM adverts ad JOIN transactions t ON ad.advert_id = t.advert_id "
                + "INNER JOIN members m1 ON t.payee = m1.member_id "
                + "WHERE payer = " + memberID + " AND t.date_completed IS NULL";

        int transactionCount = -1;
        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();
        if (rset.next()) {
            transactionCount = Integer.parseInt(rset.getString(1));
        }
        pstmt.clearParameters();
        rset.close();
        return transactionCount;

    }

    // SQL for rules -----------------------------------------------------------
    // -------------------------------------------------------------------------
    /**
     * Retrieves all the rules of the LETS system.
     *
     * @return - A List of Rule objects containing each rule.
     * @throws SQLException
     */
    public List<Rule> getAllRules() throws SQLException {
        String queryString = "SELECT rule_id, rule FROM rules";

        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        List<Rule> rules = new ArrayList<>();
        while (rset.next()) {
            try {
                DecimalFormat dc = new DecimalFormat();
                dc.setParseBigDecimal(true);
                Rule rule = new Rule((BigDecimal) dc.parse(rset.getString(1)),
                        rset.getString(2));
                rules.add(rule);
            } catch (ParseException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pstmt.clearParameters();
        rset.close();
        return rules;
    }

    // SQL for bids ------------------------------------------------------------
    // -------------------------------------------------------------------------
    /**
     * Creates a new entry in the Bids table using the new bid details supplied.
     *
     * @param bid - A Bid object containing the details of the new bid.
     * @return - Long representing the ID of the newly created bid.
     * @throws SQLException
     */
    public Long createBid(Bid bid) throws SQLException {
        String SQL = "INSERT INTO BIDS (ADVERT_ID,OFFERER,OFFEREE,TEXT, ADVERT_TYPE_ID, BID_COMPLETED, IS_ACCEPTED) VALUES "
                + "(" + bid.getAdvertID() + ", "
                + bid.getOffererID() + ", "
                + bid.getOffereeID() + ", "
                + "q'{" + bid.getText() + "}', "
                + bid.getAdvertTypeID() + ","
                + "'N',"
                + "'N')";

        Long generatedKey = -1l;
        pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        pstmt.executeUpdate();
        PreparedStatement ps = conn.prepareStatement("select bids_seq.currval from dual");
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                generatedKey = rs.getLong(1);
            }
            pstmt.clearParameters();
        }
        return generatedKey;
    }

    /**
     * Retrieves a count of how many bids still need to be confirmed by a
     * member.
     *
     * @param memberID - Long representing the member ID to whom the bids have
     * been sent.
     * @return - Integer representing the number of bids that are still to be
     * confirmed.
     * @throws SQLException
     */
    public int countBidsNeedAction(Long memberID) throws SQLException {
        String sql = "SELECT count(bid_id)"
                + "FROM bids JOIN adverts ON bids.advert_id = adverts.advert_id "
                + "WHERE offeree = " + memberID + " AND bid_completed = 'N' "
                + "AND date_completed IS null "
                + "AND to_char(date_exp, 'YYYY-MM-DD HH24:MI:SS') > to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')"
                + "AND is_active = 'Y'";
        return getCount(sql);
    }

    /**
     * Retrieves a count of sent bids which have been refused by other members
     * since last login
     *
     * @param memberID - Long representing the member ID who sent the bids which
     * have been refused.
     * @return - Integer representing the number of bids that have been refused.
     * @throws SQLException
     */
    public int countRejectedBids(Long memberID) throws SQLException {
        String sql = "SELECT COUNT(bid_id)\n"
                + "FROM bids\n"
                + "WHERE offerer = " + memberID + " AND bid_completed = 'Y' AND is_accepted = 'N' AND bid_date > \n"
                + "(SELECT session_date\n"
                + "FROM\n"
                + "    (\n"
                + "    SELECT session_date, ROWNUM AS rn \n"
                + "    FROM (\n"
                + "          SELECT session_date\n"
                + "          FROM sessions\n"
                + "          WHERE member_id = " + memberID + "\n"
                + "          ORDER BY session_date DESC\n"
                + "          )\n"
                + "    )\n"
                + "WHERE rn =2)";
        return getCount(sql);
    }

    /**
     * Retrieves a count of sent bids which have been accepted by other members
     * since last login
     *
     * @param memberID - Long representing the member ID who sent the bids which
     * have been accepted.
     * @return - Integer representing the number of bids that have been
     * accepted.
     * @throws SQLException
     */
    public int countAcceptedBids(Long memberID) throws SQLException {
        String sql = "SELECT COUNT(bid_id)\n"
                + "FROM bids\n"
                + "WHERE offerer = " + memberID + " AND bid_completed = 'Y' AND is_accepted = 'Y' AND bid_date > \n"
                + "(SELECT session_date\n"
                + "FROM\n"
                + "    (\n"
                + "    SELECT session_date, ROWNUM AS rn \n"
                + "    FROM (\n"
                + "          SELECT session_date\n"
                + "          FROM sessions\n"
                + "          WHERE member_id = " + memberID + "\n"
                + "          ORDER BY session_date DESC\n"
                + "          )\n"
                + "    )\n"
                + "WHERE rn =2)";
        return getCount(sql);
    }

    private int getCount(String sql) throws SQLException {
        int bids = -1;
        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();
        if (rset.next()) {
            bids = Integer.parseInt(rset.getString(1));
        }
        pstmt.clearParameters();
        rset.close();
        return bids;
    }

    /**
     * Retrieves the bids sent by a member which have not been accepted or
     * rejected by another member.
     *
     * @param id - Long representing the ID of the sender.
     * @return - A List of Bid objects containing the details of each bid.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Bid> GetBidsOutgoing(Long id) throws SQLException, ParseException {
        String sql = "SELECT title, text, bid_date, m1.forename ||' '|| m1.surname AS offeree,"
                + " m1.member_id, bid_id, bid.advert_id, bid.advert_type_id, is_accepted, return_message "
                + "FROM adverts ad JOIN bids bid ON ad.advert_id = bid.advert_id "
                + "INNER JOIN members m1 ON bid.offeree = m1.member_id "
                + "WHERE offerer = " + id
                + " AND (bid_completed = 'N' AND date_completed IS NULL) "
                + "ORDER BY bid_date DESC";
        return GetBids("outgoing", sql);
    }

    /**
     * Retrieves the bids received by a member which the member has not accepted
     * or rejected.
     *
     * @param id - Long representing the ID of the receiver.
     * @return - A List of Bid objects containing the details of each bid.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Bid> GetBidsIncoming(Long id) throws SQLException, ParseException {
        String sql = "SELECT title, text, bid_date, m1.forename ||' '|| m1.surname AS offerer,"
                + " m1.member_id, bid_id, bid.advert_id, bid.advert_type_id, is_accepted, return_message "
                + "FROM adverts ad JOIN bids bid ON ad.advert_id = bid.advert_id "
                + "INNER JOIN members m1 ON bid.offerer = m1.member_id "
                + "WHERE offeree = " + id
                + " AND bid_completed = 'N' AND date_completed IS NULL "
                + "ORDER BY bid_date DESC";
        return GetBids("incoming", sql);
    }

    /**
     * Retrieves the bids sent by a member which have been rejected by another
     * member.
     *
     * @param id - Long representing the ID of the sender.
     * @return - A List of Bid objects containing the details of each bid.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Bid> GetBidsRefused(Long id) throws SQLException, ParseException {
        String sql = "SELECT title, text, bid_date, m1.forename ||' '|| m1.surname AS offeree, "
                + "m1.member_id, bid_id, bid.advert_id, bid.advert_type_id, is_accepted, return_message "
                + "FROM adverts ad JOIN bids bid ON ad.advert_id = bid.advert_id "
                + "INNER JOIN members m1 ON bid.offeree = m1.member_id "
                + "WHERE offerer = " + id
                + " AND bid_completed = 'Y' AND is_accepted = 'N' "
                + "ORDER BY bid_date DESC";
        return GetBids("outgoing", sql);
    }

    /**
     * Retrieves the bids sent by a member which have been accepted by another
     * member.
     *
     * @param id - Long representing the ID of the sender.
     * @return - A List of Bid objects containing the details of each bid.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Bid> GetBidsAccepted(Long id) throws SQLException, ParseException {
        String sql = "SELECT title, text, bid_date, m1.forename ||' '|| m1.surname AS offeree, "
                + "m1.member_id, bid_id, bid.advert_id, bid.advert_type_id, is_accepted, return_message "
                + "FROM adverts ad JOIN bids bid ON ad.advert_id = bid.advert_id "
                + "INNER JOIN members m1 ON bid.offeree = m1.member_id "
                + "WHERE offerer = " + id
                + " AND bid_completed = 'Y' AND is_accepted = 'Y' "
                + "ORDER BY bid_date DESC";
        return GetBids("outgoing", sql);
    }

    /**
     * Retrieves the bids associated with an advert.
     *
     * @param id - Long representing the ID of the advert for which the bids
     * belong.
     * @return - A List of Bid objects containing the details of each bid.
     * @throws SQLException
     * @throws ParseException
     */
    public List<Bid> GetBidsByAdvertID(Long id) throws SQLException, ParseException {
        String sql = "SELECT title, text, bid_date, m1.forename ||' '|| m1.surname AS offerer,"
                + " m1.member_id, bid_id, bid.advert_id, bid.advert_type_id, is_accepted, return_message "
                + "FROM adverts ad JOIN bids bid ON ad.advert_id = bid.advert_id "
                + "INNER JOIN members m1 ON bid.offerer = m1.member_id "
                + "WHERE bid.advert_id = " + id
                + " AND (bid_completed = 'N' AND date_completed IS NULL)"
                + " ORDER BY bid_date ASC";
        return GetBids("incoming", sql);
    }

    private List<Bid> GetBids(String whichbid, String sql) throws SQLException, ParseException {
        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();
        List<Bid> bidsList = new ArrayList<>();
        Bid bid;

        while (rset.next()) {
            bid = new Bid();
            bid.setAdvertTitle(rset.getString(1));
            bid.setText(rset.getString(2));

            java.util.Date date = new java.util.Date(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(rset.getString(3)).getTime());

            bid.setBidDate(date);

            if (whichbid.equals("incoming")) {
                bid.setOffererName(rset.getString(4));
                bid.setOffererID(Long.parseLong(rset.getString(5)));
            } else {
                bid.setOffereeName(rset.getString(4));
                bid.setOffereeID(Long.parseLong(rset.getString(5)));
            }
            bid.setID(Long.parseLong(rset.getString(6)));
            bid.setAdvertID(Long.parseLong(rset.getString(7)));
            bid.setAdvertTypeID(Integer.parseInt(rset.getString(8)));
            bid.setIsAccepted(rset.getString(9));
            bid.setReturnMessage(rset.getString(10));

            bidsList.add(bid);
        }
        pstmt.clearParameters();
        rset.close();
        return bidsList;
    }

    /**
     * Updates all the relevant tables whenever a bid is accepted by a member.
     * Credits are transfered taken from the payer at this point but not given
     * to the payee until the payer is satisfied with the transaction.
     *
     * @param bid - Bid object containing the bid which is being accepted.
     * @return - 0 if failed to make update else the number of rows affected.
     * @throws SQLException
     * @throws ParseException
     */
    public int UpdateBidAccept(Bid bid) throws SQLException, ParseException {
        int success = 0;
        String sql = "SELECT item_type_id, cost "
                + "FROM adverts "
                + "WHERE advert_id = " + bid.getAdvertID();

        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();
        int itemTypeID = -1;
        int credits = -1;
        if (rset.next()) {
            itemTypeID = Integer.parseInt(rset.getString(1));
            credits = Integer.parseInt(rset.getString(2));
        }
        pstmt.clearParameters();
        rset.close();

        sql = "SELECT contact_number, email "
                + "FROM members "
                + "WHERE member_id = " + bid.getOffereeID();

        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();

        String contactNo = "";
        String email = "";
        if (rset.next()) {
            contactNo = rset.getString(1);
            email = rset.getString(2);
        }
        pstmt.clearParameters();
        rset.close();

        if (credits != -1 && itemTypeID != -1 && !contactNo.isEmpty() && !email.isEmpty()) {
            // Set the accepted bid to accepted
            sql = "UPDATE bids SET bid_completed = 'Y', is_accepted = 'Y', "
                    + "return_message = q'{" + bid.getReturnMessage()
                    + "\nContact Number: " + contactNo
                    + "\nEmail: " + email
                    + "}' "
                    + "WHERE bid_id = " + bid.getID();
            pstmt = conn.prepareStatement(sql);
            success = pstmt.executeUpdate();
            pstmt.clearParameters();

            // Set the advert to completed if it is a product
            if (itemTypeID == 1) {
                sql = "UPDATE adverts SET date_completed = sysdate "
                        + "WHERE advert_id = " + bid.getAdvertID();
                pstmt = conn.prepareStatement(sql);
                pstmt.executeQuery();
                pstmt.clearParameters();

                // Set all other bids on this advert to refused
                sql = "UPDATE bids SET bid_completed = 'Y', is_accepted = 'N', "
                        + "return_message = 'Accepted another bid' "
                        + "WHERE advert_id = " + bid.getAdvertID()
                        + " AND bid_completed = 'N'";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeQuery();
                pstmt.clearParameters();
            }

            // Create a transaction with the payer set to who is paying.
            if (bid.getAdvertTypeID() == 1) {
                sql = "INSERT INTO transactions (advert_id, payer, payee, credit_paid) "
                        + "VALUES ("
                        + bid.getAdvertID() + ","
                        + bid.getOffererID() + ","
                        + bid.getOffereeID() + ","
                        + credits + ")";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeQuery();
                pstmt.clearParameters();

                //Remove funds from payer
                RemoveFunds(bid.getOffererID(), credits);

            } else if (bid.getAdvertTypeID() == 2) {
                sql = "INSERT INTO transactions (advert_id, payer, payee, credit_paid) "
                        + "VALUES ("
                        + bid.getAdvertID() + ","
                        + bid.getOffereeID() + ","
                        + bid.getOffererID() + ","
                        + credits + ")";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeQuery();
                pstmt.clearParameters();

                //Remove funds from payer
                RemoveFunds(bid.getOffereeID(), credits);
            }
        } else {
            return success;
        }

        return success;
    }

    /**
     * Updates a bid to rejected state.
     *
     * @param bid - Bid object containing the bid which is being rejected.
     * @return - 0 if failed to make update else the number of rows affected.
     * @throws SQLException
     */
    public int UpdateBidReject(Bid bid) throws SQLException {
        int success;
        String sql = "UPDATE bids SET bid_completed = 'Y', is_accepted = 'N',"
                + "return_message = q'{" + bid.getReturnMessage() + "}' "
                + "WHERE bid_id = " + bid.getID();

        pstmt = conn.prepareStatement(sql);
        success = pstmt.executeUpdate();

        return success;
    }

    // SQL to transfer funds between members -----------------------------------
    // -------------------------------------------------------------------------
    private void RemoveFunds(Long payerID, int credits) throws SQLException {
        String sql = "SELECT balance "
                + "FROM members "
                + "WHERE member_id = " + payerID;
        pstmt = conn.prepareStatement(sql);
        rset = pstmt.executeQuery();
        int balance = -999;
        if (rset.next()) {
            balance = Integer.parseInt(rset.getString(1));
        }
        pstmt.clearParameters();
        rset.close();

        if (balance != -999) {
            sql = "UPDATE members SET balance = " + (balance - credits)
                    + "WHERE member_id = " + payerID;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            pstmt.clearParameters();
        }
    }

    private void AddFunds(Long payeeID, int payeeBalance, int credits) throws SQLException {
        String sql = "UPDATE members SET balance = " + (payeeBalance + credits)
                + "WHERE member_id = " + payeeID;
        pstmt = conn.prepareStatement(sql);
        pstmt.executeQuery();
        pstmt.clearParameters();
    }
}
