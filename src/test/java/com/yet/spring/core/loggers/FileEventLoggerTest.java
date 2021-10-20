package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class FileEventLoggerTest {
    private File file;

    @Before
    public void createFile() throws Exception {
        this.file = File.createTempFile("test", "FileEventLogger");
    }

    @After
    public void removeFile() {
        file.delete();
    }

    @Test
    public void testLogEvent() throws IOException {
        Event event = new Event(new Date(), DateFormat.getDateInstance());
        FileEventLogger logger = new FileEventLogger(file.getAbsolutePath());
        logger.init();

        String contents = FileUtils.readFileToString(file);
        assertTrue(contents.isEmpty());

        logger.logEvent(event);

        contents = FileUtils.readFileToString(file);
        assertFalse(contents.isEmpty());
    }

    @Test
    public void testInit() throws IOException {
        FileEventLogger logger = new FileEventLogger(file.getAbsolutePath());
        logger.init();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitFail() throws IOException {
        file.setReadOnly();
        FileEventLogger logger = new FileEventLogger(file.getAbsolutePath());
        logger.init();
    }
}