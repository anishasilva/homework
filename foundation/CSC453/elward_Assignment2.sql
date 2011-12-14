/*
Raymond Elward
CSC 453 Section 901 
Assignment 2
April 11, 2011
*/

-- 1:
select fname, minit, lname
  from employee
  where dno = '5' and
    ssn in (
      select essn from works_on where hours > 15 and
        pno = (select pnumber from project where pname = 'ProductX'))

  order by lname, fname, minit;
  
  
-- 2:
select fname, minit, lname
  from employee right outer join dependent on employee.ssn = dependent.essn
  where relationship = 'Son' and employee.fname = dependent.dependent_name
  order by lname, fname, minit;
  
  
--3:
select fname, minit, lname
  from employee
  where super_ssn =(select ssn
    from employee
    where fname = 'Franklin' and minit = 'T' and lname = 'Wong')
  order by lname, fname, minit;
  
  
--4:
select pname, pno, sum(hours)
  from project, works_on
  where project.pnumber = works_on.pno
  group by pname, pno
  order by pno;
  
--5:
select fname, minit, lname
  from employee
  where ssn in (select essn
      from (project join works_on on pno = pnumber)
      group by essn
      having count(pno) >= (select count(pnumber) from project))
  order by lname, fname, minit;
  
  --6:
  select fname, lname, minit
  from employee
  where not exists(select *
              from works_on
              where ssn = essn)
  order by lname, fname, minit;
  
  --7:
  select dname, avg(salary)
  from department, employee
  where dno = dnumber
  group by dname;
  
  
  --8:
  select avg(salary)
  from employee
  where sex = 'F';

  --9:
  select fname, minit, lname, address
  from employee
  where ssn in (select distinct essn
        from project join works_on on pnumber = pno
        where plocation = 'Houston')
      and dno not in (select department.dnumber
        from department join dept_locations on department.dnumber = dept_locations.dnumber
        where dlocation = 'Houston')
  order by lname, fname, minit;
  
  
  --10:
  
  select lname
  from employee
  where exists (select * from department where mgr_ssn = ssn)
    and not exists (select * from dependent where essn = ssn)
    
  order by lname;
    
  --11:
  
  select dname, count(*) 
  from employee, department
  where dnumber = dno
    
  group by dname, dno
  having avg(salary) > 40000;
  
  
  --12:
  
  select fname, minit, lname
  from employee
  where dno = (select dno
    from employee
    where salary <= all (select salary from employee))
  order by lname, fname, minit;
  
  --13:
  
  select fname, minit, lname
  from employee
  where super_ssn in (select ssn from employee where super_ssn = '888665555')
  order by lname, fname, minit;
  
  
  --14:
  select fname, minit, lname
  from employee
  where salary >= ((select max(salary)
          from employee) - 20000)
  order by lname, fname, minit;
  
  
  
  
  
  
  
  