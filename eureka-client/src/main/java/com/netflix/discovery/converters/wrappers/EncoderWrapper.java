package com.netflix.discovery.converters.wrappers;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 编码器包装器
 *
 * @author David Liu
 */
public interface EncoderWrapper extends CodecWrapperBase {

    /**
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> String encode(T object) throws IOException;

    /**
     * @param object
     * @param outputStream
     * @param <T>
     * @throws IOException
     */
    <T> void encode(T object, OutputStream outputStream) throws IOException;
}
