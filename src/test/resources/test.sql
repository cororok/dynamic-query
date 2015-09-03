parameter: insert into test_user(user_name, email_address)
values( $user_name, $email_address);

autoParameter: where amt = $$, qty = 2 * $$;

main: select id, $name @sub1{, zip, $city }, email
from tb where id=$$ @sub2{ and email =$$ } and zip is not null;