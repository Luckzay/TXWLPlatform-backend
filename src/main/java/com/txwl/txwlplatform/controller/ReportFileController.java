package com.txwl.txwlplatform.controller;

import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.service.IReportService;
import com.txwl.txwlplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/reports")
public class ReportFileController {

    @Autowired
    private IReportService reportService;
    
    @Autowired
    private IUserService userService;

    @GetMapping("/generated/{fileName}")
    public ResponseEntity<Resource> getReportFile(@PathVariable String fileName) {
        try {
            // 首先尝试旧路径
            String oldBasePath = System.getProperty("user.home") + "/txwl-platform/reports/";
            String oldFilePath = oldBasePath + fileName;
            File oldFile = new File(oldFilePath);
            
            if (oldFile.exists()) {
                // 验证用户是否有权访问此报告
                if (!hasAccessToReport(fileName)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                
                InputStreamResource oldResource = new InputStreamResource(new FileInputStream(oldFile));
                
                MediaType contentType = determineMediaType(fileName);
                
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .contentType(contentType)
                        .contentLength(oldFile.length())
                        .body(oldResource);
            }
            
            // 如果旧路径不存在，返回404
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 新增一个方法用于访问新路径的报告文件
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getNewReportFile(@PathVariable String fileName) {
        try {
            // 对于新存储的报告，它们位于classpath:/static/reports/下
            // 但由于这是静态资源，通常不需要额外的控制器，Spring Boot会自动提供
            // 这里保留以防需要特殊处理
            String projectPath = System.getProperty("user.dir", "");
            if (projectPath.isEmpty()) {
                projectPath = "./";
            }
            String basePath = projectPath + "/src/main/resources/static/reports/";
            String filePath = basePath + fileName;
            
            File file = new File(filePath);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            // 验证用户是否有权访问此报告
            if (!hasAccessToReport(fileName)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            
            MediaType contentType = determineMediaType(fileName);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(contentType)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 根据文件扩展名确定媒体类型
     */
    private MediaType determineMediaType(String fileName) {
        if (fileName.toLowerCase().endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.toLowerCase().endsWith(".html") || fileName.toLowerCase().endsWith(".htm")) {
            return MediaType.TEXT_HTML;
        } else {
            return MediaType.ALL;
        }
    }
    
    /**
     * 验证当前用户是否有权访问指定的报告文件
     */
    private boolean hasAccessToReport(String fileName) {
        // 从安全上下文中获取当前认证用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 如果未认证，返回false
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }
        
        // 获取当前用户的ID
        String username = authentication.getName();
        User currentUser = userService.getUserByIdentifier(username);
        if (currentUser == null) {
            return false;
        }
        Long currentUserId = currentUser.getUid();
        
        // 从文件名解析出报告信息 (格式: report_{userId}_{paperId}_{timestamp}.(html|pdf))
        if (!fileName.endsWith(".html") && !fileName.endsWith(".pdf")) {
            return false;
        }
        
        try {
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
            String[] parts;
            
            // 检查文件名格式，支持两种格式：report_{userId}_{paperId}_{timestamp} 或 {userId}_{paperId}_{timestamp}
            if (fileNameWithoutExt.startsWith("report_")) {
                parts = fileNameWithoutExt.split("_");
                if (parts.length < 4) { // 至少要有 "report", "{userId}", "{paperId}", "{timestamp}"
                    return false;
                }
                // 用户ID是第三部分（索引为2），因为格式是 report_{userId}_{paperId}_{timestamp}
                Long reportUserId = Long.parseLong(parts[2]); // 用户ID是第三部分
                // 检查当前用户ID是否与报告中的用户ID匹配
                return currentUserId.equals(reportUserId);
            } else {
                parts = fileNameWithoutExt.split("_");
                if (parts.length < 3) { // 至少要有 "{userId}", "{paperId}", "{timestamp}"
                    return false;
                }
                // 用户ID是第一部分（索引为0）
                Long reportUserId = Long.parseLong(parts[0]); // 用户ID是第一部分
                // 检查当前用户ID是否与报告中的用户ID匹配
                return currentUserId.equals(reportUserId);
            }
            
        } catch (NumberFormatException e) {
            // 如果无法解析文件名中的ID，返回false
            return false;
        }
    }
}