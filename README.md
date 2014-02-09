dynamic-query
========


## What is it?
It is a **java database framework** located between low level JDBC and high level ORM. 
* **Low level JDBC** - You can achieve the best performace but it's annoying.
* **ORM** - It's convenient but hard for complex joins and some restrictions.


## features 
**1. plain SQL.**
It doesn't use XML or other formats containg redundant tags. But it uses the native SQL with minimal syntax.
It doesn't even use " or '. See below an example file *sample.sql*. It looks like just a saved query with name *listUsers*.

```sql
/* It also supports comment.
This code is in an external file 'sample.sql', Not inisde java code.*/
listUsers : select * from user_table
where user_id= $$;  -- $$ will automatically catch a parameter userId
```


**2. expandable SQL.**
If there is a query, there can be additional queries derived from the query.
So, rather than coping & making similar queries or programmatically manipulating String in the runtime,
you can add only additional parts in the query in developing time and call them dynamically in the run time. see example below.


```sql
listUsers:
select
	id, amount, created
	@checkEmail{ ,email } 
from user_table
where amount > $amt and balance < $amt
	@checkDate { and created = $$ }
	@checkEmail{ and email in (
		select email from vip_list ) } ;		
/* Above query can be four queries like below.
1. listUsers
2. listUsers.checkDate 
3. listUsers.checkEmail
4. listUsers.checkDate.checkEmail 
*/


-- It can include other files like below
& ../hr/additional hr.sql ; 
& ../fi/additional fi.sql ;
```


**3. save effort to put/get data.**
If you have a query, the remaining is **setting** parameters to the query and **getting** the result
from the query. You can call native jdbc API directly or you can simply **get/set** them with a Bean,
Array or Map. see below example. tried to minimize the performance loss and memory usage.

**setting example**
```java
QueryUtil qu = qm.createQueryUtil("selectAll");
try {
	qu.setConnection(conn);

	// with native jdbc
	qu.setString("alpha");
	qu.setDouble(10.1);
	qu.executeQuery();
	
	// or with bean
	qu.executeQuery(new User("alpha", 10.1));
	
	// or with map
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("userName", "alpha");
	map.put("amt", 10.1);
	qu.executeQuery(map);
	
	// or with array
	qu.executeQueryParameters("alpha", 10.1);

```





**getting example**

```java

	while (qu.next()) // == qu.rs.next()
	{
		// native jdbc
		String usreName = qu.getString("user_name"); 
		double amt = qu.getDouble("amt");
	
		// or bean
		User user = new User();
		qu.updateBean(user);
	
		// or array
		Object[] values = qu.populateArray();
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	qu.closeJust();
}

````


## Dependency
It uses a log library **SLF4J**. download library from below 
	http://www.slf4j.org


## Usage
see demo firt **./demo/examples/Sample.java**

1. Copy and put a jar file **./dist/cororok-dq-1.0.jar** to your project
2. write your **sql files**. It can be located anywhere
3. (Optional file) **dq.properties**

If you want to use cororok.dq.util.QueryMapHelper to manage QueryMap, 
you will need **dq.properties** in the root path of your application. see below example.

* demo/dq.properties
* demo/examples/HelperTest.java


	
