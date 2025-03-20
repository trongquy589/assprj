CREATE DATABASE assprj;
go
use assprj;
go

CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1), -- AUTO_INCREMENT in SQL Server
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) CHECK (Role IN ('Citizen', 'AreaLeader', 'Police', 'Admin')) NOT NULL, -- ENUM equivalent in SQL Server
    PhoneNumber VARCHAR(20),
    DateOfBirth DATE,
    Gender VARCHAR(10) CHECK (Gender IN ('Male', 'Female', 'Other')),
    Address TEXT,
    IdentityNumber VARCHAR(20) UNIQUE,
    PlaceOfIssue VARCHAR(100),
    DateOfIssue DATE,
    Nationality VARCHAR(50),
    Ethnic VARCHAR(50),
    Religion VARCHAR(50),
    Occupation VARCHAR(100),
	IsActive VARCHAR(5) DEFAULT 'true' CHECK (IsActive IN ('true', 'false')),
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE()
);
go

CREATE TABLE Households (
    HouseholdID INT PRIMARY KEY IDENTITY(1,1),
    HeadOfHouseholdID INT,
    Address TEXT NOT NULL,
    CreatedDate DATE DEFAULT GETDATE(),
    Ward VARCHAR(100),
    District VARCHAR(100),
    Province VARCHAR(100),
	IsActive VARCHAR(5) DEFAULT 'true' CHECK (IsActive IN ('true', 'false')),
    FOREIGN KEY (HeadOfHouseholdID) REFERENCES Users(UserID)
);
go

CREATE TABLE Registrations (
    RegistrationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT,
    RegistrationType VARCHAR(20) CHECK (RegistrationType IN ('Permanent', 'Temporary', 'TemporaryStay')) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE,
    Reason TEXT,
    Status VARCHAR(20) CHECK (Status IN ('Pending', 'Approved', 'Rejected')) DEFAULT 'Pending',
    ApprovedBy INT,
    ApprovalDate DATETIME,
    Comments TEXT,
    HouseholdID INT,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ApprovedBy) REFERENCES Users(UserID),
    FOREIGN KEY (HouseholdID) REFERENCES Households(HouseholdID)
);
go

CREATE TABLE HouseholdMembers (
    MemberID INT PRIMARY KEY IDENTITY(1,1),
    HouseholdID INT,
    UserID INT,
    Relationship VARCHAR(50) NOT NULL,
	IsActive VARCHAR(5) DEFAULT 'true' CHECK (IsActive IN ('true', 'false')),
    FOREIGN KEY (HouseholdID) REFERENCES Households(HouseholdID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
go

CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT,
    Message TEXT NOT NULL,
    SentDate DATETIME DEFAULT GETDATE(),
    IsRead BIT DEFAULT 0, -- BOOLEAN equivalent in SQL Server
    Type VARCHAR(20) CHECK (Type IN ('Registration', 'Household', 'Other')),
    ReferenceID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
go

CREATE TABLE Logs (
    LogID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT,
    Action VARCHAR(100) NOT NULL,
    Timestamp DATETIME DEFAULT GETDATE(),
    Details TEXT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
go

CREATE TABLE Attachments (
    AttachmentID INT PRIMARY KEY IDENTITY(1,1),
    RegistrationID INT,
    FileName VARCHAR(255) NOT NULL,
    FilePath VARCHAR(255) NOT NULL,
    FileType VARCHAR(50),
    FileSize INT,
    UploadDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (RegistrationID) REFERENCES Registrations(RegistrationID)
);
go