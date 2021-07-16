package com.php25.qiuqiu.media.service;

import java.nio.channels.ReadableByteChannel;

/**
 * @author penghuiping
 * @date 2021/7/14 15:32
 */
public interface ImageService {

    /**
     * 根据code生成图片验证码
     *
     * @param code 验证码
     * @return 验证码字节流
     */
    ReadableByteChannel getCode(String code);


    /**
     * 扩大或者缩小图片
     * <p>
     * 此方法应该需要有本地文件缓存，如果已经有相应的尺寸的图片则直接使用
     * 本地文件缓存使用lru淘汰算法来实现
     *
     * @param imageId 图片id
     * @param width   图片宽度(px)
     * @param height  图片高度(px)
     * @return 图片字节流
     */
    ReadableByteChannel resizeImage(String imageId, Integer width, Integer height);

    /**
     * 上传图片
     *
     * @param readableByteChannel 图片字节流
     * @return 成功返回图片id
     */
    String uploadImage(ReadableByteChannel readableByteChannel);
}
