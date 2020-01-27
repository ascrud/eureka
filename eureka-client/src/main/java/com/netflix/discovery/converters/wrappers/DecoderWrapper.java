package com.netflix.discovery.converters.wrappers;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author David Liu
 */
public interface DecoderWrapper extends CodecWrapperBase {

    /**
     * @param textValue
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T decode(String textValue, Class<T> type) throws IOException;

    /**
     * @param inputStream
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T decode(InputStream inputStream, Class<T> type) throws IOException;
}
