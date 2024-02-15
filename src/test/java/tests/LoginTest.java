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
    public void Successfull_Login(String UserName , String PassWord) {

        //Step_1: Enter the email and password to login
        Loginobj = new LoginPage(driver);

        Loginobj.SiginProcess(UserName , PassWord);
        Assert.assertEquals(driver.getCurrentUrl(), Expected_HomePageURL);
    }
}

