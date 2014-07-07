<?php
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

$d1 = new DateTime("2010-01-01T01:01:00.000Z");
$d2 = new DateTime("2011-02-02T02:02:00.000Z");

$diff = $d1->diff($d2);
echo $diff->format('%y Year(s), %m Month(s), %d Day(s), %h Hour(s), and %i Minute(s)');

?>