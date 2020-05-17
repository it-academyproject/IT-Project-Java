package com.it_academyproject.tools.excel;

import jxl.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;

public class Excel
{
	private Workbook workbook;

	// -------------------- -------------------- //
	
	public void openFile(String fileLocation) throws IOException, BiffException
	{
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		workbook = Workbook.getWorkbook(new File(fileLocation), ws);
	}

	public Map<Integer, List<String>> readJExcelContent(int sheetNumber) throws IOException, BiffException
	{
		Map<Integer, List<String>> data = new HashMap<>();
		Sheet sheet = workbook.getSheet(sheetNumber);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		for (int i = 0; i < rows; i++)
		{
			data.put(i, new ArrayList<String>());
			for (int j = 0; j < columns; j++)
			{
				data.get(i).add(sheet.getCell(j, i).getContents());
			}
		}
		return data;
	}

}