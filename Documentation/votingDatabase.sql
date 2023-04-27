Create Database voting;

Create Table record_vote(
    username varchar(255),
    candidate varchar(255)
);

Create Table user_accounts(
    username varchar(255),
    password varchar(255)
);