create database second_hand;

use second_hand;

create table postings (
	
    posting_id varchar(128) not null,
    posting_date varchar(128) not null,
    name varchar(128) not null,
    email varchar(128) not null,
    phone varchar(128) default '',
    title varchar(256),
    description text,
    image varchar(128),
    
    primary key(posting_id)
);