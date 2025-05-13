package wth.spring.OssEncrypt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class OssUrlController {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.video-dir}")
    private String videoDir;

    @GetMapping("/play")
    public ResponseEntity<String> getSignedVideoUrl(@RequestParam String filename,
                                                    @RequestHeader("Authorization") String token) {
        // 假设有一个 Token 验证服务
        if (!AuthService.verifyToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("无权限");
        }

        // 生成签名 URL（有效期 5 分钟）
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
        String objectKey = videoDir + "/" + filename;
        URL signedUrl = ossClient.generatePresignedUrl(bucketName, objectKey, expiration);

        return ResponseEntity.ok(signedUrl.toString());
    }
        @GetMapping("/key")
        public ResponseEntity<byte[]> getKey(@RequestParam String id,
                @RequestHeader("Authorization") String token) {
            User user = AuthService.getUserFromToken(token);
            if (user == null || !VideoService.userHasAccess(user, id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // 返回 AES 密钥二进制内容
            byte[] keyBytes = VideoService.getVideoKey(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return new ResponseEntity<>(keyBytes, headers, HttpStatus.OK);
        }



}
