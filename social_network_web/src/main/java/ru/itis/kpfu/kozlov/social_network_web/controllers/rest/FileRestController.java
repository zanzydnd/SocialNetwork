package ru.itis.kpfu.kozlov.social_network_web.controllers.rest;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController(value = "/api")
public class FileRestController {
    @Autowired
    private PostService postService;

    private static final String UPLOAD_DIR = System.getProperty("user.home") + "\\social_network\\img";

    @GetMapping(value = "/uploads/{postId}/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getFile(@PathVariable Long postId, @PathVariable String fileName) throws IOException {
        InputStream in = new FileInputStream(UPLOAD_DIR + "\\" + fileName);
        byte[] array = IOUtils.toByteArray(in);
        ByteArrayResource resource = new ByteArrayResource(array);
        return ResponseEntity.ok()
                .contentLength(array.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
