DateDifference
==============

Purpose
-------
Provides a date difference string consistent between Java and JavaScript.  The output is also consistent with PHP when the time zone is handled properly.

This was created in order to create a consistent date difference string between an Android notification and a hybrid mobile web application.

### Output
2010-02-02T02:02:00.000Z - 2010-01-01T01:01:00.000Z: `1 Month, 1 Day, 1 Hour, and 1 Minute`

2012-02-02T02:02:00.000Z - 2010-01-01T01:01:00.000Z: `2 Years, 1 Month, 1 Day, 1 Hour, and 1 Minute`

Usage
-----
Constructor behavior:
`new DateDifference(date1[, date2 = NOW]);`

- The difference is calculated as: `{date2} - {date1}`
- If the second argument is not specified, the current date/time will be used (ie. `Now - {date1}`)

*See the example folder for sources of the following examples.*

> In order for the output to be consistent between platforms, make sure the time zone is being handled appropriately.

### JavaScript
    <script type="text/javascript" src="DateDifference.js"></script>`
    
    <script>
    var diff = new DateDifference(new Date("2010-01-01T01:01:00.000Z"), new Date("2011-02-02T02:02:01.000Z"));
    
    alert(diff.getString());
    alert(diff.getString(true));  // Prevent suppressing seconds from output
    alert(diff);  // Invokes the toString() prototype which is equivalent to using the getString() method
    </script>

Alerts the following:

    1 Year, 1 Month, 1 Day, 1 Hour, and 1 Minute
    1 Year, 1 Month, 1 Day, 1 Hour, 1 Minute, and 1 Second
    1 Year, 1 Month, 1 Day, 1 Hour, and 1 Minute


### Java
    import com.jtd.DateDifference;
    
    ...
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    DateDifference diff = new DateDifference(dateFormat.parse("2010-01-01T01:01:00+0000"), dateFormat.parse("2011-02-02T02:02:01+0000"));
    
    System.out.println(diff.getString(false));
    System.out.println(diff.getString(true));  // Prevent suppressing seconds from output
    System.out.println(diff);  // Invokes the toString() method which is equivalent to using the getString(false) method

Writes the following:

    1 Year, 1 Month, 1 Day, 1 Hour, and 1 Minute
    1 Year, 1 Month, 1 Day, 1 Hour, 1 Minute, and 1 Second
    1 Year, 1 Month, 1 Day, 1 Hour, and 1 Minute


### Similar output using PHP
    <?php
    $d1 = new DateTime("2010-01-01T01:01:00.000Z");
    $d2 = new DateTime("2011-02-02T02:02:00.000Z");
    
    $diff = $d1->diff($d2);
    echo $diff->format('%y Year(s), %m Month(s), %d Day(s), %h Hour(s), and %i Minute(s)');
    ?>

Outputs the following:

    1 Year(s), 1 Month(s), 1 Day(s), 1 Hour(s), and 1 Minute(s)
