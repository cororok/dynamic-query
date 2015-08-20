list: select 11111		

/*
 Above query missed ';' at the end. 
 so the parser thinks there is no list2 below
 but only one id 'list' containing below llist2.
 see warning.
 */

list2:select 2222; -- it will be included in list because list missed ;
