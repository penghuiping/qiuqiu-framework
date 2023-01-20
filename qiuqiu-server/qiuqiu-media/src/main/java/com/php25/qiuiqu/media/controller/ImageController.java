package com.php25.qiuiqu.media.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.PooledDataBuffer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author penghuiping
 * @date 2022/11/6 21:48
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping(path = "/{id}",produces = {MediaType.IMAGE_JPEG_VALUE ,MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_GIF_VALUE})
    public Flux<DataBuffer> findOne(@PathVariable String id, ServerHttpResponse response) {
        return Mono.just(id).flatMapMany(fileName -> {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource resource = resourcePatternResolver.getResource("classpath:"+fileName);
            return DataBufferUtils.readInputStream(resource::getInputStream, response.bufferFactory(), 1024)
                    .doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
        });
    }
}
