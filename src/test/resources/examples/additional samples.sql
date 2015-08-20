selectAll :
select u.* from test_user u
@checkUserName { where 1=1 }
@@checkUserName ;

deleteAll: delete from test_user
@checkUserName { where 1=1 }
@@checkUserName ;
