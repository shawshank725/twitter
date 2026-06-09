package com.social.twitter.media;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {

    String uploadFile(MultipartFile multipartFile) throws IOException;

    void deleteFile(String url) throws IOException;
}

