create table member(
	member_id varchar2(15),
	member_pw varchar2(20),
	member_name varchar2(15),
	member_gender varchar2(6),
	member_email varchar2(30),
	PRIMARY KEY(member_id)
);

create table board(
	board_id varchar2(15), 
	board_num number,
	board_title varchar2(30),
	board_content varchar2(2000),
	board_like number,
	board_comment varchar2(200),
	board_date date,
	board_image varchar2(20),
	PRIMARY KEY(board_num)
);

create table comment1(
	comment1_bnum number,
	comment1_num number,
	comment1_id varchar2(15),
	comment1_date date,
	comment1_content varchar2(1000)
);

create table suggest(
	suggest_num number,
	suggest_nickname varchar2(30),
	suggest_content varchar2(500),
	suggest_date date
);
create table likecheck(
like_num number,
board_num number,
member_id varchar2(15),
primary key (like_num)
);


select * from member;
select * from board;
select * from comment1;
select * from suggest;


alter table board add constraint pk_board_id foreign key(board_id)
references member(member_id) ON DELETE CASCADE;


alter table comment1 add constraint pk_comment1_bnum foreign key(comment1_bnum)
references board(board_num) ON DELETE CASCADE;

alter table comment1 add constraint pk_comment1_id foreign key(comment1_id)
references member(member_id) ON DELETE CASCADE;

alter table likecheck add constraint like_count foreign key(board_num)
references board(board_num) ON DELETE CASCADE;

alter table likecheck add constraint like_user foreign key(member_id)
references member(member_id) ON DELETE CASCADE;

alter table board drop constraint pk_board_id;

drop table suggest purge;
drop table likecheck purge;
drop table comment1 purge;
drop table board purge;
drop table member purge;