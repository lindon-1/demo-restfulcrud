package com.example.demo.Controller;

import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 19:27  2019/2/12
 * @return :
 */
@Controller
@Slf4j
@Transactional
public class DataController {
    @Resource
    private EmpService empService;
    @Resource
    private DepartmentService departmentService;

    @RequestMapping("/importAndExport")
    public String turnToHtml(Model model) {
        List<Department> departments = departmentService.selectAll();
        model.addAttribute("depts", departments);
        return "excel/exportAndimportForExcel";
    }

    @RequestMapping("/exportEmp")
    @ResponseBody
    public void exportEmp(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String departId) {
        Integer id = Integer.parseInt(departId);
        List<Employee> employees = empService.searchByBmid(id);
        Department department = departmentService.searchById(id);
        //将员工写入excel表中
        HSSFWorkbook wb = null;
        if(employees.size() == 0 || department == null){
            log.error("employees.size() : " + employees.size() + ";department : " + department);
        }
        try {
            wb = getSheets(employees, department);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        response.reset();
        response.setContentType("application/msexcel;charset=UTF-8");
        try {
            SimpleDateFormat newsdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = newsdf.format(new Date());
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(("emp_" + date + ".xls").getBytes("GBK"),
                    "ISO8859_1") + "\"");
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "导出失败!");
            log.error(e.getMessage());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "导出失败!");
            log.error(e.getMessage());
        }


    }

    @RequestMapping("/importEmp")
    @ResponseBody
    public void importEmp(@RequestParam MultipartFile file, HttpServletRequest request,Model model) {
        //把MultipartFile转化为File
        Integer num = null;

        try {
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            String type = originalFilename.substring(originalFilename.indexOf(".") + 1);
            System.out.println(type);
            Workbook wb = null;
            Sheet sheet = null;
            InputStream inputStream = file.getInputStream();
            if ("xls".equals(type)) {
                wb = new HSSFWorkbook(inputStream);
                //获取第一个工作表
                sheet = wb.getSheet("员工表");
            } else {
                wb = new XSSFWorkbook(inputStream);
                //获取第一个工作表
                sheet = wb.getSheet("员工表");
            }
            //获取sheet中第一行行号
            int firstRowNum = sheet.getFirstRowNum();

            //获取sheet中最后一行行号
            int lastRowNum = sheet.getLastRowNum();



            List<Employee> employees = new ArrayList<>();
            for (int i = firstRowNum + 3; i <= lastRowNum; i++) {
                Employee employee = null;
                try {
                    employee = getEmployee(sheet, i);
                    if(employee.getDepartmentId() == null){
                        log.error("员工 ：" + employee.getName() + "没有输入部门名称");
                    }

                } catch (ParseException e) {
                    log.error(e.getMessage());
                }
                employees.add(employee);
            }
            employees.forEach(e -> System.out.println(e.toString()));
            num = empService.addAll(employees);

        } catch (IOException e) {
           log.error(e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        Map<String,String> map = new HashMap<>();
        if(num != null){
            map.put("msg","true");
        }else {
            map.put("msg","false");
        }
        model.addAttribute("map",map);
    }

    private Employee getEmployee(Sheet sheet, int i) throws ParseException {
        Row row = sheet.getRow(i);
        Employee employee = new Employee();

        Cell name = row.getCell(0);//员工名称
        if(name!=null){
            name.setCellType(Cell.CELL_TYPE_STRING);
            employee.setName(name.getStringCellValue());
        }

        Cell email = row.getCell(1);
        if(email != null){
            email.setCellType(Cell.CELL_TYPE_STRING);
            employee.setEmail(email.getStringCellValue());
        }

        Cell gender = row.getCell(2);
        if(gender != null){
            gender.setCellType(Cell.CELL_TYPE_STRING);
            employee.setGender(gender.getStringCellValue().equals("男") ? 1 :0 );
        }

        Cell bmmc = row.getCell(3);
        if(bmmc != null){
            bmmc.setCellType(Cell.CELL_TYPE_STRING);
            String bmmcStringCellValue = bmmc.getStringCellValue();
            Integer bmid = departmentService.searchIdByName(bmmcStringCellValue);
            employee.setDepartmentId(bmid);
        }

        Cell birth = row.getCell(4);
        if(birth != null){
            birth.setCellType(Cell.CELL_TYPE_STRING);
            String stringCellValue = birth.getStringCellValue();
           // System.out.println(stringCellValue);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            employee.setBirth(sdf.parse(stringCellValue));
        }
        return employee;
    }

    private HSSFWorkbook getSheets(List<Employee> employees, Department department) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("员工表");
        sheet.setDefaultColumnWidth(15);//设置表宽
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle headerStyle = wb.createCellStyle();
        HSSFFont headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setFont(headerFont);
        CellRangeAddress cra0 = new CellRangeAddress(0, 1, 0, 9);
        sheet.addMergedRegion(cra0);
        sheet.setDefaultColumnWidth((short) 15);
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);

        cell1.setCellValue(department.getDepartmentName() + "员工表");
        cell1.setCellStyle(headerStyle);
        //设置字体样式
        HSSFFont titleFont = wb.createFont();
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        Row row1 = sheet.createRow(2);
        Cell cell = row1.createCell(0);
        cell.setCellValue("员工名称");
        cell.setCellStyle(style);
        cell = row1.createCell(1);
        cell.setCellValue("邮箱");
        cell.setCellStyle(style);
        cell = row1.createCell(2);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row1.createCell(3);
        cell.setCellValue("部门");
        cell.setCellStyle(style);
        cell = row1.createCell(4);
        cell.setCellValue("入职时间");
        cell.setCellStyle(style);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (employees.size() > 0) {
            for (int i = 0; i < employees.size(); i++) {
                row1 = sheet.createRow(i + 3);
                if (employees.get(i).getName() == null || "".equals(employees.get(i).getName())) {
                    row1.createCell(0).setCellValue("-");
                } else {
                    row1.createCell(0).setCellValue(employees.get(i).getName());
                }
                if (employees.get(i).getEmail() == null || "".equals(employees.get(i).getEmail())) {
                    row1.createCell(1).setCellValue("-");
                } else {
                    row1.createCell(1).setCellValue(employees.get(i).getEmail());
                }
                if (employees.get(i).getGender() == null || "".equals(employees.get(i).getGender())) {
                    row1.createCell(2).setCellValue("-");
                } else {
                    row1.createCell(2).setCellValue(employees.get(i).getGender() == 0 ? "女" : "男");
                }
                row1.createCell(3).setCellValue(department.getDepartmentName());
                if (employees.get(i).getBirth() == null || "".equals(employees.get(i).getBirth())) {
                    row1.createCell(4).setCellValue("-");
                } else {
                    row1.createCell(4).setCellValue(sdf.format(employees.get(i).getBirth()));
                }
            }
        }
        return wb;
    }
}
