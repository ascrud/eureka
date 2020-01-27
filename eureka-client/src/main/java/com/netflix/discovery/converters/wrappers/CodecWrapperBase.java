package com.netflix.discovery.converters.wrappers;

import javax.ws.rs.core.MediaType;

/**
 * 编码、解码器接口定义
 *
 * @author David Liu
 */
public interface CodecWrapperBase {

    /**
     * @return
     */
    String codecName();

    /**
     * @param mediaType
     * @return
     */
    boolean support(MediaType mediaType);
}
