USE master; --ϵͳ���ݿ�,�û��Խ������ݿ���Ҫ��ϵͳ���ݿ��ϵǼ��Ա���̨����
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
GO  --�ȴ�֮ǰ�Ĳ������

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
	account_number nvarchar(50),	--�˺�
	branch_name nvarchar(50)  NOT NULL,	--����֧��
	balance NUMERIC(12,2) CHECK (balance >= 0),	--�˻���� ����Ϊ10��С��Ϊ2λ
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

IF OBJECT_ID('PTransfer','P') IS NOT NULL   --P����洢����
	DROP PROCEDURE PTransfer;
GO


CREATE PROCEDURE PTransfer
		@account_no_x nvarchar(30), --x�˺�
		@account_no_y nvarchar(30), --y�˺�
		@amount_k NUMERIC (8,2)    --ת�˽��
AS
	BEGIN
	set nocount on
		--����1��ע�͵��¾����Ĵ洢���̣��ֱ��ÿռ�¼(�����ڵ��˺Ų���ת��)��balanceΪ��ֵ�ļ�¼����ת�˲��ԣ��ᷢ��ʲô���⣿
		--1.�ռ�¼ת�˲���:��ʾת�˳ɹ�,���ǲ�ѯ���˺���ʾ������,��ѯy�˺�,���������,ת�˳ɹ�
		--2.balanceΪ��ֵת�˲���:����ǰ����balance>=0��Լ��,xת�˺�Ϊ��,���򱨴�,����ͨ��
		
		--����2��ת�˹����е������˻����ܳ��ֿռ�¼�����Ϊ��ֵ��Ϊʲô��
		--�ռ�¼ת�˻�ƾ��������һ���˺ŵ����,�������߼�,���Ϊ��ֵת�˲�����ת�˽����ڵ���0��Լ������
		IF (SELECT balance
		 FROM account
		 WHERE account_number=@account_no_x) IS NULL
		 OR
		 (SELECT balance
		 FROM account
		 WHERE account_number=@account_no_y) IS NULL
		 RETURN -1;
		 
		 DECLARE @ErrorVar INT; --�����û�����,���ڴ洢SQL���Ĵ�����
		 BEGIN TRANSACTION;
		 --(1)******y�˺��ϼ�kԪ*******
		 UPDATE account
		 SET balance = balance + @amount_k
		 WHERE account_number = @account_no_y;
		 --�ж���һSQL����ִ��״̬
		 SELECT @ErrorVar = @@ERROR;   --@@ERRORΪ��һ��SQL����ִ��״̬
		 IF @ErrorVar != 0  --Ϊ0��ʾ����ִ����ȷ,��0ֵ��ʾϵͳ����Ĵ�����
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END
			
		 --(2)******x�˺��ϼ�kԪ*******
		 UPDATE account
		 SET balance = balance - @amount_k
		 WHERE account_number = @account_no_x;
		 --�ж���һSQL����ִ��״̬
		 SELECT @ErrorVar = @@ERROR;   --@@ERRORΪ��һ��SQL����ִ��״̬
		 IF @ErrorVar != 0  --Ϊ0��ʾ����ִ����ȷ,��0ֵ��ʾϵͳ����Ĵ�����
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END
		 --(3)*********�ύ����*********
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




IF OBJECT_ID('SPayment','P') IS NOT NULL   --P����洢����
	DROP PROCEDURE SPayment;
GO

CREATE PROCEDURE SPayment
		@account_no_x varchar(30), --x�˺�
		@loan_no_y varchar(30), --y�˺�
		@amount_k NUMERIC (12,2) --ת�˽��
		
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
			DECLARE @ErrorVar INT; --�����û�����,���ڴ洢SQL���Ĵ�����
		    BEGIN TRANSACTION;
		    --(1)******Ϊloan_number����kԪ,��¼payment_datetime�������˺ţ��������˺�*******
            INSERT INTO payment VALUES (@loan_no_y,@account_no_x,CONVERT(varchar(100), GETDATE(), 20),@amount_k)
		    --�ж���һSQL����ִ��״̬
		    SELECT @ErrorVar = @@ERROR;   --@@ERRORΪ��һ��SQL����ִ��״̬
		    IF @ErrorVar != 0  --Ϊ0��ʾ����ִ����ȷ,��0ֵ��ʾϵͳ����Ĵ�����
			BEGIN
				ROLLBACK;
				RETURN @ErrorVar;
			END
			
		    --(2)******x�˺��ϼ�kԪ*******
		    UPDATE account
		    SET balance = balance - @amount_k
		    WHERE account_number = @account_no_x;
		    --�ж���һSQL����ִ��״̬
		    SELECT @ErrorVar = @@ERROR;   --@@ERRORΪ��һ��SQL����ִ��״̬
		    IF @ErrorVar != 0  --Ϊ0��ʾ����ִ����ȷ,��0ֵ��ʾϵͳ����Ĵ�����
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
					SELECT '�����ˡ�'
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
					SELECT '�������ˡ�'
			   END	
			    
			--(3)*********�ύ����*********
			COMMIT;
			RETURN 0;
	END
GO		


SELECT *
FROM payment