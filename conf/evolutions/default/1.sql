# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dumb_model (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  constraint pk_dumb_model primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table dumb_model;

SET FOREIGN_KEY_CHECKS=1;

