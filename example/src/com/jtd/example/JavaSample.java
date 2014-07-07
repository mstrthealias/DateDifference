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

package com.jtd.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.jtd.DateDifference;

public class JavaSample {
    public static final void main(String args[]) throws ParseException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	    
	    DateDifference diff = new DateDifference(dateFormat.parse("2010-01-01T01:01:00+0000"), dateFormat.parse("2011-02-02T02:02:01+0000"));
	    
	    System.out.println(diff.getString(false));
	    System.out.println(diff.getString(true));  // Prevent suppressing seconds from output
	    System.out.println(diff);  // Invokes the toString() method which is equivalent to using the getString(false) method
	}
}
