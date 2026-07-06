USE master;
GO

IF DB_ID(N'SampleDB') IS NULL
BEGIN
    CREATE DATABASE SampleDB;
END
GO

USE SampleDB;
GO

IF OBJECT_ID(N'dbo.Registration', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.Registration;
END
GO

CREATE TABLE dbo.Registration (
    UserName VARCHAR(15) NOT NULL PRIMARY KEY,
    [Password] VARCHAR(20) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    IsAdmin BIT NOT NULL
);
GO

INSERT INTO dbo.Registration
    (UserName, [Password], LastName, IsAdmin)
VALUES
    ('admin', 'admin123', N'Administrator', 1),
    ('U001', '123', N'Tom', 1),
    ('U002', '456', N'David', 0),
    ('U003', '789', N'John', 0),
    ('U004', '012', N'Mark', 1),
    ('U005', '134', N'Kate', 0);
GO

SELECT * FROM dbo.Registration;
GO
