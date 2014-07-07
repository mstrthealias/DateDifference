/*
 * Copyright 2014 - jd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jtd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * @author jd 4/30/2014
 */
public class DateDifference {
    final private static int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private long seconds;
    private long minutes;
    private long hours;
    private long days;
    private long months;
    private long years;

    /**
     * eg. mkPart(5, "Minute") returns 5 Minutes
     * @param val
     * @param base
     * @return
     */
    static private String mkPart(long val, String base) {
        return val + " " + base + (val == 0 || Math.abs(val) != 1 ? "s" : "");
    }


    /**
     * @param d1
     * @param d2 If null, now is used
     * @return d2 - d1
     * @throws IllegalArgumentException If d1 == null
     */
    public DateDifference(Date d1, Date d2) {
        if (d1 == null) {
            throw new IllegalArgumentException("d1 == null");
        } else if (d2 == null) {
            d2 = new Date();
        }

        Date date1, date2;
        int adj;

        if (d2.getTime() < d1.getTime()) {
            adj = -1;
            date1 = d2;
            date2 = d1;
        } else {
            adj = 1;
            date1 = d1;
            date2 = d2;
        }

        String d1_year, d1_month, d1_day, d2_year, d2_month, d2_day, d1_hour, d2_hour, d1_min, d2_min, d1_sec, d2_sec;

        final TimeZone utc = new SimpleTimeZone(0, "UTC");

        DateFormat dateFormat = new SimpleDateFormat("y");
        dateFormat.setTimeZone(utc);
        d1_year = dateFormat.format(date1);
        d2_year = dateFormat.format(date2);

        dateFormat = new SimpleDateFormat("M");
        dateFormat.setTimeZone(utc);
        d1_month = dateFormat.format(date1);
        d2_month = dateFormat.format(date2);

        dateFormat = new SimpleDateFormat("d");
        dateFormat.setTimeZone(utc);
        d1_day = dateFormat.format(date1);
        d2_day = dateFormat.format(date2);

        dateFormat = new SimpleDateFormat("H");
        dateFormat.setTimeZone(utc);
        d1_hour = dateFormat.format(date1);
        d2_hour = dateFormat.format(date2);

        dateFormat = new SimpleDateFormat("m");
        dateFormat.setTimeZone(utc);
        d1_min = dateFormat.format(date1);
        d2_min = dateFormat.format(date2);

        dateFormat = new SimpleDateFormat("s");
        dateFormat.setTimeZone(utc);
        d1_sec = dateFormat.format(date1);
        d2_sec = dateFormat.format(date2);

        int d2Year = Integer.parseInt(d2_year),
                years = d2Year - Integer.parseInt(d1_year);
        int d2Month = Integer.parseInt(d2_month),
                months = d2Month - Integer.parseInt(d1_month);
        int days = Integer.parseInt(d2_day) - Integer.parseInt(d1_day);
        int hours = Integer.parseInt(d2_hour) - Integer.parseInt(d1_hour);
        int mins = Integer.parseInt(d2_min) - Integer.parseInt(d1_min);
        int secs = Integer.parseInt(d2_sec) - Integer.parseInt(d1_sec);

        if (secs < 0) {
            // decrement mins
            mins--;

            // increment secs by number of secs in minute
            secs += 60;
        }

        while (mins < 0) {
            // decrement hours
            hours--;

            // increment mins by number of mins in hour
            mins += 60;
        }

        while (hours < 0) {
            // decrement days
            days--;

            // increment hours by number of hours in day
            hours += 24;
        }

        int curMonth = d2Month,
                curYear = d2Year;
        while (days < 0) {
            // decrement months
            months--;

            // increment days by number of days in previous month
            if (curMonth == 1) {
                curMonth = 12;
                curYear--;
            } else {
                curMonth--;

                // consider leap year
                if (curMonth == 2 && curYear % 4 == 0) {
                    days++;
                }
            }

            days += DAYS_IN_MONTH[curMonth];
        }

        while (months < 0)  {
            // increment months by number of months in year, decrement years
            months += 12;
            years--;
        }

        if (years < 0) {
            throw new RuntimeException();  // shouldnt get here (d2 > d1)
        }

        this.years = adj * years;
        this.months = adj * months;
        this.days = adj * days;
        this.hours = adj * hours;
        this.minutes = adj * mins;
        this.seconds = adj * secs;
    }

    /**
     * @param d1
     * @return Now - d1
     */
    public DateDifference(Date d1) {
        this(d1, new Date());
    }

    /**
     * @return [[[<years> years, ]<days> days, ], <months> months, ]<hours> hours, <minutes> minutes
     */
    @Override
    public String toString() {
        return getString(false);
    }

    /**
     * @return [[[<years> years, ]<days> days, ], <months> months, ]<hours> hours, <minutes> minutes[, <seconds> seconds]
     */
    public String getString(boolean withSeconds) {
        String suffix = withSeconds && this.seconds != 0 ? ", " + mkPart(this.minutes, "Minute") + ", and " + mkPart(this.seconds, "Second") : ", and " + mkPart(this.minutes, "Minute");

        if (this.years != 0) {
            return mkPart(this.years, "Year") + ", " + mkPart(this.months, "Month") + ", " + mkPart(this.days, "Day") + ", " + mkPart(this.hours, "Hour") + suffix;
        } else if (this.months != 0) {
            return mkPart(this.months, "Month") + ", " + mkPart(this.days, "Day") + ", " + mkPart(this.hours, "Hour") + suffix;
        } else if (this.days != 0) {
            return mkPart(this.days, "Day") + ", " + mkPart(this.hours, "Hour") + suffix;
        } else {
            return mkPart(this.hours, "Hour") + suffix;
        }
    }

    public long getSeconds() {
        return seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getHours() {
        return hours;
    }

    public long getDays() {
        return days;
    }

    public long getMonths() {
        return months;
    }

    public long getYears() {
        return years;
    }
}
