package tests;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;


public class LoginTest extends TestBase {

    LoginPage Loginobj;
    String Expected_HomePageURL =
            "http://weblogictn20-qe.tradenet.com:7002/IMOREGATEWAY/IMORECORE/TN20/BackOffice/ENBD/landing#/index/Login_1453031380370";

    @DataProvider(name = "Login")
    public static Object[][] getDataFromExcel() throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\DataSources\\TestDataFile.xlsx");
        FileInputStream FIS = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(FIS);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        int cols = 2;

        Object[][] lst = new Object[rows][cols];
        for (int i = 1; i <= rows; i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                lst[i - 1][j] = row.getCell(j).toString();
            }
        }
        workbook.close();
        return lst;
    }

    @Test(priority = 1 , dataProvider = "Login")
    public void Successful_Login(String UserName , String PassWord) {

        //Step_1: Enter the email and password to login
        Loginobj = new LoginPage(driver);

        Loginobj.SiginProcess(UserName , PassWord);
        Assert.assertEquals(driver.getCurrentUrl(), Expected_HomePageURL);
        Loginobj.signOut();
        Assert.assertTrue(Loginobj.username_TxtField.isDisplayed());
    }

    @Test
    public void getDataFromDataBase() {
        try {

            String query = "SELECT * FROM TRADESUPER.SM_DERIVATIVES_FUTURE";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

 //  --------------------------- Get All Rows from the Table
            while (rs.next()) {
                String MarketID = rs.getString(1);
                System.out.println("Market ID : " + MarketID);
                String ProductID = rs.getString(2);
                System.out.println("Product ID : " + ProductID);
                String ContractID = rs.getString(3);
                System.out.println("Contract ID : " + ContractID);
                int ContractSize = rs.getInt(4);
                System.out.println("Contract Size : " + ContractSize);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

