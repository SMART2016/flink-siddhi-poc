package com.hiramsoft.commons.jsalparser;

/**
 * Created by ihiram on 8/27/14.
 * <p>
 * Only used by the builder
 */
interface IValueBuilder {

    void accept(String value, CloudFrontWebLogEntry entry);
}
