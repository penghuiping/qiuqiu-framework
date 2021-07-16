package com.php25.qiuqiu.media.service;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.RandomUtil;
import com.php25.common.redis.RedisManager;
import com.php25.qiuqiu.media.dto.CodeConfigDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author penghuiping
 * @date 2021/7/14 16:04
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    /**
     * RGB颜色范围上限
     */
    private static final int RGB_COLOR_BOUND = 256;


    @Override
    public ReadableByteChannel getCode(String code) {
        return generateCodeImg(code);
    }


    private ReadableByteChannel generateCodeImg(String code) {
        int width = 300;
        int height = 150;
        Font font = Font.decode("Arial-BOLD-70");
        int interfereCount = 300;
        CodeConfigDto codeConfigDto = new CodeConfigDto();
        codeConfigDto.setFont(font);
        codeConfigDto.setHeight(height);
        codeConfigDto.setWidth(width);
        codeConfigDto.setInterfereCount(interfereCount);
        return this.generateCodeImg(code, codeConfigDto);
    }

    private ReadableByteChannel generateCodeImg(String code, CodeConfigDto codeConfigDto) {
        int width = codeConfigDto.getWidth();
        int height = codeConfigDto.getHeight();
        Color bgColor = Color.white;
        Color fontColor = Color.BLACK;
        Font font = codeConfigDto.getFont();
        int interfereCount = codeConfigDto.getInterfereCount();

        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = image.createGraphics();
        if (null != bgColor) {
            // 填充背景
            g.setColor(bgColor);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
        }

        final ThreadLocalRandom random = RandomUtil.getRandom();
        // 干扰线
        for (int i = 0; i < interfereCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            g.setColor(new Color(random.nextInt(RGB_COLOR_BOUND), random.nextInt(RGB_COLOR_BOUND), random.nextInt(RGB_COLOR_BOUND)));
            g.drawLine(xs, ys, xe, ye);
        }

        // 抗锯齿
        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        // 创建字体
        g.setFont(font);

        // 文字高度（必须在设置字体后调用）
        int midY = getCenterY(g, height);
        if (null != fontColor) {
            g.setColor(fontColor);
        }

        final int len = code.length();
        int charWidth = width / len;
        for (int i = 0; i < len; i++) {
            g.drawString(String.valueOf(code.charAt(i)), i * charWidth, midY);
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);) {
            ImageIO.write(image, "png", bos);
            return Channels.newChannel(new ByteArrayInputStream(bos.toByteArray()));
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("图形验证码生成失败", e);
        }
    }

    /**
     * 获取文字居中高度的Y坐标（距离上边距距离）<br>
     * 此方法依赖FontMetrics，如果获取失败，默认为背景高度的1/3
     *
     * @param g                {@link Graphics2D}画笔
     * @param backgroundHeight 背景高度
     * @return 最小高度，-1表示无法获取
     */
    private int getCenterY(Graphics g, int backgroundHeight) {
        // 获取允许文字最小高度
        FontMetrics metrics = null;
        try {
            metrics = g.getFontMetrics();
        } catch (Exception e) {
            // 此处报告bug某些情况下会抛出IndexOutOfBoundsException，在此做容错处理
        }
        int y;
        if (null != metrics) {
            y = (backgroundHeight - metrics.getHeight()) / 2 + metrics.getAscent();
        } else {
            y = backgroundHeight / 3;
        }
        return y;
    }

    @Override
    public ReadableByteChannel resizeImage(String imageId, Integer width, Integer height) {
        return null;
    }

    @Override
    public String uploadImage(ReadableByteChannel readableByteChannel) {
        return null;
    }
}
