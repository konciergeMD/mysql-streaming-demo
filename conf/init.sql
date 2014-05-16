-- Create database
drop database if exists stream_test;

create database stream_test;

create table stream_test.play_evolutions (
                        id int not null primary key,
                        hash varchar(255) not null,
                        applied_at timestamp not null,
                        apply_script longtext,
                        revert_script longtext,
                        state varchar(255),
                        last_problem text
                        );

-- Create application user
drop user stream_test_user@localhost;

create user stream_test_user@localhost IDENTIFIED BY 'stream_test_user';

grant all on stream_test.* to stream_test_user@localhost;

use stream_test;