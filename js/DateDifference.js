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

/**
* @constructor Calculates the different between d2-d1
* @param {Date} d1
* @param {Date|} d2 If null or undefined, now is used
* @returns {DateDifference}
* @throws {Error} If invalid parameter(s)
*/
function DateDifference(d1, d2) {
    if (!(d1 instanceof Date) || d1 == 'Invalid Date') {
        throw new Error('Invalid Parameter: d1');
    } else if (typeof d2 == 'undefined' || d2 === null) {
        d2 = new Date();
    } else if (!(d2 instanceof Date) || d2 == 'Invalid Date') {
        throw new Error('Invalid Parameter: d2');
    }

    /**
    * @type Date
    */
    var date1,
        /**
        * @type Date
        */
        date2,
        /**
        * @type Number
        */
        adj;
    
    if (d2.getTime() < d1.getTime()) {
        adj = -1;
        date1 = d2;
        date2 = d1;
    } else {
        adj = 1;
        date1 = d1;
        date2 = d2;
    }
    
    var d1_year = date1.getUTCFullYear(),
        d1_month = date1.getUTCMonth() + 1,
        d1_day = date1.getUTCDate(),
        d1_hour = date1.getUTCHours(),
        d1_min = date1.getUTCMinutes(),
        d1_sec = date1.getUTCSeconds(),
        d2_year = date2.getUTCFullYear(),
        d2_month = date2.getUTCMonth() + 1,
        d2_day = date2.getUTCDate(),
        d2_hour = date2.getUTCHours(),
        d2_min = date2.getUTCMinutes(),
        d2_sec = date2.getUTCSeconds();
    
    var years = d2_year - d1_year,
        months = d2_month - d1_month,
        days = d2_day - d1_day,
        hours = d2_hour - d1_hour,
        mins = d2_min - d1_min,
        secs = d2_sec - d1_sec;
    
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

    var curMonth = d2_month,
            curYear = d2_year;
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

        days += DateDifference.DAYS_IN_MONTH[curMonth];
    }

    while (months < 0)  {
        // increment months by number of months in year, decrement years
        months += 12;
        years--;
    }

    if (years < 0) {
        throw new Error('Logic Error: years < 0');  // shouldnt get here (d2 > d1)
    }

    /**
    * @property years
    * @type Number
    */
    this.years = adj * years;
    
    /**
    * @property months
    * @type Number
    */
    this.months = adj * months;
    
    /**
    * @property days
    * @type Number
    */
    this.days = adj * days;
    
    /**
    * @property hours
    * @type Number
    */
    this.hours = adj * hours;
    
    /**
    * @property minutes
    * @type Number
    */
    this.minutes = adj * mins;
    
    /**
    * @property seconds
    * @type Number
    */
    this.seconds = adj * secs;
}

/**
* @type Array.<Number>
* @static
*/
DateDifference.DAYS_IN_MONTH = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

/**
* @returns {String} [[[<years> years, ]<days> days, ], <months> months, ]<hours> hours, <minutes> minutes
*/
DateDifference.prototype.toString = function() {
    return this.getString(false);
};

/**
* @param {Boolean|} withSeconds If true, seconds will be included
* @returns {String} [[[<years> years, ]<days> days, ], <months> months, ]<hours> hours, <minutes> minutes[, <seconds> seconds]
*/
DateDifference.prototype.getString = function(withSeconds) {
    function part(val, base) {
        return val + " " + base + (!val || Math.abs(val) !== 1 ? 's' : '');
    }
    
    var suffix = withSeconds && this.seconds != 0 ? ", " + part(this.minutes, "Minute") + ", and " + part(this.seconds, "Second") : ", and " + part(this.minutes, "Minute");
    
    if (this.years != 0) {
        return part(this.years, "Year") + ", " + part(this.months, "Month") + ", " + part(this.days, "Day") + ", " + part(this.hours, "Hour") + suffix;
    } else if (this.months != 0) {
        return part(this.months, "Month") + ", " + part(this.days, "Day") + ", " + part(this.hours, "Hour") + suffix;
    } else if (this.days != 0) {
        return part(this.days, "Day") + ", " + part(this.hours, "Hour") + suffix;
    } else {
        return part(this.hours, "Hour") + suffix;
    }
};

// freeze DateDifference if possible
if (Object.freeze) {
    Object.freeze(DateDifference);
}
