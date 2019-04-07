package com.nju.controller;

import com.nju.enums.ResultMessage;
import com.nju.service.PublishService;
import com.nju.service.RestaurantService;
import com.nju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final PublishService publishService;

    @Autowired
    public FileController(UserService userService, RestaurantService restaurantService, PublishService publishService){
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.publishService = publishService;
    }

    /**
     * 定义分隔符
     */
    private static String fileSeparator = System.getProperty("file.separator");
    /**
     * 存取路径
     */
    private static String UPLOADED_FOLDER = "images";


    @PostMapping("/upload/avatar")
    public String avatarUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long userId) {
        try {
            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "avatar");

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "avatar"
                    + fileSeparator + userId);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileSeparator + "avatar" + fileSeparator + userId + fileSeparator + file.getOriginalFilename());
            Files.write(path, bytes);

//            System.out.println(file.getOriginalFilename());
            ResultMessage re = userService.updateAvatar(userId, file.getOriginalFilename());

            if (re == ResultMessage.SUCCESS) {
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/show/avatar/{userId}/{filename:.+}")
    public byte[] showAvatar(@PathVariable Long userId, @PathVariable String filename) throws IOException {
        final String imagePath = "images/avatar/" + userId + "/";

        FileInputStream input = new FileInputStream(imagePath + filename);
        return IOUtils.toByteArray(input);
    }

    @PostMapping("/upload/frontPic")
    public String frontPicUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long resId) {
        try {
            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "frontPic");

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "frontPic"
                    + fileSeparator + resId);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileSeparator + "frontPic" + fileSeparator + resId + fileSeparator + file.getOriginalFilename());
            Files.write(path, bytes);

//            System.out.println(file.getOriginalFilename());
            ResultMessage re = restaurantService.updateFrontPic(resId, file.getOriginalFilename());

            if (re == ResultMessage.SUCCESS) {
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/show/frontPic/{resId}/{filename:.+}")
    public byte[] showFrontPic(@PathVariable Long resId, @PathVariable String filename) throws IOException {
        final String imagePath = "images/frontPic/" + resId + "/";

        FileInputStream input = new FileInputStream(imagePath + filename);
        return IOUtils.toByteArray(input);
    }

    @PostMapping("/upload/shopPic")
    public String shopPicUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long resId) {
        try {
            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "shopPic");

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "shopPic"
                    + fileSeparator + resId);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileSeparator + "shopPic" + fileSeparator + resId + fileSeparator + file.getOriginalFilename());
            Files.write(path, bytes);

//            System.out.println(file.getOriginalFilename());
            ResultMessage re = restaurantService.updateShopPic(resId,file.getOriginalFilename());

            if (re == ResultMessage.SUCCESS) {
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/show/shopPic/{resId}/{filename:.+}")
    public byte[] showShopPic(@PathVariable Long resId, @PathVariable String filename) throws IOException {
        final String imagePath = "images/shopPic/" + resId + "/";

        FileInputStream input = new FileInputStream(imagePath + filename);
        return IOUtils.toByteArray(input);
    }

    @PostMapping("/upload/logo")
    public String logoload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long resId) {
        try {
            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "logo");

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "logo"
                    + fileSeparator + resId);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileSeparator + "logo" + fileSeparator + resId + fileSeparator + file.getOriginalFilename());
            Files.write(path, bytes);

//            System.out.println(file.getOriginalFilename());
            ResultMessage re = restaurantService.updataLogo(resId,file.getOriginalFilename());

            if (re == ResultMessage.SUCCESS) {
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/show/logo/{resId}/{filename:.+}")
    public byte[] showLogo(@PathVariable Long resId, @PathVariable String filename) throws IOException {
        final String imagePath = "images/logo/" + resId + "/";
        final String imagePath1 = "/static/image/res_default.png";
        FileInputStream input;

        if(!filename.equals("")){
            input = new FileInputStream(imagePath + filename);
        }else {
            input = new FileInputStream(imagePath1);
        }
        return IOUtils.toByteArray(input);
    }

    @PostMapping("/upload/productImage")
    public String productImageload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
//        System.out.println("id  "+id);
        if(id == 0){
            return "success";
        }
        try {
            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "productImage");

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "productImage"
                    + fileSeparator + id);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileSeparator + "productImage" + fileSeparator + id + fileSeparator + file.getOriginalFilename());
            Files.write(path, bytes);

//            System.out.println("file   "+file.getOriginalFilename());
            ResultMessage re = publishService.updateProImage(id, file.getOriginalFilename());

            if (re == ResultMessage.SUCCESS) {
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/show/productImage/{id}/{filename:.+}")
    public byte[] showProductImage(@PathVariable Long id, @PathVariable String filename) throws IOException {
        final String imagePath = "images/productImage/" + id + "/";
        System.out.println("showProductImage");

        FileInputStream input = new FileInputStream(imagePath + filename);
        return IOUtils.toByteArray(input);
    }

    @PostMapping("/upload/packageImage")
    public String packageImageload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
//        System.out.println("id  "+id);
        if(id == 0){
            return "success";
        }
        try {
            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "packageImage");

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            dir = new File(UPLOADED_FOLDER + fileSeparator + "packageImage"
                    + fileSeparator + id);

            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileSeparator + "packageImage" + fileSeparator + id + fileSeparator + file.getOriginalFilename());
            Files.write(path, bytes);

//            System.out.println("file   "+file.getOriginalFilename());
            ResultMessage re = publishService.updatePacImage(id, file.getOriginalFilename());

            if (re == ResultMessage.SUCCESS) {
                return "success";
            } else {
                return "fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/show/packageImage/{id}/{filename:.+}")
    public byte[] showPackageImage(@PathVariable Long id, @PathVariable String filename) throws IOException {
        final String imagePath = "images/packageImage/" + id + "/";

        FileInputStream input = new FileInputStream(imagePath + filename);
        return IOUtils.toByteArray(input);
    }
}
