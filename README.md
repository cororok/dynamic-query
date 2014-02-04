dynamic-query
========


## What is it?
It is a something between low level JDBC and high level ORM.
Basically you know your SQLs containing complex joins but it does not.
So you will write SQLs in external files and it will load them and you will call them by id.


## features 
1. plain SQL
It dose not use XML or other format to save SQLs but native SQL with minimal syntax.
It does not even use " or '. It is a pretty much native SQL without redundant tags, " and '. 
See below example. 
listUsers : select * from user_table 
where id= $$;

2. expandable SQL.
If there is a query, there can be additional queries derived from the query.
So, rather than coping & making similar queries or programmatically manipulating String in runtime,
you can add only additional parts in the query in developing time and call them dynamically.

3. save effort to put/get data
If you have a query, the remaining is setting parameters to the query and getting the result
from the query. You can call native jdbc API directly or you can simply get/set them with a Bean,
Array or Map. tried to minimize the performance loss and memory usage.


## Dependency
It uses a log library. download library from below 
	http://www.slf4j.org


## Usage
1. Use a jar file in your project - dist/cororok-dq-1.0.jar.
2. you sql files
3. (Optional ) demo/dq.properties
If you want to use cororok.dq.util.QueryMapHelper to manage QueryMap, 
you will need dq.properties in the root path of your application. see below files
	demo/dq.properties 
	demo/examples/HelperTest.java 

	
