package com.github.toficzak.digletto.core;

import lombok.RequiredArgsConstructor;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
// TODO: this class exists only because read() parses date differently than deserialization
//  - differs in rounding on millis. When rounding will work as it should, this class is ready to be removed.
public class ODTMatcher extends BaseMatcher<OffsetDateTime> { // TODO: name to change

    private final OffsetDateTime originalDate;
    private final int precision;


    @Override
    public boolean matches(Object actual) {
        if (actual == null) return false;
        if (actual instanceof String) {
            actual = OffsetDateTime.parse((String) actual);
        }
        OffsetDateTime dateTime = (OffsetDateTime) actual;

        OffsetDateTime from = OffsetDateTime.from(originalDate).minus(precision, ChronoUnit.SECONDS);
        OffsetDateTime to = OffsetDateTime.from(originalDate).plus(precision, ChronoUnit.SECONDS);

        return dateTime.isAfter(from) && dateTime.isBefore(to);
    }

    @Override
    public void describeTo(Description description) {

    }
}
