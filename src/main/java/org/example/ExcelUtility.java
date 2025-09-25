import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelUtility {
  public static Map<String, String> getRowData(String filePath, String sheetName, int rowNumber) {
Map<String, String> requestData = new HashMap<>();
try (FileInputStream file  = new FileInputStream(new File(filePath));
  Workbook workbook = new XSSFWorkbook(file)){;
  Sheet sheet = workbook.getSheet(sheetName);

  Row row = sheet.getRow(rowNumber);
  Row headerRow = sheet.getRow(0);

  for(int i = 0; i<row.getLastCellNum(); i++){
    Cell headerCell = headerRow.getCell(i);
    Cell valueCell = row.getCell(i);

    String key = headerCell.getStringCellValue();
    String value = "" ;

    if(valueCell != null){
      switch(valueCell.getCellType()){
        case STRING:
          value = valueCell.getStringCellValue();
          break;
        case NUMERIC:
          value = String.valueOf((long)valueCell.getNumericCellValue());
          break;
        case BOOLEAN:
          value = String.valueOf(valueCell.getBooleanCellValue());
          break;
        default:
          value = "";

      }
    }
    requestData.put(key, value);

  }
  workbook.close();
  file.close();


} catch (IOException e) {
  e.printStackTrace();
}
return requestData;


  }
}
