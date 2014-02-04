/*
-- test ddl for mysql
CREATE TABLE test_user (
	id INT NOT NULL AUTO_INCREMENT,
	user_name	varchar(50),
	email_address	varchar(100),
	amount	numeric(10,3),
	created TIMESTAMP,
	memo varchar(200),
	PRIMARY KEY (id)
)

-- for oracle, remove auto increment and primary key
CREATE TABLE test_user (
	id INT ,
	user_name	varchar(50),
	email_address	varchar(100),
	amount	numeric(10,3),
	created TIMESTAMP,
	memo varchar(200)
)

 */

basicSelect : -- mainId without space without ' or ", then use ':' to write sql
select count(1) count from test_user; -- should end with ';'. 
-- It does not pass ';' to db but uses to determin sql ends.


insertUser : insert into test_user(user_name, email_address, amount, memo, created )
values(
	$user_name, $email_address, $amount 
-- $ is used without space, without ' or " to represent parameter.
-- the name is converted to java name. for example, user_name -> useName, email_address -> emailAddress 
	,'--$ @a { } /* ..*/',  $created
	); -- It ignores its syntax inside string '... ' 

getAmount:
select amount from test_user
where user_name= $$; -- or you can give parameter name like $userName
/*
 if $$ is used it will find column name before '=' and uses it as the name
 */


updateAmount: 
update test_user set 
	@normal{amount=$$ }
	@add{amount= (amount + $$ ) }
where user_name=$$ 
 	and created is not null
@@checkEmail -- sharedID, It uses body of the mainID
; -- don't forget ;
/*
@ is used to represent subQuery. then follows id of subQuery. 
 extQuery starts with { and ends with } without ;. ; ends mainQuery.
 
@@ uses mainQuery as extQuery. it does not have body(== {.... } ) but only mainId. 
 */




checkEmail: -- it is main query but will be uses as an extQuery
	and email_address=$$;  

checkUserName:
	and user_name=$userName -- you can use any name for parameter 
-- but $$ or manaul same name $amount is better
; -- don't forget it


& additional samples.sql ;  -- don't forget ;. don't use ' or  "
/* external file starts with & and ends with ;
  external file is a relative path fro this file. 
  look at jdk "new File(String parent(=this file), String child(==externail file)) "
 */

& ../path.sql; -- another external file


otherId: where select table_x from ; -- it does not check sql syntax


userId2 : -- does not allow the same id.
select user_name /* this is block comment but
it will pass thise block comment to db unlike line comment --. 
because oracle uses /*.. */ as a hint 
*/,amt, @detail{ memo } 
from  test_user -- this comment is ignored when it parse this file. and of couse not pass to db.
@detail{ where memo is not null }
;