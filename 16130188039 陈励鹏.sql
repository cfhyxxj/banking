USE master; --系统数据库,用户自建的数据库需要在系统数据库上登记以备后台管理
GO

CREATE DATABASE banking
ON
PRIMARY  
    (NAME = banking,
    FILENAME = 'E:\bankingData\bankingdat1.mdf',
    SIZE = 100MB,
    MAXSIZE = 200,
    FILEGROWTH = 20)
    
LOG ON 
   (NAME = banklog1,
    FILENAME = 'E:\bankingData\bankinglog1.ldf',
    SIZE = 100MB,
    MAXSIZE = 200,
    FILEGROWTH = 20)
  ;
GO  --等待之前的操作完成

USE banking;
GO
CREATE TABLE info
(
		  
		account_number nvarchar(50),
		pwd nvarchar(50),
		PRIMARY KEY(account_number)
)
INSERT INTO info VALUES ('5','1')
INSERT INTO info VALUES ('6','1')
INSERT INTO info VALUES ('7','2')



SELECT *
FROM info

CREATE TABLE account(
	account_number nvarchar(50),	--账号
	branch_name nvarchar(50)  NOT NULL,	--开户支行
	balance NUMERIC(12,2) CHECK (balance >= 0),	--账户余额 长度为10，小数为2位
	PRIMARY KEY (account_number)
);
GO

INSERT INTO account VALUES ('5','Downtown','505');
INSERT INTO account VALUES ('6','Downtown','505');
INSERT INTO account VALUES ('7','Perryridge','400');
INSERT INTO account VALUES ('A-201','Brighton','900');
INSERT INTO account VALUES ('A-215','Mianus','700');
INSERT INTO account VALUES ('A-217','Brighton','750');
INSERT INTO account VALUES ('A-222','Redwood','700');
INSERT INTO account VALUES ('A-305','Round','350');
INSERT INTO account VALUES ('A-213','Perryridge','250');
INSERT INTO account VALUES ('A-214','Perryridge',NULL);
GO

SELECT *
FROM account;

IF OBJECT_ID('PTransfer','P') IS NOT NULL   --P代表存储过程
	DROP PROCEDURE PTransfer;
GO


CREATE PROCEDURE PTransfer
		@account_no_x nvarchar(30), --x账号
		@account_no_y nvarchar(30), --y账号
		@amount_k NUMERIC (8,2)    --转账金额
AS
	BEGIN
	set nocount on
		--问题1：注释掉下句代码的存储过程，分别用空记录(不存在的账号参与转账)和balance为空值的记录进行转账测试，会发现什么问题？
		--1.空记录转账测试:显示转账成功,但是查询空账号显示不存在,查询y账号,余额增加了,转账成功
		--2.balance为空值转账测试:由于前面有balance>=0的约束,x转账后为负,程序报错,不能通过
		
		--问题2：转账过程中的两个账户不能出现空记录或余额为空值。为什么？
		--空记录转账会凭空增加另一个账号的余额,不符合逻辑,余额为空值转账不满足转账金额大于等于0的约束条件
		IF (SELECT balance
		 FROM account
		 WHERE account_number=@account_no_x) IS NULL
		 OR
		 (SELECT balance
		 FROM account
		 WHERE account_number=@account_no_y) IS NULL
		 RETURN -1;
		 
		 DECLARE @ErrorVar INT; --声明用户变量,用于存储SQL语句的错误编号
		 BEGIN TRANSACTION;
		 --(1)******y账号上加k元*******
		 UPDATE account
		 SET balance = balance + @amount_k
		 WHERE account_number = @account_no_y;
		 --判定上一SQL语句的执行状态
		 SELECT @ErrorVar = @@ERROR;   --@@ERROR为上一条SQL语句的执行状态
		 IF @ErrorVar != 0  --为0表示代码执行正确,非0值表示系统定义的错误编号
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END
			
		 --(2)******x账号上减k元*******
		 UPDATE account
		 SET balance = balance - @amount_k
		 WHERE account_number = @account_no_x;
		 --判定上一SQL语句的执行状态
		 SELECT @ErrorVar = @@ERROR;   --@@ERROR为上一条SQL语句的执行状态
		 IF @ErrorVar != 0  --为0表示代码执行正确,非0值表示系统定义的错误编号
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END
		 --(3)*********提交事务*********
			COMMIT;
			RETURN 0;
	END
GO

CREATE TABLE loan(
	loan_number CHAR(30),
	branch_name CHAR(30),
	amount int,
	PRIMARY KEY (loan_number)
);
GO	
ALTER TABLE loan ADD pay_over_date DATE;

SELECT *
FROM loan	



CREATE TABLE payment(
	loan_number varchar(30),
	payment_number varchar(30),
	payment_datetime smalldatetime,
	payment_amount NUMERIC(12,2) CHECK (payment_amount >= 0),
	PRIMARY KEY (loan_number,payment_number)
);
GO




IF OBJECT_ID('SPayment','P') IS NOT NULL   --P代表存储过程
	DROP PROCEDURE SPayment;
GO

CREATE PROCEDURE SPayment
		@account_no_x varchar(30), --x账号
		@loan_no_y varchar(30), --y账号
		@amount_k NUMERIC (12,2) --转账金额
		
AS
	BEGIN
		IF (SELECT balance
			FROM account
			WHERE account.account_number=@account_no_x) IS NULL
			OR
			(SELECT amount
			 FROM loan
			 WHERE loan.loan_number=@loan_no_y) IS NULL
			RETURN -1;
			DECLARE @ErrorVar INT; --声明用户变量,用于存储SQL语句的错误编号
		    BEGIN TRANSACTION;
		    --(1)******为loan_number还款k元,记录payment_datetime，还款账号，被还款账号*******
            INSERT INTO payment VALUES (@loan_no_y,@account_no_x,CONVERT(varchar(100), GETDATE(), 20),@amount_k)
		    --判定上一SQL语句的执行状态
		    SELECT @ErrorVar = @@ERROR;   --@@ERROR为上一条SQL语句的执行状态
		    IF @ErrorVar != 0  --为0表示代码执行正确,非0值表示系统定义的错误编号
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END
			
		    --(2)******x账号上减k元*******
		    UPDATE account
		    SET balance = balance - @amount_k
		    WHERE account_number = @account_no_x;
		    --判定上一SQL语句的执行状态
		    SELECT @ErrorVar = @@ERROR;   --@@ERROR为上一条SQL语句的执行状态
		    IF @ErrorVar != 0  --为0表示代码执行正确,非0值表示系统定义的错误编号
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END	
			IF(SELECT loan.amount
			FROM loan,payment
			WHERE loan.loan_number=payment.loan_number AND payment.loan_number=@loan_no_y)
			=(SELECT sum(payment.payment_amount)
			   FROM payment,loan
			   WHERE loan.loan_number=payment.loan_number AND payment.loan_number=@loan_no_y)
			   BEGIN
					SELECT '还完了。'
					UPDATE loan
					SET loan.pay_over_date=CONVERT(varchar(100), GETDATE(), 20)
			   END
			 IF(SELECT loan.amount
			 FROM loan,payment
			 WHERE loan.loan_number=payment.loan_number AND payment.loan_number=@loan_no_y)
			 <(SELECT sum(payment.payment_amount)
			   FROM payment,loan
			   WHERE loan.loan_number=payment.loan_number AND payment.loan_number=@loan_no_y)
			   BEGIN
					SELECT '您还多了。'
			   END	
			    
			--(3)*********提交事务*********
			COMMIT;
			RETURN 0;
	END
GO		


SELECT *
FROM payment