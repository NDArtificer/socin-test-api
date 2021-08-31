create table user(

	id bigint not null auto_increment,
	name varchar(200) not null,
	email varchar(255) not null,
	password varchar (40) not null,
	
	primary key (id)
)