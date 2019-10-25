package logicalView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.PageFactory;

import commonFunctions.ApplicationUtility;
import testObjects.Loginobject;

public class ReadWriteDataExcel extends ApplicationUtility{
	static XSSFWorkbook workbook;
	static XSSFCell cell;
	static XSSFSheet sheet;
	

public static void ReadWriteDataMethod() throws InterruptedException, IOException {

	Loginobject loginpage = PageFactory.initElements(driver, Loginobject.class);

	//Create an file object to open the excel file
	File excelfile=new File(System.getProperty("user.dir")+"\\src\\testData\\Testdata.xlsx");
	
	//To load the file
	FileInputStream fileinput=new FileInputStream(excelfile);
	
	//load the workbook
	workbook =new XSSFWorkbook(fileinput);
	
	//load the sheet
	sheet = workbook.getSheetAt(0);
	
	for (int i=1 ; i<= sheet.getLastRowNum();i++) {
		
		//to load the row and cell no. of Username
		cell = sheet.getRow(i).getCell(1);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		
		//Load the value of the Username
		loginpage.setEmailclear();
		loginpage.setEmail(cell.getStringCellValue());
		Thread.sleep(500);
		
		//to load the row and cell no. of Password
		cell = sheet.getRow(i).getCell(2);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		
		//Load the value of the Password
		loginpage.setPasswordclear();
		loginpage.setPassword(cell.getStringCellValue());
		Thread.sleep(500);
		
		//To submit the entered data
		loginpage.setsubmitButton();
		
		String title = driver.getPageSource();
	
		// For writing data to the File
		FileOutputStream fos=new FileOutputStream(excelfile);
		
		//Condition to check the success message is correct or not to write the status as Pass
		if(title.contains("Welcome")) {
			
			
			//To set the value of the cell
			sheet.getRow(i).createCell(3).setCellValue(ApplicationUtility.getValueFromPropertyFile("Status_Pass"));
			workbook.write(fos);
			
			loginpage.setprofileDropdown();
			Thread.sleep(500);
			loginpage.setSignoutButton();
			Thread.sleep(500);
			
		}
		
		//If the condition is not correct then it will write Fail in the particular cell of testcase
		else
		{
			
			sheet.getRow(i).createCell(3).setCellValue(ApplicationUtility.getValueFromPropertyFile("Status_Fail"));
			workbook.write(fos);
			driver.get(ApplicationUtility.getValueFromPropertyFile("Url"));
			
		}
	}
		
	Thread.sleep(2000);
	//To close the browser
	driver.quit();
	}
}

