package com.pet.bank.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.pet.bank.aspects.LoggableAspect;

public class LogAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {

        if(eventObject.getLevel().isGreaterOrEqual(Level.WARN)){

            String logMessage = eventObject.getFormattedMessage();
            LoggableAspect.addLogMessage(logMessage);
        }
    }

}