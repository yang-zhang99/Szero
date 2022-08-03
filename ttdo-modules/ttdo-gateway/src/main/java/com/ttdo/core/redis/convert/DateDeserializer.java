package com.ttdo.core.redis.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
public class DateDeserializer extends JsonDeserializer<Date> {

    private static final Logger logger = LoggerFactory.getLogger(DateDeserializer.class);
    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        try {
            return DATE_FORMAT.parse(jsonParser.getValueAsString());
        } catch (ParseException var4) {
            logger.warn("date format error : {}", ExceptionUtils.getStackTrace(var4));
            return null;
        }
    }
}
