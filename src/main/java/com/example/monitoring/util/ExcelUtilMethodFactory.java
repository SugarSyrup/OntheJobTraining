package com.example.monitoring.util;

import com.example.monitoring.domain.dto.EquipmentDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelUtilMethodFactory {
    void equipmentExcelDownload(List<EquipmentDto> data, HttpServletResponse response);
    void renderEquipmentExcelBody(List<EquipmentDto> data, Sheet sheet, Row row, Cell cell);
}
