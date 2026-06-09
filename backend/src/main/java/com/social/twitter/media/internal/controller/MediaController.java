package com.social.twitter.media.internal.controller;

import com.social.twitter.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/uploadMedia")
    public String uploadMedia(@RequestParam("media") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()){
            throw new RuntimeException("Media is empty.");
        }
        else {
            try {
                String imageUrl = mediaService.uploadFile(multipartFile);
                System.out.println(imageUrl);
                return imageUrl;
            }
            catch (Exception e){
                System.out.println("printing from controller - ");
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @PostMapping("/deleteMedia")
    public String deleteMedia(@RequestParam String imageUrl){
        try {
            mediaService.deleteFile(imageUrl);
            return "success";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
