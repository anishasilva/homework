-- Base Table Schema for the Star Schema Benchmark
-- CSC-553 Project, DB2

drop table lineorder
drop table part
drop table supplier
drop table dwdate
drop table customer

create table part (   p_partkey     integer     not null,   p_name        varchar(22) not null,   p_mfgr        varchar(6)     not null,   p_category    varchar(7)     not null,   p_brand1      varchar(9)     not null,   p_color       varchar(11) not null,   p_type        varchar(25) not null,   p_size        integer     not null,   p_container   varchar(10)    not null )
create table supplier (   s_suppkey     integer     not null,    s_name        varchar(25)    not null,   s_address     varchar(25) not null,   s_city        varchar(10)    not null,   s_nation      varchar(15)    not null,   s_region      varchar(12)    not null,   s_phone       varchar(15)    not null)
create table customer (   c_custkey     integer     not null,   c_name        varchar(25) not null,   c_address     varchar(25) not null,   c_city        varchar(10)    not null,   c_nation      varchar(15)    not null,   c_region      varchar(12)    not null,   c_phone       varchar(15)    not null,   c_mktsegment  varchar(10)    not null )
create table dwdate (   d_datekey            integer     not null,    d_date               varchar(19)    not null,   d_dayofweek          varchar(10)    not null,   d_month              varchar(10)    not null,   d_year               integer     not null,   d_yearmonthnum       integer	   not null,   d_yearmonth          varchar(8)	   not null,   d_daynuminweek       integer	   not null,   d_daynuminmonth      integer     not null,   d_daynuminyear       integer     not null,   d_monthnuminyear     integer     not null,   d_weeknuminyear      integer     not null,   d_sellingseason      varchar(13)    not null,   d_lastdayinweekfl    varchar(1)     not null,   d_lastdayinmonthfl   varchar(1)     not null,   d_holidayfl          varchar(1)     not null,   d_weekdayfl          varchar(1)     not null )
create table lineorder (   lo_orderkey          integer     not null,   lo_linenumber        integer     not null,   lo_custkey           integer     not null,   lo_partkey           integer     not null,  lo_suppkey           integer     not null,  lo_orderdate         integer     not null,  lo_orderpriority     varchar(15)    not null,  lo_shippriority      varchar(1)     not null,  lo_quantity          integer     not null,  lo_extendedprice     integer     not null,  lo_ordertotalprice   integer     not null,  lo_discount          integer     not null,  lo_revenue           integer     not null,  lo_supplycost        integer     not null,  lo_tax               integer     not null,  lo_commitdate         integer     not null,  lo_shipmode          varchar(10)    not null)

alter table part   add primary key (p_partkey)
alter table supplier   add primary key (s_suppkey)
alter table customer   add primary key (c_custkey)
alter table dwdate   add primary key (d_datekey)

alter table lineorder   add primary key (lo_orderkey, lo_linenumber)
alter table lineorder  add constraint custconstr foreign key (lo_custkey) references customer(c_custkey)
alter table lineorder   add constraint partconstr foreign key (lo_partkey) references part(p_partkey)
alter table lineorder   add constraint suppconstr foreign key (lo_suppkey) references supplier(s_suppkey)
alter table lineorder   add constraint dateconstr foreign key (lo_orderdate) references dwdate(d_datekey)
